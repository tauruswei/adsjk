package org.cos.common.entity.data.dto;

import lombok.Data;

@Data
public class LokiDto {
    private String status;
    private LokiResultDto data;
}