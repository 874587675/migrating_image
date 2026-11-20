package com.dfg.java_template.business.controller.front;

import com.dfg.java_template.business.param.remove.RemoveBaseParam;
import com.dfg.java_template.business.param.save.UserSaveParam;
import com.dfg.java_template.business.param.update.UserUpdateParam;
import com.dfg.java_template.business.param.vo.UserVO;
import com.dfg.java_template.business.service.UserService;
import com.dfg.java_template.common.web.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 用户信息 模块
 *
 * @author zgc
 * @since 2025-06-30 11:42:56
 */
@Api(value = "用户信息模块", tags = "用户信息模块相关接口")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping(value = "/front/saveUser", produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "新增用户信息", produces = "application/json", consumes = "application/json")
    public R<?> saveUser(@Validated @RequestBody UserSaveParam userSaveParam) {
        userService.saveUser(userSaveParam);
        return R.ok();
    }

    @PostMapping(value = "/front/updateUser", produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "根据主键修改用户信息", produces = "application/json", consumes = "application/json")
    public R<?> updateUser(@Validated @RequestBody UserUpdateParam userUpdateParam) {
        userService.updateUser(userUpdateParam);
        return R.ok();
    }

    @ApiOperation(value = "根据主键删除用户信息", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/front/removeUser", produces = "application/json", consumes = "application/json")
    public R<?> removeUser(@Validated @RequestBody RemoveBaseParam removeBaseParam) {
        userService.removeUser(removeBaseParam);
        return R.ok();
    }

    @ApiOperation(value = "前台用户查询用户信息详情", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/front/frontQueryUser", produces = "application/json", consumes = "application/json")
    public R<UserVO> frontQueryUser() {
        return R.ok(userService.frontQueryUser());
    }


}
