
import org.junit.jupiter.api.Test;
import org.nott.web.entity.User;
import org.nott.web.mapper.UserMapper;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author Nott
 * @date 2024-5-10
 */

public class MybatisTestClass {

    private ClassPathXmlApplicationContext context;

    public ClassPathXmlApplicationContext getContext() {
        if(context == null){
            context = new ClassPathXmlApplicationContext("spring-context.xml");
        }
        return context;
    }

    @Test
    public void testIssues01(){
        ClassPathXmlApplicationContext context = this.getContext();
        UserMapper userMapper = (UserMapper)context.getBean("userMapper");
        String id = "410544b2-4001-4271-9855-fec4b6a6442a";
        User user = userMapper.selectUser(id);
        System.out.println(user);
    }
}
