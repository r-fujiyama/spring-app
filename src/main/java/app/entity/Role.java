package app.entity;

import app.constants.RoleName;
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
      grantList.add(new SimpleGrantedAuthority(RoleName.CREATE));
    }
    if (allowRead) {
      grantList.add(new SimpleGrantedAuthority(RoleName.READ));
    }
    if (allowUpdate) {
      grantList.add(new SimpleGrantedAuthority(RoleName.UPDATE));
    }
    if (allowDelete) {
      grantList.add(new SimpleGrantedAuthority(RoleName.DELETE));
    }
    return grantList;
  }

}
