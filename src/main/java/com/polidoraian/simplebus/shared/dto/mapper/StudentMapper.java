package com.polidoraian.simplebus.shared.dto.mapper;

import com.polidoraian.simplebus.shared.entity.Student;
import com.polidoraian.simplebus.shared.dto.StudentCreationDTO;
import com.polidoraian.simplebus.shared.dto.StudentDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface StudentMapper {
    @Mapping(target = "state", ignore = true)
    Student dtoToEntity(StudentDTO studentDTO);
    
    @Mapping(target = "status", ignore = true)
    StudentDTO entityToDto(Student student);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parentId", ignore = true)
    @Mapping(target = "riding", ignore = true)
    @Mapping(target = "stopId", ignore = true)
    Student creationDtoToEntity(StudentCreationDTO studentCreationDTO);
}
