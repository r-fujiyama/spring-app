package app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum APIResult implements CodeValue<String, String> {

  UNKNOWN("Unknown", "Unknown"),
  SUCCESS("0", "Success"),
  FAILURE("9", "Failure");

  private final String code;
  private final String value;

  @Override
  public String getCode() {
    return this.code;
  }

  @JsonValue
  @Override
  public String getValue() {
    return this.value;
  }

  public static APIResult fromCode(String code) {
    return CodeValue.fromCode(Arrays.asList(values()), code, UNKNOWN);
  }

  @JsonCreator
  public static APIResult fromValue(String value) {
    return CodeValue.fromValue(Arrays.asList(values()), value, UNKNOWN);
  }

}
