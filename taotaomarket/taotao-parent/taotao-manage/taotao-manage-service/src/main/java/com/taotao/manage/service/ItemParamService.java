package com.taotao.manage.service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.bean.ItemParam;
import com.taotao.manage.dao.ItemParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemParamService extends BaseService<ItemParam> {

    @Autowired
    private ItemParamMapper itemParamMapper;

    //分页查询
    public PageInfo<ItemParam> queryPageListByWhere(Integer page, Integer rows){
        Example example = new Example(ItemParam.class);
        //排序
        example.setOrderByClause("updated  DESC");
        //设置分页参数
        PageHelper.startPage(page,rows);
        List<ItemParam> itemParamList = itemParamMapper.selectByExample(example);
        return new PageInfo<ItemParam>(itemParamList);
    }

}
