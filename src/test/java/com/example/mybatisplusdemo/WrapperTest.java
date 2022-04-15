package com.example.mybatisplusdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplusdemo.Entity.User;
import com.example.mybatisplusdemo.Mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class WrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
        // 查询name不为空的用户，并且邮箱不为空的用户，年龄大于等于22
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 和前面的map查询相似
        wrapper.isNotNull("name").isNotNull("email").ge("age", 22);
        userMapper.selectList(wrapper);
    }

    @Test
    public void test() {
        // 查询name为elden的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 和前面的map查询相似
        wrapper.eq("name", "elden");
        // 查询一个建议用selectOne，不用selectList
        userMapper.selectOne(wrapper);
    }

    @Test
    public void test2() {
        // 查询年龄为20~30的用户,包括20,30
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age", 20, 30);
        Long count = userMapper.selectCount(wrapper);
        System.out.println(count);
    }

    //模糊查询
    @Test
    public void test3() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //不包括e， 左和右， %t和t%
        wrapper.notLike("name", "e").likeRight("email", "t");

        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    @Test
    public void test4() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //id在子查询中查出来
        wrapper.inSql("name", "select name from user where id < 3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    @Test
    public void test5() {
        // 通过id排序，降序
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }
}
