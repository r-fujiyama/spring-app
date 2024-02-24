package app;

import app.enums.UserType;
import lombok.Value;

public class TransactionInfo {

  private static final ThreadLocal<ThreadVo> threadLocal = new ThreadLocal<>();

  public static void init(String userName, UserType userType) {
    ThreadVo vo = new ThreadVo(userName, userType);
    threadLocal.set(vo);
  }

  public static void unbind() {
    threadLocal.remove();
  }

  public static String getUserName() {
    checkInitialized();
    return threadLocal.get().getUserName();
  }

  public static UserType getUserType() {
    checkInitialized();
    return threadLocal.get().getUserType();
  }

  public static boolean isInitialized() {
    return threadLocal.get() != null;
  }

  private static void checkInitialized() {
    if (!isInitialized()) {
      throw new IllegalThreadStateException("ThreadLocal is not initialized.");
    }
  }

  @Value
  private static class ThreadVo {

    String userName;
    UserType userType;
  }

}
