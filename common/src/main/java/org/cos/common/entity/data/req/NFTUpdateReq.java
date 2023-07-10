package org.cos.common.entity.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.cos.common.entity.data.vo.NFTVo;

@Data
@ApiModel(value = "NFTUpdateReq",description = "NFT 更新请求实体")
public class NFTUpdateReq {
    private NFTVo nftVo;
//    @ApiModelProperty(value = "状态",name = "status",example = "0，0-已购买；1-已使用，2-已失效",dataType = "Integer",required = false)
//    private int status;
    private Long userId;
}

