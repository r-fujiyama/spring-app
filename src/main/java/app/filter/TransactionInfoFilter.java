package app.filter;

import app.TransactionInfo;
import app.constants.DIOrder;
import app.dao.UserDao;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
    if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
      var userID = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      var user = UserDao.findByUserID(userID);
      if (user != null) {
        TransactionInfo.init(user.getUserType());
      }
    }
    filterChain.doFilter(request, response);
  }

}
