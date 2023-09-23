package app.security;

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

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String userID = authentication.getName();
    String password = authentication.getCredentials().toString();

    var user = userDao.findByUserID(userID);
    if (user == null) {
      return null;
    }
    if (!user.getPassword().equals(password)) {
      return null;
    }
    return new UsernamePasswordAuthenticationToken(userID, password, user.getRole().toList());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

}
