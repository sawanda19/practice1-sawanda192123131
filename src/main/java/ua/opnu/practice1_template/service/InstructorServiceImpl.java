package ua.opnu.practice1_template.service;

import ua.opnu.practice1_template.dto.InstructorDTO;
import ua.opnu.practice1_template.model.Instructor;
import ua.opnu.practice1_template.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;

    @Autowired
    public InstructorServiceImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    @Transactional
    public InstructorDTO createInstructor(InstructorDTO instructorDTO) {

        if (instructorRepository.existsByEmail(instructorDTO.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Instructor instructor = convertToEntity(instructorDTO);
        Instructor savedInstructor = instructorRepository.save(instructor);
        return convertToDTO(savedInstructor);
    }

    @Override
    public List<InstructorDTO> getAllInstructors() {
        return instructorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InstructorDTO getInstructorById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor not found with id: " + id));
        return convertToDTO(instructor);
    }

    @Override
    @Transactional
    public InstructorDTO updateInstructor(Long id, InstructorDTO instructorDTO) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor not found with id: " + id));


        if (!instructor.getEmail().equals(instructorDTO.getEmail()) &&
                instructorRepository.existsByEmail(instructorDTO.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        instructor.setFirstName(instructorDTO.getFirstName());
        instructor.setLastName(instructorDTO.getLastName());
        instructor.setEmail(instructorDTO.getEmail());
        instructor.setDepartment(instructorDTO.getDepartment());

        Instructor updatedInstructor = instructorRepository.save(instructor);
        return convertToDTO(updatedInstructor);
    }

    @Override
    @Transactional
    public void deleteInstructor(Long id) {
        if (!instructorRepository.existsById(id)) {
            throw new RuntimeException("Instructor not found with id: " + id);
        }
        instructorRepository.deleteById(id);
    }

    private Instructor convertToEntity(InstructorDTO instructorDTO) {
        Instructor instructor = new Instructor();
        instructor.setId(instructorDTO.getId());
        instructor.setFirstName(instructorDTO.getFirstName());
        instructor.setLastName(instructorDTO.getLastName());
        instructor.setEmail(instructorDTO.getEmail());
        instructor.setDepartment(instructorDTO.getDepartment());
        return instructor;
    }

    private InstructorDTO convertToDTO(Instructor instructor) {
        return new InstructorDTO(
                instructor.getId(),
                instructor.getFirstName(),
                instructor.getLastName(),
                instructor.getEmail(),
                instructor.getDepartment()
        );
    }
}
