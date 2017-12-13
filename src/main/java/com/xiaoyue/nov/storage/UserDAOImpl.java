package com.xiaoyue.nov.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by xiaoyue26 on 17/11/30.
 */
@Repository
public class UserDAOImpl extends JdbcDaoSupport implements IUserDAO {

    @Autowired
    public void setDs(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public void addUser(User user) {
        String sql = "insert into user values(?,?,?)";
        this.getJdbcTemplate().update(sql, user.getId(), user.getUsername(),
                user.getPassword());
    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public String searchUserName(int id) {
        return null;
    }


    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User searchUser(int id) {
        String sql = "select * from user where id=?";
        return this.getJdbcTemplate().queryForObject(sql, USER_ROW_MAPPER, id);
    }


    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rownum) -> {
        //rs为返回结果集，以每行为单位封装着
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        return user;
    };

}
