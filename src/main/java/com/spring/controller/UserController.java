package com.spring.controller;

import com.spring.model.Message;
import com.spring.model.User;
import com.spring.service.UserService;
import com.spring.util.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Sam on 19/11/2016.
 */

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/addUser", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Message> addUser(@ModelAttribute User user) {
        logger.debug("Start addUser() for /addUser");
        Validation validation = new Validation();
        Message message = new Message();
        if (validation.isValid(user)) {
            message = userService.addUser(user);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        } else {
            message.setMessage("empty/null input/s");
            message.setStatus(false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
    public ResponseEntity<Message> updateUser(@ModelAttribute User user) {
        logger.debug("Start updateUser() for /updateUser");
        Validation validation = new Validation();
        Message message = new Message();
        if (validation.isValid(user)) {
            message = userService.updateUser(user);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        } else {
            message.setMessage("empty/null input/s");
            message.setStatus(false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Message> deleteUser(@PathVariable("id") int id) {
        logger.debug("Start deleteUser() for /deleteUser");
        Validation validation = new Validation();
        Message message = new Message();
        if (validation.isValid(id)) {
            message = userService.deleteUser(id);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        } else {
            message.setMessage("empty/null id");
            message.setStatus(false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/viewUser/{id}", method = RequestMethod.POST)
    public ResponseEntity viewUser(@PathVariable("id") int id) {
        logger.debug("Start viewUser() for /viewUser");
        Validation validation = new Validation();
        Message message = new Message();
        if (validation.isValid(id)) {
            User user = userService.getUserById(id);
            if (user.getId() > 0) {
                return new ResponseEntity<User>(user, HttpStatus.OK);
            } else {
                message.setMessage("no user found");
                message.setStatus(false);
                return new ResponseEntity<Message>(message, HttpStatus.NOT_FOUND);
            }
        } else {
            message.setMessage("empty/null id");
            message.setStatus(false);
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/listUsers", method = RequestMethod.POST)
    public ResponseEntity<List<User>> listUsers() {
        logger.debug("Start listUsers() for /listUsers");
        List<User> listUsers = userService.userList();

        return new ResponseEntity<List<User>>(listUsers, HttpStatus.OK);
    }

}
