package com.zew.demo.iot.service;

import com.zew.demo.iot.mapper.DatabaseMapper;
import com.zew.demo.iot.mapper.WeatherMapper;
import com.zew.demo.iot.model.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class WeatherService {

    private final DatabaseMapper databaseMapper;
    private final WeatherMapper weatherMapper;

    public WeatherService(DatabaseMapper databaseMapper, WeatherMapper weatherMapper) {
        this.databaseMapper = databaseMapper;
        this.weatherMapper = weatherMapper;
    }

    public boolean init() {
        try {
            // 删除
            databaseMapper.dropDatabase("db");
            // 创建 databaseMapper.createDatabase( "db")
            Map<String, String> map = new HashMap<>(4);
            map.put("dbName", "db");
            map.put("keep", "36500");
            map.put("days", "30");
            map.put("blocks", "4");
            databaseMapper.creatDatabaseWithParameters(map);
            // 选择
            databaseMapper.useDatabase("db");
            // 创建表
            weatherMapper.createTable("db", "weather");
            return true;
        } catch (Exception e) {
            log.error("创建数据表出错", e);
        }
        return false;
    }

    public int save(Weather weather) {
        return weatherMapper.insert(weather);
    }

    public int batchSave(List<Weather> weatherList) {
        return weatherMapper.batchInsert(weatherList);
    }

    public List<Weather> query(Long limit, Long offset) {
        return weatherMapper.select(limit, offset);
    }
}
