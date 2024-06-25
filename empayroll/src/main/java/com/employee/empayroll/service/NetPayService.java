package com.employee.empayroll.service;

import com.employee.empayroll.models.Employee;
import com.employee.empayroll.models.Grade;
import com.employee.empayroll.models.NetPay;
import com.employee.empayroll.repository.GradeRepository;
import com.employee.empayroll.repository.NetPayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NetPayService {

    public final NetPayRepository netPayRepository;
    public final GradeRepository gradeRepository;

    @Autowired
    public NetPayService(NetPayRepository netPayRepository, GradeRepository gradeRepository){
        this.netPayRepository = netPayRepository;
        this.gradeRepository = gradeRepository;
    }
    // Calculating hra
    public void calculateHra(Employee employee) {
        Grade grade = gradeRepository.findById(employee.getEmpGrade())
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        Double hra = employee.getBasicPay() * (grade.getHraPercentage() / 100.0);
        updateNetPayField(employee, "hra", hra);
    }

    // Calculating da
    public void calculateDa(Employee employee) {
        Grade grade = gradeRepository.findById(employee.getEmpGrade())
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        Double da = employee.getBasicPay() * (grade.getDaPercentage() / 100.0);
        updateNetPayField(employee, "da", da);
    }

    // Calculating ma
    public void calculateMa(Employee employee) {
        Grade grade = gradeRepository.findById(employee.getEmpGrade())
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        Double ma = employee.getBasicPay() * (grade.getMaPercentage() / 100.0);
        updateNetPayField(employee, "ma", ma);
    }

    // calculating hra,da,ma, PerDayPay and add them back to NetPay table
    public void calculatePerDayPay(Employee employee) {
        Optional<NetPay> existingNetPay = netPayRepository.findNetPayByEmpId(employee.getEmpId());
        if (existingNetPay.isPresent()) {
            NetPay netPay = existingNetPay.get();
            Double perDayPay = (employee.getBasicPay() + netPay.getHra() + netPay.getDa() + netPay.getMa()) / 24.0;
            updateNetPayField(employee, "perDayPay", perDayPay);
        } else {
            throw new RuntimeException("NetPay record not found for employee");
        }
    }

    //display all values in NetPay Table
    public List<NetPay> getAllNeyPay(){
        return netPayRepository.findAll();
    }

    public void createNetPayField(Employee employee){
        Grade grade = gradeRepository.findById(employee.getEmpGrade())
                .orElseThrow(() -> new RuntimeException("Grade not found"));
        Double hra = employee.getBasicPay() * (grade.getHraPercentage() / 100.0);
        Double da = employee.getBasicPay() * (grade.getDaPercentage() / 100.0);
        Double ma = employee.getBasicPay() * (grade.getMaPercentage() / 100.0);
        Double perDayPay = (employee.getBasicPay() + hra + da + ma ) /24.0;

        NetPay netPay = new NetPay();
        netPay.setEmpId(employee.getEmpId());
        netPay.setHra(hra);
        netPay.setDa(da);
        netPay.setMa(ma);
        netPay.setPerDayPay(perDayPay);

        netPayRepository.save(netPay);
    }

    private void updateNetPayField(Employee employee, String field, Double value) {
        Optional<NetPay> existingNetPay = netPayRepository.findNetPayByEmpId(employee.getEmpId());
        if (existingNetPay.isPresent()) {
            NetPay netPay = existingNetPay.get();
            switch (field) {
                case "hra":
                    netPay.setHra(value);
                    break;
                case "da":
                    netPay.setDa(value);
                    break;
                case "ma":
                    netPay.setMa(value);
                    break;
                case "perDayPay":
                    netPay.setPerDayPay(value);
                    break;
            }
            netPayRepository.save(netPay);
        }
    }

    //deleting an employee's Net Pay details when deleteEmployee is called
    public void deleteNetPay(String empId) {
        Optional<NetPay> netPayOptional = netPayRepository.findById(empId);
        if (netPayOptional.isEmpty()) {
            throw new IllegalStateException("NetPay record not found for employee with empId: " + empId);
        }
        netPayRepository.delete(netPayOptional.get());
    }

}
