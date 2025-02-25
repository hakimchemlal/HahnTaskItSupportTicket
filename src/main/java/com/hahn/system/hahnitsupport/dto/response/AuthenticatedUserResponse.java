package com.hahn.system.hahnitsupport.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticatedUserResponse {

    private String username;
    private String fullName;
    private List<String> authorities;
}
