package com.taotao.manage.controller;

import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.bean.Item;

import com.taotao.manage.service.ItemService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 新增商品
 */
@Controller
@RequestMapping("item")
@Slf4j
public class ItemController {


    @Autowired
    private ItemService itemService;

    /**
     * 新增商品
     *
     * @param item
     * @param desc
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> itemSave(Item item, @RequestParam("desc") String desc) {
        try {
            //入参是打印日志
            if (log.isInfoEnabled()) {
                log.info("新增商品, item={} , desc={}", item, desc);
            }

            if (StringUtils.isEmpty(item.getTitle())) {
                //响应400
                log.error("新增商品失败, itemTitle={}", item.getTitle());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            //保存商品
            itemService.saveItem(item, desc);
            if (log.isInfoEnabled()) {
                log.info("新增商品成功, itemId={}", item.getId());
            }
        } catch (Exception e) {
            log.error("新增商品失败!  title={}, cId={}", item.getTitle(), item.getCid());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    /**
     * 查询所有的商品
     *
     * @param page
     * @param rows
     * @return
     */
    @GetMapping
    public ResponseEntity<EasyUIResult> queryItemList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                      @RequestParam(value = "rows", defaultValue = "30") Integer rows) {

        try {
            PageInfo<Item> pageInfo = itemService.queryPageListByWhere(page, rows);
            EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
            return ResponseEntity.ok(easyUIResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    /**
     * 修改商品
     * @param item
     * @param desc
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateItem(Item item, @RequestParam("desc") String desc) {
        try {
            //入参是打印日志
            if (log.isInfoEnabled()) {
                log.info("修改商品, item={} , desc={}", item, desc);
            }

            if (StringUtils.isEmpty(item.getTitle())) {
                //响应400
                log.error("修改商品失败, itemTitle={}", item.getTitle());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            //修改商品
            this.itemService.updateItem(item, desc);

            if (log.isInfoEnabled()) {
                log.info("修改商品成功, itemId={}", item.getId());
            }
            //204成功
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            log.error("修改商品失败!  title={}, cId={}", item.getTitle(), item.getCid());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
