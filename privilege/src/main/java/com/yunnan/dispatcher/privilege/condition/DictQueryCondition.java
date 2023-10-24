package com.yunnan.dispatcher.privilege.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 数据字典查询
 * @author: zhangxinzhong
 * @date: 2019-09-17 下午2:30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictQueryCondition implements Serializable {

    /**
     * 字典编码
     */
    private String code;

}
