package com.yunnan.dispatcher.privilege.condition;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description:关联用户/权限/菜单表
 * @author: hss
 * @date: 2019-09-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpmAddCondition implements Serializable {


    private Integer roleId;

    private List<Integer> ids;

    private  Integer createdBy;

    private  Integer status;



}
