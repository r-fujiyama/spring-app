package app.filter;

import app.TransactionInfo;
import app.constants.DIOrder;
import app.dao.UserDao;
import app.entity.User;
import app.security.AuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Order(DIOrder.TRANSACTION_INFO_FILTER)
@AllArgsConstructor
@Component
public class TransactionInfoFilter extends OncePerRequestFilter {

  private final UserDao UserDao;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    User user;
    if (SecurityContextHolder.getContext().getAuthentication() instanceof AuthenticationToken authenticationToken) {
      user = UserDao.findByUserID((String) authenticationToken.getPrincipal());
      if (user == null) {
        throw new RuntimeException();
      }
      TransactionInfo.init(user.getUserType());
      filterChain.doFilter(request, response);
    } else {
      throw new RuntimeException();
    }
  }

}
