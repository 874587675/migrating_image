package com.dfg.migrating_image.business.service;

import com.dfg.migrating_image.business.entity.Config;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dfg.migrating_image.business.param.page.vo.PageVO;
import com.dfg.migrating_image.business.param.save.ConfigSaveParam;
import com.dfg.migrating_image.business.param.update.ConfigUpdateParam;
import com.dfg.migrating_image.business.param.list.ConfigListParam;
import com.dfg.migrating_image.business.param.query.ConfigQueryParam;
import com.dfg.migrating_image.business.param.remove.RemoveBaseParam;
import com.dfg.migrating_image.business.param.page.ConfigPageParam;
import com.dfg.migrating_image.business.param.vo.ConfigVO;
import java.util.List;
/**
* @author zgc
* @since 2026-04-04 15:06:19
*/
public interface ConfigService extends IService<Config> {

    /**
    * 保存配置
    *
    * @param configSaveParam 保存配置参数
    */
    void saveConfig(ConfigSaveParam configSaveParam);

    /**
    * 修改配置
    *
    * @param configUpdateParam 修改配置参数
    */
    void updateConfig(ConfigUpdateParam configUpdateParam);

    /**
    * 删除配置
    *
    * @param removeBaseParam 删除配置参数
    */
    void removeConfig(RemoveBaseParam removeBaseParam);

    /**
    * 前台用户查询配置列表
    *
    * @param configListParam 查询配置列表参数
    * @return 配置VO列表
    */
    List<ConfigVO> frontListConfig(ConfigListParam configListParam);
    
    /**
    * 前台用户分页查询配置列表
    *
    * @param configPageParam 查询配置列表参数
    * @return 配置VO列表
    */
    PageVO<ConfigVO> frontPageConfig(ConfigPageParam configPageParam);

    /**
    * 前台用户查询配置详情
    *
    * @param configQueryParam 查询配置详情参数
    * @return 配置VO
    */
    ConfigVO frontQueryConfig(ConfigQueryParam configQueryParam);
        
    /**
    * 后台用户查询配置列表
    *
    * @param configListParam 查询配置列表参数
    * @return 配置VO列表
    */
    List<ConfigVO> backListConfig(ConfigListParam configListParam);

    /**
    * 后台用户分页查询配置列表
    *
    * @param configPageParam 查询配置列表参数
    * @return 配置VO列表
    */
    PageVO<ConfigVO> backPageConfig(ConfigPageParam configPageParam);

    /**
    * 后台用户查询配置详情
    *
    * @param configQueryParam 查询配置详情参数
    * @return 配置VO
    */
    ConfigVO backQueryConfig(ConfigQueryParam configQueryParam);        
}
