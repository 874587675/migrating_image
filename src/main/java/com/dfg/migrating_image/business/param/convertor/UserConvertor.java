package com.dfg.migrating_image.business.param.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dfg.migrating_image.business.entity.User;
import com.dfg.migrating_image.framework.generate.abstracts.AbstractConvertor;
import com.dfg.migrating_image.business.param.save.UserSaveParam;
import com.dfg.migrating_image.business.param.update.UserUpdateParam;
import com.dfg.migrating_image.business.param.vo.UserVO;

import java.util.List;

/**
* UserConvertor
*
* @author zgc
* @since 2026-07-11 15:13:27
*/
@Mapper
public interface UserConvertor extends AbstractConvertor<UserSaveParam , UserUpdateParam, UserVO, User,List<User>,List<UserVO>> {

    UserConvertor SAVE = Mappers.getMapper(UserConvertor.class);

    UserConvertor UPDATE = Mappers.getMapper(UserConvertor.class);

    UserConvertor LIST = Mappers.getMapper(UserConvertor.class);

    UserConvertor QUERY = Mappers.getMapper(UserConvertor.class);

    }