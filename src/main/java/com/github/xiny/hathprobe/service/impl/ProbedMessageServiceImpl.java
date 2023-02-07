package com.github.xiny.hathprobe.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.xiny.hathprobe.domain.ProbedMessage;
import com.github.xiny.hathprobe.service.ProbedMessageService;
import com.github.xiny.hathprobe.mapper.ProbedMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author 86459
* @description 针对表【probed_message】的数据库操作Service实现
* @createDate 2023-02-04 20:51:39
*/
@Service
public class ProbedMessageServiceImpl extends ServiceImpl<ProbedMessageMapper, ProbedMessage>
    implements ProbedMessageService{

}




