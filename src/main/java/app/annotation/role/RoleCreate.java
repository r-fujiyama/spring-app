package app.annotation.role;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.access.prepost.PreAuthorize;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('CREATE')")
public @interface RoleCreate {

}
