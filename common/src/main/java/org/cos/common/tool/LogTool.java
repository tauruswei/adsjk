package org.cos.common.tool;

import org.cos.common.result.CodeMsg;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public final class LogTool {
    public static final String STATUS_FAIL = "fail";
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 失败的日志
     * @param keyName
     * @param codeMsg
     * @return
     */
    public static String failLog(List keyName, CodeMsg codeMsg) {
        StringBuilder sbf = new StringBuilder();

        sbf.append("[errcode:").append(codeMsg.getCode()).append(", ");
        sbf.append("errmsg:").append(codeMsg.getMsg()).append("] ");

        if (!keyName.isEmpty()) {
            sbf.append("[args =");
            sbf.append(keyName.toString());
            sbf.append("] ");
        }
        return sbf.toString();
    }

    /**
     * 成功的日志
     * @param keyName
     * @param codeMsg
     * @return
     */
    public static String successLog( List keyName, CodeMsg codeMsg) {
        StringBuilder sbf = new StringBuilder();

        sbf.append("[code: ").append(codeMsg.getCode()).append(", ");
        sbf.append("msg:").append(codeMsg.getMsg()).append("] ");

        if (!keyName.isEmpty()) {
            sbf.append("[args =");
            sbf.append(keyName.toString());
            sbf.append("] ");
        }
        return sbf.toString();
    }
    /**
     * 获取当前的文件名和行号
     */
    public static List<String> returnErrorInfo() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        String errorFileName = e.getFileName();
        String errorLineNum = e.getLineNumber() + "";
        List list = new ArrayList<String>();
        list.add(errorFileName);
        list.add(errorLineNum);
        return list;
    }
}
