package com.druh.myRPCVersion2.service;

import com.druh.myRPCVersion2.common.Blog;

// 新的服务接口
public interface BlogService {
    Blog getBlogById(Integer id);
}
