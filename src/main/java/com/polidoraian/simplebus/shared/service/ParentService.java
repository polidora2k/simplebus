package com.polidoraian.simplebus.shared.service;

import java.util.List;
import java.util.stream.Collectors;

import com.polidoraian.simplebus.shared.database.dao.StudentDAO;
import com.polidoraian.simplebus.shared.database.entity.Student;
import com.polidoraian.simplebus.shared.dto.StudentCreationDTO;
import com.polidoraian.simplebus.shared.dto.StudentDTO;
import com.polidoraian.simplebus.shared.dto.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ParentService {
	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private StudentDAO studentDAO;
	
	@Autowired
	private StudentService studentService;
	
	public StudentDTO addStudent(StudentCreationDTO studentCreationDTO, Integer parentId) {
		Student newStudent = studentMapper.creationDtoToEntity(studentCreationDTO);
		log.debug("addStudent in service");
		newStudent.setParentId(parentId);
		newStudent.setRiding(true);
		newStudent.setStopId(1);
		
		studentDAO.save(newStudent);
		
		return studentMapper.entityToDto(newStudent);
	}
	
	public List<StudentDTO> getAllStudents(Integer parentId){
		List<Student> students = studentDAO.findByParentId(parentId);
		
		return students.stream().map(studentMapper::entityToDto).collect(Collectors.toList());
	}
	
	public List<StudentDTO> getRidingStudents(Integer parentId){
		List<Student> students = studentDAO.findRidingStudentsForParent(parentId);
		List<StudentDTO> studentDTOs = students.stream().map(studentMapper::entityToDto).collect(Collectors.toList());
		
		studentDTOs.forEach(s -> s.setStatus(studentService.getStudentStatus(s)));
		return studentDTOs;
	}
}
