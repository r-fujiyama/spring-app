package app.security;

import app.dao.RoleDao;
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
  private final RoleDao roleDao;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String apiKey = authentication.getCredentials().toString();
    var user = userDao.findByAPIKey(apiKey);
    if (user == null) {
      return null;
    }

    var role = roleDao.findByUserID(user.getUserID());
    if (role == null) {
      return null;
    }
    return new PreAuthenticatedAuthenticationToken("", user.getUserID(), role.getGrantList());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(PreAuthenticatedAuthenticationToken.class);
  }

}
