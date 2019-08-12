package com.ein.test;

import com.alibaba.fastjson.JSON;
import com.ein.Dao.BBSDao;
import com.ein.Dao.CompetitionDao;
import com.ein.Dao.ProblemDao;
import com.ein.Dao.UserDao;
import com.ein.Model.BBS;
import com.ein.Model.Competition;
import com.ein.Model.Problem;
import com.ein.Model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class db_test {

//    @Resource(name = "MusicList_MusicDao")
//    private MusicList_MusicDao musicList_musicDao;

    @Test
    public void test() throws Exception {
        Reader reader = Resources.getResourceAsReader("SqlMapperConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();
//
//
//        UserDao userDao = sqlSession.getMapper(UserDao.class);
//        User user = userDao.findUserByUserName("jianglianEin");
//        System.out.println(user.getEmail());
//
//        MusicDao musicDao = sqlSession.getMapper(MusicDao.class);
//        Music music = musicDao.findMusicByUserName("music1");
//        System.out.println(music.getDatasrc());
//        UserDaoImpl userDao = new UserDaoImpl();
//        User user =  userDao.findUserByUserName("jianglianEin");
//        System.out.println(user.getEmail());



//        user.setIconsrc("img/user_icon/54191467_p0.png");
//        int changeRow = userDao.updataIconsrcByUserName(user);
//        User user = new User();
//        user.setSex(true);
//        user.setUsername("jianglianEIn");
//        user.setEmail("jianglianEin@gmail.com");
//        user.setDiscription("test test");
//        int changeRow = userDao.updataUserMsgByUsername(user);
//        sqlSession.commit();

//        UserDao userDao = sqlSession.getMapper(UserDao.class);
//        Map<String,Object> list = new HashMap<String, Object>();
//        list.put("listname","ISLAND オリジナルサウンドトラック(ISLAND)");
//        list.put("page",8);
//        List<MusicList_Music> musicList_musics = musicList_musicDao.selectMusicList_MusicByListnameLimitPage(list);
//        for (MusicList_Music musicList_music:musicList_musics){
//            System.out.println(musicList_music.getMusicList().getListname());
//            System.out.println(musicList_music.getMusic().getMusicname());
//        }
//        MusicList_Music musicList_music1 = new MusicList_Music();


//        User user = new User();
//
//        user.setDiscription("no");
//        user.setSex(true);
//        user.setStudentId("201613150620");
//        int updataRow = userDao.updataUserProvider(user);
//        sqlSession.commit();
//        System.out.println(updataRow);
        Problem problem = new Problem();
        HashMap<String,String> example = new HashMap<String, String>();
        example.put("inputdata","6,6");
        example.put("outputdata","12");

        problem.setExample(JSON.toJSON(example).toString());
        problem.setId(1);
        ProblemDao problemDao = sqlSession.getMapper(ProblemDao.class);
        int updataRow = problemDao.updataProblemProvider(problem);
        sqlSession.commit();
        System.out.println(updataRow);
    }

}
