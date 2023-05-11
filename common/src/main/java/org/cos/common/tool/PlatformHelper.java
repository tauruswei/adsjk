package org.cos.common.tool;

import org.cos.common.util.crypt.DateUtil;
import org.cos.common.util.crypt.SignUtil;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

public class PlatformHelper {
    public static String generateOwnToken(String prefix,Long sequence,Date date){
        String dt= DateUtil.formatDate(date,DateUtil.DateStyle.TIME_FORMAT_SHORT.getFormat());
        String randomStr=RandomStringUtils.randomAlphanumeric(10);
        String sequenceStr= SignUtil.getMD5ValueUpperCaseByDefaultEncode(randomStr+sequence);
        String token=prefix+dt+sequenceStr;
        return token;
    }
}
