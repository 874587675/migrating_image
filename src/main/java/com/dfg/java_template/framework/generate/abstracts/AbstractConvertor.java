package com.dfg.java_template.framework.generate.abstracts;


import java.util.List;

/**
 * 参数转换器
 *
 * @author zgc
 */
public interface AbstractConvertor<SaveParam, UpdateParam, VO, Entity, ListEntity, ListVO> {

    /**
     * SaveParam转化为Entity
     *
     * @param saveParam 保存参数
     * @return Entity
     */
    Entity saveParamToEntity(SaveParam saveParam);

    /**
     * SaveParam列表转化为Entity列表（批量插入用）
     *
     * @param saveParamList 保存参数列表
     * @return Entity列表
     */
    List<Entity> saveParamListToEntityList(List<SaveParam> saveParamList);

    /**
     * UpdateParam转化为Entity
     *
     * @param updateParam 修改参数
     * @return Entity
     */
    Entity updateParamToEntity(UpdateParam updateParam);

    /**
     * UpdateParam列表转化为Entity列表（批量更新用）
     *
     * @param updateParamList 修改参数列表
     * @return Entity列表
     */
    List<Entity> updateParamListToEntityList(List<UpdateParam> updateParamList);

    /**
     * Entity转化为VO
     *
     * @param entity 实体类
     * @return VO
     */
    VO entityToVo(Entity entity);

    /**
     * ListEntity转化为ListVO
     *
     * @param listEntity 列表实体类
     * @return ListVO
     */
    ListVO listEntityToListVO(ListEntity listEntity);

    /**
     * VO转化为Entity
     *
     * @param vo VO类
     * @return entity
     */
    Entity voToEntity(VO vo);

    /**
     * ListVO转化为
     *
     * @param listVO VO列表
     * @return ListEntity
     */
    ListEntity listVOToListEntity(ListVO listVO);


}
