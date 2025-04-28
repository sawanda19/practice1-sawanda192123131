package ua.opnu.practice1_template.service;

import ua.opnu.practice1_template.dto.AssignmentDTO;
import java.util.List;

public interface AssignmentService {
    AssignmentDTO createAssignment(AssignmentDTO assignmentDTO);
    List<AssignmentDTO> getAssignmentsByCourseId(Long courseId);
    AssignmentDTO updateAssignment(Long id, AssignmentDTO assignmentDTO);
    void deleteAssignment(Long id);
}
