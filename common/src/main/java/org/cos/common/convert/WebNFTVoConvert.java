package org.cos.common.convert;

import org.apache.commons.lang3.ObjectUtils;
import org.cos.common.constant.CommonConstant;
import org.cos.common.entity.data.po.Asset;
import org.cos.common.entity.data.po.NFT;
import org.cos.common.entity.data.po.User;
import org.cos.common.entity.data.vo.UserGameVo;
import org.cos.common.entity.data.vo.WebNFTVo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WebNFTVoConvert {
    public static List<WebNFTVo> WebNFTVoListConvert( List<NFT> nftList) {
        List<WebNFTVo> newList = nftList.stream().map(obj -> {
            WebNFTVo webNFTVo = new WebNFTVo();
            webNFTVo.setId(obj.getId());
            webNFTVo.setTxId(obj.getTxid());
            webNFTVo.setTokenId(obj.getTokenId());
            webNFTVo.setMintedAt(obj.getUpchainTime());
            webNFTVo.setRunOutTime(Objects.isNull(obj.getUpdateTime())?0L:obj.getUpdateTime().getTime());
            webNFTVo.setGameChances(obj.getAttr2());
            webNFTVo.setBlockChain("Binance Smart Chain");
            webNFTVo.setNftType(obj.getAttr1());
            webNFTVo.setStatus(obj.getStatus());
            return webNFTVo;
        }).collect(Collectors.toList());

        return newList;

    }
}
