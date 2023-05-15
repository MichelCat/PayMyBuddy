package com.paymybuddy.paymybuddy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class PaymybuddySecurityConfiguration {
  
  private static final String[] AUTH_WHITELIST = {
      "/registrer/**","/home", "/transfer", "/profile", "/contact"  };
  
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors(withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
                              .requestMatchers(AUTH_WHITELIST).authenticated()
                              .anyRequest().permitAll()
        );
      
    http
        .formLogin(a -> a.loginPage("/login")
           .loginProcessingUrl("/login")
           .defaultSuccessUrl("/home")
           .failureUrl("/login-error.html")
           .permitAll());
    
    http
        .logout(a -> a.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .permitAll());

    return http.build();
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder();
  }
}