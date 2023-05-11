package org.cos.common.redis;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description:
 * @Date: 2020/5/13 0013 23:47
 */
public class OcspKey extends BasePrefix{
    private OcspKey(String prefix){
        super(600,prefix);
    }
    public static OcspKey getById= new OcspKey("ocspId");
}
