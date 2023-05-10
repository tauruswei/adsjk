package org.cos.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtil {

    private static final String JDBC_PATTERN="jdbc:(?<type>[a-zA-Z0-9]+)://(?<host>[a-zA-Z0-9-//.]+):(?<port>[0-9]+)/(?<database>[a-zA-Z0-9_//-]+)?";

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+){0,4}@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+){0,4}$";

    public static Map<String,Object> getJDBCData(String url){
        Map<String,Object> map = new HashMap<>();
        Pattern namePattern = Pattern.compile(JDBC_PATTERN);
        Matcher dateMatcher = namePattern.matcher(url);
        while (dateMatcher.find()) {
            map.put("type",dateMatcher.group("type"));
            map.put("host",dateMatcher.group("host"));
            map.put("port",Integer.valueOf(dateMatcher.group("port")));
            map.put("database",dateMatcher.group("database"));
        }
        return map;
    }

    public static boolean isEmail(String email){
        return Pattern.compile(EMAIL_PATTERN).matcher(email).find();
    }

    public static String trimFirstAndLastChar(String str, String element){
        boolean beginIndexFlag = true;
        boolean endIndexFlag = true;
        do{
            int beginIndex = str.indexOf(element) == 0 ? 1 : 0;
            int endIndex = str.lastIndexOf(element) + 1 == str.length() ? str.lastIndexOf(element) : str.length();
            str = str.substring(beginIndex, endIndex);
            beginIndexFlag = (str.indexOf(element) == 0);
            endIndexFlag = (str.lastIndexOf(element) + 1 == str.length());
        } while (beginIndexFlag || endIndexFlag);
        return str;
    }
}
