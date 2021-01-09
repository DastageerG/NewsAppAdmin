package com.example.newsappadmin.utils;
import com.example.newsappadmin.model.User;
import java.util.List;


public interface UsersCallBack
{
    void getUserData(User user); // single User data
    void getAllUsers(List<User>userList);

} // interface closed
