package app.config;

import app.config.properties.SecurityProperties;
import app.security.ErrorAuthenticationEntryPoint;
import app.security.api_key.APIKeyAuthenticationFilterConfig;
import app.security.api_key.APIKeyAuthenticationProvider;
import app.security.basic.UsernamePasswordAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.NullSecurityContextRepository;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

  private final SecurityProperties securityProperties;
  private final ErrorAuthenticationEntryPoint errorAuthenticationEntryPoint;

  @Bean
  public SecurityFilterChain v1APISecurityFilterChain(HttpSecurity http,
      UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider) throws Exception {
    http.securityMatcher("/v1/**")
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.GET, "/v1/health").permitAll()
            .anyRequest().authenticated()
        )
        .cors(Customizer.withDefaults())
        .csrf((csrf) -> csrf.disable())
        .sessionManagement(session -> session
            .sessionCreationPolicy(securityProperties.isV1APISessionEnable()
                ? SessionCreationPolicy.NEVER
                : SessionCreationPolicy.STATELESS)
        )
        .httpBasic((basic) -> basic.addObjectPostProcessor(new ObjectPostProcessor<BasicAuthenticationFilter>() {
          @Override
          public <O extends BasicAuthenticationFilter> O postProcess(O filter) {
            filter.setSecurityContextRepository(securityProperties.isV1APISessionEnable()
                ? new HttpSessionSecurityContextRepository()
                : new NullSecurityContextRepository());
            return filter;
          }
        }))
        .authenticationProvider(usernamePasswordAuthenticationProvider)
        .exceptionHandling(handling -> handling.authenticationEntryPoint(errorAuthenticationEntryPoint));
    return http.build();
  }

  @Bean
  public SecurityFilterChain v2APISsecurityFilterChain(HttpSecurity http,
      APIKeyAuthenticationFilterConfig apiKeyAuthenticationFilterConfig,
      APIKeyAuthenticationProvider apiKeyAuthenticationProvider) throws Exception {
    http.securityMatcher("/v2/**")
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.GET, "/v2/health").permitAll()
            .anyRequest().authenticated()
        )
        .cors(Customizer.withDefaults())
        .csrf((csrf) -> csrf.disable())
        .sessionManagement(session -> session
            .sessionCreationPolicy(securityProperties.isV2APISessionEnable()
                ? SessionCreationPolicy.NEVER
                : SessionCreationPolicy.STATELESS)
        )
        .with(apiKeyAuthenticationFilterConfig, Customizer.withDefaults())
        .authenticationProvider(apiKeyAuthenticationProvider)
        .exceptionHandling(handling -> handling.authenticationEntryPoint(errorAuthenticationEntryPoint));
    return http.build();
  }

}
