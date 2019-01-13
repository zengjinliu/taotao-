package com.taotao.manage.service;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.bean.BasePojo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 通用service层
 */
public abstract class BaseService<T extends BasePojo> {

//    public abstract Mapper<T> getMapper();
    @Autowired
    private Mapper<T> mapper;

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    public T queryById(Long id){
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所欲数据
     * @return
     */
    public List<T> queryAll(){
        return mapper.select(null);
    }

    /**
     * 查询一条数据
     * @param record
     * @return
     */
    public T queryOne(T record){
        return mapper.selectOne(record);
    }

    /**
     * 根据条件查询数据列表
     * @param record
     * @return
     */
    public List<T> queryListByWhere(T record){
        return mapper.select(record);
    }

    /**
     * 分页查询数据列表
     * @param page
     * @param rows
     * @param record
     * @return
     */
    public PageInfo<T> queryPageListByWhere(Integer page,Integer rows,T record){
        //设置分页参数
        PageHelper.startPage(page,rows);
        List<T> list = mapper.select(record);
        return new PageInfo<T>(list);
    }

    /**
     * 新增对象
     * @param t
     * @return
     */
    public Integer save(T t){
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());
        return mapper.insert(t);
    }

    /**
     * 有选择的保存，选择补位null的字段作为插入字段
     * @param t
     * @return
     */
    public Integer saveSelective(T t){
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());
        return mapper.insertSelective(t);
    }

    /**
     * 更新对象
     * @param t
     * @return
     */
    public Integer update(T t){
        t.setCreated(new Date());
        return mapper.updateByPrimaryKey(t);
    }

    /**
     * 有选择的更新，选择不为null的字段作为插入字段
     * @param t
     * @return
     */
    public Integer updateSelective(T t){
        t.setCreated(new Date());
        t.setUpdated(new Date());
        return mapper.insertSelective(t);
    }

    /**
     * 根据id删除数据
     * @param id
     * @return
     */
    public Integer deleteById(Long id){
        return mapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     * @param clazz
     * @param property
     * @param values
     * @return
     */
    public Integer deleteByIds(Class<T> clazz,String property,List<Object> values){
        Example example = new Example(clazz);
        //设置条件
        example.createCriteria().andIn(property,values);
        return mapper.deleteByExample(example);
    }

    /**
     *  根据条件做删除
     * @param record
     * @return
     */
    public Integer deleteByWhere(T record){
        return mapper.delete(record);
    }

}
