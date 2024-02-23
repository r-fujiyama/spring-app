package app.service;

import com.github.database.rider.spring.api.DBRider;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DBRider
public abstract class ServiceTest {

}
