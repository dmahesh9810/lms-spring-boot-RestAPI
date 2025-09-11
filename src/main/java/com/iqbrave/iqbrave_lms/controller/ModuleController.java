package com.iqbrave.iqbrave_lms.controller;

import com.iqbrave.iqbrave_lms.dto.ModuleDTO;
import com.iqbrave.iqbrave_lms.entity.Module;
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
    public Module createModule(@RequestBody ModuleDTO moduleDTO) {
        return moduleService.createModule(moduleDTO);
    }

    // Update module
    @PutMapping("/{id}")
    public Module updateModule(@PathVariable Long id, @RequestBody ModuleDTO moduleDTO) {
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
    public List<Module> listModules() {
        return moduleService.listModules();
    }

    // Get single module
    @GetMapping("/{id}")
    public Module getModule(@PathVariable Long id) {
        return moduleService.getModuleById(id)
                .orElseThrow(() -> new RuntimeException("Module not found with id " + id));
    }

    // Optional: list modules by course
    @GetMapping("/course/{courseId}")
    public List<Module> getModulesByCourse(@PathVariable Long courseId) {
        return moduleService.listModules()
                .stream()
                .filter(m -> m.getCourse() != null && m.getCourse().getId().equals(courseId))
                .toList();
    }
}
