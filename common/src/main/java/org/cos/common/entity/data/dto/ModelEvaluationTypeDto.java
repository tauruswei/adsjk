package org.cos.common.entity.data.dto;

import lombok.Data;

@Data
public class ModelEvaluationTypeDto {
    private String meanSquaredError;
    private String explainedVariance;
    private String meanAbsoluteError;
    private String meanSquaredLogError;
    private String medianAbsoluteError;
    private String r2Score;
    private String rootMeanSquaredError;
}
