package com.dfg.java_template.business.mapper;

import com.dfg.java_template.business.entity.SysUser;
import com.dfg.java_template.framework.generate.abstracts.AbstractMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author zgc
 * @since 2025-11-02 19:31:23
 */
@Mapper
public interface SysUserMapper extends AbstractMapper<SysUser> {

}
