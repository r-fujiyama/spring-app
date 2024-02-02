package app.dao;

import app.entity.Role;
import app.entity.User;
import app.entity.join.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

  @Select({"<script>", """
      SELECT
        *
      FROM
        user
      WHERE
        user_id = #{userID}
      """, "</script>"})
  User findByUserID(String userID);

  @Select({"<script>", """
      SELECT
        u.*
        , r.user_id AS role_user_id
        , r.allow_create AS role_allow_create
        , r.allow_read AS role_allow_read
        , r.allow_update AS role_allow_update
        , r.allow_delete AS role_allow_delete
        , r.updated_by AS role_updated_by
        , r.updated_at AS role_updated_at
        , r.created_by AS role_created_by
        , r.created_at AS role_created_at
      FROM
        user u JOIN role r
          ON u.user_id = r.user_id JOIN api_key a
          ON u.user_id = a.user_id
      WHERE
        a.api_key = #{apiKey}
      """, "</script>"})
  @Results(value = {
      @Result(property = "user", one = @One(resultMap = "userResultMap")),
      @Result(property = "role", one = @One(resultMap = "roleResultMap", columnPrefix = "role_"))
  })
  UserInfo findUserAndRoleByAPIKey(String apiKey);

  @Select("SELECT 1")
  @Results(id = "userResultMap", value = {
      @Result(id = true, column = "user_id", property = "userID"),
      @Result(column = "password", property = "password"),
      @Result(column = "user_type", property = "userType"),
      @Result(column = "user_status", property = "userStatus"),
      @Result(column = "last_name", property = "lastName"),
      @Result(column = "first_name", property = "firstName"),
      @Result(column = "age", property = "age"),
      @Result(column = "updated_by", property = "updatedBy"),
      @Result(column = "updated_at", property = "updatedAt"),
      @Result(column = "created_by", property = "createdBy"),
      @Result(column = "created_at", property = "createdAt")
  })
  User __userResultMap();

  @Select("SELECT 1")
  @Results(id = "roleResultMap", value = {
      @Result(id = true, column = "user_id", property = "userID"),
      @Result(column = "allow_create", property = "allowCreate"),
      @Result(column = "allow_read", property = "allowRead"),
      @Result(column = "allow_update", property = "allowUpdate"),
      @Result(column = "allow_delete", property = "allowDelete"),
      @Result(column = "updated_by", property = "updatedBy"),
      @Result(column = "updated_at", property = "updatedAt"),
      @Result(column = "created_by", property = "createdBy"),
      @Result(column = "created_at", property = "createdAt")
  })
  Role __roleResultMap();

}
