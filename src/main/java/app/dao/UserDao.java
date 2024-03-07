package app.dao;

import app.entity.User;
import app.entity.join.UserInfo;
import app.service.userV1.parameter.SearchUsersParam;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
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
        name = #{name}
      """, "</script>"})
  User findByName(String name);

  @Select({"<script>", """
      SELECT
        *
      FROM
        user
      WHERE
        1 = 1
        <if test="id != null">
          AND id = #{id}
        </if>
        <if test="userName != null">
          AND name = #{userName}
        </if>
        <if test="userType != null">
          AND type = #{userType.code}
        </if>
        <if test="userStatus != null">
          AND status = #{userStatus.code}
        </if>
        <if test="firstName != null">
          AND first_name = #{firstName}
        </if>
        <if test="lastName != null">
          AND last_name = #{lastName}
        </if>
        <if test="age != null">
          AND age = #{age}
        </if>
      """, "</script>"})
  List<User> findBySearchUsersParam(SearchUsersParam param);

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
          ON u.id = r.user_id
      WHERE
        u.name = #{userName}
      """, "</script>"})
  @Results(value = {
      @Result(property = "user", one = @One(resultMap = "userResultMap")),
      @Result(property = "role", one = @One(resultMap = "app.dao.RoleDao.roleResultMap", columnPrefix = "role_"))
  })
  UserInfo findUserAndRoleByUserName(String userName);

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
          ON u.id = r.user_id JOIN api_key a
          ON u.id = a.user_id
      WHERE
        a.api_key = #{apiKey}
      """, "</script>"})
  @Results(value = {
      @Result(property = "user", one = @One(resultMap = "userResultMap")),
      @Result(property = "role", one = @One(resultMap = "app.dao.RoleDao.roleResultMap", columnPrefix = "role_"))
  })
  UserInfo findUserAndRoleByAPIKey(String apiKey);

  @Insert({"<script>", """
      INSERT INTO user(
        name
        , password
        , type
        , status
        , first_name
        , last_name
        , age
        , updated_by
        , updated_at
        , created_by
        , created_at
      )
      VALUES (
        #{name}
        , #{password}
        , #{type}
        , #{status}
        , #{firstName}
        , #{lastName}
        , #{age}
        , '${@app.TransactionInfo@getUserName()}'
        , NOW()
        , '${@app.TransactionInfo@getUserName()}'
        , NOW()
      )
      """, "</script>"})
  void insert(User user);

  @Select("SELECT 1")
  @Results(id = "userResultMap", value = {
      @Result(id = true, column = "id", property = "id"),
      @Result(column = "name", property = "name"),
      @Result(column = "password", property = "password"),
      @Result(column = "type", property = "type"),
      @Result(column = "status", property = "status"),
      @Result(column = "first_name", property = "firstName"),
      @Result(column = "last_name", property = "lastName"),
      @Result(column = "age", property = "age"),
      @Result(column = "updated_by", property = "updatedBy"),
      @Result(column = "updated_at", property = "updatedAt"),
      @Result(column = "created_by", property = "createdBy"),
      @Result(column = "created_at", property = "createdAt")
  })
  User __userResultMap();

}
