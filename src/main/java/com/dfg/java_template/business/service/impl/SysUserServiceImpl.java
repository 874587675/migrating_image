package com.dfg.java_template.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfg.java_template.business.entity.SysRole;
import com.dfg.java_template.business.entity.SysUser;
import com.dfg.java_template.business.entity.SysUserRole;
import com.dfg.java_template.business.mapper.SysRoleMapper;
import com.dfg.java_template.business.mapper.SysUserMapper;
import com.dfg.java_template.business.mapper.SysUserRoleMapper;
import com.dfg.java_template.business.param.convertor.SysRoleConvertor;
import com.dfg.java_template.business.param.convertor.SysUserConvertor;
import com.dfg.java_template.business.param.list.SysUserListParam;
import com.dfg.java_template.business.param.page.SysUserPageParam;
import com.dfg.java_template.business.param.page.vo.PageVO;
import com.dfg.java_template.business.param.query.SysUserQueryParam;
import com.dfg.java_template.business.param.remove.RemoveBaseParam;
import com.dfg.java_template.business.param.remove.RemoveBatchParam;
import com.dfg.java_template.business.param.save.SysUserSaveParam;
import com.dfg.java_template.business.param.update.SysUserUpdateParam;
import com.dfg.java_template.business.param.vo.SysRoleVO;
import com.dfg.java_template.business.param.vo.SysUserVO;
import com.dfg.java_template.business.service.SysUserService;
import com.dfg.java_template.common.constant.CacheConstants;
import com.dfg.java_template.common.constant.CommonConstants;
import com.dfg.java_template.common.exception.token.TokenException;
import com.dfg.java_template.common.exception.token.TokenExceptionEnum;
import com.dfg.java_template.common.exception.user.UserExceptionEnum;
import com.dfg.java_template.framework.redis.RedisCache;
import com.dfg.java_template.framework.security.constant.LoginRoleEnum;
import com.dfg.java_template.framework.security.constant.TokenTypeEnum;
import com.dfg.java_template.framework.security.core.AuthenticationContextHolder;
import com.dfg.java_template.framework.security.param.LoginUser;
import com.dfg.java_template.framework.security.param.SysLoginBody;
import com.dfg.java_template.framework.security.param.SysLoginVO;
import com.dfg.java_template.framework.security.service.token.TokenService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author zgc
 * @since 2025-07-01 15:33:56
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource(name = "backendAuthenticationManager")
    private AuthenticationManager backendAuthenticationManager;

    @Resource
    private TokenService tokenService;

    @Resource
    private RedisCache redisCache;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 保存后台用户
     *
     * @param sysUserSaveParam 保存后台用户参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysUser(SysUserSaveParam sysUserSaveParam) {
        List<SysRoleVO> roleList = sysUserSaveParam.getRoleList();

        SysUser sysUser = SysUserConvertor.SAVE.saveParamToEntity(sysUserSaveParam);
        String encode = bCryptPasswordEncoder.encode(sysUser.getPassword());
        sysUser.setPassword(encode);
        sysUserMapper.insert(sysUser);

        String userId = sysUser.getUserId();

        if (CollectionUtil.isNotEmpty(roleList)) {
            List<SysUserRole> userRoleList = roleList.stream()
                    .map(role -> SysUserRole.builder()
                            .userId(userId)
                            .roleId(role.getRoleId())
                            .build())
                    .collect(Collectors.toList());
            sysUserRoleMapper.insertBatchSomeColumn(userRoleList);
        }
    }

    /**
     * 修改后台用户
     *
     * @param sysUserUpdateParam 修改后台用户参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSysUser(SysUserUpdateParam sysUserUpdateParam) {
        String userId = sysUserUpdateParam.getUserId();
        String oldPassword = sysUserUpdateParam.getOldPassword();
        String newPassword = sysUserUpdateParam.getNewPassword();
        List<SysRoleVO> roleList = sysUserUpdateParam.getRoleList();

        if (ObjectUtil.isNotEmpty(oldPassword) && ObjectUtil.isNotEmpty(newPassword)) {
            SysUser sysUser = sysUserMapper.selectById(userId);
            if (ObjectUtil.isNull(sysUser)) {
                throw UserExceptionEnum.USER_IS_NOT_EXIST.toException();
            }
            if (!bCryptPasswordEncoder.matches(oldPassword, sysUser.getPassword())) {
                throw UserExceptionEnum.USER_PASSWORD_UPDATE_FAIL.toException();
            }

            sysUserUpdateParam.setPassword(bCryptPasswordEncoder.encode(newPassword));
        }

        if (CollectionUtil.isNotEmpty(roleList)) {
            sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
            List<SysUserRole> userRoleList = roleList.stream()
                    .map(role -> SysUserRole.builder()
                            .userId(userId)
                            .roleId(role.getRoleId())
                            .build())
                    .collect(Collectors.toList());
            sysUserRoleMapper.insertBatchSomeColumn(userRoleList);
        }
        SysUser sysUser = SysUserConvertor.UPDATE.updateParamToEntity(sysUserUpdateParam);
        sysUserMapper.updateById(sysUser);
    }

    /**
     * 删除后台用户属性
     *
     * @param removeBaseParam 删除后台用户属性参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeSysUser(RemoveBaseParam removeBaseParam) {
        sysUserMapper.deleteById(removeBaseParam.getSerializableId());
    }

    /**
     * 批量删除后台用户属性
     *
     * @param removeBatchParam 删除后台用户属性参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeBatchSysUser(RemoveBatchParam removeBatchParam) {
        sysUserMapper.deleteByIds(removeBatchParam.getSerializableIds());
    }

    /**
     * 后台用户查询后台用户列表
     *
     * @param sysUserListParam 查询后台用户列表参数
     * @return 后台用户VO列表
     */
    @Override
    public List<SysUserVO> backListSysUser(SysUserListParam sysUserListParam) {
        String sysUserId = sysUserListParam.getUserId();
        List<SysUser> list = list(
                new LambdaQueryWrapper<SysUser>()
                        .eq(ObjectUtil.isNotEmpty(sysUserId), SysUser::getUserId, sysUserId)
                        .orderByDesc(SysUser::getUpdateTime)
        );
        return SysUserConvertor.LIST.listEntityToListVO(list);
    }

    /**
     * 后台用户分页查询后台用户列表
     *
     * @param sysUserPageParam 查询后台用户列表参数
     * @return 后台用户VO列表
     */
    @Override
    public PageVO<SysUserVO> backPageSysUser(SysUserPageParam sysUserPageParam) {
        String sysUserId = sysUserPageParam.getUserId();
        Page<SysUser> page = page(new Page<>(sysUserPageParam.getPageNo(), sysUserPageParam.getPageSize()),
                new LambdaQueryWrapper<SysUser>()
                        .eq(ObjectUtil.isNotEmpty(sysUserId), SysUser::getUserId, sysUserId)
                        .orderByDesc(SysUser::getUpdateTime)
        );

        List<SysUserVO> sysUserVOList = SysUserConvertor.LIST.listEntityToListVO(page.getRecords());

        // 2. 批量查询所有用户的角色
        if (CollectionUtil.isNotEmpty(sysUserVOList)) {
            // 获取所有用户ID
            List<String> userIds = sysUserVOList.stream().map(SysUserVO::getUserId).collect(Collectors.toList());

            // 批量查询用户角色关联
            List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(
                    new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId, userIds)
            );

            if (CollectionUtil.isNotEmpty(sysUserRoles)) {
                // 按用户ID分组角色
                Map<String, List<String>> userRoleIdMap = sysUserRoles.stream()
                        .collect(Collectors.groupingBy(
                                SysUserRole::getUserId,
                                Collectors.mapping(SysUserRole::getRoleId, Collectors.toList())
                        ));

                // 收集所有角色ID
                List<String> allRoleIds = sysUserRoles.stream()
                        .map(SysUserRole::getRoleId)
                        .distinct()
                        .collect(Collectors.toList());

                // 批量查询角色信息
                Map<String, SysRoleVO> roleIdMap = new HashMap<>();
                if (CollectionUtil.isNotEmpty(allRoleIds)) {
                    List<SysRole> roles = sysRoleMapper.selectByIds(allRoleIds);
                    roleIdMap = roles.stream()
                            .collect(Collectors.toMap(
                                    SysRole::getRoleId,
                                    SysRoleConvertor.LIST::entityToVo
                            ));
                }

                // 为每个用户设置角色列表
                for (SysUserVO sysUserVO : sysUserVOList) {
                    List<String> roleIds = userRoleIdMap.getOrDefault(sysUserVO.getUserId(), Collections.emptyList());
                    List<SysRoleVO> roleVOList = roleIds.stream()
                            .map(roleIdMap::get)
                            .filter(ObjectUtil::isNotEmpty)
                            .collect(Collectors.toList());
                    sysUserVO.setRoleList(roleVOList);
                }
            }
        }

        // 3. 构建分页返回对象
        return PageVO.addPageData(page, sysUserVOList);
    }

    /**
     * 后台用户查询后台用户详情
     *
     * @return 后台用户VO
     */
    @Override
    public SysUserVO backQuerySysUser(SysUserQueryParam sysUserQueryParam) {
        String backUserId = (ObjectUtil.isEmpty(sysUserQueryParam) || ObjectUtil.isEmpty(sysUserQueryParam.getUserId()))
                ? AuthenticationContextHolder.getBackUserId()
                : sysUserQueryParam.getUserId();

        SysUser sysUser = sysUserMapper.selectById(backUserId);
        if (sysUser == null) {
            throw UserExceptionEnum.USER_IS_NOT_EXIST.toException();
        }
        SysUserVO sysUserVO = SysUserConvertor.QUERY.entityToVo(sysUser);

        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, backUserId)
        );
        List<String> roleIds = sysUserRoles.stream().map(SysUserRole::getRoleId).toList();

        List<String> roleNames = Collections.emptyList();
        if (!roleIds.isEmpty()) {
            List<SysRole> sysRoles = sysRoleMapper.selectByIds(roleIds);
            roleNames = sysRoles.stream().map(SysRole::getRoleName).toList();
        }
        sysUserVO.setRoleNames(roleNames);
        return sysUserVO;
    }

    @Override
    public SysLoginVO loginSysUser(SysLoginBody sysLoginBody, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String username = sysLoginBody.getUsername();
        String password = sysLoginBody.getPassword();

        // 这里的AuthenticationContextHolder.setContext(authenticationToken)是为了在后续的操作中可以获取到当前登录用户的信息
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        AuthenticationContextHolder.setBackContext(authenticationToken);
        // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        Authentication authentication = backendAuthenticationManager.authenticate(authenticationToken);

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUserId();
        String remoteAddr = httpServletRequest.getRemoteAddr();
        sysUserMapper.update(new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getUserId, userId)
                .set(SysUser::getLastIpAddress, remoteAddr)
                .set(SysUser::getLastLoginTime, new Date()));//更新最后登录时间
        // 生成双token
        Map<String, Object> accessTokenClaims = new HashMap<>();
        accessTokenClaims.put("userId", userId);
        accessTokenClaims.put("username", loginUser.getUsername());
        accessTokenClaims.put("role", LoginRoleEnum.BACK_ROLE.getCode());
        accessTokenClaims.put("tokenType", CacheConstants.ACCESS_TOKEN);
        accessTokenClaims.put("ipAddress", remoteAddr);

        Map<String, Object> refreshTokenClaims = new HashMap<>();
        refreshTokenClaims.put("userId", userId);
        refreshTokenClaims.put("username", loginUser.getUsername());
        refreshTokenClaims.put("role", LoginRoleEnum.BACK_ROLE.getCode());
        refreshTokenClaims.put("tokenType", CacheConstants.REFRESH_TOKEN);
        refreshTokenClaims.put("ipAddress", remoteAddr);

        TokenService.TokenParam tokenParam = tokenService.getTokenParam();
        int accessExpireTime = tokenParam.getAccessExpireTime(); // 访问token过期时间（分钟）
        int refreshExpireTime = tokenParam.getRefreshExpireTime(); // 刷新token过期时间（分钟）
        String accessToken = tokenService.generateToken(userId, accessTokenClaims, accessExpireTime, CacheConstants.ACCESS_TOKEN);
        String refreshToken = tokenService.generateToken(userId, refreshTokenClaims, refreshExpireTime, CacheConstants.REFRESH_TOKEN);
        // 保存登录用户信息到Redis（使用refresh token的过期时间）
        redisCache.setCacheObject(CacheConstants.LOGIN_USER + CacheConstants.BACK_KEY + userId, loginUser, refreshExpireTime, TimeUnit.MINUTES);
        // 设置refresh token到cookie
        tokenService.setRefreshTokenCookie(httpServletResponse, refreshToken);

        return new SysLoginVO(accessToken, refreshToken);
    }

    @Override
    public void exitSysUser(HttpServletResponse httpServletResponse) {
        String backUserId = AuthenticationContextHolder.getBackUserId();
        //删除token信息
        redisCache.deleteObject(CacheConstants.LOGIN_TOKEN_USER + CacheConstants.BACK_KEY + CacheConstants.ACCESS_TOKEN + backUserId);
        redisCache.deleteObject(CacheConstants.LOGIN_TOKEN_USER + CacheConstants.BACK_KEY + CacheConstants.REFRESH_TOKEN + backUserId);
        //删除登录用户信息
        redisCache.deleteObject(CacheConstants.LOGIN_USER + CacheConstants.BACK_KEY + backUserId);
        //清除cookie
        tokenService.clearAccessTokenCookie(httpServletResponse);
    }

    @Override
    public SysLoginVO refresh(HttpServletRequest request) {
        try {
            String refreshToken = request.getHeader(tokenService.getTokenParam().getRefreshToken());
            if (ObjectUtil.isNotEmpty(refreshToken)) {
                refreshToken = refreshToken.replace(CommonConstants.TOKEN_PREFIX, "");
            }
            log.info("refresh_token:{}", refreshToken);
            Claims refreshClaims = tokenService.parseToken(refreshToken, TokenTypeEnum.REFRESH_TOKEN.getCode());
            Claims expiredClaims;
            try {
                String expiredToken = request.getHeader(tokenService.getTokenParam().getAccessToken());
                expiredToken = expiredToken.replace(CommonConstants.TOKEN_PREFIX, "");
                // 尝试解析过期的token来获取用户信息
                expiredClaims = tokenService.parseToken(expiredToken, TokenTypeEnum.ACCESS_TOKEN.getCode());
            } catch (TokenException ex) {
                // 如果无法解析过期的token，就使用refreshToken中的用户信息
                expiredClaims = refreshClaims;
                expiredClaims.put("tokenType", TokenTypeEnum.ACCESS_TOKEN.getDesc());
            }
            String userId = expiredClaims.get("userId", String.class);
            String role = expiredClaims.get("role", String.class);
            // 验证refreshToken中的用户信息是否匹配
            String refreshUserId = refreshClaims.get("userId", String.class);
            String refreshRole = refreshClaims.get("role", String.class);
            if (!userId.equals(refreshUserId) || !role.equals(refreshRole)) {
                throw TokenExceptionEnum.TOKEN_INVALID.toException();
            }
            // 重新生成accessToken
            String accessToken = tokenService.generateToken(userId, expiredClaims, tokenService.getTokenParam().getAccessExpireTime(), CacheConstants.ACCESS_TOKEN);
            return new SysLoginVO(accessToken, refreshToken);
        } catch (Exception e) {
            log.error("刷新token失败", e);
            throw TokenExceptionEnum.TOKEN_INVALID.toException();
        }
    }


}
