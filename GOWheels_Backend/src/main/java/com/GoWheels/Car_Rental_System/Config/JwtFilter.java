package com.GoWheels.Car_Rental_System.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/api/auth") ||
        path.startsWith("/api/customer/register") ||
        path.startsWith("/oauth2") ||
        path.startsWith("/api/admin/create") ||
        path.startsWith("/login/oauth2") ||
        path.startsWith("/uploads") ||
        path.startsWith("/api/cars")) {

    filterChain.doFilter(request, response);
    return;
}
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            try {

                if (!jwtUtil.isTokenValid(token)) {
                    filterChain.doFilter(request, response);
                    return;
                }


                String email = jwtUtil.extractEmail(token);
                String role = jwtUtil.extractRole(token);


                SimpleGrantedAuthority authority =
                        new SimpleGrantedAuthority(role);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(authority)
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);


            } catch (Exception e) { }
        }

        filterChain.doFilter(request, response);
    }
}
