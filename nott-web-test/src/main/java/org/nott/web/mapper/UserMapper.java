package org.nott.web.mapper;

import org.apache.ibatis.annotations.*;
import org.nott.mybatis.mapper.CommonMapper;
import org.nott.web.entity.User;
import org.nott.web.provider.UserSelectProvider;

public interface UserMapper extends CommonMapper<User> {

    User selectUser(String id);

    @SelectProvider(type = UserSelectProvider.class,method = "selectUserByEmail")
    @ResultType(User.class)
    User selectUserByEmail(@Param("email") String email);

    @SelectProvider(type = UserSelectProvider.class, method = "selectUserByName")
    @ResultType(User.class)
    User selectUserByName(@Param("name") String name, @Param("orderByColum")String orderByColum);

}
