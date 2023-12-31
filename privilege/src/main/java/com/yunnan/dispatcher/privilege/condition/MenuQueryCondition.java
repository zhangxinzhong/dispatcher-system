package com.yunnan.dispatcher.privilege.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 菜单查询条件
 * @author: zhangxinzhong
 * @date: 2019-09-17 下午2:30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuQueryCondition implements Serializable {

    /**
     * 菜单父id 默认查询
     */
    private Integer parentId;
}
