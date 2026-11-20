package com.dfg.migrating_image.business.param.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dfg.migrating_image.business.entity.SysRole;
import com.dfg.migrating_image.framework.generate.abstracts.AbstractConvertor;
import com.dfg.migrating_image.business.param.save.SysRoleSaveParam;
import com.dfg.migrating_image.business.param.update.SysRoleUpdateParam;
import com.dfg.migrating_image.business.param.vo.SysRoleVO;

import java.util.List;

/**
* SysRoleConvertor
*
* @author zgc
* @since 2025-06-30 11:42:56
*/
@Mapper
public interface SysRoleConvertor extends AbstractConvertor<SysRoleSaveParam , SysRoleUpdateParam, SysRoleVO, SysRole,List<SysRole>,List<SysRoleVO>> {

    SysRoleConvertor SAVE = Mappers.getMapper(SysRoleConvertor.class);

    SysRoleConvertor UPDATE = Mappers.getMapper(SysRoleConvertor.class);

    SysRoleConvertor LIST = Mappers.getMapper(SysRoleConvertor.class);

    SysRoleConvertor QUERY = Mappers.getMapper(SysRoleConvertor.class);

    }