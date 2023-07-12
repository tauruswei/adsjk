package org.cos.common.redis;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description:
 * @Date: 2020/5/7 0007 20:24
 */
public class ProfileKey extends BasePrefix{
    private ProfileKey(String prefix){
        super(prefix);
    }
    public static ProfileKey getClub= new ProfileKey("club");
    public static ProfileKey getChannel= new ProfileKey("channel");
    public static ProfileKey getCompany= new ProfileKey("company");
}