package com.example.mybatisplusdemo.Entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//下面三个注解使用lombok
//Data注解可以提供set，get，toString方法
//后边两个提供所有参数和无参构造方法
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Custom {
    //对应数据库的主键
    //可以选择是自增还是手动输入
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;

    //逻辑删除注解
    @TableLogic
    private Byte deleted;

    //乐观锁注解
    @Version
    private Integer version;

    //填充字段，插入时填充
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //插入和更新时填充，需要编写handler填充策略
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
