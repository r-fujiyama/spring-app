package app.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import lombok.AllArgsConstructor;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(Pattern.List.class)
@Documented
@Constraint(validatedBy = {PatternValidator.class})
public @interface Pattern {

  String regexp();

  Flag flag() default Flag.SKIP_BLANK;

  String message() default "{validation.constraints.Pattern.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  @AllArgsConstructor
  enum Flag {
    NORMAL,
    SKIP_BLANK
  }

  @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
  @Retention(RUNTIME)
  @Documented
  @interface List {

    Pattern[] value();
  }
}
