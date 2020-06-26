package com.ekin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ekin.entity.Blog;
import com.ekin.mapper.BlogMapper;
import com.ekin.service.BlogService;
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

}
