package com.zew.demo.iot.mapper;

import com.zew.demo.iot.model.Weather;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Administrator
 */
@Component
@Mapper
public interface WeatherMapper {

    /**
     * 创建数据表
     *
     * @param dbName    数据库名
     * @param tableName 表名
     */
    void createTable(String dbName, String tableName);

    /**
     * 单条插入数据
     *
     * @param weather 数据
     * @return
     */
    int insert(Weather weather);

    /**
     * 批量插入数据
     *
     * @param weatherList 数据列表
     * @return
     */
    int batchInsert(List<Weather> weatherList);

    /**
     * 查询数据
     *
     * @param limit  个数
     * @param offset 偏移
     * @return
     */
    List<Weather> select(@Param("limit") Long limit, @Param("offset") Long offset);
}
