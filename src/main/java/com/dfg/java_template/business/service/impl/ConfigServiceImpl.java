package com.dfg.java_template.business.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfg.java_template.business.entity.Config;
import com.dfg.java_template.business.mapper.ConfigMapper;
import com.dfg.java_template.business.param.convertor.ConfigConvertor;
import com.dfg.java_template.business.param.list.ConfigListParam;
import com.dfg.java_template.business.param.page.ConfigPageParam;
import com.dfg.java_template.business.param.page.vo.PageVO;
import com.dfg.java_template.business.param.query.ConfigQueryParam;
import com.dfg.java_template.business.param.remove.RemoveBaseParam;
import com.dfg.java_template.business.param.save.ConfigSaveParam;
import com.dfg.java_template.business.param.update.ConfigUpdateParam;
import com.dfg.java_template.business.param.vo.ConfigVO;
import com.dfg.java_template.business.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* <p>
* 配置表 服务实现类
* </p>
*
* @author zgc
* @since 2026-04-04 15:06:19
*/
@Service
@Slf4j
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {
    @Resource
    private ConfigMapper configMapper;

    /**
    * 保存配置
    *
    * @param configSaveParam 保存配置参数
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveConfig(ConfigSaveParam configSaveParam){
        configMapper.insert(ConfigConvertor.SAVE.saveParamToEntity(configSaveParam));
    }

    /**
    * 修改配置
    *
    * @param configUpdateParam 修改配置参数
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateConfig(ConfigUpdateParam configUpdateParam){
        configMapper.updateById(ConfigConvertor.UPDATE.updateParamToEntity(configUpdateParam));
    }

    /**
    * 删除配置属性
    *
    * @param removeBaseParam 删除配置属性参数
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeConfig(RemoveBaseParam removeBaseParam) {
        configMapper.deleteById(removeBaseParam.getSerializableId());
    }

    /**
    * 前台用户查询配置列表
    *
    * @param configListParam 查询配置列表参数
    * @return 配置VO列表
    */
    @Override
    public List<ConfigVO> frontListConfig(ConfigListParam configListParam){
        String configId = configListParam.getConfigId();
        List<Config> list =list(
                new LambdaQueryWrapper<Config>()
                        .eq(ObjectUtil.isNotEmpty(configId), Config::getConfigId, configId)
                        .orderByDesc(Config::getUpdateTime)
        );
        return ConfigConvertor.LIST.listEntityToListVO(list);
    }

    /**
    * 前台用户分页查询配置列表
    *
    * @param configPageParam 查询配置列表参数
    * @return 配置VO列表
    */
    @Override
    public PageVO<ConfigVO> frontPageConfig(ConfigPageParam configPageParam){
        String configId = configPageParam.getConfigId();
        Page<Config> page = page(new Page<>(configPageParam.getPageNo(), configPageParam.getPageSize()),
                new LambdaQueryWrapper<Config>()
                        .eq(ObjectUtil.isNotEmpty(configId), Config::getConfigId, configId)
                        .orderByDesc(Config::getUpdateTime)
        );
        return PageVO.addPageData(page, ConfigConvertor.LIST.listEntityToListVO(page.getRecords()));
    }

    /**
    * 前台用户查询配置详情
    *
    * @param configQueryParam 查询配置详情参数
    * @return 配置VO
    */
    @Override
    public ConfigVO frontQueryConfig(ConfigQueryParam configQueryParam){
        return ConfigConvertor.QUERY.entityToVo(configMapper.selectById(configQueryParam.getConfigId()));
    }

    /**
    * 后台用户查询配置列表
    *
    * @param configListParam 查询配置列表参数
    * @return 配置VO列表
    */
    @Override
    public List<ConfigVO> backListConfig(ConfigListParam configListParam){
        String configId = configListParam.getConfigId();
        List<Config> list =list(
                new LambdaQueryWrapper<Config>()
                    .eq(ObjectUtil.isNotEmpty(configId), Config::getConfigId, configId)
                    .orderByDesc(Config::getUpdateTime)
        );
        return ConfigConvertor.LIST.listEntityToListVO(list);
    }

    /**
    * 后台用户分页查询配置列表
    *
    * @param configPageParam 查询配置列表参数
    * @return 配置VO列表
    */
    @Override
    public PageVO<ConfigVO> backPageConfig(ConfigPageParam configPageParam){
        String configId = configPageParam.getConfigId();
        Page<Config> page = page(new Page<>(configPageParam.getPageNo(), configPageParam.getPageSize()),
                new LambdaQueryWrapper<Config>()
                    .eq(ObjectUtil.isNotEmpty(configId), Config::getConfigId, configId)
                    .orderByDesc(Config::getUpdateTime)
        );
        return PageVO.addPageData(page, ConfigConvertor.LIST.listEntityToListVO(page.getRecords()));
    }

    /**
    * 后台用户查询配置详情
    *
    * @param configQueryParam 查询配置详情参数
    * @return 配置VO
    */
    @Override
    public ConfigVO backQueryConfig(ConfigQueryParam configQueryParam){
        return ConfigConvertor.QUERY.entityToVo(configMapper.selectById(configQueryParam.getConfigId()));
    }        
        
}
