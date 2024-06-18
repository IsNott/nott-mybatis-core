
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.nott.Application;
import org.nott.datasource.DynamicDataSourceHolder;
import org.nott.mybatis.model.Page;
import org.nott.mybatis.sql.builder.ComplexityWrapper;
import org.nott.mybatis.sql.builder.DeleteSqlConditionBuilder;
import org.nott.mybatis.sql.builder.QuerySqlConditionBuilder;
import org.nott.mybatis.sql.builder.UpdateSqlConditionBuilder;
import org.nott.mybatis.sql.enums.LikeMode;
import org.nott.mybatis.sql.enums.SqlOperator;
import org.nott.mybatis.sql.model.*;
import org.nott.web.entity.User;
import org.nott.web.entity.UserRelation;
import org.nott.web.mapper.UserMapper;
import org.nott.web.service.UserService;
import org.nott.web.vo.UserRelationVo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * @author Nott
 * @date 2024-5-10
 */

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class MybatisTestClass {

    private ClassPathXmlApplicationContext context;

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Test
    public void testIssues01() {
        String id = "410544b2-4001-4271-9855-fec4b6a6442a";
        User user = userMapper.selectUser(id);
        System.out.println(user);
    }

    @Test
    public void testIssues02() {
        String email = "test";
        email = "%" + email + "%";
        User singleColumUser = userMapper.selectUserByEmail(email);
        System.out.println(singleColumUser);
    }

    @Test
    public void testIssues03() {
        String name = "you";
        String orderBy = "id";
        name = "%" + name + "%";

        User mutlColumUser = userMapper.selectUserByName(name, orderBy);
        System.out.println(mutlColumUser);
    }


    @Test
    public void testIssues04() {
        List<User> users = userMapper.selectList();
        System.out.println(users);
    }

    @Test
    public void testIssues05() {
        User user = userMapper.selectOneByCondition(
                QuerySqlConditionBuilder.build()
                        .eq("name", "youKnowWho")
        );
        System.out.println(user);
    }

    @Test
    public void testIssues06() {
        User user = userMapper.selectOneByCondition(
                QuerySqlConditionBuilder.build()
                        .eq("name", "youKnowWho")
                        .select(InSelect.colum("name").as("test")));
        ;
        System.out.println(user);
    }

    @Test
    public void testIssues07() {
        User user = userMapper.selectOneByCondition(QuerySqlConditionBuilder.build().le("age", 32));
        ;
        System.out.println(user);
    }

    @Test
    public void testIssues08() {
        User user = userMapper.selectById("410544b2-4001-4271-9855-fec4b62350b");
        System.out.println(user);
    }

    @Test
    public void testIssues09() {
        User user = new User();
        user.setId("410544b2-4001-4271-9855-fec4b62350b");
        user.setName("mybatis-test1");
        int affectRow = userMapper.updateById(user);
        System.out.println(affectRow);
    }

    @Test
    public void testIssues10() {
        System.out.println(userMapper.count());
    }

    @Test
    public void testIssues11() {
        Page page = userService.page(new Page<User>(1, 10));
        System.out.println(page);
    }

    @Test
    public void testIssues12() {
        User user = new User();
        user.setId("123435345");
        user.setName("naem");
        user.setAge(12);
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    public void testIssues13() {
        int result = userMapper.deleteById("123435345");
        System.out.println(result);
    }

    @Test
    public void testIssues14() {
        int result = userMapper.deleteByIds(Arrays.asList("123435345"));
        System.out.println(result);
    }

    @Test
    public void testIssues15() {
        User user = userMapper.selectOneByCondition(
                QuerySqlConditionBuilder.build().like(InLike.choose("name", "you", LikeMode.AFTER))
        );
        System.out.println(user);
    }

    @Test
    public void testIssues16() {
        User user = userMapper.selectOneByCondition(QuerySqlConditionBuilder.build().like("name", "you", LikeMode.AFTER));
        System.out.println(user);
    }

    @Test
    public void testIssues17() {
        User user = userMapper.selectOneByCondition(QuerySqlConditionBuilder.build().like("id", "1", LikeMode.ALL)
                .orderByAsc("id", "name"));
        System.out.println(user);
    }

    @Test
    public void testIssues18() {
        User user = userMapper.selectOneByCondition(QuerySqlConditionBuilder.build()
                .like("id", "1", LikeMode.ALL)
                .orderByAsc("id").orderByDesc("email"));
        System.out.println(user);
    }

    @Test
    public void testIssues19() {
        User user = userMapper.selectOneByCondition(
                QuerySqlConditionBuilder.build()
                        .select(InSelect.colum("name"))
                        .like("name", "you", LikeMode.ALL)
                        .orderByAsc("name")
                        .groupBy("name"));
        System.out.println(user);
    }

    @Test
    public void testIssues20() {
        User user = new User();
        user.setId("410544b2-4001-4271-9855-fec4b6a6442a");
        user.setName("134");
        int row = userMapper.updateByCondition(
                UpdateSqlConditionBuilder.build().eq("id", "410544b2-4001-4271-9855-fec4b6a6442a")
                        .set("name", "test")
        );
        System.out.println(row);
    }

    @Test
    public void testIssues21() {
        User user = new User();
        user.setId("410544b2-4001-4271-9855-fec4b6a6442a");
        user.setName("134");
        Object o = userService.updateById(user);
        System.out.println(o);
    }

    @Test
    public void testIssues22() {
        int o = userMapper.deleteByCondition(DeleteSqlConditionBuilder.build().eq("age", 1));
        System.out.println(o);
    }

    @Test
    public void testIssues23() {
        User test = userMapper.selectOneByCondition(QuerySqlConditionBuilder.build().eq("id", "123435345"));
        System.out.println(test);
    }


    @Test
    public void testIssues24() {
        User one = userMapper.selectOneByCondition(QuerySqlConditionBuilder.build().eq("id", "410544b2-4001-4271-9855-fec4b62350b"));
        DynamicDataSourceHolder.setDynamicDataSourceKey("mysql-db02");
        User two = userMapper.selectOneByCondition(QuerySqlConditionBuilder.build().eq("id", "410544b2-4001-4271-9855-fec4b62350b"));
        Assert.isTrue("mybatis-test".equals(one.getName()),"");
        Assert.isTrue("mybatis-test01".equals(two.getName()),"");
    }

    @Test
    public void testIssues25(){
        List<User> users = userMapper.selectListByCondition(QuerySqlConditionBuilder.build().eq("id", "410544b2-4001-4271-9855-fec4b62350b")
                .or(Where.eq("age", 16), Where.eq("age", "17")));
        Assert.isTrue(users.size() == 3,"");
    }

    @Test
    public void testIssues26(){
        List<User> users = userMapper.selectListByCondition(QuerySqlConditionBuilder.build().notNull("id"));
        Assert.isTrue(users.size() == 3,"");
    }

    @Test
    public void testIssues27() {
        List<UserRelationVo> result = ComplexityWrapper.build(User.class, "t1")
                .leftJoin(UserRelation.class, "t2", Join.on("t1.id", "t2.user_id", SqlOperator.EQ))
                .colums(Colum.select("T1.id","userId"))
                .condition(Where.eq("t1.id", "410544b2-4001-4271-9855-fec4b62350d"))
                .orderByAsc("t1.id")
                .groupBy("t1.id")
                .beanType(UserRelationVo.class);
        Assert.noNullElements(result,"");
    }

    @Test
    public void testTransaction() {
        User user1 = new User();
        user1.setId(UUID.randomUUID().toString());
        user1.setName("张三");
        userMapper.insert(user1);

        User user2 = new User();
        user2.setId(UUID.randomUUID().toString());
        user2.setName("李四");
        userMapper.insert(user2);

        // 模拟异常，触发回滚
        int i = 1 / 0;
    }
}
