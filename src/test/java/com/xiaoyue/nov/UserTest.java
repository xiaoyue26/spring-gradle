package com.xiaoyue.nov;

import com.xiaoyue.nov.storage.jdbcTemplate.IUserDAO;
import com.xiaoyue.nov.storage.jdbcTemplate.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by xiaoyue26 on 17/11/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = ReadingListApplication.class)
@SpringBootTest(classes = ReadingListApplication.class)
@WebAppConfiguration
public class UserTest {
    @Autowired
    private IUserDAO dao;

    @Test//查（简单查询，返回对象）
    public void demo5() {
        User user = dao.searchUser(1);
        System.out.println(user.getUsername());
    }
}
