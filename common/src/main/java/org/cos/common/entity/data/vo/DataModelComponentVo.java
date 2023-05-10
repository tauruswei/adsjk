package org.cos.common.entity.data.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DataModelComponentVo {
    /**
     * 组件id
     */
    private Long componentId;
    /**
     * 模型id
     */
    private Long modelId;
    /**
     * 组件code
     */
    private String componentCode;
    /**
     * 组件名称
     */
    private String componentName;
    /**
     * 耗时
     */
    private Integer timeConsuming;

    /**
     * 耗时比例
     */
    private BigDecimal  timeRatio = new BigDecimal(0);
    /**
     * 0初始 1成功 2运行中 3失败
     */
    private Integer componentState;
}
