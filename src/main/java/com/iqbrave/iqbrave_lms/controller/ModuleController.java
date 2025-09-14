package com.iqbrave.iqbrave_lms.controller;

import com.iqbrave.iqbrave_lms.dto.ModuleDTO;
import com.iqbrave.iqbrave_lms.entity.CourseModule;
import com.iqbrave.iqbrave_lms.service.ModuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    // Create module
    @PostMapping
    public CourseModule createModule(@RequestBody @jakarta.validation.Valid ModuleDTO moduleDTO) {
        return moduleService.createModule(moduleDTO);
    }

    // Update module
    @PutMapping("/{id}")
    public CourseModule updateModule(@PathVariable Long id, @RequestBody @jakarta.validation.Valid ModuleDTO moduleDTO) {
        return moduleService.updateModule(id, moduleDTO);
    }

    // Delete module
    @DeleteMapping("/{id}")
    public String deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return "Module deleted successfully";
    }

    // List all modules
    @GetMapping
    public List<CourseModule> listModules() {
        return moduleService.listModules();
    }

    // Get single module
    @GetMapping("/{id}")
    public CourseModule getModule(@PathVariable Long id) {
        return moduleService.getModuleById(id)
                .orElseThrow(() -> new RuntimeException("Module not found with id " + id));
    }

    // Optional: list modules by course
    @GetMapping("/course/{courseId}")
    public List<CourseModule> getModulesByCourse(@PathVariable Long courseId) {
        return moduleService.listModules()
                .stream()
                .filter(m -> m.getCourse() != null && m.getCourse().getId().equals(courseId))
                .toList();
    }
}
