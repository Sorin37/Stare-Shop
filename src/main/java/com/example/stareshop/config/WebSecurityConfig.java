package com.example.stareshop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig implements WebMvcConfigurer {
    private static final String[] METHODS_ALLOWED = {
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.DELETE.name(),
    };

    @Bean
    SecurityFilterChain resources (HttpSecurity http) throws Exception{
        return http
            .authorizeHttpRequests()
            .requestMatchers("/user/register").permitAll()
            .requestMatchers("/user/errorPassDontMatch").permitAll()
            .requestMatchers("/product/add").hasAnyAuthority("BToCAdmin", "BToBAdmin", "Admin")
            .requestMatchers("/business/register").hasAuthority("Client")
            .requestMatchers("/business/redirect").hasAnyAuthority("Client", "BToCAdmin", "BToBAdmin", "Admin")
            .requestMatchers("/business/*").hasAnyAuthority("BToCAdmin", "BToBAdmin", "Admin")
            .requestMatchers("/request/*").hasAnyAuthority("BToCAdmin", "Admin")
            .requestMatchers("/pending/**").hasAuthority("Admin")
            .requestMatchers("/**").hasAnyAuthority("BToCAdmin", "Client", "BToBAdmin", "Admin")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login").permitAll()
            .defaultSuccessUrl("/", true)
            .and()
            .logout()
            .logoutSuccessUrl("/login").permitAll()
            .and().build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(METHODS_ALLOWED)
                .allowedOrigins("*");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
