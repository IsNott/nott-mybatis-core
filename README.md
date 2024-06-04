# mybatis通用操作包
基于mybatis3.5,spring,mysql，java17+的通用操作包，封装curd通用基础操作。

## 创建初衷
根据mybatis文档教程从头进行学习(aop、动态数据源、mybatis插件等)，封装自用的mybatis数据操作包。

## 版本 (Version)
0.0.4

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
### 引入
pom.xml引入模块(nott-mybatis-orm/dynamic-datasource)
```xml
<dependencies>
    <dependency>
        <groupId>io.github.isnott</groupId>
        <artifactId>nott-mybatis-orm</artifactId>
        <version>0.0.4</version>
    </dependency>
    
    <dependency>
    <groupId>io.github.isnott</groupId>
    <artifactId>nott-mybatis-dynamic-datasource</artifactId>
    <version>0.0.4</version>
    </dependency>
</dependencies>
```

在web项目启动主方法上加入**@ComponentScan({"org.nott"})**

可参考项目web模块test文件夹下的单元测试方法。

自定义service实现类继承ICommonService<T>获取通用service实现方法
（在CommonMapper提供的方法上二次封装分页等方法）
```java
@Service
public class UserService extends ICommonService<User> {

}
```

自定义mapper可直接获取CommonMapper基础方法（需要传递泛型）
```java
public interface UserMapper extends CommonMapper<User> {}
```

## ORM
**application.yml**加入以下配置
```yml
nott:
  mybatis:
    mapper-location: classpath:mapper/**Mapper.xml
  # 不加入dynamic-dataSource时加入下面配置
  datasource:
    name: mysql-db01
    url: jdbc:mysql://127.0.0.1:3306/test?allowMultiQueries=true&useSSL=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true
    username: root
    password: 888888
    driverClassName: com.mysql.cj.jdbc.Driver
    type: com.zaxxer.hikari.HikariDataSource
    hikari:
      minimumIdle: 0
      maximumPoolSize: 20
      idleTimeout: 10000
      connectionTestQuery: select 1
      poolName: nott-hikari-01
```

使用aop支持Select、Update、Delete、Insert基础方法（selectById、selectOne）、
单表查询、更新条件构造器

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
    
   SqlConditionBuilder eq(String colum, Object val);

    SqlConditionBuilder neq(String colum, Object val);

    SqlConditionBuilder gt(String colum, Object val);

    SqlConditionBuilder ge(String colum, Object val);

    SqlConditionBuilder lt(String colum, Object val);

    SqlConditionBuilder le(String colum, Object val);

    SqlConditionBuilder select(InSelect... selects);

    SqlConditionBuilder like(InLike... inLike);

    SqlConditionBuilder like(String colum, Object val, LikeMode likeMode);

    SqlConditionBuilder or(SqlConditions... sqlConditions);

    SqlConditionBuilder limit(Integer value);

    SqlConditionBuilder append(String sql);

    SqlConditionBuilder orderByDesc(String... colum);

    SqlConditionBuilder orderByAsc(String... colum);

    SqlConditionBuilder isNull(String fieldName);

    SqlConditionBuilder notNull(String fieldName);

    SqlConditionBuilder groupBy(String... colum);

    SqlConditionBuilder having(String... sql);
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
## dynamic-datasource
切换数据源，支持使用hikari/druid数据源

在application.yml加入动态数据源开关

```yaml
nott:
  enable-dynamic-datasource: true
```
添加data-source.yml文件，加入数据源列表(hikari/druid),
当application.yml和data-source.yml数据源配置内容同时存在，并且打开动态数据源开关，
程序只加载data-source.yml里的内容。

*不需要动态切换数据源时，关闭application.yml中的开关即可。*
```yaml
dataSourceConfigs:
  - name: mysql-db01
    url: jdbc:mysql://127.0.0.1/test?allowMultiQueries=true&useSSL=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true
    username: root
    password: 123456
    driverClassName: com.mysql.cj.jdbc.Driver
    # 标识默认数据源
    primary: true
    type: com.zaxxer.hikari.HikariDataSource
    #type: com.alibaba.druid.pool.DruidDataSource
    hikari:
      minimumIdle: 0
      maximumPoolSize: 20
      idleTimeout: 10000
      connectionTestQuery: select 1
      poolName: nott-hikari-01
  - name: mysql-db02
   ...
```
在代码或者方法体上切换数据源
```java
public void test(){
    // 显式切换
   DynamicDataSourceHolder.setDynamicDataSourceKey("mysql-db02");    
}


@DataSource("mysql-db02")
public void test(){
        ...
}

```

## 迭代
待增加：别名查询、多表联表查询

## 参考文档
mybatis中文文档：https://mybatis.net.cn/getting-started.html

mybatis与spring整合版本对应关系参考：https://mybatis.org/spring/

jsqlparser:https://jsqlparser.github.io/JSqlParser/usage.html

hikari数据源配置参考: https://www.cnblogs.com/didispace/p/12291832.html

druid配置属性：https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8
