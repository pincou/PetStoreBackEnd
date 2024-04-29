package com.example.petstorebackend.AccountLogAndRegister.controller;

import com.example.petstorebackend.AccountLogAndRegister.Util.ResponseMessage;
import com.example.petstorebackend.AccountLogAndRegister.VO.CategoryPreview;
import com.example.petstorebackend.AccountLogAndRegister.VO.CategoryVO;
import com.example.petstorebackend.AccountLogAndRegister.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    /**
     * 添加分类
     * @author Luo
     * @param category
     * @return 是否添加成功
     */
    @PostMapping("/add")
    public ResponseEntity<ResponseMessage> addCategory(@RequestBody CategoryVO category) {
        ResponseMessage responseMessage = new ResponseMessage();
        if (categoryService.addCategory(category)) {
            responseMessage.setStatus("true");
            responseMessage.setMessage("添加成功");
        } else {
            responseMessage.setStatus("false");
            responseMessage.setMessage("添加失败");
        }
        return ResponseEntity.ok(responseMessage);
    }

    /**
     * 获取现在已有的Category的基本信息
     * @author Luo
     * @return CategoryPreview列表
     */
    @RequestMapping("/info")
    public ResponseEntity<List<CategoryPreview>> getCategoriesBasicInfo() {
        return ResponseEntity.ok(categoryService.getCategoriesBasicInfo());
    }

    /**
     * 分页获取分类的详细信息，用于展示在thymeleaf页面，需要注意对图片路径的处理
     * @author Luo
     * @param page
     * @param size
     * @param model
     * @return CategoryVO列表(具体信息)
     */
    @RequestMapping("/")
    public String getCategoriesByPage(Model model, Integer page, Integer size) {
        List<CategoryVO> categories = categoryService.getCategoriesByPage(page, size);
        model.addAttribute("categories", categories);
        return "category/categoryList";
    }

}
