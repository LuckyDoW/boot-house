package com.etoak.bean;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

/**
 * 房源的主类
 * 注解为后端验证
 */
@Data
public class House {
    private Integer id;
    /**
     * 不能为空 message为提示信息
     */
    @NotNull(message = "省份必填")
    private Integer province;
    @NotNull(message = "市必填")
    private Integer city;
    @NotNull(message = "区必填")
    private Integer area;
    /**
     * 所在区名称
     */
    private String areaName;
    /**
     * 租赁方式 NotBlank包含NotNull
     */
    @NotBlank(message = "租赁方式不能为空")
    private String rentMode;
    /**
     * 户型
     */
    private String houseType;
    /**
     * 朝向
     */
    private String orientation;
    /**
     * 租金
     */
    @NotNull(message = "租金必填")
    @Max(value = 100000,message = "租金不能超过十万")
    @Min(value = 100,message = "租金最小为￥100")
    private Integer rental;
    /**
     * 详细地址
     */
    @Size(min = 6,max = 30,message = "地址为6-30个字符")
    @NotNull(message = "地址必填")
    private String address;
    /**
     * 房屋图片
     */
    private String pic;
    /**
     * 发布时间
     */
    private String publishTime;


}
