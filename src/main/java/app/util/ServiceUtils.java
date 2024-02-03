package app.util;

import app.TransactionInfo;
import app.enums.UserType;
import java.lang.reflect.Proxy;
import java.util.EnumMap;
import java.util.Map;

public final class ServiceUtils {

  private ServiceUtils() {
  }

  public static <T> T newProxy(Class<T> clazz, Map<UserType, T> serviceMap) {
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
