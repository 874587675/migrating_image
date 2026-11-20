package com.dfg.migrating_image.business.mapper;

import com.dfg.migrating_image.business.entity.User;
import com.dfg.migrating_image.framework.generate.abstracts.AbstractMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author zgc
 * @since 2026-07-11 15:13:27
 */
@Mapper
public interface UserMapper extends AbstractMapper<User> {

}
