package edu.jmu.security.mapper;

import edu.jmu.security.model.LoginHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author guantianmin
 * @Date 2018-06-02
 */
@Mapper
public interface LoginHistoryMapper {

    /**
     * 添加登录日志
     *
     * @param loginHistoryMapper 登录日志信息
     * @return 返回登录是否成功
     */
    Boolean addLoginHistory(LoginHistory loginHistoryMapper);

    /**
     * 对登录日志表的所有字段进行模糊查询
     * @param keyword 查询条件
     * @return 返回登录日志记录
     */
    List<LoginHistory> queryByKeyword(@Param("keyword") Object keyword);

    /**
     * 根基条件查询
     * @param userId
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    List<LoginHistory> queryByRequirement(@Param("userId") Long userId, @Param("type") Integer type, @Param("startTime") Long startTime, @Param("endTime") Long endTime);
}
