package com.github.xiny.hathprobe.mapper;

import com.github.xiny.hathprobe.domain.Client;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86459
* @description 针对表【client】的数据库操作Mapper
* @createDate 2023-02-04 20:43:45
* @Entity com.github.xiny.domain.Client
*/
@Mapper
public interface ClientMapper extends BaseMapper<Client> {

}




