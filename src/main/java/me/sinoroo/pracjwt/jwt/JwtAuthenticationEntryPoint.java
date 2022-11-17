package me.sinoroo.pracjwt.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
   @Override
   public void commence(HttpServletRequest request,
                        HttpServletResponse response,
                        AuthenticationException authException) throws IOException {
      // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      // 위의 방식으로 바로 Error처리해되 되지만
      // 아래와 같이 별도의 처리 루틴으로 전달
      //response.sendRedirect("/exception/entrypoint");
   }
}
