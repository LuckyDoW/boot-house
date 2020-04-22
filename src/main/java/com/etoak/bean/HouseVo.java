package com.etoak.bean;

import lombok.Data;

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

}
