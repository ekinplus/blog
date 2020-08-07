package com.ekin.schedules;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ekin.entity.Blog;
import com.ekin.service.BlogService;
import com.ekin.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class ViewCountSyncTask {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    BlogService blogService;

    @Scheduled(cron = "0 0/60 * * * *")//一分钟（测试用）
    public void blogViewCountSync() {
        Set<String> keys = redisTemplate.keys("rank_blog_*");
        List<String> ids = new ArrayList<>();
        for (String key : keys) {
            String blogId = key.substring("rank_blog_".length());
            if(redisUtil.hHasKey("rank_blog_" + blogId, "blog:viewCount")){
                ids.add(blogId);
            }
        }
        if(ids.isEmpty()) return;
        List<Blog> blogs = blogService.list(new QueryWrapper<Blog>().in("id", ids));
        Iterator<Blog> it = blogs.iterator();
        List<String> syncKeys = new ArrayList<>();
        while (it.hasNext()) {
            Blog blog = it.next();
            Object count =redisUtil.hget("rank_blog_" + blog.getId(), "blog:viewCount");
            if(count != null) {
                blog.setViewCount(Integer.valueOf(count.toString()));
                syncKeys.add("rank_blog_" + blog.getId());
            } else {
                //不需要同步的
            }
        }
        if(blogs.isEmpty()) return;
        boolean isSuccess = blogService.updateBatchById(blogs);
        if(isSuccess) {
            for(Blog blog : blogs) {
                // 删除缓存中的阅读数量，防止重复同步（根据实际情况来）
                redisUtil.hdel("rank_blog_" + blog.getId(), "blog:viewCount");
            }
        }
        log.info("同步文章阅读成功 ------> {}", syncKeys);
    }
}

