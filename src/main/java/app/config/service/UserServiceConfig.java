package app.config.service;

import app.annotation.service.Corporate;
import app.annotation.service.Freelance;
import app.annotation.service.Private;
import app.service.userV1.UserV1Service;
import app.service.userV2.UserV2Service;
import app.util.ServiceUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class UserServiceConfig {

  @Bean
  @Primary
  public UserV1Service userV1Service(@Private UserV1Service privateService,
      @Freelance UserV1Service freelanceService,
      @Corporate UserV1Service corporateService) {
    var serviceMap = ServiceUtils.createServiceMap(privateService, freelanceService, corporateService);
    return ServiceUtils.newProxy(UserV1Service.class, serviceMap);
  }

  @Bean
  @Primary
  public UserV2Service userV2Service(@Private UserV2Service privateService,
      @Freelance UserV2Service freelanceService,
      @Corporate UserV2Service corporateService) {
    var serviceMap = ServiceUtils.createServiceMap(privateService, freelanceService, corporateService);
    return ServiceUtils.newProxy(UserV2Service.class, serviceMap);
  }

}
