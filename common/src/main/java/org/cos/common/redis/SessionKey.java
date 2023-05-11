package org.cos.common.redis;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description:
 * @Date: 2020/4/24 0024 14:32
 */
//@Component
public class SessionKey extends BasePrefix{

//    @Value("${app.expireSeconds}")
    int expireSeconds;

    public SessionKey(){}
    public SessionKey(int expireSeconds, String prefix){
        super(expireSeconds,prefix);
    }
    public SessionKey(String prefix){
        super(prefix);
    }

    public static SessionKey getBySessionId(){
       return new SessionKey("sessionId");
    }
    public static SessionKey getBySessionId(int expireSeconds){
       return new SessionKey(expireSeconds,"sessionId");
    }
    public static SessionKey getByList(){
        return new SessionKey("list");
    }
//    public static SessionKey getByUserName(){
//        return new SessionKey(Integer.parseInt((String) ConstantsContext.getConstnt(Const.ExpireSeconds)),"userName");
//    }
//    public static SessionKey getByAppName(){
//        return new SessionKey(Integer.parseInt((String) ConstantsContext.getConstnt(Const.ExpireSeconds)),"appName");
//    }
}
