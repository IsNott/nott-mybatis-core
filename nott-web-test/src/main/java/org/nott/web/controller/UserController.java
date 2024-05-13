package org.nott.web.controller;

import jakarta.annotation.Resource;
import org.nott.web.entity.User;
import org.nott.web.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/test")
    public void test() {
        String id = "410544b2-4001-4271-9855-fec4b6a6442a";
        User user = userMapper.selectUser(id);
        logger.info("{}", user);
    }
}
