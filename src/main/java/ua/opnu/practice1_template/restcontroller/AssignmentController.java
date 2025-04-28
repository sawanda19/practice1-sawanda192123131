package ua.opnu.practice1_template.restcontroller;

import ua.opnu.practice1_template.dto.AssignmentDTO;
import ua.opnu.practice1_template.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping("/assignments")
    public ResponseEntity<AssignmentDTO> createAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        AssignmentDTO createdAssignment = assignmentService.createAssignment(assignmentDTO);
        return new ResponseEntity<>(createdAssignment, HttpStatus.CREATED);
    }


    @PostMapping("/courses/{courseId}/assignments")
    public ResponseEntity<AssignmentDTO> addAssignmentToCourse(
            @PathVariable Long courseId,
            @RequestBody AssignmentDTO assignmentDTO) {

        assignmentDTO.setCourseId(courseId);
        AssignmentDTO createdAssignment = assignmentService.createAssignment(assignmentDTO);
        return new ResponseEntity<>(createdAssignment, HttpStatus.CREATED);
    }

    @GetMapping("/assignments/course/{courseId}")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsByCourseId(@PathVariable Long courseId) {
        List<AssignmentDTO> assignments = assignmentService.getAssignmentsByCourseId(courseId);
        return new ResponseEntity<>(assignments, HttpStatus.OK);
    }

    @GetMapping("/courses/{courseId}/assignments")
    public ResponseEntity<List<AssignmentDTO>> getCourseAssignments(@PathVariable Long courseId) {
        List<AssignmentDTO> assignments = assignmentService.getAssignmentsByCourseId(courseId);
        return new ResponseEntity<>(assignments, HttpStatus.OK);
    }

    @PutMapping("/assignments/{id}")
    public ResponseEntity<AssignmentDTO> updateAssignment(
            @PathVariable Long id,
            @RequestBody AssignmentDTO assignmentDTO) {
        AssignmentDTO updatedAssignment = assignmentService.updateAssignment(id, assignmentDTO);
        return new ResponseEntity<>(updatedAssignment, HttpStatus.OK);
    }

    @PutMapping("/courses/{courseId}/assignments/{assignmentId}")
    public ResponseEntity<AssignmentDTO> updateCourseAssignment(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @RequestBody AssignmentDTO assignmentDTO) {

        assignmentDTO.setCourseId(courseId);
        AssignmentDTO updatedAssignment = assignmentService.updateAssignment(assignmentId, assignmentDTO);
        return new ResponseEntity<>(updatedAssignment, HttpStatus.OK);
    }

    @DeleteMapping("/assignments/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/courses/{courseId}/assignments/{assignmentId}")
    public ResponseEntity<Void> deleteCourseAssignment(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId) {

        assignmentService.deleteAssignment(assignmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}