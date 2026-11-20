package com.dfg.migrating_image.business.param.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dfg.migrating_image.business.entity.SysUser;
import com.dfg.migrating_image.framework.generate.abstracts.AbstractConvertor;
import com.dfg.migrating_image.business.param.save.SysUserSaveParam;
import com.dfg.migrating_image.business.param.update.SysUserUpdateParam;
import com.dfg.migrating_image.business.param.vo.SysUserVO;

import java.util.List;

/**
* SysUserConvertor
*
* @author zgc
* @since 2025-11-02 19:31:23
*/
@Mapper
public interface SysUserConvertor extends AbstractConvertor<SysUserSaveParam , SysUserUpdateParam, SysUserVO, SysUser,List<SysUser>,List<SysUserVO>> {

    SysUserConvertor SAVE = Mappers.getMapper(SysUserConvertor.class);

    SysUserConvertor UPDATE = Mappers.getMapper(SysUserConvertor.class);

    SysUserConvertor LIST = Mappers.getMapper(SysUserConvertor.class);

    SysUserConvertor QUERY = Mappers.getMapper(SysUserConvertor.class);

    }