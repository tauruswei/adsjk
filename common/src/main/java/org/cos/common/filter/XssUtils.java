package org.cos.common.filter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by wjy on 2020/11/5.
 * xss工具类
 */
public class XssUtils {

    private static List<Pattern> PATTERNLIST;

    private static List<XssPattern> getXssPatternList() {
        List<XssPattern> ret = new ArrayList<>();
        ret.add(new XssPattern("<(no)?script[^>]*>.*?</(no)?script>", 2));
        ret.add(new XssPattern("<(no)?iframe[^>]*>.*?</(no)?iframe>", 2));
        ret.add(new XssPattern("eval\\((.*?)\\)", 42));
        ret.add(new XssPattern("expression\\((.*?)\\)", 42));
        ret.add(new XssPattern("(window\\.location|window\\.|\\.location|document\\" +
                ".cookie|document\\.|alert\\(.*?\\)|window\\.open\\()*", 42));
        ret.add(new XssPattern("<+\\s*\\w*\\s*(oncontrolselect|oncopy|oncut|ondataavailabte|" +
                "ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|" +
                "ondragleave|ondragover|ondragstant|ondrop|onerror=|onerroupdate|onfilterchange|onfinish|" +
                "onfocus|onfocusin|onfocusout|onhelp|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|" +
                "onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove|onmousout|onmouseover|onmouseup|" +
                "onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|onafterprint|onafterupdate|onbefore|" +
                "onbeforeactivate|onbeforecopy|onbeforecut|onbeforedeactivate|onbeforeeditocus|onbeforepaste|onbeforeprint|" +
                "onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange|onchange|onclick|oncontextmenu|onpaste|" +
                "onpropertychange|onceadystatechange|onreset|onresize|onresizend|onresizestant|onrowenter|onrowexit|" +
                "onrowsdelete|onrowsinserted|onsoroll|onselect|onselectionchange|onselectstant|onstart|onstop|onsubmit|" +
                "onunload)+\\s*=+", 42));
        return ret;
    }
    private static List<Pattern> getPatterns(){
        if(PATTERNLIST==null){
            PATTERNLIST = getXssPatternList().stream().map(xssPattern -> Pattern.compile(xssPattern.getRegex(),xssPattern.getFlag())).collect(Collectors.toList());
        }
        return PATTERNLIST;
    }


    public static String stripXss(String value){
        if(StringUtils.isNotBlank(value)){
            Matcher matcher;
            for ( Pattern pattern:getPatterns()){
                matcher = pattern.matcher(value);
                if(matcher.find()){
                    value = matcher.replaceAll("");
                }
            }
            value = value.replaceAll("<","<").replaceAll(">",">");
        }
        return value;
    }
}
