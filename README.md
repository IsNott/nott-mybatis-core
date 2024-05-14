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
## 功能
update on：2024-5-14 使用aop支持Select基础方法（selectById、selectOne）
案例：
```java
User user = userMapper.selectOneByCondition(
                SimpleSqlConditionBuilder.build()
                        .eq("name","youKnowWho")
                        .select(InSelect.colum("name").as("test")));
```

## 使用说明
参考项目web模块test文件夹下的单元测试方法。

## 参考文档
mybatis中文文档：https://mybatis.net.cn/getting-started.html

mybatis与spring整合版本对应关系参考：https://mybatis.org/spring/

jsqlparser:https://jsqlparser.github.io/JSqlParser/usage.html

