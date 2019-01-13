package com.taotao.manage.controller;

import com.taotao.manage.bean.ItemCat;
import com.taotao.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;


    /**
     * 查询所有的类目,符合restful设计风格
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ItemCat>> queryItemCatListByParentId(
            @RequestParam(value = "id" ,defaultValue = "0") Long parentId){

        try {
//            List<ItemCat> list =  itemCatService.queryItemCatListByParentId(parentId);
            //定义查询条件
            ItemCat record = new ItemCat();
            record.setParentId(parentId);
            List<ItemCat> list =  itemCatService.queryListByWhere(record);
            if (null == list || list.isEmpty()) {

                //404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            //200
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);


    }


}
