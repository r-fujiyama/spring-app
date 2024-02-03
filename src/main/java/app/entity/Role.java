package app.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Data
public class Role {

  private String userID;
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
