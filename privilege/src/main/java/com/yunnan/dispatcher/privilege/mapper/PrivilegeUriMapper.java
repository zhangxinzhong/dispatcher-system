package com.yunnan.dispatcher.privilege.mapper;

import com.github.pagehelper.Page;
import com.yunnan.dispatcher.privilege.model.PrivilegeUri;
import com.yunnan.dispatcher.privilege.model.Uri;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午4:59
 */
public interface PrivilegeUriMapper {


    /**
     * 通过privilegeId 查询 权限关联uri
     *
     * @param privilegeIds
     * @return
     */
    @Select({
            "<script>",
            " select r.* from t_uri r inner join t_privilege_uri pu on pu.uri_id=r.uri_id where r.status=0 and pu.status =0 and pu.privilege_id in ",
            "<foreach collection='privilegeIds' item='privilegeId' open='(' separator=',' close=')'>",
            "#{privilegeId}",
            "</foreach>",
            "</script>"
    })
    List<Uri> queryUriByPrivilegeId(@Param("privilegeIds") List<Integer> privilegeIds);

    /**
     * 通过登录名loginNo获取所有uri
     *
     * @param loginNo
     * @return
     */
    @Select(" select distinct u.* from t_uri u inner join t_privilege_uri pu on pu.uri_id = u.uri_id  inner join t_role_privilege rp on rp.privilege_id = pu.privilege_id  inner join t_role_user ur on ur.role_id = rp.role_id inner join t_user usr on usr.user_id=ur.user_id where usr.login_no=#{loginNo} ")
    List<Uri> queryUriByLoginNo(@Param("loginNo") String loginNo);

    /**
     * 通过权限id获取权限uri关联表分页
     *
     * @param privilegeUri
     * @return
     */
    @Select("select pu.*,u.uri_name uriName,u.uri_pattern uriPattern from t_privilege_uri pu join t_uri u on pu.uri_id = u.uri_id   where pu.privilege_id = #{privilegeId} ")
    Page<PrivilegeUri> queryPrivilegeUriAll(PrivilegeUri privilegeUri);

    /**
     * 创建权限uri关联表
     *
     * @param privilegeUri
     * @return
     */
    @Insert(" insert into t_privilege_uri(privilege_id, uri_id,status, created_by, created_time, last_modified_by, last_modified_time ) values (#{privilegeId},#{uriId},#{status},#{createdBy},current_timestamp,#{lastModifiedBy},current_timestamp) ")
    Integer insert(PrivilegeUri privilegeUri);

    /**
     * 修改权限uri关联表
     *
     * @param privilegeUri
     * @return
     */
    @Update({
            "<script>",
            " update t_privilege_uri set  ",
            " <if test='privilegeId != null'> privilege_id=#{privilegeId},  </if> ",
            " <if test='uriId != null'> uri_id=#{uriId},  </if> ",
            " <if test='status != null'> status=#{status},  </if> ",
            " <if test='lastModifiedBy != null'> last_modified_by=#{lastModifiedBy},  </if> ",
            " last_modified_time = CURRENT_TIMESTAMP ",
            "where privilege_id=#{privilegeId}",
            "</script>"
    })
    Integer update(PrivilegeUri privilegeUri);

    /**
     * 通过uriId获取某条权限uri关联
     *
     * @param privilegeUri
     * @return
     */
    @Select("select * from t_privilege_uri where privilege_id = #{privilegeId} and uri_id = #{uriId}")
    Optional<PrivilegeUri> getPrivilegeUri(PrivilegeUri privilegeUri);

    /**
     * 通过权限id与uriId获取关联信息
     *
     * @param privilegeUri
     * @return
     */
    @Select("select * from t_privilege_uri where privilege_id = #{privilegeId} and uri_id = #{uriId}")
    List<PrivilegeUri> getPrivilegeUriList(PrivilegeUri privilegeUri);

    /**
     * 批量插入权限与uri关联
     *
     * @param privilegeUri
     * @return
     */
    @Select({
            "<script>",
            " insert into t_privilege_uri(privilege_id, uri_id,status, created_by, created_time) values ",
            "<foreach collection='ids' item='id' open='(' close=')' separator='),('>",
            "#{privilegeId},#{id},#{status},#{createdBy},current_timestamp",
            "</foreach>",
            "</script>"
    })
    Integer batchInsertPriUri(PrivilegeUri privilegeUri);

    /**
     * 通过权限id 删除权限与uri的关联
     *
     * @param privilegeId
     * @return
     */
    @Delete("delete from t_privilege_uri where privilege_id = #{privilegeId} ")
    Integer delByPriId(@Param("privilegeId") Integer privilegeId);

    /**
     * 通过权限id与uriId 删除权限与uri的关联
     *
     * @param privilegeUri
     * @return
     */
    @Delete("delete from t_privilege_uri where privilege_id = #{privilegeId} and uri_id = #{uriId}")
    Integer del(PrivilegeUri privilegeUri);
}
