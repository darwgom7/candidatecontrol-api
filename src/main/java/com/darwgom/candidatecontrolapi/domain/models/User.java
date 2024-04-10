package com.darwgom.candidatecontrolapi.domain.models;

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
public class User {

    private Long id;
    private String username;
    private String password;
    private RoleEnum role;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;

}

