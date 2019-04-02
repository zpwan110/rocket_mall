package com.zpwan.serviceuser.dao;
import com.zpwan.serviceuser.model.RmUsers;

import java.util.List;

public interface RmUsersMapper {
    int insert(RmUsers record);

    int insertSelective(RmUsers record);

    RmUsers findUserByMobile(String mobile);

    List<RmUsers> findAll();
}