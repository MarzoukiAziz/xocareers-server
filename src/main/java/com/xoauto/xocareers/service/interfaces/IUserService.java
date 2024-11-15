package com.xoauto.xocareers.service.interfaces;



import com.xoauto.xocareers.model.User;

import java.util.List;

public interface IUserService {
    List<User> selectAll();
    User findUserById(long id);
    User findUserByEmail(String username);
    void saveUser(User user);
    void saveAdmin(User user);
    User updateUser(long id, User user);
    void deleteUserById(long id);
}
