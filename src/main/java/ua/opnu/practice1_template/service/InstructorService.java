package ua.opnu.practice1_template.service;

import ua.opnu.practice1_template.dto.InstructorDTO;
import java.util.List;

public interface InstructorService {
    InstructorDTO createInstructor(InstructorDTO instructorDTO);
    List<InstructorDTO> getAllInstructors();
    InstructorDTO getInstructorById(Long id);
    InstructorDTO updateInstructor(Long id, InstructorDTO instructorDTO);
    void deleteInstructor(Long id);
}
