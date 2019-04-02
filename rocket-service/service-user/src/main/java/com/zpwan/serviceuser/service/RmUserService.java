package com.zpwan.serviceuser.service;

import com.zpwan.serviceuser.model.RmUsers;

import java.util.List;

public interface RmUserService {
    void insertOne(RmUsers rmUsers);

    RmUsers findUserByMobile(String mobile);

    List<RmUsers> findAll();

}
