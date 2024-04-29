package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.result.Result;
import com.sky.service.impl.DishServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/admin/dish")
@Api(tags = "菜品信息")
public class DishController {

    @Autowired
    private DishServiceImpl dishService;
    /**
     * 新增菜品
     * @param dishDTO
     */
    @PostMapping
    @ApiOperation(value = "新增菜品")
    public Result saveDishAndFlavors(@RequestBody DishDTO dishDTO){

        dishService.saveDishAndFlavors(dishDTO);
        return Result.success();
    }
}
