package app.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.context.SecurityContextRepository;

public class APIKeyAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

  private static final String API_KEY = "API-Key";

  public APIKeyAuthenticationFilter(AuthenticationManager authenticationManager,
      SecurityContextRepository securityContextRepository) {
    setAuthenticationManager(authenticationManager);
    setSecurityContextRepository(securityContextRepository);
  }

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    return "";
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    var authorization = request.getHeader(API_KEY);
    return authorization != null ? authorization : "";
  }

}
