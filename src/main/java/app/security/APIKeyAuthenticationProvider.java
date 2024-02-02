package app.security;

import app.dao.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class APIKeyAuthenticationProvider implements AuthenticationProvider {

  private final UserDao userDao;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String apiKey = authentication.getCredentials().toString();
    var userInfo = userDao.findUserAndRoleByAPIKey(apiKey);
    if (userInfo == null) {
      return null;
    }
    return new AuthenticationToken(userInfo.getUser().getUserID(), apiKey, userInfo.getRole().getGrantList());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(PreAuthenticatedAuthenticationToken.class);
  }

}
