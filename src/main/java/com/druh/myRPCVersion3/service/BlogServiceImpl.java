package com.druh.myRPCVersion3.service;

import com.druh.myRPCVersion3.common.Blog;
import com.druh.myRPCVersion3.service.BlogService;

public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").useId(22).build();
        System.out.println("客户端查询了id为："+id+"的博客");
        return blog;
    }
}
