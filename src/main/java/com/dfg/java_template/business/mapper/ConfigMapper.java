package com.dfg.java_template.business.mapper;

import com.dfg.java_template.business.entity.Config;
import com.dfg.java_template.framework.generate.abstracts.AbstractMapper;
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
