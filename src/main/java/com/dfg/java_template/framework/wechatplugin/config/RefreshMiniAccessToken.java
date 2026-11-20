package com.dfg.java_template.framework.wechatplugin.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.dfg.java_template.common.constant.CacheConstants;
import com.dfg.java_template.framework.redis.RedisCache;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

@Component
@Slf4j
public class RefreshMiniAccessToken {
    @Resource
    private WxMaService wxMaService;
    @Resource
    private RedisCache redisCache;

    /**
     * 刷新小程序AccessToken
     */
    @PostConstruct
    @Scheduled(initialDelay = 0, fixedDelay = 6000 * 1000)
    public void refreshMiniAccessToken() {
        log.info("[{}]刷新微信小程序AccessToken开始", DateUtil.formatDate(new Date()));
        try {
            WxMaConfig wxMaConfig = wxMaService.getWxMaConfig();
            String accessToken = wxMaService.getAccessToken();
            if (ObjectUtil.isEmpty(accessToken)) {
                log.error("刷新微信小程序AccessToken异常");
            }
            redisCache.setCacheObject(CacheConstants.WX_ACCESS_TOKEN + wxMaConfig.getAppid(), accessToken);
        } catch (WxErrorException e) {
            log.error("刷新微信小程序AccessToken异常");
        }
        log.info("刷新微信小程序AccessToken结束");
    }
}
