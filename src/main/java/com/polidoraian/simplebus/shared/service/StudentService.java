package com.polidoraian.simplebus.shared.service;

import com.polidoraian.simplebus.shared.dto.RouteDTO;
import com.polidoraian.simplebus.shared.dto.StopDTO;
import com.polidoraian.simplebus.shared.dto.StudentDTO;
import com.polidoraian.simplebus.shared.dto.StudentStatusDTO;

import java.util.List;

public interface StudentService {
    StudentDTO getStudent(Integer id);
    StudentStatusDTO getStudentStatus(StudentDTO student);
    void changeRiding(Integer id, Boolean riding);
    List<StudentDTO> getStudentsByStop(StopDTO stop);
    List<StudentDTO> getStudentsForRoute(RouteDTO route);
}
