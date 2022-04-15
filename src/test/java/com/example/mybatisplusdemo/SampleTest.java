package com.example.mybatisplusdemo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplusdemo.Entity.User;
import com.example.mybatisplusdemo.Mapper.GETMapper;
import com.example.mybatisplusdemo.Mapper.StudentMapper;
import com.example.mybatisplusdemo.Mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//@Slf4j
@SpringBootTest
public class SampleTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private GETMapper getMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        User user = new User();
        user.setAge(22);
        user.setName("b22");
        user.setEmail("b22@qq.com");
//        userMapper.insert(user);
        user.setId(8L);
        userMapper.updateById(user);
        //log.info("select start");
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);

    }

    //测试乐观锁成功
    @Test
    public void testOptimisticLocker() {
        User user = userMapper.selectById(2L);
        user.setName("LZH");
        user.setEmail("LZH@qq.com");
        userMapper.updateById(user);
    }

    //测试乐观锁失败
    @Test
    public void testOptimisticLocker2() {
        //线程1
        User user = userMapper.selectById(2L);
        user.setName("ring");
        user.setEmail("ring@qq.com");

        //线程2 模拟另外一个线程执行了插队操作
        User user2 = userMapper.selectById(2L);
        user2.setName("elden");
        user2.setEmail("elden@qq.com");
        userMapper.updateById(user2);

        userMapper.updateById(user);    //如果没有乐观锁，就会覆盖插队线程的值
    }

    //测试查询
    @Test
    public void testSelectById() {
        User user = userMapper.selectById(2L);
        System.out.println(user);
    }

    //查询多个
    @Test
    public void testSelectBatchById() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    //条件查询 map
    @Test
    public void testSelectByBatchIds() {
        HashMap<String, Object> map = new HashMap<>();
        //自定义查询
        map.put("name", "elden");
        map.put("age", "20");

        List<User> user = userMapper.selectByMap(map);
        user.forEach(System.out::println);
    }

    //分页查询
    @Test
    public void testSelectPage() {
        //参数1：当前页
        //参数2 ：页面大小
        Page<User> page = new Page<>(1, 9);;
        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
        //总条数，有其他方法也可以获取到下一页之类的
        System.out.println(page.getTotal());
    }

    //删除
    // application.yml如果配置了逻辑删除，那个以下语句的结果就不是删除数据，而是更新代表删除的逻辑字段名
    // 被逻辑删除的数据，查询时不会被查到
    @Test
    public void testDel() {
        userMapper.deleteById(9L);
    }

    @Test
    public void testGetStudent() {
        getMapper.selectById(1);
    }

    @Test
    public void testCreate() {
        System.out.println(System.getProperty("user.dir"));
        /*FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3307/mybatisplus", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("LZH") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(System.getProperty("user.dir") + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.example.mybatisplusdemo") // 设置父包名
                            .moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir") + "/src/main/java")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();*/
    }
}
