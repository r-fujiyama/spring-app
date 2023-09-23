package app.model;

import app.enums.Role;
import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Roles {

  private final HashMap<Role, Integer> roleMap;
  private static final int ROLE_COUNT = Role.values().length - 1;
  private static final int MAX_ROLE_INT = (1 << ROLE_COUNT) - 1;

  public Roles(Role... roles) {
    roleMap = new HashMap<>();
    for (Role role : roles) {
      roleMap.put(role, role.getCode());
    }
  }

  public Roles(int roleInt) {
    if (roleInt > MAX_ROLE_INT) {
      throw new IllegalArgumentException(
          "max value that can be specified for roleInt: " + MAX_ROLE_INT + ". Specified value: " + roleInt + ".");
    }
    var rolesBinary = Integer.toBinaryString(roleInt);
    rolesBinary = Strings.padStart(rolesBinary, ROLE_COUNT, '0');

    var roleBinaryList = Arrays.asList(rolesBinary.split(""));
    Collections.reverse(roleBinaryList);

    roleMap = new HashMap<>();
    for (int i = 0; i < roleBinaryList.size(); i++) {
      var roleBinary = Strings.padEnd(roleBinaryList.get(i), i + 1, '0');
      var roleCode = Integer.parseInt(roleBinary, 2);
      var role = Role.fromCode(roleCode);
      if (role == Role.UNKNOWN) {
        continue;
      }
      roleMap.put(role, role.getCode());
    }
  }

  public List<Role> toList() {
    return new ArrayList<>(roleMap.keySet());
  }

  public int getRoleInt() {
    return roleMap.values().stream().mapToInt(v -> v).sum();
  }

  public String getRoleBinary() {
    var rolesBinary = Integer.toBinaryString(getRoleInt());
    return Strings.padStart(rolesBinary, ROLE_COUNT, '0');
  }

  public Roles set(Role role) {
    roleMap.put(role, role.getCode());
    return this;
  }

  public Roles set(List<Role> roles) {
    roles.forEach(role -> roleMap.put(role, role.getCode()));
    return this;
  }

  public boolean hasRole(Role role) {
    return roleMap.containsKey(role);
  }

  public boolean hasRole(List<Role> roleList) {
    return roleList.stream().allMatch(roleMap::containsKey);
  }

}
