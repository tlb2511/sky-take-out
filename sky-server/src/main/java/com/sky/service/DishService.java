package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    public void saveDishAndFlavors(DishDTO dishDTO);

    public PageResult queryDishPage(DishPageQueryDTO dishPageQueryDTO);

    public void deleteDish(List<Long> ids);

    public void updateDish(DishDTO dishDTO);

    public DishVO queryDishById(Long id);
}
