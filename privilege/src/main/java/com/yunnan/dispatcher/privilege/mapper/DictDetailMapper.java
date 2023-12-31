package com.yunnan.dispatcher.privilege.mapper;

import com.github.pagehelper.Page;
import com.yunnan.dispatcher.privilege.condition.DictDetailQueryCondition;
import com.yunnan.dispatcher.privilege.model.DictDetail;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

/**
 * @description: 字典mapper
 * @author: hengquan
 * @date: 14:40 2019/9/4
 */
public interface DictDetailMapper {

    /**
     * 查询所有字典项项
     *
     * @return
     */
    @Select(" select * from t_dict_detail order by created_time desc ")
    Page<DictDetail> getDictDetailList();

    /**
     * 插入单条字典项
     *
     * @param dictDetail
     * @return Integer
     */
    @Insert(" insert into t_dict_detail (dict_id,code,name,seq,parent_id,is_leaf,created_time) values(#{dictId},#{code},#{name},#{seq},#{parentId},#{isLeaf},current_timestamp) ")
    Integer insertDictDetail(DictDetail dictDetail);

    /**
     * 修改字典项
     *
     * @param dictDetail
     * @return Integer
     */
    @Update({
            "<script>",
            " update t_dict_detail set ",
            " <if test='name != null'> name = #{name},</if>",
            " <if test='code != null'> code = #{code},</if>",
            " <if test='parentId != null'> parent_id = #{parentId},</if>",
            " <if test='seq != null'> seq = #{seq},</if>",
            " <if test='isLeaf != null'> is_leaf = #{isLeaf},</if>",
            " last_modified_time = current_timestamp ",
            " where detail_id=#{detailId} ",
            "</script>"
    })
    Integer updateDictDetail(DictDetail dictDetail);

    /**
     * 删除字典项
     *
     * @param detailId
     * @return Integer
     */
    @Delete(" delete from t_dict_detail where detail_id=#{detailId} ")
    Integer deleteDictDetail(@Param("detailId") Integer detailId);

    /**
     * 通过字典ID查询字典项
     *
     * @param detailId
     * @return
     */
    @Select(" select * from t_dict_detail where detail_id=#{detailId} ")
    Optional<DictDetail> getById(@Param("detailId") Integer detailId);

    /**
     * 通过数据字典编号查询数据字典项
     *
     * @param dictDetailQueryCondition 数据字典项查询条件
     * @return 数据字典子项分页
     */
    @Select({
            "<script>",
            " select * from t_dict_detail where dict_id=#{dictId} ",
            " <if test='code != null'> and code = #{code}</if>",
            " order by created_time asc  ",
            "</script>"
    })
    Page<DictDetail> queryDictDetailByDict(DictDetailQueryCondition dictDetailQueryCondition);

}
