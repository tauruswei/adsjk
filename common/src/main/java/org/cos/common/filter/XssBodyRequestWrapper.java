package org.cos.common.filter;


import org.apache.commons.io.Charsets;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Created by wjy on 2020/11/5.
 * xss 包装
 */
public class XssBodyRequestWrapper extends HttpServletRequestWrapper {
    private String body;

    public XssBodyRequestWrapper(HttpServletRequest request, String context) {
        super(request);
        this.body = context;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException{
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.body.getBytes(StandardCharsets.UTF_8));
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public int read() { return byteArrayInputStream.read ();}
            @Override
            public boolean isFinished(){ return false; }
            @Override
            public boolean isReady() {return false;}
            @Override
            public void setReadListener(ReadListener Listener) {};
        };
        return servletInputStream;
    }


        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(this.getInputStream(), Charsets.UTF_8));
        }
}