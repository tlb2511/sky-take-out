package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.impl.DishServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/page")
    @ApiOperation(value = "分页查询菜品信息")
    public Result<PageResult> queryDishPage(DishPageQueryDTO dishPageQueryDTO){
        PageResult pageResult = dishService.queryDishPage(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除菜品")
    public Result deleteDish(@RequestParam List<Long> ids){
        dishService.deleteDish(ids);
        return Result.success();
    }
}
