package org.cos.common.entity.data.vo;

import lombok.Data;

import java.security.acl.Owner;

@Data
public class NFTVo {
    private String tokenId;
    private String ownerName;
    // 该 nft 是哪个游戏上的
    private int gameType;

    private String attr1;
    private String attr2;
}
