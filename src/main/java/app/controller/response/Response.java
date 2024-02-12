package app.controller.response;

import app.enums.APIResult;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Response {

  private final APIResult status;
  private final List<Error> errors;

  public Response() {
    this.status = APIResult.SUCCESS;
    this.errors = Collections.emptyList();
  }

  public Response(Error... errors) {
    this.status = APIResult.FAILURE;
    this.errors = Arrays.asList(errors);
  }

  public Response(List<Error> errors) {
    this.status = APIResult.FAILURE;
    this.errors = errors;
  }

}
