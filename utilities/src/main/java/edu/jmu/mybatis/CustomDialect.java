package edu.jmu.mybatis;

import com.github.pagehelper.Page;
import com.github.pagehelper.dialect.MySqlDialect;
import com.github.pagehelper.util.SqlUtil;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

import java.util.Map;

/**
 * CustomDialect
 *
 * @author guantianmin
 * @date 2018/6/23 19:57
 */
public class CustomDialect extends MySqlDialect {

    public static final String SQL_SIGN = "AS limitable";

    public CustomDialect(SqlUtil sqlUtil) {
        super(sqlUtil);
    }

    @Override
    public Object processPageParameter(MappedStatement ms, Map<String, Object> paramMap, Page page, BoundSql boundSql, CacheKey pageKey) {
        return super.processPageParameter(ms, paramMap, page, boundSql, pageKey);
    }

    @Override
    public String getPageSql(String sql, Page page, RowBounds rowBounds, CacheKey pageKey) {
        StringBuilder sqlBuilder = new StringBuilder(sql.length() + 14);
        if (sql.indexOf(SQL_SIGN) != -1) {
            StringBuffer stringBuffer = new StringBuffer(sql);
            stringBuffer.indexOf(SQL_SIGN);

            StringBuffer mae = new StringBuffer(stringBuffer.substring(0, stringBuffer.indexOf(SQL_SIGN)));
            StringBuffer uShiRo = new StringBuffer(stringBuffer.substring(stringBuffer.indexOf(SQL_SIGN), sql.length()));

            mae.insert(mae.lastIndexOf(")"), " limit ?,?");
            return mae.append(uShiRo).toString();
        }
        sqlBuilder.append(sql);
        sqlBuilder.append(" limit ?,?");
        return sqlBuilder.toString();
    }
}