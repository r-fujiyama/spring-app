package app.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class APIKeyAuthenticationFilterConfig extends
    AbstractHttpConfigurer<APIKeyAuthenticationFilterConfig, HttpSecurity> {

  @Override
  public void configure(HttpSecurity http) {
    var authenticationManager = http.getSharedObject(AuthenticationManager.class);
    http.addFilterAfter(new APIKeyAuthenticationFilter(authenticationManager), BasicAuthenticationFilter.class);
  }

}
