package com.zew.demo.iot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Administrator
 */
@Component
@Mapper
public interface DatabaseMapper {

    /**
     * 创建数据库
     *
     * @param dbName 数据库名
     * @return
     */
    int createDatabase(String dbName);

    /**
     * 创建数据库屏配置相关参数
     *
     * @param map 参数
     * @return
     */
    int creatDatabaseWithParameters(Map<String, String> map);

    /**
     * 删除数据库
     *
     * @param dbName 数据库名
     * @return
     */
    int dropDatabase(String dbName);

    /**
     * 选择数据库
     *
     * @param dbName 数据库名
     * @return
     */
    int useDatabase(String dbName);

}
