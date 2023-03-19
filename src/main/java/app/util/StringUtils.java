package app.util;


import com.google.common.base.Strings;

public final class StringUtils {

  private StringUtils() {
  }

  public static boolean isEmpty(String s) {
    return Strings.isNullOrEmpty(s);
  }

  public static boolean isBlank(String s) {
    if (isEmpty(s)) {
      return true;
    }
    return s.chars().allMatch(Character::isWhitespace);
  }

}
