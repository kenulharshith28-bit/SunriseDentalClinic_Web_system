package com.sunrisedental.util;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.dao.UserDAOImpl;
import com.sunrisedental.model.User;

import java.util.Optional;

public class UserDAOConnectionCheck {

    public static void main(String[] args)
            throws Exception {

        final UserDAO userDAO =
                new UserDAOImpl();

        final Optional<User> user =
                userDAO.findByUsername(
                        "receptionist");

        if (user.isPresent()) {

            System.out.println(
                    "User found: "
                            + user.get().getUsername());

            System.out.println(
                    "Role: "
                            + user.get().getRole());

        } else {

            System.out.println(
                    "User not found");
        }
    }
}