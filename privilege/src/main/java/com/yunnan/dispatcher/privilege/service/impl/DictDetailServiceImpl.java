package com.yunnan.dispatcher.privilege.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yunnan.dispatcher.privilege.condition.DictDetailQueryCondition;
import com.yunnan.dispatcher.privilege.mapper.DictDetailMapper;
import com.yunnan.dispatcher.privilege.model.DictDetail;
import com.yunnan.dispatcher.privilege.service.DictDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @description: 字典表
 * @author: hengquan
 * @date: 14:32 2019/9/4
 */
@Service
public class DictDetailServiceImpl implements DictDetailService {

    @Resource
    private DictDetailMapper dictDetailMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean insertDictDetail(DictDetail dictDetail) {
        return dictDetailMapper.insertDictDetail(dictDetail) > 0 ? true : false;
    }

    @Override
    public Optional<DictDetail> getById(Integer detailId) {
        return dictDetailMapper.getById(detailId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateDictDetail(DictDetail dictDetail) {
        return dictDetailMapper.updateDictDetail(dictDetail) > 0 ? true : false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteDictDetail(Integer detailId) {
        return dictDetailMapper.deleteDictDetail(detailId) > 0 ? true : false;
    }

    @Override
    public Page<DictDetail> getDictDetailList(Integer nowPage, Integer pageSize) {
        PageHelper.startPage(nowPage, pageSize);
        return dictDetailMapper.getDictDetailList();
    }

    @Override
    public Page<DictDetail> queryDictDetailByDict(DictDetailQueryCondition dictDetailQueryCondition, Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        return dictDetailMapper.queryDictDetailByDict(dictDetailQueryCondition);
    }
}
