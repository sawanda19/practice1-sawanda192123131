package ua.opnu.practice1_template.service;

import ua.opnu.practice1_template.dto.EnrollmentDTO;
import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO enrollStudentInCourse(EnrollmentDTO enrollmentDTO);
    List<EnrollmentDTO> getStudentsByCourseId(Long courseId);
    List<EnrollmentDTO> getCoursesByStudentId(Long studentId);
    EnrollmentDTO updateStudentGrade(Long studentId, Long courseId, String grade);
    void removeStudentFromCourse(Long studentId, Long courseId);
}
