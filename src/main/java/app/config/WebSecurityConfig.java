package app.config;

import app.security.ErrorAuthenticationEntryPoint;
import app.security.TokenAuthenticationProvider;
import app.security.UsernamePasswordAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain v1APISecurityFilterChain(HttpSecurity http,
      UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider,
      ErrorAuthenticationEntryPoint errorAuthenticationEntryPoint) throws Exception {
    http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.GET,"/v1/user/**").permitAll()
            .anyRequest().authenticated()
        )
        .securityMatcher("/v1/**")
        .csrf().disable()
        .authenticationProvider(usernamePasswordAuthenticationProvider)
        .httpBasic().and()
        .exceptionHandling()
        .authenticationEntryPoint(errorAuthenticationEntryPoint);
    return http.build();
  }

  @Bean
  public SecurityFilterChain v2APISsecurityFilterChain(HttpSecurity http,
      TokenAuthenticationProvider tokenAuthenticationProvider,
      ErrorAuthenticationEntryPoint errorAuthenticationEntryPoint) throws Exception {
    http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.GET,"/v1/user/**").permitAll()
            .anyRequest().authenticated()
        )
        .securityMatcher("/v2/**")
        .csrf().disable()
        .authenticationProvider(tokenAuthenticationProvider)
        .httpBasic().and()
        .exceptionHandling()
        .authenticationEntryPoint(errorAuthenticationEntryPoint);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    var authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    return authenticationManagerBuilder.build();
  }

}
