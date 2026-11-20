package com.dfg.migrating_image.business.mapper;

import com.dfg.migrating_image.business.entity.SysRoleMenu;
import com.dfg.migrating_image.framework.generate.abstracts.AbstractMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @author zgc
 * @since 2025-06-30 11:42:56
 */
@Mapper
public interface SysRoleMenuMapper extends AbstractMapper<SysRoleMenu> {

}
