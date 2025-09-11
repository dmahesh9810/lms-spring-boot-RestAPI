package com.iqbrave.iqbrave_lms.dto;

import com.iqbrave.iqbrave_lms.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;       // optional for POST requests, useful for responses
    private String email;
    private String name;
    private String password;
    private Role role;     // Assuming Role is an enum like ADMIN, INSTRUCTOR, STUDENT
}
