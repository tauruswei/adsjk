package org.cos.common.entity.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.cos.common.entity.data.vo.NFTVo;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "PoolListReq",description = "用户请求是否能质押 请求实体")
public class PoolListReq {
    // pool id
    private Long poolId;

    // 开始时间
    private Long startTime;
    // lockTime
    private Long lockTime;

}

