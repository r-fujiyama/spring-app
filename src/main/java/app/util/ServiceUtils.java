package app.util;

import app.TransactionInfo;
import app.enums.UserType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.EnumMap;
import java.util.Map;

public final class ServiceUtils {

  private ServiceUtils() {
  }

  public static <T> T newProxy(Class<T> clazz, T privateService, T freelanceService, T corporateService) {
    var serviceMap = createServiceMap(privateService, freelanceService, corporateService);
    var object = Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz},
        (proxy, method, args) -> {
          try {
            return method.invoke(serviceMap.get(TransactionInfo.getUserType()), args);
          } catch (InvocationTargetException e) {
            throw e.getCause();
          }
        }
    );
    return clazz.cast(object);
  }

  private static <T> Map<UserType, T> createServiceMap(T privateService, T freelanceService, T corporateService) {
    Map<UserType, T> map = new EnumMap<>(UserType.class);
    map.put(UserType.PRIVATE, privateService);
    map.put(UserType.FREELANCE, freelanceService);
    map.put(UserType.CORPORATE, corporateService);
    return map;
  }

}
