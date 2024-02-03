package app.security.api_key;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@AllArgsConstructor
public class APIKeyAuthenticationFilterConfig extends
    AbstractHttpConfigurer<APIKeyAuthenticationFilterConfig, HttpSecurity> {

  @Override
  public void configure(HttpSecurity http) {
    var authenticationManager = http.getSharedObject(AuthenticationManager.class);
    var apiKeyAuthenticationFilter = new APIKeyAuthenticationFilter(authenticationManager);
    http.addFilter(apiKeyAuthenticationFilter);
  }

}
