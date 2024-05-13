package app.dao;

import app.entity.User;
import app.entity.join.UserAndRole;
import app.service.userV1.parameter.SearchUsersParam;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {

  @Select({"<script>", """
      SELECT
        *
      FROM
        user
      WHERE
        id = #{id}
      """, "</script>"})
  User findByID(long id);

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
  UserAndRole findUserAndRoleByUserName(String userName);

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
  UserAndRole findUserAndRoleByAPIKey(String apiKey);

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

  @Update({"<script>", """
      UPDATE user
      SET
        <if test="name != null"> name = #{name}, </if>
        <if test="password != null"> password = #{password}, </if>
        <if test="type != null"> type = #{type}, </if>
        <if test="status != null"> status = #{status}, </if>
        <if test="firstName != null"> first_name = #{firstName}, </if>
        <if test="lastName != null"> last_name = #{lastName}, </if>
        <if test="age != null"> age = #{age}, </if>
        updated_by = '${@app.TransactionInfo@getUserName()}',
        updated_at = NOW()
      WHERE
        id = #{id}
      """, "</script>"})
  void update(User user);

  @Delete({"<script>", """
      DELETE FROM user
      WHERE
        id = #{id}
      """, "</script>"})
  void delete(long id);

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
