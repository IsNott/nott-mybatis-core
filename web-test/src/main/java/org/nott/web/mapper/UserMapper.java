package org.nott.web.mapper;

import org.nott.web.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    User selectUser(String id);
}
