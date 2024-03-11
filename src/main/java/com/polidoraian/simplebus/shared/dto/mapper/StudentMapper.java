package com.polidoraian.simplebus.shared.dto.mapper;

import com.polidoraian.simplebus.shared.database.entity.Student;
import com.polidoraian.simplebus.shared.dto.StudentCreationDTO;
import com.polidoraian.simplebus.shared.dto.StudentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
	@Autowired
	private ModelMapper mapper;

	public StudentCreationDTO toStudentCreationDTO(Student student) {
		return mapper.map(student, StudentCreationDTO.class);
	}

	public Student toStudent(StudentCreationDTO studentCreationDTO) {

		return mapper.map(studentCreationDTO, Student.class);
	}

	public Student toStudent(StudentDTO studentDTO) {

		return mapper.map(studentDTO, Student.class);
	}

	public StudentDTO toStudentDTO(Student student) {
		return mapper.map(student, StudentDTO.class);
	}
}
