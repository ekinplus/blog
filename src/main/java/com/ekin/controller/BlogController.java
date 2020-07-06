package com.ekin.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ekin.common.lang.Result;
import com.ekin.config.RabbitConfig;
import com.ekin.entity.Blog;
import com.ekin.search.mq.PostMqIndexMessage;
import com.ekin.service.BlogService;
import com.ekin.service.SearchService;
import com.ekin.util.ShiroUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ekin
 * @since 2020-06-20
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @Autowired
    SearchService searchService;

    @Autowired
    AmqpTemplate amqpTemplate;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {

        /*return Result.success("ES索引初始化成功，共 " + total + " 条记录！", null);*/
        Page page = new Page(currentPage, 5);
        IPage<Blog> pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.succ(pageData);
    }

        @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");

        return Result.succ(blog);
    }

    @PostMapping("/blog/delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id) {
        boolean b = blogService.removeById(id);
        Assert.isTrue(b,"该博客已被删除");

        amqpTemplate.convertAndSend(RabbitConfig.es_exchage, RabbitConfig.es_bind_key,
                new PostMqIndexMessage(id, PostMqIndexMessage.REMOVE));

        return Result.succ(null);
    }

    @GetMapping("/blog/search")
    public Result searchList(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "") String word) {

        Page page = new Page(currentPage, 5);
        IPage pageData = searchService.search(page,word);

        return Result.succ(pageData);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {

        Blog temp = null;
        if(blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            System.out.println(ShiroUtil.getProfile().getId());
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有权限编辑");

        } else {

            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setUserName(ShiroUtil.getProfile().getUsername());
            temp.setCreated(new Date());
            temp.setStatus(0);
        }

        BeanUtil.copyProperties(blog, temp, "id", "userId", "userName","created", "status");
        blogService.saveOrUpdate(temp);

        amqpTemplate.convertAndSend(RabbitConfig.es_exchage, RabbitConfig.es_bind_key,
                new PostMqIndexMessage(temp.getId(), PostMqIndexMessage.CREATE_OR_UPDATE));

        return Result.succ(null);
    }
}
