package cz.kdolezal.culturaleventshub.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtService.extractTokenFromCookies(request);
        Long userId = null;

        if(token!= null){
            //username = jwtService.extractUserName(token);
            userId = jwtService.extractUserId(token);
        }

        if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = context.getBean(CustomUserDetailsService.class).loadUserByUserId(userId);

            if(jwtService.validateToken(token, userDetails)){
                List<GrantedAuthority> authorityList = jwtService.extractAuthorities(token);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorityList);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));	// adding a request to the authorized token
                SecurityContextHolder.getContext().setAuthentication(authToken);	// add the token to the chain.
            }
        }

        filterChain.doFilter(request, response);
    }
}
