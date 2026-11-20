package com.dfg.migrating_image.business.param.convertor;

import com.dfg.migrating_image.business.entity.Config;
import com.dfg.migrating_image.business.param.save.ConfigSaveParam;
import com.dfg.migrating_image.business.param.update.ConfigUpdateParam;
import com.dfg.migrating_image.business.param.vo.ConfigVO;
import com.dfg.migrating_image.framework.generate.abstracts.AbstractConvertor;
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