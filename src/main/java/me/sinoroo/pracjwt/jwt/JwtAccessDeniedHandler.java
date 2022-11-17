package me.sinoroo.pracjwt.jwt;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;


@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler{

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            org.springframework.security.access.AccessDeniedException accessDeniedException)
            throws java.io.IOException, ServletException {
                // 권한이 다른 경우 403
                //response.sendError(HttpServletResponse.SC_FORBIDDEN);
                // 위의 방식으로 바로 Error처리해도 되지만
                // 아래와 같이 별도의 처리 루틴으로 전달
                response.sendRedirect("/exception/entrypoint");
    }
    
}
