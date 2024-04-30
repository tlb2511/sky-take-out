package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorsMapper {

    public void save(List<DishFlavor> dishFlavor);

    @Delete("delete from dish_flavor where dish_id = #{dish_id}")
    void deleteById(Long dish_id);
}
