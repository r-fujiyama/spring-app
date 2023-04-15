package app.filter;

import app.config.properties.SecurityProperties;
import app.constants.DIOrder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Order(DIOrder.LOGGING_FILTER)
@AllArgsConstructor
@Component
public class LoggingFilter extends OncePerRequestFilter {

  private final SecurityProperties securityProperties;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {
    MDC.put("Trace-ID", UUID.randomUUID().toString());
    var requestWrapper = new CachedBodyHttpServletRequest(request);
    var responseWrapper = new ContentCachingResponseWrapper(response);
    try {
      logger.info(createRequestLog(requestWrapper));
      filterChain.doFilter(requestWrapper, responseWrapper);
    } finally {
      logger.info(createResponseLog(responseWrapper));
      responseWrapper.copyBodyToResponse();
      MDC.clear();
    }
  }

  private String createRequestLog(CachedBodyHttpServletRequest request) {
    var msg = new StringBuilder("START, ");
    msg.append(request.getMethod()).append(' ');
    msg.append(request.getRequestURI());

    var queryString = request.getQueryString();
    if (queryString != null) {
      msg.append('?').append(queryString);
    }

    var client = request.getRemoteAddr();
    if (StringUtils.hasLength(client)) {
      msg.append(", client=").append(client);
    }

    var session = request.getSession(false);
    if (session != null) {
      msg.append(", session=").append(session.getId());
    }

    var user = request.getRemoteUser();
    if (user != null) {
      msg.append(", user=").append(user);
    }

    var headers = new ServletServerHttpRequest(request).getHeaders();
    securityProperties.getMaskRequestHeaderNames().forEach(maskHeaderName -> {
      var header = headers.get(maskHeaderName);
      if (header == null) {
        return;
      }
      header.clear();
      header.add("*****");
    });
    msg.append(", headers=").append(headers);

    msg.append(", body=").append(request.getReader().lines().collect(Collectors.joining()));
    return msg.toString();
  }

  private String createResponseLog(ContentCachingResponseWrapper response) throws UnsupportedEncodingException {
    var msg = new StringBuilder("END");
    msg.append(", headers=[");
    for (Iterator<String> iterator = response.getHeaderNames().iterator(); iterator.hasNext(); ) {
      var headerName = iterator.next();
      msg.append(headerName).append(":");
      if (!iterator.hasNext()) {
        msg.append(response.getHeader(headerName));
        break;
      }
      msg.append(response.getHeader(headerName)).append(", ");
    }
    msg.append("]");
    msg.append(", body=").append(new String(response.getContentAsByteArray(), response.getCharacterEncoding()));
    return msg.toString();
  }

}
