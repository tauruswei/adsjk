package org.cos.common.redis;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description:
 * @Date: 2020/5/11 0011 14:27
 */
public class ApplicationKey extends BasePrefix{

    public ApplicationKey(String prefix) {
        super(prefix);
    }
    public static ApplicationKey getByAppId= new ApplicationKey("appId");
    public static ApplicationKey getByAppName= new ApplicationKey("appName");
}
