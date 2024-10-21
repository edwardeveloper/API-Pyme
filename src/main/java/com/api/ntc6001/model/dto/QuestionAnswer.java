package com.api.ntc6001.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionAnswer {
    private String question;
    private String answer;
    private float compliance;
}
