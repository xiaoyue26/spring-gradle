package com.xiaoyue.nov.storage.jdbcTemplate;

import java.util.List;

/**
 * Created by xiaoyue26 on 17/11/30.
 */
public interface IUserDAO {

    public void addUser(User user);

    public void deleteUser(int id);

    public void updateUser(User user);

    public String searchUserName(int id);

    public User searchUser(int id);

    public List<User> findAll();

}