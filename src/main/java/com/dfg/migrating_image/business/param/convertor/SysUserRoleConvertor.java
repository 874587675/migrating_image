package com.dfg.migrating_image.business.param.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dfg.migrating_image.business.entity.SysUserRole;
import com.dfg.migrating_image.framework.generate.abstracts.AbstractConvertor;
import com.dfg.migrating_image.business.param.save.SysUserRoleSaveParam;
import com.dfg.migrating_image.business.param.update.SysUserRoleUpdateParam;
import com.dfg.migrating_image.business.param.vo.SysUserRoleVO;

import java.util.List;

/**
* SysUserRoleConvertor
*
* @author zgc
* @since 2025-06-30 11:42:56
*/
@Mapper
public interface SysUserRoleConvertor extends AbstractConvertor<SysUserRoleSaveParam , SysUserRoleUpdateParam, SysUserRoleVO, SysUserRole,List<SysUserRole>,List<SysUserRoleVO>> {

    SysUserRoleConvertor SAVE = Mappers.getMapper(SysUserRoleConvertor.class);

    SysUserRoleConvertor UPDATE = Mappers.getMapper(SysUserRoleConvertor.class);

    SysUserRoleConvertor LIST = Mappers.getMapper(SysUserRoleConvertor.class);

    SysUserRoleConvertor QUERY = Mappers.getMapper(SysUserRoleConvertor.class);

    }