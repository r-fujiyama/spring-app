package app.service;

import app.util.MessageUtils;
import com.github.database.rider.spring.api.DBRider;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DBRider
@Import({MessageUtils.class})
public abstract class ServiceTest {

}
