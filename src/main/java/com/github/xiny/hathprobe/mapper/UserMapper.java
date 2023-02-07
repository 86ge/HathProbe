package com.github.xiny.hathprobe.mapper;

import com.github.xiny.hathprobe.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86459
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-02-06 22:31:43
* @Entity com.github.xiny.hathprobe.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




