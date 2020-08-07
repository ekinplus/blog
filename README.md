# blog
博客地址：https://blog.csdn.net/jie_7012/article/details/107158343

本博客是学习过程中搭建的项目，为了融合更多知识点，让博客看起来更加高大上，使用了多个框架组合。目前实现了博客的增删改查，搭建了博客搜索引擎。

### 实现效果：
![image](https://img-blog.csdnimg.cn/20200706142755515.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2ppZV83MDEy,size_16,color_FFFFFF,t_70)
![image](https://img-blog.csdnimg.cn/20200706143239387.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2ppZV83MDEy,size_16,color_FFFFFF,t_70)
### 技术栈：
* SpringBoot
* Rabbitmq
* ElasticSearch
* mybatis plus
* shiro
* lombok
* redis
* hibernate validatior
* jwt

### Java后端接口开发
1.SpringBoot整合Mybatis Plus

2.统一结果封装
这里我们用到了一个Result的类，这个用于我们的异步统一返回的结果封装。一般来说，结果里面有几个要素必要的

是否成功，可用code表示（如200表示成功，400表示异常）
结果消息
结果数据

3.整合shiro+jwt，并会话共享

4.登录接口开发

5.博客接口开发

6.ElasticSearch+Rabbitmq实现搜索引擎

### Vue前端页面开发
1.Vue环境准备，新建项目

2.安装element-ui，axios
引入element-ui组件（element.eleme.cn），这样我们就可以获得好看的vue组件，开发好看的博客界面。

3.页面路由
我们在views文件夹下定义几个页面：

* BlogDetail.vue（博客详情页）
* BlogEdit.vue（编辑博客）
* Blogs.vue（博客列表）
* Login.vue（登录页面）
* Search.vue（搜索页面）

4.路由权限拦截

