package com.dfg.java_template.business.mapper;

import com.dfg.java_template.business.entity.SysRole;
import com.dfg.java_template.framework.generate.abstracts.AbstractMapper;
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
