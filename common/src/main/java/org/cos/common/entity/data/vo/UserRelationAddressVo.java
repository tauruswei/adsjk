package org.cos.common.entity.data.vo;

import lombok.Data;
import org.cos.common.result.Result;

import java.util.Map;

@Data
public class UserRelationAddressVo {
    // 渠道商地址
    private String channelAddress;
    // 俱乐部老板地址
    private String clubAddress;
    // 用户地址
    private String userAddress;

    public UserRelationAddressVo(){
        this.channelAddress = "0x0000000000000000000000000000000000000000";
        this.clubAddress = "0x0000000000000000000000000000000000000000";
        this.userAddress = "0x0000000000000000000000000000000000000000";

    }
}
