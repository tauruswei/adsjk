package org.cos.common.redis;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description:
 * @Date: 2020/5/7 0007 20:24
 */
public class EvicKey extends BasePrefix{
    private EvicKey(String prefix){
        super(7200,prefix);
    }
    public static EvicKey getWithdrawKey= new EvicKey("withdraw");
}