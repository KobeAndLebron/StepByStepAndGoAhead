package com.cjs.gohead.mybatis.typehandler;

import com.cjs.gohead.mybatis.entity.Student;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Get the class of T.
 * Created by ChenJingShuai on 2017/5/21.
 */
public class EnumHandler<T> extends BaseTypeHandler<Enum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Enum parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public Enum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Enum.valueOf(Student.Sex.class, rs.getString(columnName));
    }

    @Override
    public Enum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Enum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
