package com.course.business.controller.web;

import com.alibaba.fastjson.JSON;
import com.course.server.common.ResponseServer;
import com.course.server.common.UuidUtil;
import com.course.server.common.ValidatorUtil;
import com.course.server.dto.LoginMemberDto;
import com.course.server.dto.MemberDto;
import com.course.server.dto.SmsDto;
import com.course.server.enums.SmsUseEnum;
import com.course.server.exception.BusinessException;
import com.course.server.service.MemberService;
import com.course.server.service.SmsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController("webMemberController")
@RequestMapping("/web/member")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    public static final String BUSINESS_NAME = "会员";

    @Resource
    private MemberService memberService;

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    @Resource
    private SmsService smsService;

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/register")
    public ResponseServer register(@RequestBody MemberDto memberDto) {
        // 保存校验
        ValidatorUtil.require(memberDto.getMobile(), "手机号");
        ValidatorUtil.length(memberDto.getMobile(), "手机号", 11, 11);
        ValidatorUtil.require(memberDto.getPassword(), "密码");
        ValidatorUtil.length(memberDto.getName(), "昵称", 1, 50);
        /*ValidatorUtil.length(memberDto.getPhoto(), "头像url", 1, 200);*/

        // 密码加密
        memberDto.setPassword(DigestUtils.md5DigestAsHex(memberDto.getPassword().getBytes()));

        // 校验短信验证码
        SmsDto smsDto = new SmsDto();
        smsDto.setMobile(memberDto.getMobile());
        smsDto.setCode(memberDto.getSmsCode());
        smsDto.setUse(SmsUseEnum.REGISTER.getCode());
        smsService.validCode(smsDto);
        logger.info("短信验证码校验通过");

        ResponseServer responseDto = new ResponseServer();
        memberService.save(memberDto);
        responseDto.setContent(memberDto);
        return responseDto;
    }



    /**
     * 登录
     */
    @PostMapping("/login")
    public ResponseServer login(@RequestBody MemberDto memberDto) {
        logger.info("用户登录开始");
        memberDto.setPassword(DigestUtils.md5DigestAsHex(memberDto.getPassword().getBytes()));
        ResponseServer responseServer = new ResponseServer();

        // 根据验证码token去获取缓存中的验证码，和用户输入的验证码是否一致
        String imageCode = (String) redisTemplate.opsForValue().get(memberDto.getImageCodeToken());
        logger.info("从redis中获取到的验证码：{}", imageCode);
        if (StringUtils.isEmpty(imageCode)) {
            responseServer.setSuccess(false);
            responseServer.setMessage("验证码已过期");
            logger.info("用户登录失败，验证码已过期");
            return responseServer;
        }
        if (!imageCode.toLowerCase().equals(memberDto.getImageCode().toLowerCase())) {
            responseServer.setSuccess(false);
            responseServer.setMessage("验证码不对");
            logger.info("用户登录失败，验证码不对");
            return responseServer;
        } else {
            // 验证通过后，移除验证码
            redisTemplate.delete(memberDto.getImageCodeToken());
        }

        LoginMemberDto loginMemberDto = memberService.login(memberDto);
        String token = UuidUtil.getShortUuid();
        loginMemberDto.setToken(token);
        redisTemplate.opsForValue().set(token, JSON.toJSONString(loginMemberDto), 3600, TimeUnit.SECONDS);
        responseServer.setContent(loginMemberDto);
        return responseServer;
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout/{token}")
    public ResponseServer logout(@PathVariable String token) {
        ResponseServer responseServer = new ResponseServer();
        redisTemplate.delete(token);
        logger.info("从redis中删除token:{}", token);
        return responseServer;
    }

    /**
     * 校验手机号是否存在
     * 存在则success=true，不存在则success=false
     */
    @GetMapping(value = "/is-mobile-exist/{mobile}")
    public ResponseServer isMobileExist(@PathVariable(value = "mobile") String mobile) throws BusinessException {
        logger.info("查询手机号是否存在开始");
        ResponseServer responseServer = new ResponseServer();
        MemberDto memberDto = memberService.findByMobile(mobile);
        if (memberDto == null) {
            responseServer.setSuccess(false);
        } else {
            responseServer.setSuccess(true);
        }
        return responseServer;
    }
    @PostMapping("/reset-password")
    public ResponseServer resetPassword(@RequestBody MemberDto memberDto) throws BusinessException {
        logger.info("会员密码重置开始:");
        memberDto.setPassword(DigestUtils.md5DigestAsHex(memberDto.getPassword().getBytes()));
        ResponseServer<MemberDto> responseDto = new ResponseServer();

        // 校验短信验证码
        SmsDto smsDto = new SmsDto();
        smsDto.setMobile(memberDto.getMobile());
        smsDto.setCode(memberDto.getSmsCode());
        smsDto.setUse(SmsUseEnum.FORGET.getCode());
        smsService.validCode(smsDto);
        logger.info("短信验证码校验通过");

        // 重置密码
        memberService.resetPassword(memberDto);

        return responseDto;
    }


}
