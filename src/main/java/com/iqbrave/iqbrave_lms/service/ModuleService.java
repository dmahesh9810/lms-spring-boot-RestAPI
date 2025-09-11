package com.iqbrave.iqbrave_lms.service;


import com.iqbrave.iqbrave_lms.dto.ModuleDTO;
import com.iqbrave.iqbrave_lms.entity.Module;


import java.util.List;
import java.util.Optional;


public interface ModuleService {


    Module createModule(ModuleDTO moduleDTO);


    Module updateModule(Long id, ModuleDTO moduleDTO);


    void deleteModule(Long id);


    List<Module> listModules();


    Optional<Module> getModuleById(Long id);
}