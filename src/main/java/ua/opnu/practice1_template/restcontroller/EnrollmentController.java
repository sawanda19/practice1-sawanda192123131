package ua.opnu.practice1_template.restcontroller;

import ua.opnu.practice1_template.dto.EnrollmentDTO;
import ua.opnu.practice1_template.dto.GradeDTO;
import ua.opnu.practice1_template.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> enrollStudentInCourse(@RequestBody EnrollmentDTO enrollmentDTO) {
        EnrollmentDTO enrollment = enrollmentService.enrollStudentInCourse(enrollmentDTO);
        return new ResponseEntity<>(enrollment, HttpStatus.CREATED);
    }

    @GetMapping("/course/{courseId}/students")
    public ResponseEntity<List<EnrollmentDTO>> getStudentsByCourseId(@PathVariable Long courseId) {
        List<EnrollmentDTO> enrollments = enrollmentService.getStudentsByCourseId(courseId);
        return new ResponseEntity<>(enrollments, HttpStatus.OK);
    }

    @GetMapping("/student/{studentId}/courses")
    public ResponseEntity<List<EnrollmentDTO>> getCoursesByStudentId(@PathVariable Long studentId) {
        List<EnrollmentDTO> enrollments = enrollmentService.getCoursesByStudentId(studentId);
        return new ResponseEntity<>(enrollments, HttpStatus.OK);
    }


    @PutMapping("/{studentId}/{courseId}/grade")
    public ResponseEntity<EnrollmentDTO> updateStudentGrade(
            @PathVariable Long studentId,
            @PathVariable Long courseId,
            @RequestBody GradeDTO gradeDTO) {

        EnrollmentDTO updatedEnrollment = enrollmentService.updateStudentGrade(studentId, courseId, gradeDTO.getGrade());
        return new ResponseEntity<>(updatedEnrollment, HttpStatus.OK);
    }


    @DeleteMapping("/{studentId}/{courseId}")
    public ResponseEntity<Void> removeStudentFromCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        enrollmentService.removeStudentFromCourse(studentId, courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}