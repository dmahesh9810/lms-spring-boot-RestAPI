package com.iqbrave.iqbrave_lms.service;


import com.iqbrave.iqbrave_lms.dto.ModuleDTO;
import com.iqbrave.iqbrave_lms.entity.CourseModule;


import java.util.List;
import java.util.Optional;


public interface ModuleService {


    CourseModule createModule(ModuleDTO moduleDTO);


    CourseModule updateModule(Long id, ModuleDTO moduleDTO);


    void deleteModule(Long id);


    List<CourseModule> listModules();


    Optional<CourseModule> getModuleById(Long id);
}