package com.ein.Dao.Provider;

import com.ein.Model.Competition;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class CompetitionDaoProvider {
    public String updataCompetition(@Param("competition") final Competition competition){
        SQL sql = new SQL(){{
            UPDATE("competition_db");



            if (competition.getSponsor() != null){
                SET("sponsor_studentId = #{competition.sponsor.studentId}");
            }
            if (competition.getTitle() != null){
                SET("title = #{competition.title}");
            }
            if (competition.getStartDate() != null){
                SET("startDate = #{competition.startDate}");
            }
            if (competition.getEndDate() != null){
                SET("endDate = #{competition.endDate}");
            }
            SET("isOpen = #{competition.isOpen}");
            if (competition.getLanguageType() != null){
                SET("languageType = #{competition.languageType}");
            }
            if (competition.getDiscription() != null){
                SET("discription = #{competition.discription}");
            }
            WHERE("id = #{competition.id}");
        }};
        return sql.toString();

    }
}
