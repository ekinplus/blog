package com.ekin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ekin.entity.User;
import com.ekin.mapper.UserMapper;
import com.ekin.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
