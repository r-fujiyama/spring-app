package app.security;

import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Getter
public class AuthenticationToken extends PreAuthenticatedAuthenticationToken {

  public AuthenticationToken(Object aPrincipal, Object aCredentials,
      Collection<? extends GrantedAuthority> anAuthorities) {
    super(aPrincipal, aCredentials, anAuthorities);
  }

}
