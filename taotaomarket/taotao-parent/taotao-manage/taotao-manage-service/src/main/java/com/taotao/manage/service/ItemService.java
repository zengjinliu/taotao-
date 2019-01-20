package com.taotao.manage.service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.bean.Item;
import com.taotao.manage.bean.ItemDesc;
import com.taotao.manage.dao.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService extends BaseService<Item>{

    @Autowired
    private ItemDescService itemDescService;
    @Autowired
    private ItemMapper itemMapper;


    //二者在同一个事物中
    public void saveItem(Item item,String desc){
        //设置初始数据
        item.setStatus(1);
        //出于安全考虑设置id为null
        item.setId(null);
        super.save(item);
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDescService.save(itemDesc);
    }

    /**
     * 设置查询所有的商品，进行分页设置
     * @param page
     * @param rows
     * @return
     */
    public PageInfo<Item> queryPageListByWhere(Integer page, Integer rows) {
        Example example = new Example(Item.class);
        //排序
        example.setOrderByClause("updated  DESC");
        //设置分页参数
        PageHelper.startPage(page,rows);
        List<Item> itemList = itemMapper.selectByExample(example);
        return new PageInfo<Item>(itemList);
    }

    /**
     * 修改商品
     * @param item
     * @param desc
     */
    public void updateItem(Item item, String desc) {
        //强制设置不能修改的字段为null
        item.setStatus(null);
        item.setCreated(null);
        super.updateSelective(item);
        //修改商品描述数据
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        this.itemDescService.updateSelective(itemDesc);

    }
}
