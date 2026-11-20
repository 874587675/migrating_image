package com.dfg.java_template.business.mapper;

import com.dfg.java_template.business.entity.SysMenu;
import com.dfg.java_template.framework.generate.abstracts.AbstractMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author zgc
 * @since 2025-09-24 20:48:40
 */
@Mapper
public interface SysMenuMapper extends AbstractMapper<SysMenu> {
    List<String> selectPermissionByUserId(@Param("userId") String userId);

    List<SysMenu> selectMenuIdByUserId(@Param("userId") String userId);

    List<SysMenu> selectSubMenuIdByUserId(@Param("userId") String userId, @Param("parentId") String parentId);
}
