package com.dfg.migrating_image.business.mapper;

import com.dfg.migrating_image.business.entity.SysRole;
import com.dfg.migrating_image.framework.generate.abstracts.AbstractMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author zgc
 * @since 2025-06-30 11:42:56
 */
@Mapper
public interface SysRoleMapper extends AbstractMapper<SysRole> {

    List<String> selectRoleByUserId(@Param("userId") String userId);
}
