package app.controller.health;

import app.controller.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

  @GetMapping("v1/health-check")
  public Response v1HealthCheck() {
    return new Response();
  }

  @GetMapping("v2/health-check")
  public Response v2HealthCheck() {
    return new Response();
  }

}
