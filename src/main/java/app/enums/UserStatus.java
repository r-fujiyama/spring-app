package app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserStatus implements CodeValue<String, String> {

  UNKNOWN("Unknown", "Unknown"),
  UNREGISTERED("0", "Unregistered"),
  REGISTERED("1", "Registered"),
  BLOCKED("2", "blocked"),
  DELETED("9", "deleted");

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

  public static UserStatus fromCode(String code) {
    return CodeValue.fromCode(Arrays.asList(values()), code, UNKNOWN);
  }

  @JsonCreator
  public static UserStatus fromValue(String value) {
    return CodeValue.fromValue(Arrays.asList(values()), value, UNKNOWN);
  }

}
