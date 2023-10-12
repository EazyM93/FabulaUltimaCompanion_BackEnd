package eazym.FabulaUltimaCompanion_BackEnd.security.config;

import eazym.FabulaUltimaCompanion_BackEnd.exceptions.UnauthorizedException;
import eazym.FabulaUltimaCompanion_BackEnd.security.user.User;
import eazym.FabulaUltimaCompanion_BackEnd.security.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    JwtTools jwtTools;

    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer"))
            throw new UnauthorizedException("Token not found in authorization header!");

        String jwtToken = authHeader.substring(7);
        System.out.println("Token: " + jwtToken);

        jwtTools.verifyToken(jwtToken); // verify if token is not manipulated or expired

        String id = jwtTools.extractSubject(jwtToken);
        User currentUser = userService.findById(UUID.fromString(id));

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        currentUser,
                        null,
                        currentUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        System.out.println(request.getServletPath());
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }

}
