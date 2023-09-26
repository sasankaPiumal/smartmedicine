package com.nibm.smartmedicine.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatedResponse {
    private Long id;
    private String message;

    public CreatedResponse(Long id, String message) {
        this.id = id;
        this.message = message;
    }
}
