package com.iqbrave.iqbrave_lms.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentDTO {
    private Long id;
    private Long studentId;
    private Long courseId;
}
