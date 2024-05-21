package org.nott.web.service;

import org.nott.datasource.annotations.DataSource;
import org.nott.mybatis.service.impl.ICommonService;
import org.nott.mybatis.sql.builder.QuerySqlConditionBuilder;
import org.nott.web.entity.User;
import org.nott.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Nott
 * @date 2024-5-17
 */

@Service
public class UserService<User> extends ICommonService<User> {

}
