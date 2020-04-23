package com.etoak.controller;

import com.etoak.bean.House;
import com.etoak.bean.HouseVo;
import com.etoak.bean.Page;
import com.etoak.exception.ParamException;
import com.etoak.service.HouseService;
import com.etoak.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/house")
@Slf4j
public class HouseController {
    /**
     * 读取默认配置文件
     * 获取文件上传目录
     */
    @Value("${upload.dir}")
    private String uploadDir;
    /**
     * 读取默认配置文件
     * 获取图片访问路径前缀
     */
    @Value("${upload.savePathPrefix}")
    private String savePathPrefix;

    @Autowired
    HouseService houseService;

    /**
     * 跳转到添加页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "house/add";
    }

    /**
     * 后端验证的第二种方法
     * @param file
     * @param house
     * @return
     * @throws IOException
     * @throws IllegalStateException
     */
    @PostMapping("/add2")
    public String add2(@RequestParam("file")MultipartFile file, House house)
            throws IOException,IllegalStateException{
        //调用后端验证的方法
        ValidationUtil.Validate(house);

        //上传文件
        //文件后缀 .jpg .png~~~
        String originalFilename=file.getOriginalFilename();

        String prefix = UUID.randomUUID().toString().replaceAll("-","");
        /*新文件组成
         * 原始文件名_uuid.文件后缀
         * */
        String newFileName =  prefix + "_" + originalFilename;

        //上传文件目录
        File destFile = new File(this.uploadDir,newFileName);
        file.transferTo(destFile);

        //给House设置访问地址
        house.setPic(this.savePathPrefix+newFileName);

        houseService.addHouse(house);
        return "redirect:/house/toAdd";
    }

    /**
     * 添加房源
     * @return
     */
    @PostMapping("/add")
    public String add(@RequestParam("file")MultipartFile file, @Valid House house, BindingResult bindingResult)
            throws IOException,IllegalStateException{
        /*第一种验证*/
        //后端的校验  + @Valid + BindingResult bindingResult  + 主类注解
        // 对参数进行校验
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if(CollectionUtils.isNotEmpty(allErrors)) {
            StringBuffer msgBuffer = new StringBuffer();
            for(ObjectError objectError : allErrors) {
                String message = objectError.getDefaultMessage();
                msgBuffer.append(message).append(";");
            }
            throw new ParamException("参数校验失败：" + msgBuffer.toString());
        }

        //上传文件
        //文件后缀 .jpg .png~~~
        String originalFilename=file.getOriginalFilename();

        String prefix = UUID.randomUUID().toString().replaceAll("-","");
        /*新文件组成
         * 原始文件名_uuid.文件后缀
         * */
        String newFileName =  prefix + "_" + originalFilename;

        //上传文件目录
        File destFile = new File(this.uploadDir,newFileName);
        file.transferTo(destFile);

        //给House设置访问地址
        house.setPic(this.savePathPrefix+newFileName);

        houseService.addHouse(house);
        return "redirect:/house/toAdd";
    }

    /**
     * 房源查询列表
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @param houseVo 查询条件
     * @return
     */
    @GetMapping(value="/list",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Page<HouseVo> queryList(
            @RequestParam(required = false,defaultValue = "1")int pageNum,
            @RequestParam(required = false,defaultValue = "10")int pageSize,
            HouseVo houseVo,
            /*多选框传值第二种办法 */
            @RequestParam(value ="rentalList[]",required = false) String[] rentalList){
        log.info("pageNum:{},pageSize:{},houseVo：{},rentalList：{}",pageNum,pageSize,houseVo,rentalList);
        return houseService.queryList(pageNum,pageSize,houseVo,rentalList);
    }

    /**
     * 房源列表页面
     * @return
     */
    @GetMapping("/toList")
    public String toList(){
        return "house/list";
    }

}
