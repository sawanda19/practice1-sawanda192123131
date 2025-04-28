package ua.opnu.practice1_template.service;

import ua.opnu.practice1_template.dto.CourseDTO;
import java.util.List;

public interface CourseService {
    CourseDTO createCourse(CourseDTO courseDTO);
    List<CourseDTO> getAllCourses();
    CourseDTO getCourseById(Long id);
    List<CourseDTO> getCoursesByInstructorId(Long instructorId);
    CourseDTO updateCourse(Long id, CourseDTO courseDTO);
    void deleteCourse(Long id);
}
