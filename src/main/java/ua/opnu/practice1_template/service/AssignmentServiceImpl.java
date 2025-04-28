package ua.opnu.practice1_template.service;

import ua.opnu.practice1_template.dto.AssignmentDTO;
import ua.opnu.practice1_template.model.Assignment;
import ua.opnu.practice1_template.model.Course;
import ua.opnu.practice1_template.repository.AssignmentRepository;
import ua.opnu.practice1_template.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, CourseRepository courseRepository) {
        this.assignmentRepository = assignmentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public AssignmentDTO createAssignment(AssignmentDTO assignmentDTO) {
        Course course = courseRepository.findById(assignmentDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + assignmentDTO.getCourseId()));

        Assignment assignment = new Assignment();
        assignment.setCourse(course);
        assignment.setTitle(assignmentDTO.getTitle());
        assignment.setDescription(assignmentDTO.getDescription());
        assignment.setDueDate(assignmentDTO.getDueDate());

        Assignment savedAssignment = assignmentRepository.save(assignment);
        return convertToDTO(savedAssignment);
    }

    @Override
    public List<AssignmentDTO> getAssignmentsByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new RuntimeException("Course not found with id: " + courseId);
        }

        return assignmentRepository.findAllByCourseId(courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AssignmentDTO updateAssignment(Long id, AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found with id: " + id));

        Course course = courseRepository.findById(assignmentDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + assignmentDTO.getCourseId()));

        assignment.setCourse(course);
        assignment.setTitle(assignmentDTO.getTitle());
        assignment.setDescription(assignmentDTO.getDescription());
        assignment.setDueDate(assignmentDTO.getDueDate());

        Assignment updatedAssignment = assignmentRepository.save(assignment);
        return convertToDTO(updatedAssignment);
    }

    @Override
    @Transactional
    public void deleteAssignment(Long id) {
        if (!assignmentRepository.existsById(id)) {
            throw new RuntimeException("Assignment not found with id: " + id);
        }
        assignmentRepository.deleteById(id);
    }

    private AssignmentDTO convertToDTO(Assignment assignment) {
        return new AssignmentDTO(
                assignment.getId(),
                assignment.getCourse().getId(),
                assignment.getCourse().getTitle(),
                assignment.getTitle(),
                assignment.getDescription(),
                assignment.getDueDate()
        );
    }
}
