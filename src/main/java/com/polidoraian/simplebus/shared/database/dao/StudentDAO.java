package com.polidoraian.simplebus.shared.database.dao;

import java.util.List;
import java.util.Optional;

import com.polidoraian.simplebus.shared.database.entity.Student;
import jakarta.annotation.Nonnull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentDAO extends JpaRepository<Student, Integer> {
	List<Student> findByParentId(Integer parentId);
	
	@Query("SELECT s FROM Student s WHERE s.parentId = :parentId AND s.riding = true")
	List<Student> findRidingStudentsForParent(Integer parentId);
	
	List<Student> findByStopIdAndRiding(Integer stopId, Boolean riding);
	
	Integer countByStopIdAndRiding(Integer stopId, Boolean riding);
}
