package com.zew.demo.iot.ctrl;

import com.zew.demo.iot.model.Weather;
import com.zew.demo.iot.service.WeatherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @ApiOperation(value = "初始数据库和数据表weather")
    @GetMapping("/init")
    public boolean init() {
        return weatherService.init();
    }

    @ApiOperation(value = "插入单条数据")
    @PostMapping
    public int saveWeather(@RequestBody Weather weather) {
        return weatherService.save(weather);
    }

    @ApiOperation(value = "插入多条数据")
    @PostMapping("/batch")
    public int batchSaveWeather(@RequestBody List<Weather> weatherList) {
        return weatherService.batchSave(weatherList);
    }

    @ApiOperation(value = "查询数据")
    @GetMapping("/{limit}/{offset}")
    public List<Weather> queryWeather(@PathVariable Long limit, @PathVariable Long offset) {
        return weatherService.query(limit, offset);
    }

}
