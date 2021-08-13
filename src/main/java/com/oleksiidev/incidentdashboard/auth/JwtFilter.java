//package com.oleksiidev.incidentdashboard.auth;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.GenericFilterBean;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//import static org.springframework.util.StringUtils.hasText;
//
//@RequiredArgsConstructor
//@Component
//public class JwtFilter extends GenericFilterBean {
//
//    public static final String AUTHORIZATION = "Authorization";
//    public static final String BEARER = "Bearer ";
//
//    private final JwtProvider jwtProvider;
//    private final CustomUserDetailsService customUserDetailsService;
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//            throws IOException, ServletException {
//        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
//
//        if (token != null && jwtProvider.validateToken(token)) {
//            String userLogin = jwtProvider.getLoginFromToken(token);
//            CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(userLogin);
//            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails,
//                    null, customUserDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }
//
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//
//    private String getTokenFromRequest(HttpServletRequest request) {
//        String bearer = request.getHeader(AUTHORIZATION);
//
//        if (hasText(bearer) && bearer.startsWith(BEARER)) {
//            return bearer.substring(BEARER.length());
//        }
//
//        return null;
//    }
//}
