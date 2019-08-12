package com.ein.Dao.Provider;

import com.ein.Model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class UserDaoProvider {
    public String updataUser(@Param("user") final User user){
        SQL sql = new SQL(){{
            UPDATE("user_db");

            SET("sex = #{user.sex}");
            if (user.getUsername() != null){
                SET("username = #{user.username}");
            }

            if (user.getPassword() != null){
                SET("password = #{user.password}");
            }
            if (user.getDiscription() != null){
                SET("discription = #{user.discription}");
            }
            if (user.getEmail() != null){
                SET("email = #{user.email}");
            }
            if (user.getGrade() != null){
                SET("grade = #{user.grade}");
            }if (user.getIcon() != null){
                SET("icon = #{user.icon}");
            }if (user.getMajor() != null){
                SET("major = #{user.major}");
            }
            if (user.getPassNum() != null){
                SET("passNum = #{user.passNum}");
            }
            if (user.getQQ() != null){
                SET("QQ = #{user.QQ}");
            }
            if (user.getRights() != null){
                SET("rights = #{user.rights}");
            }
            WHERE("studentId = #{user.studentId}");
        }};
        return sql.toString();

    }
}
