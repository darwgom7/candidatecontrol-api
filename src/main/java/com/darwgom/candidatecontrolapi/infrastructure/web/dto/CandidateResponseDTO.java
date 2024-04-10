package com.darwgom.candidatecontrolapi.infrastructure.web.dto;

import com.darwgom.candidatecontrolapi.domain.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateResponseDTO {

    private Long id;
    private String name;
    private String email;
    private GenderEnum gender;
    private BigDecimal salaryExpected;
    private String phoneNumber;
    private String address;

}
