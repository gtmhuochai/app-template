package edu.jmu.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author LiuJiaJun
 * @date 2018/9/27 10:43
 */
@MappedTypes(List.class)
@MappedJdbcTypes({JdbcType.VARCHAR,JdbcType.LONGNVARCHAR})
public class ListTypeHandler extends BaseTypeHandler<List<String>> {
    private final static String SEPARATOR = ",";
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
       //1.List集合转成字符串
        StringBuffer buffer = new StringBuffer();
        for (String str: strings) {
            buffer.append(str).append(',');
        }
        //2.设置ps,并且去掉最后一个,
        preparedStatement.setString(i,buffer.toString().substring(0,buffer.length()-1));
    }

    @Override
    public List<String> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        if(null != resultSet.getString(s)){
            String [] split = resultSet.getString(s).split(SEPARATOR);
            return Arrays.asList(split);
        }else{
            return null;
        }

    }

    @Override
    public List<String> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        if(null != resultSet.getString(i)){
            String [] split = resultSet.getString(i).split(SEPARATOR);
            return Arrays.asList(split);
        }else{
            return null;
        }

    }

    @Override
    public List<String> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        if(null != callableStatement.getString(i)){
            String [] split = callableStatement.getString(i).split(SEPARATOR);
            return Arrays.asList(split);
        }else{
            return null;
        }
    }
}
