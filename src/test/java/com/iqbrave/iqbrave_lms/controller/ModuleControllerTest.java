package com.iqbrave.iqbrave_lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqbrave.iqbrave_lms.dto.ModuleDTO;
import com.iqbrave.iqbrave_lms.entity.Course;
import com.iqbrave.iqbrave_lms.entity.Module;
import com.iqbrave.iqbrave_lms.service.ModuleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.iqbrave.iqbrave_lms.security.JwtAuthenticationFilter;
import com.iqbrave.iqbrave_lms.security.JwtUtil;
import com.iqbrave.iqbrave_lms.service.CustomUserDetailsService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = ModuleController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class ModuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Provide mocks so security components (if referenced) won’t fail wiring
    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public ModuleService moduleService() {
            return Mockito.mock(ModuleService.class);
        }
    }


    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateModule() throws Exception {
        ModuleDTO dto = new ModuleDTO(null, "Java Basics", "Intro to Java", 1L, null);
        Module module = new Module(1L, "Java Basics", "Intro to Java", new Course(), null);

        Mockito.when(moduleService.createModule(any(ModuleDTO.class))).thenReturn(module);

        mockMvc.perform(post("/api/modules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Basics"));
    }

    @Test
    void testUpdateModule() throws Exception {
        ModuleDTO dto = new ModuleDTO(null, "Spring Boot", "Updated", 1L, null);
        Module module = new Module(1L, "Spring Boot", "Updated", new Course(), null);

        Mockito.when(moduleService.updateModule(eq(1L), any(ModuleDTO.class))).thenReturn(module);

        mockMvc.perform(put("/api/modules/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Spring Boot"));
    }

    @Test
    void testDeleteModule() throws Exception {
        mockMvc.perform(delete("/api/modules/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Module deleted successfully"));
    }

    @Test
    void testListModules() throws Exception {
        Module m1 = new Module(1L, "Java", "Basics", new Course(), null);
        Module m2 = new Module(2L, "Spring", "Boot", new Course(), null);

        Mockito.when(moduleService.listModules()).thenReturn(List.of(m1, m2));

        mockMvc.perform(get("/api/modules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetModuleById() throws Exception {
        Module module = new Module(1L, "Java", "Basics", new Course(), null);

        Mockito.when(moduleService.getModuleById(1L)).thenReturn(Optional.of(module));

        mockMvc.perform(get("/api/modules/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java"));
    }
}
