package com.zpwan.serviceuser.service.impl;

import com.zpwan.serviceuser.dao.RmUsersMapper;
import com.zpwan.serviceuser.model.RmUsers;
import com.zpwan.serviceuser.service.RmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: rocket
 * @description: 用户操作
 * @author: hzzp
 * @create: 2019-03-09 16:43
 **/
@Service
public class RmUserserviceImpl implements RmUserService{

    @Autowired
    private RmUsersMapper rmUsersMapper;


    @Override
    public void insertOne(RmUsers rmUsers) {
        rmUsersMapper.insert(rmUsers);
    }

    @Override
    public RmUsers findUserByMobile(String mobile) {
        return rmUsersMapper.findUserByMobile(mobile);
    }

    @Override
    public List<RmUsers> findAll() {
        return rmUsersMapper.findAll();
    }


}
