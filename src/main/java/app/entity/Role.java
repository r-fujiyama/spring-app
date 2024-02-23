package app.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Role {

  private long userID;
  private boolean allowCreate;
  private boolean allowRead;
  private boolean allowUpdate;
  private boolean allowDelete;
  private String updatedBy;
  private LocalDateTime updatedAt;
  private String createdBy;
  private LocalDateTime createdAt;

  public List<SimpleGrantedAuthority> getGrantList() {
    var grantList = new ArrayList<SimpleGrantedAuthority>();
    if (allowCreate) {
      grantList.add(new SimpleGrantedAuthority(app.constants.Role.CREATE));
    }
    if (allowRead) {
      grantList.add(new SimpleGrantedAuthority(app.constants.Role.READ));
    }
    if (allowUpdate) {
      grantList.add(new SimpleGrantedAuthority(app.constants.Role.UPDATE));
    }
    if (allowDelete) {
      grantList.add(new SimpleGrantedAuthority(app.constants.Role.DELETE));
    }
    return grantList;
  }

}
