package com.yunnan.dispatcher.privilege.service.impl;

import com.github.pagehelper.Page;
import com.yunnan.dispatcher.privilege.mapper.UriMapper;
import com.yunnan.dispatcher.privilege.model.Uri;
import com.yunnan.dispatcher.privilege.service.UriService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @description:任务
 * @author: zhangxinzhong
 * @date: 2019-08-26 下午1:19
 */
@Service
public class UriServiceImpl implements UriService {

    @Resource
    private UriMapper uriMapper;

    @Override
    public Page<Uri> queryTaskAll(Uri uri, Integer page, Integer limit) {
        return uriMapper.queryTaskAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean insert(Uri uri) {
        return uriMapper.insert(uri) > 0 ? true : false;
    }

    @Override
    public Optional<Uri> getUri(Integer id) {
        return uriMapper.getUri(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean save(Uri uri) {
        Boolean result = true;
        Integer uriId = uri.getUriId();
        if (uriId == null) {
            result = this.insert(uri);
        } else {
            result = this.update(uri);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean update(Uri uri) {
        return uriMapper.update(uri) > 0 ? true : false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean del(Integer uriId) {
        return uriMapper.del(uriId) > 0 ? true : false;
    }

    @Override
    public List<Uri> getUriALL() {
        return uriMapper.getUriAll();
    }
}
