package org.cos.common.redis;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description:
 * @Date: 2020/5/11 0011 13:42
 */
public class CaKey  extends BasePrefix{
    private CaKey(String prefix){
        super(prefix);
    }
    public static CaKey getByCaId= new CaKey("caId");
}
