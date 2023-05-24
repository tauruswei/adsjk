package org.cos.common.convert;

import jdk.nashorn.internal.parser.Token;
import org.cos.common.constant.CommonConstant;
import org.cos.common.entity.data.po.Asset;
import org.cos.common.entity.data.po.NFT;
import org.cos.common.entity.data.po.User;
import org.cos.common.entity.data.vo.UserGameVo;

import java.util.List;
import java.util.stream.Collectors;

public class GameVoConvert {

    public static UserGameVo UserGameVoConvert(String Authorization, User user, List<Asset> assetList, List<NFT> nftList,boolean SLFlag) {
        UserGameVo userGameVo = new UserGameVo();
        userGameVo.setUserId(user.getId());
        userGameVo.setPlayerName(user.getName());
        userGameVo.setAuthorization(Authorization);
        userGameVo.setGuestFlag(false);

        userGameVo.setMoney1(assetList.stream()                                                     // 创建 Stream 对象
                .filter(asset -> (CommonConstant.EVIC==asset.getAssetType()))       // 过滤符合条件的 Asset 对象
                .map(asset -> (int)asset.getAmount())                                                    // 选取 Asset 对象的属性
                .findFirst()                                                                        // 返回第一个符合条件的值
                .orElse(0));

        userGameVo.setMoney2(
            assetList.stream()                                                                              // 创建 Stream 对象
                        .filter(asset -> (CommonConstant.SL==asset.getAssetType()))       // 过滤符合条件的 Asset 对象
                        .map(asset -> (int)asset.getAmount())                                                    // 选取 Asset 对象的属性
                        .findFirst()                                                                        // 返回第一个符合条件的值
                        .orElse(0)
        );

//        userGameVo.setMoney2PassFlag(
//                assetList.stream()                                                                          // 创建 Stream 对象
//                        .filter(asset -> "2".equalsIgnoreCase(asset.getAssetType() + ""))       // 过滤符合条件的 Asset 对象
//                        .map(asset -> asset.getAssetStatus()>0)                                             // 选取 Asset 对象的属性
//                        .findFirst()                                                                        // 返回第一个符合条件的值
//                        .orElse(null)
//        );
        userGameVo.setMoney2PassFlag(SLFlag);

        userGameVo.setChesserDict(nftList.stream().collect(Collectors.groupingBy(NFT::getAttr1, Collectors.summingInt(nft -> Integer.parseInt(nft.getAttr2())))));


        return userGameVo;

    }
}
