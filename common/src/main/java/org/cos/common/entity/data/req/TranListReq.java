package org.cos.common.entity.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.cos.common.entity.data.vo.NFTVo;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "TranListReq",description = "交易列表查询 实体")
public class TranListReq {
    @ApiModelProperty(value = "交易类型",name = "transType",example = "2，说明：0-用户使用 USDT 购买 COSD、1-用户质押COSD 到 DEFI、2-用户质押 COSD到星光、3-质押 COSD 到俱乐部老板质押池、4-用户从 defi 提现 COSD、5-用户从 星光池中 提现 COSD、6-用户从 俱乐部老板质押池中 提现 COSD、7-用户使用USDT 购买 EVIC、8-用户提现EVIC、9-用户使用USDT 购买 NFT 盲盒、10-NFT交易",dataType = "Integer",required = true)
    private Integer transType;
    @ApiModelProperty(value = "用户id",name = "userId",example = "123",dataType = "Long",required = false)
    private Long userId;
    @ApiModelProperty(value = "状态",name = "status",example = "1",dataType = "Integer",required = false)
    private int status;
    @ApiModelProperty(value = "时间戳（秒）",name = "time",example = "1000000",dataType = "Long",required = false)
    private Long time;
    @ApiModelProperty(value = "页码",name = "pageNo",example = "1" ,dataType = "Integer",required = false)
    private Integer pageNo = 1;
    @ApiModelProperty(value = "每页显示多少条数据",name = "pageSize",example = "5" ,dataType = "Integer",required = false)
    private Integer pageSize = 5;
}

