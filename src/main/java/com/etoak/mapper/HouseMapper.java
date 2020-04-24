package com.etoak.mapper;

import com.etoak.bean.House;
import com.etoak.bean.HouseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseMapper {
    /**
     * 添加房源
     * @param house
     * @return
     */
    int addHouse(House house);

    /**
     * 房源列表查询
     * @param houseVo
     * @return
     */
    List<HouseVo> queryList(HouseVo houseVo);

    /**
     * 更新房源
     * @param house
     * @return
     */
    int updateHouse(House house);

    /**
     * 删除房源
     * @param id
     * @return
     */
    int  deleteById(@Param("id") int id);
}
