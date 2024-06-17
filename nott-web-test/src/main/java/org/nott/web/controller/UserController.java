package org.nott.web.controller;

import jakarta.annotation.Resource;
import org.nott.datasource.DynamicDataSourceHolder;
import org.nott.datasource.annotations.DataSource;
import org.nott.mybatis.exception.OrmOperateException;
import org.nott.mybatis.sql.builder.ComplexityWrapper;
import org.nott.mybatis.sql.builder.QuerySqlConditionBuilder;
import org.nott.mybatis.sql.builder.UpdateSqlConditionBuilder;
import org.nott.mybatis.sql.enums.SqlOperator;
import org.nott.mybatis.sql.model.Colum;
import org.nott.mybatis.sql.model.Join;
import org.nott.mybatis.sql.model.Where;
import org.nott.web.entity.User;
import org.nott.web.entity.UserRelation;
import org.nott.web.mapper.UserMapper;
import org.nott.web.service.UserService;
import org.nott.web.vo.UserRelationVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试Web模块 Controller层
 * @author Nott
 * @date 2024-5-11
 */
@RestController
@RequestMapping("user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @RequestMapping("/test")
    public void test() {
        User one = userMapper.selectOneByCondition(QuerySqlConditionBuilder.build().eq("id", "410544b2-4001-4271-9855-fec4b62350b"));
        DynamicDataSourceHolder.setDynamicDataSourceKey("mysql-db02");
        User two = userMapper.selectOneByCondition(QuerySqlConditionBuilder.build().eq("id", "410544b2-4001-4271-9855-fec4b62350b"));
        Assert.isTrue("mybatis-test".equals(one.getName()),"");
        Assert.isTrue("mybatis-test01".equals(two.getName()),"");
    }

    @DataSource("mysql-db02")
    @RequestMapping("/test01")
    User test1(){
        String id = "410544b2-4001-4271-9855-fec4b6a6442a";
        User user = userMapper.selectUser(id);
        return user;
    }

    @RequestMapping("/test02")
    @Transactional(rollbackFor = OrmOperateException.class)
    public void testTransation(){
        userService.update(UpdateSqlConditionBuilder.build().eq("id","410544b2-4001-4271-9855-fec4b6a6442a")
                .set("name","spring"));

        throw new OrmOperateException("test");
    }

    @RequestMapping("/test03")
    public void test03(){
        List<UserRelationVo> userRelationVos = ComplexityWrapper.build(User.class)
                .alias("t1")
                .leftJoin(UserRelation.class, "t2", Join.on("t1.id", "t2.userid", SqlOperator.EQ))
                .colums(Colum.select("T1.id", "userId"))
                .condition(Where.eq("t1.id", "410544b2-4001-4271-9855-fec4b62350b"))
                .beanType(UserRelationVo.class);

    }


}
