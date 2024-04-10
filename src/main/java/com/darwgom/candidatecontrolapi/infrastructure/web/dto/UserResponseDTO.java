package com.darwgom.candidatecontrolapi.infrastructure.web.dto;

import com.darwgom.candidatecontrolapi.domain.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String username;
    private String password;
    private RoleEnum role;
    private LocalDateTime createdAt;

}
