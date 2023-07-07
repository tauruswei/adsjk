package org.cos.common.filter;

import lombok.Data;

@Data
public class XssPattern {
    private String regex;
    private Integer flag;

    public XssPattern() {
    }

    public XssPattern(String regex, Integer flag) {
        this.regex = regex;
        this.flag = flag;
    }
    @Override
    public String toString(){
        return "XssPattern{"+
                "regex='" + regex + '\''+
                ", flag=" + flag + '}';
    }
}
