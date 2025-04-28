package ua.opnu.practice1_template.repository;

import ua.opnu.practice1_template.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAllByCourseId(Long courseId);
}
