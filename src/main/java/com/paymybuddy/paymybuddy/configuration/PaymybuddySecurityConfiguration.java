package com.paymybuddy.paymybuddy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.paymybuddy.paymybuddy.dao.user.entities.AppUserRole;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class PaymybuddySecurityConfiguration {
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors(withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/default").authenticated()
            .requestMatchers("/admin/**").hasAuthority(AppUserRole.ADMIN_ROLE.name())
            .requestMatchers("/user/**").hasAuthority(AppUserRole.USER_ROLE.name())
            .anyRequest().permitAll()
        );
      
    http
        .formLogin(a -> a.loginPage("/login")
           .loginProcessingUrl("/login")
           .defaultSuccessUrl("/default")
           .failureUrl("/login-error")
           .permitAll());

      http
            .logout(withDefaults());

    return http.build();
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder();
  }
}
