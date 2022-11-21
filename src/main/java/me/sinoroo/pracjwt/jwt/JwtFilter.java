package me.sinoroo.pracjwt.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import me.sinoroo.pracjwt.jwt.TokenProvider.JwtCode;
import me.sinoroo.pracjwt.service.SecurityService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

   private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
   public static final String AUTHORIZATION_HEADER = "Authorization";
   public static final String REFRESH_HEADER = "Refresh";
   private TokenProvider tokenProvider;
   private SecurityService securityService;
   public JwtFilter(TokenProvider tokenProvider, SecurityService securityService) {
      this.tokenProvider = tokenProvider;
      this.securityService = securityService;
   }

   @Override
   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
      HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

      String jwt = resolveToken(httpServletRequest, AUTHORIZATION_HEADER);

      String requestURI = httpServletRequest.getRequestURI();

      if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt) == JwtCode.ACCESS) {
         Authentication authentication = tokenProvider.getAuthentication(jwt);
         SecurityContextHolder.getContext().setAuthentication(authentication);
         logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
      /*
      } else if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt) == JwtCode.EXPIRED) {
         String refresh = resolveToken(httpServletRequest, REFRESH_HEADER);
         
         // refresh token을 확인해서 재발급해준다
         if(refresh != null && tokenProvider.validateToken(refresh) == JwtCode.ACCESS){
            String newRefresh = securityService.reissue(refresh);
            if(newRefresh != null){
               httpServletResponse.setHeader(REFRESH_HEADER, "Bearer "+newRefresh);

                // access token 생성
                Authentication authentication = tokenProvider.getAuthentication(refresh);
                httpServletResponse.setHeader(AUTHORIZATION_HEADER, "Bearer "+tokenProvider.createAccessToken(authentication));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("reissue refresh Token & access Token");
            }
        }
      */
      } else {
         logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
      }

      filterChain.doFilter(servletRequest, servletResponse);
   }

   private String resolveToken(HttpServletRequest request, String header) {
      String bearerToken = request.getHeader(header);

      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
         return bearerToken.substring(7);
      }

      return null;
   }
}
