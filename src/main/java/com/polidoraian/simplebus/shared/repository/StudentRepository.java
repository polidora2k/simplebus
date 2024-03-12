package com.polidoraian.simplebus.shared.repository;

import java.util.List;

import com.polidoraian.simplebus.shared.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	List<Student> findByParentId(Integer parentId);
	
	@Query("SELECT s FROM Student s WHERE s.parentId = :parentId AND s.riding = true")
	List<Student> findRidingStudentsForParent(Integer parentId);
	
	List<Student> findByStopIdAndRiding(Integer stopId, Boolean riding);
	
	Integer countByStopIdAndRiding(Integer stopId, Boolean riding);
}
