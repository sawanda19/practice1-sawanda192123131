package ua.opnu.practice1_template.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {
    private Long id;
    private Long studentId;
    private String studentName; // Combined first and last name for display purposes
    private Long courseId;
    private String courseTitle;
    private LocalDate enrollmentDate;
    private String grade;
}
