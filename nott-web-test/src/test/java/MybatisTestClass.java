
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.nott.Application;
import org.nott.mybatis.sql.model.InSelect;
import org.nott.mybatis.sql.SimpleSqlConditionBuilder;
import org.nott.web.entity.User;
import org.nott.web.mapper.UserMapper;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author Nott
 * @date 2024-5-10
 */


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = Application.class)
public class MybatisTestClass {

    private ClassPathXmlApplicationContext context;

    @Resource
    private UserMapper userMapper;

    public ClassPathXmlApplicationContext getContext() {
        if(context == null){
            context = new ClassPathXmlApplicationContext("spring-context.xml");
        }
        return context;
    }

    @Test
    public void testIssues01(){
        String id = "410544b2-4001-4271-9855-fec4b6a6442a";
        User user = userMapper.selectUser(id);
        System.out.println(user);
    }

    @Test
    public void testIssues02(){
        String email = "test";
        email = "%" + email + "%";
        User singleColumUser = userMapper.selectUserByEmail(email);
        System.out.println(singleColumUser);
    }

    @Test
    public void testIssues03(){
        String name = "you";
        String orderBy = "id";
        name = "%" + name + "%";

        User mutlColumUser = userMapper.selectUserByName(name, orderBy);
        System.out.println(mutlColumUser);
    }


    @Test
    public void testIssues04(){
        User user = userMapper.selectOne();
        System.out.println(user);
    }

    @Test
    public void testIssues05(){
        User user = userMapper.selectOneByCondition(
                SimpleSqlConditionBuilder.create(UserMapper.class)
                        .eq("name","youKnowWho")
        );
        System.out.println(user);
    }

    @Test
    public void testIssues06(){
        User user = userMapper.selectOneByCondition(
                SimpleSqlConditionBuilder.create(UserMapper.class)
                        .eq("name","youKnowWho")
                        .select(InSelect.colum("name").as("test")));
        ;
        System.out.println(user);
    }

}
