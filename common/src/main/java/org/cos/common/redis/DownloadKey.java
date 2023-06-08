package org.cos.common.redis;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description:
 * @Date: 2020/5/7 0007 20:24
 */
public class DownloadKey extends BasePrefix{
    private DownloadKey(String prefix){
        super(prefix);
    }
    public static DownloadKey getDownload= new DownloadKey("download");
}