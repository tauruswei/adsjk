package org.cos.common.redis;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description:
 * @Date: 2020/5/7 0007 20:24
 */
public class TokenBlockedKey extends BasePrefix{
    private TokenBlockedKey(String prefix){
        super(prefix);
    }
    public TokenBlockedKey(int expireSeconds, String prefix){
        super(expireSeconds,prefix);
    }

//    public static TokenBlockedKey getBlockedKey= new TokenBlockedKey("tokenBlocked");
    public static TokenBlockedKey getBlockedKey(int expireSeconds){
        return new TokenBlockedKey(expireSeconds,"");
    }

}