package app.security;

import app.config.properties.SecurityProperties;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class APIKeyAuthenticationProvider implements AuthenticationProvider {

  private final SecurityProperties securityProperties;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String apiKey = authentication.getCredentials().toString();
    if (apiKey.equals(securityProperties.getApiKey())) {
      return new PreAuthenticatedAuthenticationToken("", apiKey, new ArrayList<>());
    }
    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(PreAuthenticatedAuthenticationToken.class);
  }

}
