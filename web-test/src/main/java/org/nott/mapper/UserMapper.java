package org.nott.mapper;

import org.nott.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    User selectUser(String id);
}
