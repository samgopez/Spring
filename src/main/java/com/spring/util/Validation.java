package com.spring.util;

import com.spring.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sam on 25/11/2016.
 */
public class Validation {

    private static final Logger logger = LoggerFactory.getLogger(Validation.class);

    public boolean isValid(int id) {
        logger.debug("Start isValid()");

        boolean status = id <= 0 ? false : true;

        return status;
    }

    public boolean isValid(User user) {
        logger.debug("Start isValid()");
        boolean status = true;

        if (user == null) {
            status = false;
        } else if (user.getUserName().isEmpty() || user.getUserName() == null) {
            status = false;
        } else if (user.getPassWord().isEmpty() || user.getPassWord() == null) {
            status = false;
        } else if (user.getFullName().isEmpty() || user.getFullName() == null) {
            status = false;
        } else if (user.getAddress().isEmpty() || user.getAddress() == null) {
            status = false;
        } else if (user.getPassWord().isEmpty() || user.getPassWord() == null) {
            status = false;
        }

        return status;
    }
}
