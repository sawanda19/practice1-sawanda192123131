package ua.opnu.practice1_template.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private Integer credits;
    private Long instructorId;
    private String instructorName; // Combined first and last name for display purposes
}
