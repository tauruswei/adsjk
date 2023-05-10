package org.cos.common.entity.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class LokiResultDto {
    private List<LokiResultContentDto> result;
}