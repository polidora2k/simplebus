package com.polidoraian.simplebus.shared.service;

import com.polidoraian.simplebus.shared.dto.StudentCreationDTO;
import com.polidoraian.simplebus.shared.dto.StudentDTO;

import java.util.List;

public interface ParentService {
    void addStudent(StudentCreationDTO studentCreationDTO, Integer parentId);
    List<StudentDTO> getAllStudents(Integer parentId);
    List<StudentDTO> getRidingStudents(Integer parentId);
}
