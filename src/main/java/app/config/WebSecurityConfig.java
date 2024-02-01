package app.config;

import app.security.APIKeyAuthenticationFilterConfig;
import app.security.APIKeyAuthenticationProvider;
import app.security.ErrorAuthenticationEntryPoint;
import app.security.UsernamePasswordAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

  private final ErrorAuthenticationEntryPoint errorAuthenticationEntryPoint;

  @Bean
  public SecurityFilterChain v1APISecurityFilterChain(HttpSecurity http,
      UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider) throws Exception {
    http.securityMatcher("/v1/**")
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.GET, "/v1/user/**").permitAll()
            .anyRequest().authenticated()
        )
        .cors(Customizer.withDefaults())
        .csrf((csrf) -> csrf.disable())
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .httpBasic(Customizer.withDefaults())
        .authenticationProvider(usernamePasswordAuthenticationProvider)
        .exceptionHandling(handling -> handling.authenticationEntryPoint(errorAuthenticationEntryPoint));
    return http.build();
  }

  @Bean
  public SecurityFilterChain v2APISsecurityFilterChain(HttpSecurity http,
      APIKeyAuthenticationProvider apiKeyAuthenticationProvider) throws Exception {
    http.securityMatcher("/v2/**")
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.GET, "/v2/user/**").permitAll()
            .anyRequest().authenticated()
        )
        .cors(Customizer.withDefaults())
        .csrf((csrf) -> csrf.disable())
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .with(new APIKeyAuthenticationFilterConfig(), Customizer.withDefaults())
        .authenticationProvider(apiKeyAuthenticationProvider)
        .exceptionHandling(handling -> handling.authenticationEntryPoint(errorAuthenticationEntryPoint));
    return http.build();
  }

}
