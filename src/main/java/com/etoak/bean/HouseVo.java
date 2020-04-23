package com.etoak.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 房源链表
 * 中文名称
 * @author DouDou、
 */
@Data
public class HouseVo  extends House{
    /**
     * 租赁方式名称
     */
    private String rentModeName;
    /**
     * 户型名称
     */
    private String houseTypeName;
    /**
     * 朝向名称
     */
    private String orientationName;
    /**
     * 接收前端户型（多选）的参数值
     * 不把这个字段封装返回结果JSON中
     */
    @JsonIgnore
    private String[] houseTypeList;
    /**
     * 接收前端朝向（多选）的参数值
     */
    @JsonIgnore
    private List<String> orientationList;

    /**
     * 100-1000,1000-1500,
     * 这个不是用来接收
     */
    @JsonIgnore
    List<Map<String,Integer>> rentalMapList;

}
