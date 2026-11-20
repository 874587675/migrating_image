package com.dfg.migrating_image.business.param.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dfg.migrating_image.business.entity.SysMenu;
import com.dfg.migrating_image.framework.generate.abstracts.AbstractConvertor;
import com.dfg.migrating_image.business.param.save.SysMenuSaveParam;
import com.dfg.migrating_image.business.param.update.SysMenuUpdateParam;
import com.dfg.migrating_image.business.param.vo.SysMenuVO;

import java.util.List;

/**
* SysMenuConvertor
*
* @author zgc
* @since 2025-09-24 20:48:40
*/
@Mapper
public interface SysMenuConvertor extends AbstractConvertor<SysMenuSaveParam , SysMenuUpdateParam, SysMenuVO, SysMenu,List<SysMenu>,List<SysMenuVO>> {

    SysMenuConvertor SAVE = Mappers.getMapper(SysMenuConvertor.class);

    SysMenuConvertor UPDATE = Mappers.getMapper(SysMenuConvertor.class);

    SysMenuConvertor LIST = Mappers.getMapper(SysMenuConvertor.class);

    SysMenuConvertor QUERY = Mappers.getMapper(SysMenuConvertor.class);

    }