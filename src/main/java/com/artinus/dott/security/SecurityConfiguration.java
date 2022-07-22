package com.artinus.dott.security;

import com.artinus.dott.api.dto.type.RoleType;
import com.artinus.dott.api.service.AuthService;
import com.artinus.dott.security.filter.JwtAuthFilter;
import com.artinus.dott.security.filter.exception.AccessDeniedHandlerImpl;
import com.artinus.dott.security.filter.exception.AuthExceptionHandler;
import com.artinus.dott.security.filter.exception.AuthenticationEntryPointImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final String baseUrl = "/artinus-dott/api/v1";

    private final AuthService authService;
    private final AuthExceptionHandler authExceptionHandler;
    private final AccessDeniedHandlerImpl accessDeniedHandlerImpl;
    private final AuthenticationEntryPointImpl authenticationEntryPoint;

    public HttpSecurity commonConfigure(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .httpBasic().disable()
                    .cors().configurationSource(corsConfigurationSource())
                .and()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .headers().frameOptions().sameOrigin()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandlerImpl)
                .and();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().antMatchers(
                "/h2-console/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                baseUrl + "/auth/signUp",
                baseUrl + "/auth/signIn",
                baseUrl + "/member/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return commonConfigure(httpSecurity)
                    .authorizeRequests()
                    .anyRequest().hasAuthority(RoleType.USER_ROLE.name())
                .and()
                    .addFilterBefore(new JwtAuthFilter(authService, authExceptionHandler), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
