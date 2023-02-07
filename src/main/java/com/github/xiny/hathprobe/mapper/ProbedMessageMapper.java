package com.github.xiny.hathprobe.mapper;

import com.github.xiny.hathprobe.domain.ProbedMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 86459
* @description 针对表【probed_message】的数据库操作Mapper
* @createDate 2023-02-04 20:51:39
* @Entity com.github.xiny.hathprobe.domain.ProbedMessage
*/
@Mapper
public interface ProbedMessageMapper extends BaseMapper<ProbedMessage> {


    @Select("""
SELECT * FROM `probed_message`
where probed_message.client_id = #{clientId}
and timestamp >= DATE_SUB(CURRENT_TIMESTAMP(),interval #{intervalTime} minute)
ORDER BY msg_id desc
""")
    List<ProbedMessage> getMessage(int clientId, int intervalTime);
}




