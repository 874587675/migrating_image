package com.dfg.migrating_image.business.mapper;

import com.dfg.migrating_image.business.entity.SysUserRole;
import com.dfg.migrating_image.framework.generate.abstracts.AbstractMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author zgc
 * @since 2025-06-30 11:42:56
 */
@Mapper
public interface SysUserRoleMapper extends AbstractMapper<SysUserRole> {

}
