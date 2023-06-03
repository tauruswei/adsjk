package org.cos.common.entity.data.vo;

import lombok.Data;

@Data
public class WebNFTVo {
    private Long id;
    private String tokenId;
    private String txId;
//    private String ownerName;
    // 该 nft 是哪个游戏上的
//    private int gameType;
    private String blockChain;
    private String nftType;
    private String gameChances;
    private Long mintedAt;
    private Long runOutTime;
}
