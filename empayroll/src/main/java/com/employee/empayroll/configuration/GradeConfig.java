package com.employee.empayroll.configuration;

import com.employee.empayroll.models.Grade;
import com.employee.empayroll.repository.GradeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GradeConfig {

    @Bean
    CommandLineRunner commandLineRunnerGrade(
            GradeRepository gradeRepository){
        return args -> {
            Grade l1 = new Grade(
                    1,
                    10,
                    10,
                    10
            );

            Grade l2 = new Grade(
                    2,
                    15,
                    15,
                    20
            );

            Grade l3 = new Grade(
                    3,
                    20,
                    20,
                    25
            );

            Grade l4 = new Grade(
                    4,
                    25,
                    25,
                    30
            );

            Grade l5 = new Grade(
                    5,
                    30,
                    30,
                    40
            );

            Grade l6 = new Grade(
                    6,
                    35,
                    35,
                    50
            );

            Grade l7 = new Grade(
                    7,
                    40,
                    40,
                    60
            );

            Grade l8 = new Grade(
                    8,
                    45,
                    45,
                    70
            );

            Grade l9 = new Grade(
                    9,
                    50,
                    50,
                    80
            );

            Grade l10 = new Grade(
                    10,
                    55,
                    55,
                    90
            );

            gradeRepository.saveAll(List.of(
                    l1,l2,l3,l4,l5,
                    l6,l7,l8,l9,l10));
        };
    }
}
