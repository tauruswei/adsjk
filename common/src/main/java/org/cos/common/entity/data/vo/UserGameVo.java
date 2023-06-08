package org.cos.common.entity.data.vo;

import lombok.Data;

import java.util.Map;

@Data
public class UserGameVo {
    // token
    private String Authorization;
    // 在网站上的用户唯一标识
    private Long userId;
    // 在网站上的用户昵称
    private String userName;
    // 游客的昵称
    private String guestUserName;
    // 游客的密码
    private String guestUserPassword;
    // 用户类型：0-渠道商；1-俱乐部老板；2-普通用户
    private int userType;
    // 在网站上的用户头像图片地址
    private String faceUrl;
    // 艾瑞克结晶
    private int money1;
    // 星光币
    private int money2;
    // 星光通行证状态，true为有通行证，false为没有通行证
    private boolean money2PassFlag;
    // 游客标记
    private boolean guestFlag;
    // 棋手字典<棋手唯一标识，剩余点数>
    private Map chesserDict;
}
