package com.polidoraian.simplebus.database.dao;

import java.util.List;
import java.util.Optional;

import com.polidoraian.simplebus.database.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentDAO extends JpaRepository<Student, Integer> {
	public List<Student> findByParentId(Integer parentId);
	
	public Optional<Student> findById(Integer id);
	
	@Query("SELECT s FROM Student s WHERE s.parentId = :parentId AND s.riding = true")
	public List<Student> findRidingStudentsForParent(Integer parentId);
	
	public List<Student> findByStopIdAndRiding(Integer stopId, Boolean riding);
	
	public Integer countByStopIdAndRiding(Integer stopId, Boolean riding);
	
	public void deleteById(Integer id);
}
