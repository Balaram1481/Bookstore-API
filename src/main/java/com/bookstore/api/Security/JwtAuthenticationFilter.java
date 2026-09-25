package com.bookstore.api.Security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanRegistrarDslMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bookstore.api.dto.JwtTokenProvider;

import java.io.IOException;

@Component 

public class JwtAuthenticationFilter extends OncePerRequestFilter{
    @Autowired 
    private JwtTokenProvider jwtTokenProvider;

    @Autowired 
    private CustomuserDetailsService userDetailsService;

    @Override 
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException,IOException {
        try{
            System.out.println(
            "FILTER HIT -> "
            + request.getMethod()
            + " "
            + request.getRequestURI()
            );
            String token=getTokenFromRequest(request);
            if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
            String username = jwtTokenProvider.getUsername(token);
            System.out.println("DEBUG: Token validation successfully for username : " + username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println("DEBUG: loaded userDetails: "+ userDetails.getUsername()+"with Authorization "+userDetails.getAuthorities());
            UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            System.out.println("DeBUG: Securitycontext updated Successfully!");
            }
        }
        catch (Exception ex){
            System.out.println("DEBUG Exception in Filter:"+ ex.getMessage());
            ex.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }
    private String getTokenFromRequest(HttpServletRequest request)
    {
        String bearerToken=request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;

    }


}
