package app.config;

import app.TransactionInfo;
import app.annotation.Corporate;
import app.annotation.Freelance;
import app.annotation.Private;
import app.enums.UserType;
import app.service.userV1.UserV1Service;
import app.service.userV2.UserV2Service;
import java.lang.reflect.Proxy;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ServiceConfig {

  @Bean
  @Primary
  public UserV1Service userV1Service(@Private UserV1Service privateService,
      @Freelance UserV1Service freelanceService,
      @Corporate UserV1Service corporateService) {
    Map<UserType, UserV1Service> serviceMap = createServiceMap(privateService, freelanceService, corporateService);
    return newProxy(UserV1Service.class, serviceMap);
  }

  @Bean
  @Primary
  public UserV2Service userV2Service(@Private UserV2Service privateService,
      @Freelance UserV2Service freelanceService,
      @Corporate UserV2Service corporateService) {
    Map<UserType, UserV2Service> serviceMap = createServiceMap(privateService, freelanceService, corporateService);
    return newProxy(UserV2Service.class, serviceMap);
  }

  private static <T> T newProxy(Class<T> clazz, Map<UserType, T> serviceMap) {
    var object = Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz},
        (proxy, method, args) -> method.invoke(serviceMap.get(TransactionInfo.getUserType()), args));
    return clazz.cast(object);
  }

  public static <T> Map<UserType, T> createServiceMap(T privateService, T freelanceService, T corporateService) {
    Map<UserType, T> map = new EnumMap<>(UserType.class);
    map.put(UserType.PRIVATE, privateService);
    map.put(UserType.FREELANCE, freelanceService);
    map.put(UserType.CORPORATE, corporateService);
    return map;
  }

}
