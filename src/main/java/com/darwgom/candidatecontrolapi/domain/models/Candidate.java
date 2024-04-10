package com.darwgom.candidatecontrolapi.domain.models;

import com.darwgom.candidatecontrolapi.domain.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    private Long id;
    private String name;
    private String email;
    private GenderEnum gender;
    private BigDecimal salaryExpected;
    private String phoneNumber;
    private String address;
    private LocalDateTime createdAt;

}
