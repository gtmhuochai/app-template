package edu.jmu.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

/**
 * unix时间戳类型转换
 *
 * @author guantianmin
 * @date 2018/9/11 10:30
 */
public class UnixTimestampTypeHandler implements TypeHandler<Instant> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Instant instant, JdbcType jdbcType) throws SQLException {
        preparedStatement.setLong(i, instant == null ? 0L : instant.getEpochSecond());
    }

    @Override
    public Instant getResult(ResultSet resultSet, String s) throws SQLException {
        long time = resultSet.getLong(s);
        if(time <= 0){
            return null;
        }else{
            return Instant.ofEpochSecond(time);
        }
    }

    @Override
    public Instant getResult(ResultSet resultSet, int i) throws SQLException {
        long time = resultSet.getLong(i);
        if(time <= 0){
            return null;
        }else{
            return Instant.ofEpochSecond(time);
        }
    }

    @Override
    public Instant getResult(CallableStatement callableStatement, int i) throws SQLException {
        long time = callableStatement.getLong(i);
        if(time <= 0){
            return null;
        }else{
            return Instant.ofEpochSecond(time);
        }
    }
}
