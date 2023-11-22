package com.tickets.service;

import com.tickets.Entity.User;

import java.util.List;


public interface UserService {

    User createUser(User user);

    List<User> getUsers();
}
