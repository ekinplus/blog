package com.ekin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ekin.entity.Blog;
import com.ekin.search.mq.PostMqIndexMessage;

import java.util.List;


public interface SearchService {

    IPage search(Page page, String keyword);

    int initEsData(List<Blog> records);

    void createOrUpdateIndex(PostMqIndexMessage message);

    void removeIndex(PostMqIndexMessage message);
}
