package ua.opnu.practice1_template.repository;

import ua.opnu.practice1_template.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByInstructorId(Long instructorId);
}
