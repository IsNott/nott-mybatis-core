package org.nott.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.nott.web.entity.User;
import org.springframework.stereotype.Component;

public interface UserMapper {

    User selectUser(String id);
}
