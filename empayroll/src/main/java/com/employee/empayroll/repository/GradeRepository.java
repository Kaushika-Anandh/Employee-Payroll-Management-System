package com.employee.empayroll.repository;

import com.employee.empayroll.models.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepository
        extends JpaRepository<Grade, Integer> {
    Optional<Grade> findGradeByGradeId(Integer grade);
}
