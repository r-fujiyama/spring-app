package app;

import app.enums.UserType;
import lombok.Value;

public class TransactionInfo {

  private static final ThreadLocal<ThreadVo> threadLocal = new ThreadLocal<>();

  public static void init(UserType userType) {
    ThreadVo vo = new ThreadVo(userType);
    threadLocal.set(vo);
  }

  public static void unbind() {
    threadLocal.remove();
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
      throw new IllegalThreadStateException();
    }
  }

  @Value
  private static class ThreadVo {

    UserType userType;
  }

}
