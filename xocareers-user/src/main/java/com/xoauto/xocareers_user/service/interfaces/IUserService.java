package com.xoauto.xocareers_user.service.interfaces;



import com.xoauto.xocareers_user.model.User;

import java.util.List;

public interface IUserService {
    List<User> selectAll();
    User findUserByEmail(String username);
    void saveUser(User user);
    void saveAdmin(User user);
    User updateUser(long id, User user);
    void deleteUserById(long id);
}
