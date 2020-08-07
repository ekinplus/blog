package com.ekin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ekin.entity.Blog;
import com.ekin.mapper.BlogMapper;
import com.ekin.service.BlogService;
import com.ekin.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ekin
 * @since 2020-06-20
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public void setViewCount(Blog blog) {

        // 从缓存中获取阅读数量
        Integer viewCount = (Integer) redisUtil.hget("rank_blog_" + blog.getId(), "blog:viewCount");
        if(viewCount != null) {
            blog.setViewCount(viewCount + 1);
        } else {
            blog.setViewCount(blog.getViewCount() + 1);
        }
        // 设置新的阅读
        redisUtil.hset("rank_blog_" + blog.getId(), "blog:viewCount", blog.getViewCount());

    }
}
