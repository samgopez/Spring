package com.spring.service;

import com.spring.model.Message;
import com.spring.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sam on 19/11/2016.
 */
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private JdbcTemplate jdbcTemplate;

    public UserServiceImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Message addUser(final User user) {
        logger.debug("Start addUser()");
        Message message = new Message("failed", false);
        final String sql = "INSERT "
                         + "INTO "
                         +     "user(user_name, user_pass) "
                         + "VALUES "
                         +     "(?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int row = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                logger.debug("Start createPreparedStatement()");
                PreparedStatement ps = connection.prepareStatement(sql, new String[] {"user_id"});
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getPassWord());
                return ps;
            }
        }, keyHolder);

        if (row > 0) {
            user.setId((int) keyHolder.getKey().longValue());
            if (insertUserInfo(user)) {
                message.setMessage("success");
                message.setStatus(true);
            } else {
                logger.error("Unable to insert user info");
            }
        } else {
            logger.error("Unable to add user");
        }

        return message;
    }

    @Override
    public Message updateUser(User user) {
        logger.debug("Start updateUser()");
        Message message = new Message("failed", false);
        String sql = "UPDATE "
                   +     "user AS user "
                   + "INNER JOIN "
                   +     "user_info AS info "
                   +         "ON user.user_id = info.user_id "
                   + "SET "
                   +     "user.user_name=?, "
                   +     "user.user_pass=?, "
                   +     "info.info_fullname=?, "
                   +     "info.info_age=?, "
                   +     "info.info_address=? "
                   + "WHERE "
                   +     "user.user_id=? "
                   +     "AND user.user_is_delete='1'";

        int row = jdbcTemplate.update(sql, user.getUserName(),
                                           user.getPassWord(),
                                           user.getFullName(),
                                           user.getAge(),
                                           user.getAddress(),
                                           user.getId());

        if (row > 0) {
            message.setMessage("success");
            message.setStatus(true);
        }
        return message;
    }

    @Override
    public Message deleteUser(int userId) {
        logger.debug("Start deleteUser()");
        Message message = new Message("failed", false);
        String sql = "UPDATE "
                   +     "user "
                   + "SET "
                   +     "user_is_delete='0' "
                   + "WHERE "
                   +     "user_id=?";
        int row = jdbcTemplate.update(sql, userId);
        if (row > 0) {
            message.setMessage("success");
            message.setStatus(true);
        }
        return message;
    }

    @Override
    public User getUserById(int userId) {
        logger.debug("Start getUserById()");
        String sql = "SELECT "
                   +     "* "
                   + "FROM "
                   +     "user "
                   + "INNER JOIN "
                   +     "user_info "
                   +         "ON user.user_id = user_info.user_id "
                   + "WHERE "
                   +     "user.user_id=? "
                   +     "AND user.user_is_delete='1'";

        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setFullName(resultSet.getString("info_fullname"));
                user.setAge(resultSet.getInt("info_age"));
                user.setAddress(resultSet.getString("info_address"));

                return user;
            }
        });
    }

    @Override
    public List<User> userList() {
        logger.debug("Start userList()");
        String sql = "SELECT "
                   +     "* "
                   + "FROM "
                   +     "user "
                   + "INNER JOIN "
                   +     "user_info "
                   +         "ON user.user_id = user_info.user_id "
                   + "WHERE "
                   +     "user.user_is_delete='1'";

        return jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                logger.debug("Start mapRow()");
                User user = new User();

                user.setId(resultSet.getInt("user_id"));
                user.setFullName(resultSet.getString("info_fullname"));
                user.setAge(resultSet.getInt("info_age"));
                user.setAddress(resultSet.getString("info_address"));

                return user;
            }
        });
    }

    private boolean insertUserInfo(User user) {
        logger.debug("Start insertUserInfo()");
        boolean status = false;
        String sql = "INSERT "
                   + "INTO "
                   +     "user_info(info_fullname, info_age, info_address, user_id) "
                   + "VALUES "
                   +     "(?,?,?,?)";

        int row = jdbcTemplate.update(sql, user.getFullName(), user.getAge(), user.getAddress(), user.getId());
        if (row > 0) {
            status = true;
        }

        return status;
    }
}
