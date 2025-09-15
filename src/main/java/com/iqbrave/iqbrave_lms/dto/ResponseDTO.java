package com.iqbrave.iqbrave_lms.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDTO<T> {
    private boolean success;
    private String message;
    private T data;
}
