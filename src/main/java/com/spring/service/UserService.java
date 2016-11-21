package com.spring.service;

import com.spring.model.Message;
import com.spring.model.User;

import java.util.List;

/**
 * Created by Sam on 19/11/2016.
 */
public interface UserService {

    Message addUser(User user);

    Message updateUser(User user);

    Message deleteUser(int userId);

    User getUserById(int userId);

    List<User> userList();
}
