package com.example.newsappadmin.utils;

import com.example.newsappadmin.model.Post;

import java.util.List;

public interface PostsCallBack
{
    void getPosts(List<Post>postList);
}
