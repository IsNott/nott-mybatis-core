# mybatis通用操作包
基于mybatis3.5,spring,mysql，java17+的通用操作包，封装curd通用基础操作。

## 创建初衷
根据mybatis文档教程从头进行学习(aop、动态数据源、mybatis插件等)，封装自用的mybatis数据操作包。

## 依赖版本

|name|version|
|--|--|
|spring-boot-starter-parent|3.1.0|
|mysql-connector-j|8.3.0|
|aspectjweaver|1.9.21.1|
|mybatis-spring|3.0.1|
|spring-context|6.0.9|
|spring-boot-configuration-processor|3.1.5|
|log4j-api|2.20.0|
|spring-test|6.0.9|
|mybatis|3.5.11|
|...|...|

*完整依赖见[GitHub](https://github.com/IsNott/nott-mybatis-core)项目pom文件*

## 模块
|name|description|
|--|--|
|nott-mybatis-curd|mybaits-基础|
|nott-mybatis-dynamic-datasource|mybatis-动态数据源|
|nott-web-test|web测试模块|


## 使用说明
参考项目web模块test文件夹下的单元测试方法。
自定义service实现类继承ICommonService<T>获取service方法
```java
@Service
public class UserService<User> extends ICommonService<User> {

}
```

自定义mapper可直接获取CommonMapper基础方法
```java
public interface UserMapper extends CommonMapper<User> {}
```

## 功能
使用aop支持Select基础方法（selectById、selectOne）、
单表查询条件构造器

使用示例：
```java
List<User> users = userMapper.selectList();
// 单表条件查询
User user = userMapper.selectOneByCondition(
        QuerySqlConditionBuilder.build()
        .eq("name","youKnowWho")
        );
```

```java
/**
 * 单表条件查询支持的方法接口
 */
public interface SqlQuery {

    QuerySqlConditionBuilder eq(String colum, Object val);

    QuerySqlConditionBuilder neq(String colum, Object val);

    QuerySqlConditionBuilder gt(String colum, Object val);

    QuerySqlConditionBuilder ge(String colum, Object val);

    QuerySqlConditionBuilder lt(String colum, Object val);

    QuerySqlConditionBuilder le(String colum, Object val);

    QuerySqlConditionBuilder select(InSelect... selects);

    QuerySqlConditionBuilder limit(Integer value);

    QuerySqlConditionBuilder append(String sql);

}
```

Update、Delete、Insert基础方法
```
int affectRow = userMapper.updateById(user);
int affectRow = userMapper.insert(user);
int affectRow = userMapper.deleteById("123435345");
int affectRow = userMapper.deleteByIds(Arrays.asList("123435345"));
```


Service封装mapper基础方法、分页方法，详细见CommonService文件
```
...
Page<T> page(Page<T> page);

Page<T> page(Page<T> page, QuerySqlConditionBuilder querySqlConditionBuilder);
...
```


## 参考文档
mybatis中文文档：https://mybatis.net.cn/getting-started.html

mybatis与spring整合版本对应关系参考：https://mybatis.org/spring/

jsqlparser:https://jsqlparser.github.io/JSqlParser/usage.html

hikari数据源配置参考: https://www.cnblogs.com/didispace/p/12291832.html

druid配置属性：https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8

