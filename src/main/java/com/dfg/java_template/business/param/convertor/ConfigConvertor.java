package com.dfg.java_template.business.param.convertor;

import com.dfg.java_template.business.entity.Config;
import com.dfg.java_template.business.param.save.ConfigSaveParam;
import com.dfg.java_template.business.param.update.ConfigUpdateParam;
import com.dfg.java_template.business.param.vo.ConfigVO;
import com.dfg.java_template.framework.generate.abstracts.AbstractConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * ConfigConvertor
 *
 * @author zgc
 * @since 2026-04-04 15:06:19
 */
@Mapper
public interface ConfigConvertor extends AbstractConvertor<ConfigSaveParam, ConfigUpdateParam, ConfigVO, Config, List<Config>, List<ConfigVO>> {

    ConfigConvertor SAVE = Mappers.getMapper(ConfigConvertor.class);

    ConfigConvertor UPDATE = Mappers.getMapper(ConfigConvertor.class);

    ConfigConvertor LIST = Mappers.getMapper(ConfigConvertor.class);

    ConfigConvertor QUERY = Mappers.getMapper(ConfigConvertor.class);

}