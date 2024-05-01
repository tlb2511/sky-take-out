package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorsMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorsMapper dishFlavorsMapper;

    @Autowired
    private SetMealDishMapper setMealDishMapper;
    /**
     * 新增菜品
     * @param dishDTO
     */
    @Transactional
    public void saveDishAndFlavors(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //保存菜品
        dishMapper.save(dish);
        //获取插入数据生成的id值，用来作为flavor表的主键值
        Long id = dish.getId();
        //保存喜好
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0){
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(id);
            });
            dishFlavorsMapper.save(flavors);
        }
    }

    /**
     * 分页查询菜品信息
     * @param dishPageQueryDTO
     * @return
     */
    public PageResult queryDishPage(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> dishVOPage = dishMapper.selectPage(dishPageQueryDTO);
        return new PageResult(dishVOPage.getTotal(), dishVOPage.getResult());
    }

    @Transactional
    public void deleteDish(List<Long> ids) {
        //1、判断当前所有id是否都是未销售状态
        for(Long id : ids){
            Dish dish = dishMapper.selectById(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        //2、是否在套餐里面
        List<Long> setMealIds = setMealDishMapper.selectByDishId(ids);
        if(setMealIds != null && setMealIds.size() > 0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

//        for(Long id : ids){
//            //3、删除菜品
//            dishMapper.deleteById(id);
//            //4、删除菜品对应的口味
//            dishFlavorsMapper.deleteByDishId(id);
//        }
        //可以改成批量删除 delete from dish where id in (***)
        dishMapper.deleteByIds(ids);
        dishFlavorsMapper.deleteByDishIds(ids);
    }

    @Transactional
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //1、更新dish
        dishMapper.update(dish);
        //2、删除关联的flavor
        dishFlavorsMapper.deleteByDishId(dishDTO.getId());
        //3、是否有flavor
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0){
            flavors.forEach(flavor->{
                flavor.setDishId(dish.getId());
            });
            dishFlavorsMapper.save(flavors);
        }
    }

    public DishVO queryDishById(Long id) {
        DishVO dishVO = new DishVO();
        Dish dish = dishMapper.selectById(id);
        BeanUtils.copyProperties(dish,dishVO);
        List<DishFlavor> dishFlavor = dishFlavorsMapper.selectByDishId(id);
        dishVO.setFlavors(dishFlavor);
        return dishVO;
    }
}
