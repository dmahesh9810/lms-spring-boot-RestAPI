package com.iqbrave.iqbrave_lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqbrave.iqbrave_lms.dto.UserDTO;
import com.iqbrave.iqbrave_lms.entity.Role;
import com.iqbrave.iqbrave_lms.entity.User;
import com.iqbrave.iqbrave_lms.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = UserController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = com.iqbrave.iqbrave_lms.security.JwtAuthenticationFilter.class)
)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Replace deprecated @MockBean with @MockitoBean
    @org.springframework.test.context.bean.override.mockito.MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(
                new User(1L, "a@test.com", "A", "pass", Role.INSTRUCTOR),
                new User(2L, "b@test.com", "B", "pass", Role.STUDENT)
        ));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());

        verify(userService, times(1)).getAllUsers();
    }
    @Test
    void testRegisterUser_invalidEmail() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("invalid"); // not a valid email
        userDTO.setName("Ishan");
        userDTO.setPassword("password123");
        userDTO.setRole(Role.INSTRUCTOR);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest()); // should fail validation
    }


    @Test
    void testGetUserById() throws Exception {
        User user = new User(1L, "instructor", "Ishan", "password123", Role.INSTRUCTOR);
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testUpdateUser() throws Exception {
        User updatedUser = new User(1L, "instructor@test.com", "Ishan Updated", "newpass123", Role.INSTRUCTOR);

        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateUser(eq(1L), any(User.class));
    }

    // DELETE
    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(1L);
    }
}
