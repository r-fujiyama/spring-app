package app.security;

import app.dao.RoleDao;
import app.dao.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

  private final UserDao userDao;
  private final RoleDao roleDao;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    var userID = authentication.getName();
    var password = authentication.getCredentials().toString();

    if (!userExists(userID, password)) {
      return null;
    }

    var role = roleDao.findByUserID(userID);
    if (role == null) {
      return null;
    }

    return new UsernamePasswordAuthenticationToken(userID, password, role.getGrantList());
  }

  private boolean userExists(String userID, String password) {
    var user = userDao.findByUserID(userID);
    if (user == null) {
      return false;
    }
    return user.getPassword().equals(password);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

}
