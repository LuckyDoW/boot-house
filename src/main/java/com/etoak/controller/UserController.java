package com.etoak.controller;

import com.etoak.bean.User;
import com.etoak.commons.CommonResult;
import com.etoak.exception.ParamException;
import com.etoak.exception.UserLoginException;
import com.etoak.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    // 用户的激活状态
    public static final int ACTIVE_STATE = 1;

    @Autowired
    UserService userService;

    /**
     * 跳转接口  注册页面
     * @return
     */
    @GetMapping("/toReg")
    public String toRegPage(){
        return "/user/reg";
    }

    /**
     * 注册
     * @param confirmPassword
     * @param user
     * @return
     */
    @PostMapping("/reg")
    public String reg(@RequestParam String confirmPassword, User user){
        if(!StringUtils.equals(confirmPassword,user.getPassword())){
            throw new ParamException("两次密码不一致");
        }
        userService.addUser(user);
        return "redirect:/user/toLogin";//跳转到登录
    }

    /**
     *  校验用户名
     *  返回为字符串
     *  需要加   @ResponseBody
     * @return
     */
    @GetMapping("/validateName")
    @ResponseBody
    public String validateName(@RequestParam String name){

        return userService.queryByName(name)!=null?"false":"true";
    }

    /**
     *  跳转登录页面
     * @return
     */
    @GetMapping("/toLogin")
    public String toLogin(){
        return "user/login";
    }

    /**
     * 登录接口
     */
    @PostMapping("/login")
    @ResponseBody
    public CommonResult login(
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String code,
            HttpServletRequest request){
        /**
         * 1、验证验证码是否正确 如果正确进入2；如果错误，直接返回
         * 2、先根据用户名查询用户，如果用户名存在，进入3，如果不存在，直接返回
         * 3、判断用户是否是激活状态，如果是，进入4; 若否，直接返回；
         * 4、对比密码 正确 进入5 ，错误：返回
         * 5、将用户放到session
         *      4.1、销毁根据之前的存储验证码的session
         *      4.2、将用户的密码设置为null
         *      4.3、创建新的session保存用户
         */
        HttpSession session = request.getSession();
        String sessionCode = String.valueOf(session.getAttribute("code"));
        if(!StringUtils.equals(code,sessionCode)){
            /**
             * 自定义Exception
             */
            throw new UserLoginException("验证码错误");

        }
        /**
         * 根据用户名查询用户
         */
        User user = userService.queryByName(name);
        if(ObjectUtils.isEmpty(user)){
            log.error("未查询到用户");
            throw  new UserLoginException("用户名或密码错误");
        }
        /**
         * 判断用户状态
         */
        if(!(user.getState() == ACTIVE_STATE)) {
            throw new UserLoginException("用户状态异常");
        }

        //md5加密
        password = DigestUtils.md5Hex(password);
        if(!StringUtils.equals(password,user.getPassword())){
            log.error("密码错误");
            throw new UserLoginException("用户名或密码错误");
        }
        //销毁session
        session.invalidate();

        session = request.getSession();
        user.setPassword(null);
        session.setAttribute("user",user);

        return new CommonResult(CommonResult.SUCCESS_CODE,CommonResult.SUCCESS_MSG);

    }

    /**
     * 退出
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/user/toLogin";
    }


}
