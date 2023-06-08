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
  

//  @Autowired
//  private DataSource dataSource;
//  
//  @Autowired
//  private AppUserBusiness appUserBusiness;
//  
//  @Bean
//  public UserDetailsManager users(HttpSecurity http) throws Exception {
//      AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class)
//          .userDetailsService(appUserBusiness)
//          .passwordEncoder(passwordEncoder())
//          .build();
//
//      JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//      jdbcUserDetailsManager.setAuthenticationManager(authenticationManager);
//      return jdbcUserDetailsManager;
//  }  
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors(withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/admin/**").hasAuthority(AppUserRole.ADMIN_ROLE.name())
            .requestMatchers("/user/**").hasAuthority(AppUserRole.USER_ROLE.name())
            .anyRequest().permitAll()
        );

    http
        .formLogin(a -> a.loginPage("/login")
           .loginProcessingUrl("/login")
           .successHandler(successHandler())
           .failureUrl("/login?error=true")
           .permitAll());

    http
          .logout(withDefaults());
    
    http
          .rememberMe()
          .key("LUJDU_DYJNF8FLFOI_Foifkqsi_432824412");

    return http.build();
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder();
  }
  
  /**
   * Custom success handler
   */
  @Bean
  public PaymybuddyAuthenticationSuccessHandler successHandler() {
    return new PaymybuddyAuthenticationSuccessHandler();
  }
  
  
  
//  public SecurityFilterChain filterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
//  
//          .rememberMeServices(rememberMeServices);  
//  
//  @Autowired
//  private DataSource dataSource;
//
//  @Bean
//  public JdbcTokenRepositoryImpl tokenRepository() {
//      JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//      tokenRepository.setDataSource(dataSource);
//      return tokenRepository;
//  }
  
  
  
// public SecurityFilterChain filterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
//  
//  @Bean
//  RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
//      RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
//      TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices("456987161654664161616_6fdsgg", userDetailsService, encodingAlgorithm);
//      rememberMe.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
//      return rememberMe;
//  }
  
//  @Autowired
//  private DataSource dataSource;
//
//  @Autowired
//  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//      auth.jdbcAuthentication()
//          .dataSource(dataSource)
//          .usersByUsernameQuery("select user_email as email, user_password as password, user_enabled as enabled "
//              + "from app_user "
//              + "where user_email = ?")
//          .authoritiesByUsernameQuery("select user_email as email, user_role as authority "
//              + "from app_user "
//              + "where user_email = ?");
//  }
  
}
