package org.cos.common.redis;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description:
 * @Date: 2020/5/7 0007 15:43
 */
public class CrlKey extends BasePrefix{
    private CrlKey(String prefix){
        super(prefix);
    }
    public static CrlKey getByInfo= new CrlKey("info");
    public static CrlKey getByCsn = new CrlKey("csn");
}
