
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nott.entity.User;
import org.nott.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author Nott
 * @date 2024-5-10
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*: spring-context.xml")
public class MybatisTestClass {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testIssues01(){
        String id = "410544b2-4001-4271-9855-fec4b6a6442a";
        User user = userMapper.selectUser(id);
        System.out.println(user);
    }
}
