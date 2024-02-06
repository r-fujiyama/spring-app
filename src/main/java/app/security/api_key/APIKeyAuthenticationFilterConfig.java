package app.security.api_key;

import app.config.properties.SecurityProperties;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class APIKeyAuthenticationFilterConfig extends
    AbstractHttpConfigurer<APIKeyAuthenticationFilterConfig, HttpSecurity> {

  private final SecurityProperties securityProperties;

  @Override
  public void configure(HttpSecurity http) {
    var authenticationManager = http.getSharedObject(AuthenticationManager.class);
    var filter = new APIKeyAuthenticationFilter(authenticationManager);
    filter.setSecurityContextRepository(securityProperties.isV2APISessionEnable()
        ? new HttpSessionSecurityContextRepository()
        : new NullSecurityContextRepository());
    http.addFilter(filter);
  }

}
