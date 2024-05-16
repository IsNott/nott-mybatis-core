
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.nott.Application;
import org.nott.mybatis.model.Page;
import org.nott.mybatis.sql.QuerySqlConditionBuilder;
import org.nott.mybatis.sql.enums.LikeMode;
import org.nott.mybatis.sql.model.InLike;
import org.nott.mybatis.sql.model.InSelect;
import org.nott.web.entity.User;
import org.nott.web.mapper.UserMapper;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;


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
        List<User> users = userMapper.selectList();
        System.out.println(users);
    }

    @Test
    public void testIssues05(){
        User user = userMapper.selectOneByCondition(
                QuerySqlConditionBuilder.build()
                        .eq("name","youKnowWho")
        );
        System.out.println(user);
    }

    @Test
    public void testIssues06(){
        User user = userMapper.selectOneByCondition(
                QuerySqlConditionBuilder.build()
                        .eq("name","youKnowWho")
                        .select(InSelect.colum("name").as("test")));
        ;
        System.out.println(user);
    }

    @Test
    public void testIssues07(){
        User user = userMapper.selectOneByCondition(QuerySqlConditionBuilder.build().le("age",32));
        ;
        System.out.println(user);
    }
    @Test
    public void testIssues08(){
        User user = userMapper.selectById("410544b2-4001-4271-9855-fec4b62350b");
        System.out.println(user);
    }

    @Test
    public void testIssues09(){
        User user = new User();
        user.setId("410544b2-4001-4271-9855-fec4b62350b");
        user.setName("mybatis-test1");
        int affectRow = userMapper.updateById(user);
        System.out.println(affectRow);
    }

    @Test
    public void testIssues10(){
        Page<User> userPage = userMapper.pageCount();
        System.out.println(userPage);
    }

    @Test
    public void testIssues11(){
        Page<User> userPage = userMapper.page(1,10,null);
        System.out.println(userPage);
    }

    @Test
    public void testIssues12(){
        User user = new User();
        user.setId("123435345");
        user.setName("naem");
        user.setAge(12);
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    public void testIssues13(){
        int result = userMapper.deleteById("123435345");
        System.out.println(result);
    }

    @Test
    public void testIssues14(){
        int result = userMapper.deleteByIds(Arrays.asList("123435345"));
        System.out.println(result);
    }

    @Test
    public void testIssues15(){
        User user = userMapper.selectOneByCondition(
                QuerySqlConditionBuilder.build().like(InLike.choose("name", "you", LikeMode.AFTER))
        );
        System.out.println(user);
    }

    @Test
    public void testIssues16(){
        User user = userMapper.selectOneByCondition(QuerySqlConditionBuilder.build().like("name", "you", LikeMode.AFTER));
        System.out.println(user);
    }

    @Test
    public void testIssues17(){
        User user = userMapper.selectOneByCondition(QuerySqlConditionBuilder.build().like("id", "1", LikeMode.ALL)
                .orderByAsc("id","name"));
        System.out.println(user);
    }

    @Test
    public void testIssues18(){
        User user = userMapper.selectOneByCondition(QuerySqlConditionBuilder.build()
                .like("id", "1", LikeMode.ALL)
                .orderByAsc("id").orderByDesc("email"));
        System.out.println(user);
    }

    @Test
    public void testIssues19(){
        User user = userMapper.selectOneByCondition(
                QuerySqlConditionBuilder.build()
                        .select(InSelect.colum("name"))
                        .like("name", "you", LikeMode.ALL)
                        .orderByAsc("name")
                        .groupBy("name"));
        System.out.println(user);
    }
}
