package org.cos.common.repository;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.cos.common.entity.data.po.NFT;
import org.cos.common.entity.data.po.TransWebsite;
import org.cos.common.entity.data.po.User;

import java.util.List;

@Mapper
public interface NFTRepository {
    void insertNFT(NFT nft);
    List<NFT> queryNFTsByUserIdAndStatus(NFT nft);
    NFT queryNFTByUserIdAndAtrr1(NFT nft);
    void updateNFTStatus(NFT nft);
    TransWebsite queryTransWebsiteByTxId(String txid);
    User queryUserByWalletAddressAndUserType(@Param("walletAddress") String walletAddress, @Param("userType") int userType);
    NFT queryNFTByTokenId(@Param("tokenId")String tokenId);
    @Select("select count(*) from nft where status = #{status}")
    Long statisticalNFT(@Param("status") int status);

//    User queryUserByWalletAddressAndUserType(String walletAddress,  int userType);
//    void insertSysUrBatch(@Param("urList") List<SysUr> urList);
//    void deleteSysUrBatch(/*@Param("roleArray") Long[] roleArray,*/ @Param("userId")Long userId);
//    void updateSysUserDelStatus(@Param("userId")Long userId);
//
//    void updateUserAccount(@Param("userAccount") String userAccount,@Param("userId") Long userId);
}