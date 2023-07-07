package org.cos.common.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        } else {
            int count = values.length;
            String[] encodedValues = new String[count];
            for (int i = 0; i < count; i++) {
                encodedValues[i] = this.encodeXSS(values[i]);
            }
            return encodedValues;
        }
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return this.encodeXSS(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(this.encodeXSS(name));
        if (value != null) {
            value = this.encodeXSS(value);
        }
        return value;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> requestMap = super.getParameterMap();
        for (Map.Entry entry : requestMap.entrySet()) {
            String[] values = (String[]) entry.getValue();
            for (int i = 0; i < values.length; i++) {
                values[i] = this.encodeXSS(values[i]);
            }
        }
        return requestMap;
    }

    private String encodeXSS(String value) {
        return XssUtils.stripXss(value);
    }
}
