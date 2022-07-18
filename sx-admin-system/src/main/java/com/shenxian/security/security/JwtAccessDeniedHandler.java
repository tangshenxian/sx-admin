package com.shenxian.security.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shenxian.utils.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当用户在没有授权的情况下访问受保护的REST资源时，将调用此方法发送403 Forbidden响应
 * @author: shenxian
 * @date: 2022/7/14 14:50
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        PrintWriter writer = httpServletResponse.getWriter();
        Result result = Result.error().code(HttpServletResponse.SC_FORBIDDEN).message(e.getMessage());
        writer.write(new ObjectMapper().writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}
