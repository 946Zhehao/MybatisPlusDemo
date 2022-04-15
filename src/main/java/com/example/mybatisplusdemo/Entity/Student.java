package com.example.mybatisplusdemo.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    //对应数据库的主键
    //可以选择是自增还是手动输入
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
}
