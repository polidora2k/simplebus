package com.polidoraian.simplebus.shared.service.impl;

import com.polidoraian.simplebus.shared.dto.StudentCreationDTO;
import com.polidoraian.simplebus.shared.dto.StudentDTO;
import com.polidoraian.simplebus.shared.dto.mapper.StudentMapper;
import com.polidoraian.simplebus.shared.entity.Student;
import com.polidoraian.simplebus.shared.repository.StudentRepository;
import com.polidoraian.simplebus.shared.service.ParentService;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ParentServiceImpl implements ParentService {
	private final StudentMapper studentMapper;
	private final StudentRepository studentRepository;
	private final StudentServiceImpl studentService;
	
	public ParentServiceImpl(@Nonnull final StudentMapper studentMapper,
							 @Nonnull final StudentRepository studentRepository,
							 @Nonnull final StudentServiceImpl studentService) {
		this.studentMapper = studentMapper;
		this.studentRepository = studentRepository;
		this.studentService = studentService;
	}
	
	public void addStudent(StudentCreationDTO studentCreationDTO, Integer parentId) {
		Student newStudent = studentMapper.creationDtoToEntity(studentCreationDTO);
		log.debug("addStudent in service");
		newStudent.setParentId(parentId);
		newStudent.setRiding(true);
		newStudent.setStopId(1);
		
		studentRepository.save(newStudent);
		
		studentMapper.entityToDto(newStudent);
	}
	
	public List<StudentDTO> getAllStudents(Integer parentId) {
		List<Student> students = studentRepository.findByParentId(parentId);
		
		return students.stream().map(studentMapper::entityToDto).collect(Collectors.toList());
	}
	
	public List<StudentDTO> getRidingStudents(Integer parentId) {
		List<Student> students = studentRepository.findRidingStudentsForParent(parentId);
		List<StudentDTO> studentDTOs = students.stream().map(studentMapper::entityToDto).collect(Collectors.toList());
		
		studentDTOs.forEach(s -> s.setStatus(studentService.getStudentStatus(s)));
		return studentDTOs;
	}
}
