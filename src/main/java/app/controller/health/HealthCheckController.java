package app.controller.health;

import app.controller.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

  @GetMapping("v1/health")
  public Response v1Health() {
    return new Response();
  }

  @GetMapping("v2/health")
  public Response v2Health() {
    return new Response();
  }

}
