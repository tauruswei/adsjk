package org.cos.common.entity.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class LokiResultContentDto {
    private List<String[]> values;
}