package org.cos.common.redis;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description:
 * @Date: 2020/5/7 0007 19:46
 */
public class PolicyKey extends BasePrefix{
    private PolicyKey(String prefix){
        super(prefix);
    }
    public static PolicyKey getAppName= new PolicyKey("appName");
}
