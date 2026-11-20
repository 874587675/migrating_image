package com.dfg.migrating_image.business.mapper;

import com.dfg.migrating_image.business.entity.Config;
import com.dfg.migrating_image.framework.generate.abstracts.AbstractMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 配置表 Mapper 接口
 * </p>
 *
 * @author zgc
 * @since 2026-04-04 15:06:19
 */
@Mapper
public interface ConfigMapper extends AbstractMapper<Config> {

}
