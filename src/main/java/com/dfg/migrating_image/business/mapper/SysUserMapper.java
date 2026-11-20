package com.dfg.migrating_image.business.mapper;

import com.dfg.migrating_image.business.entity.SysUser;
import com.dfg.migrating_image.framework.generate.abstracts.AbstractMapper;
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
