package com.GoWheels.Car_Rental_System.Config;

import com.GoWheels.Car_Rental_System.Entity.Admin;
import com.GoWheels.Car_Rental_System.Entity.Customer;
import com.GoWheels.Car_Rental_System.Service.AdminService;
import com.GoWheels.Car_Rental_System.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomerService customerService;
    private final AdminService adminService;
    private final JwtFilter jwtFilter;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(Customizer.withDefaults())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/admin/create"
                        ).permitAll()

                        .requestMatchers(
                                "/api/auth/signin",
                                "/api/customer/register",
                                "/oauth2/**",
                                "/login/oauth2/**",
                                "/api/cars/**",
                                "/uploads/**",
                                "/api/customer/view/**"
                        ).permitAll()

                        .requestMatchers("/api/customer/**")
                        .hasAnyAuthority(
                                "CUSTOMER",
                                "ROLE_CUSTOMER"
                        )

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/admin/**"
                        ).hasAnyAuthority(
                                "ADMIN",
                                "ROLE_ADMIN"
                        )

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/admin/**"
                        ).hasAnyAuthority(
                                "ADMIN",
                                "ROLE_ADMIN"
                        )

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/admin/**"
                        ).hasAnyAuthority(
                                "ADMIN",
                                "ROLE_ADMIN"
                        )

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/admin/**"
                        ).hasAnyAuthority(
                                "ADMIN",
                                "ROLE_ADMIN"
                        )

                        .anyRequest()
                        .authenticated()
                )

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, exx) -> {
                            res.sendError(401, "Unauthorized");
                        })
                )

                .formLogin(form -> form.disable())

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .oauth2Login(oauth -> oauth
                        .successHandler((request, response, authentication) -> {

                            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
                            String email = oauthUser.getAttribute("email");
                            String providerId = oauthUser.getAttribute("sub");

                            if (email == null || providerId == null) {
                                response.sendError(400, "Invalid Google response");
                                return;
                            }

                            email = email.toLowerCase().trim();

                            try {

                                Admin admin = adminService.loginWithGoogle(email, providerId);

                                String token = jwtUtil.generateToken(
                                        admin.getEmail(),
                                        admin.getRole().name()
                                );

                                response.sendRedirect(
                                        "http://localhost:5173/oauth-success?token=" + token
                                );
                                return;

                            } catch (Exception e) {
                            }

                            Customer customer = customerService.loginWithGoogle(email, providerId);

                            String token = jwtUtil.generateToken(
                                    customer.getEmail(),
                                    customer.getRole().name()
                            );
                            response.sendRedirect(
                                    "http://localhost:5173/oauth-success?token=" + token
                            );
                        })
                );

        return http.build();
    }
}