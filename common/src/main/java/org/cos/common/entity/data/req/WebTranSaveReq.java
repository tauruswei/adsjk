package org.cos.common.entity.data.req;

import jdk.jfr.internal.PrivateAccess;
import lombok.Data;

@Data
public class WebTranSaveReq {
    // 用户id
    private Long userId;
    // 交易id
    private String txId;
    // 上链时间
    private String upChainTime;
    // 数量
    private Double amount;
    // remark
    private String remark;

}
