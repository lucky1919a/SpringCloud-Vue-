package com.course.system.controller.admin;

import com.alibaba.fastjson.JSON;
import com.course.server.common.Page;
import com.course.server.common.ResponseServer;
import com.course.server.common.UuidUtil;
import com.course.server.common.ValidatorUtil;
import com.course.server.dto.Constants;
import com.course.server.dto.LoginUserDto;
import com.course.server.dto.UserDto;
import com.course.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public static final String BUSINESS_NAME = "用户";

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/query.do")
    public ResponseServer query(@RequestBody Page page) {
        ResponseServer responseServer = new ResponseServer();
       userService.list(page);
        responseServer.setContent(page);
        return responseServer;
    }

    @RequestMapping("/save")
    public ResponseServer save(@RequestBody UserDto userDto) {

        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        // 保存校验
        ValidatorUtil.require(userDto.getLoginName(), "登陆名");
        ValidatorUtil.length(userDto.getLoginName(), "登陆名", 1, 50);
        ValidatorUtil.length(userDto.getName(), "昵称", 1, 50);
        ValidatorUtil.require(userDto.getPassword(), "密码");
        ResponseServer responseServer = new ResponseServer();
        userService.save(userDto);
        responseServer.setContent(userDto);
        return responseServer;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseServer save(@PathVariable String id) {
        logger.info("id: {}", id);
        ResponseServer responseServer = new ResponseServer();
        userService.delete(id);
        return responseServer;
    }

    /**
     * 重置密码
     * @param userDto
     * @return
     */
    @PostMapping(value="/save-password")
    public ResponseServer savePassword(@RequestBody UserDto userDto){
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseServer responseServer = new ResponseServer();
        userService.savePassword(userDto);
        responseServer.setContent(userDto);
        return responseServer;

    }

    /**
     * 控台登录
     */
    @PostMapping("/login")
    public ResponseServer login(@RequestBody UserDto userDto,HttpServletRequest request) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseServer responseServer = new ResponseServer();

        String imageCode = (String) redisTemplate.opsForValue().get(userDto.getImageCodeToken());
        logger.info("从redis中获取到的验证码：{}", imageCode);
        if (StringUtils.isEmpty(imageCode)) {
            responseServer.setSuccess(false);
            responseServer.setMessage("验证码已过期");
            logger.info("用户登录失败，验证码已过期");
            return responseServer;
        }
        if (!imageCode.toLowerCase().equals(userDto.getImageCode().toLowerCase())) {
            responseServer.setSuccess(false);
            responseServer.setMessage("验证码不对");
            logger.info("用户登录失败，验证码不对");
            return responseServer;
        } else {
            // 验证通过后，移除验证码
            /*request.getSession().removeAttribute(userDto.getImageCodeToken());*/
            redisTemplate.delete(userDto.getImageCodeToken());
        }
        LoginUserDto loginUserDto = userService.login(userDto);
        String token = UuidUtil.getShortUuid();
        loginUserDto.setToken(token);
//        request.getSession().setAttribute(Constants.LOGIN_USER, loginUserDto);
        redisTemplate.opsForValue().set(token, JSON.toJSONString(loginUserDto), 3600, TimeUnit.SECONDS);  //1小时有效
        responseServer.setContent(loginUserDto);
        return responseServer;
    }
    /**
     * 控台退出
     */
    @GetMapping("/logout/{token}")
    public  ResponseServer logout(@PathVariable String token) {
        ResponseServer responseServer = new ResponseServer();
       /* request.getSession().removeAttribute(Constants.LOGIN_USER);*/
        redisTemplate.delete(token);
        logger.info("从redis中删除token:{}", token);
        return responseServer;
    }
}
