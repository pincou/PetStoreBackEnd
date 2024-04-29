package com.example.petstorebackend.AccountLogAndRegister.controller;

import com.example.petstorebackend.AccountLogAndRegister.VO.ItemDetail;
import com.example.petstorebackend.AccountLogAndRegister.VO.ItemSummary;
import com.example.petstorebackend.AccountLogAndRegister.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/test")
    public String getItemList() {
        return "item/itemList";
    }

    /**
     * 获取商品列表
     * @author Luo
     * @param page 页码
     * @param size 每页大小
     * @return 商品列表
     */
    @GetMapping("/")
    public String getItemList(Model model, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        List<ItemSummary> items = itemService.getItemListByPage(page, size, 1);

        model.addAttribute("items", items);
        return "item/itemList";
    }


    /**
     * 获取商品详情
     * @author Luo
     * @param itemId 商品id
     * @return 商品详情
     */
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDetail> getItemDetail(@PathVariable("itemId") String itemId) {
        System.out.println(itemId);
        return ResponseEntity.ok(itemService.getItemDetail(itemId));
    }
    /**
     * 修改商品信息。
     * @author Luo
     * @param itemDetail 包含要修改的商品信息的对象
     * @return JSON数据格式，包含一个布尔值字段，指示修改是否成功
     */
    @PutMapping("/{itemId}")
    public ResponseEntity<Map<String, Boolean>> updateItem(@RequestBody ItemDetail itemDetail) {
        Integer supplierId = 1;
        boolean success = itemService.updateItem(itemDetail, supplierId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", success);
        return ResponseEntity.ok().body(response);
    }

    /**
     * 下架商品。
     * @author Luo
     * @param itemId 商品ID
     * @return 返回是否修改成功的json数据，成功的话包含成功的状态码和具体删除的是哪一项
     */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Object> removeItem(@PathVariable String itemId) {
        boolean success = itemService.removeItem(itemId);
        if (success) {
            // 创建一个包含成功信息的JSON对象
            String successMessage = "Item with ID " + itemId + " has been successfully removed.";
            return ResponseEntity.ok().body("{\"success\": true, \"message\": \"" + successMessage + "\"}");
        } else {
            // 创建一个包含失败信息的JSON对象
            String errorMessage = "Failed to remove item with ID " + itemId + ".";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"success\": false, \"message\": \"" + errorMessage + "\"}");
        }
    }

    /**
     * 搜索商品。
     * @author Luo
     * @param keyword 搜索关键词
     * @param page    当前页数
     * @param size    每页显示数量
     * @param model   存放搜索结果
     * @return 返回search视图
     */
    @GetMapping("/search")
    public String searchItems(@RequestParam("keyword") String keyword,
                              @RequestParam(value = "page",defaultValue = "1") int page,
                              @RequestParam(value = "size",defaultValue = "10") int size,
                              Model model) {
        List<ItemSummary> searchResults = itemService.searchItems(keyword, page, size);
        model.addAttribute("items", searchResults);
        return "item/search";
    }
    /**
     * 现在只写了一个基本的框架，估计后面有够呛的,前端那里我想是需要做页面重定向的处理
     * 添加商品信息
     * @author Luo
     * @param itemDetail 包含要添加的商品信息的对象
     * @return 返回是否添加成功的 JSON 数据，成功的话包含成功的状态码和具体添加的商品信息
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addItem(@RequestBody ItemDetail itemDetail) {
        //获取session对象中的supplierId
        Integer supplierId = 1;
        boolean success = itemService.addItem(itemDetail, supplierId);
        if (success) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Item has been successfully added");
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add item.");
        }
    }
}
