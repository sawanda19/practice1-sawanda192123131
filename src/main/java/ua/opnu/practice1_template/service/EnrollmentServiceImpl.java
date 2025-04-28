package ua.opnu.practice1_template.service;

import ua.opnu.practice1_template.dto.EnrollmentDTO;
import ua.opnu.practice1_template.model.Course;
import ua.opnu.practice1_template.model.Enrollment;
import ua.opnu.practice1_template.model.Student;
import ua.opnu.practice1_template.repository.CourseRepository;
import ua.opnu.practice1_template.repository.EnrollmentRepository;
import ua.opnu.practice1_template.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 StudentRepository studentRepository,
                                 CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public EnrollmentDTO enrollStudentInCourse(EnrollmentDTO enrollmentDTO) {
        Student student = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + enrollmentDTO.getStudentId()));

        Course course = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + enrollmentDTO.getCourseId()));

        // Check if the student is already enrolled in this course
        if (enrollmentRepository.findByStudentIdAndCourseId(student.getId(), course.getId()).isPresent()) {
            throw new RuntimeException("Student is already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(enrollmentDTO.getEnrollmentDate() != null ?
                enrollmentDTO.getEnrollmentDate() : LocalDate.now());
        enrollment.setGrade(enrollmentDTO.getGrade());

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return convertToDTO(savedEnrollment);
    }

    @Override
    public List<EnrollmentDTO> getStudentsByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new RuntimeException("Course not found with id: " + courseId);
        }

        return enrollmentRepository.findAllByCourseId(courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentDTO> getCoursesByStudentId(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found with id: " + studentId);
        }

        return enrollmentRepository.findAllByStudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EnrollmentDTO updateStudentGrade(Long studentId, Long courseId, String grade) {
        // Перевірка вхідних даних
        if (studentId == null || courseId == null || grade == null) {
            throw new IllegalArgumentException("Student ID, Course ID and grade must not be null");
        }

        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found for student id: " + studentId +
                        " and course id: " + courseId));

        enrollment.setGrade(grade);
        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);
        return convertToDTO(updatedEnrollment);
    }

    @Override
    @Transactional
    public void removeStudentFromCourse(Long studentId, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found for student id: " + studentId +
                        " and course id: " + courseId));

        enrollmentRepository.delete(enrollment);
    }

    private EnrollmentDTO convertToDTO(Enrollment enrollment) {
        return new EnrollmentDTO(
                enrollment.getId(),
                enrollment.getStudent().getId(),
                enrollment.getStudent().getFirstName() + " " + enrollment.getStudent().getLastName(),
                enrollment.getCourse().getId(),
                enrollment.getCourse().getTitle(),
                enrollment.getEnrollmentDate(),
                enrollment.getGrade()
        );
    }
}