package com.github.xiny.hathprobe.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.xiny.hathprobe.domain.Client;
import com.github.xiny.hathprobe.service.ClientService;
import com.github.xiny.hathprobe.mapper.ClientMapper;
import org.springframework.stereotype.Service;

/**
* @author 86459
* @description 针对表【client】的数据库操作Service实现
* @createDate 2023-02-04 20:43:45
*/
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client>
    implements ClientService{

}




