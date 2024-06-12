package com.example.petstorebackend.AccountLogAndRegister.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petstorebackend.AccountLogAndRegister.Util.RegexUtils;
import com.example.petstorebackend.AccountLogAndRegister.VO.CategoryPreview;
import com.example.petstorebackend.AccountLogAndRegister.VO.CategoryVO;
import com.example.petstorebackend.AccountLogAndRegister.entity.Category;
import com.example.petstorebackend.AccountLogAndRegister.persistence.CategoryMapper;
import com.example.petstorebackend.AccountLogAndRegister.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public boolean addCategory(CategoryVO categoryVO) {
        Category category=new Category();
        category.setCatid(categoryVO.getCatid());
        category.setName(categoryVO.getName());
        category.setDescn(categoryVO.getImage());
        categoryMapper.insert(category);
        return true;
    }

    @Override
    public boolean deleteCategoryByCatid(String catid) {
        QueryWrapper<Category> catid_Category_table =new QueryWrapper<Category>().eq("catid",catid);
        categoryMapper.delete(catid_Category_table);
        return true;
    }

    @Override
    public List<CategoryPreview> getCategoriesBasicInfo() {
        List<Category> categories= categoryMapper.selectList(null);
        List<CategoryPreview> categoryPreviews=new ArrayList<>();
        for (Category category :categories) {
            CategoryPreview categoryPreview=new CategoryPreview();
            categoryPreview.setName(category.getName());
            categoryPreviews.add(categoryPreview);
        }
        return  categoryPreviews;
    }

    /**
     * 分页获取Categories
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<CategoryVO> getCategoriesByPage(Integer page, Integer size) {
        Page<Category> categoryPage =new Page<>(page,size);
        categoryPage=categoryMapper.selectPage(categoryPage,null);
        List<Category> categories=categoryPage.getRecords();
        List<CategoryVO> categoryVOS=categories.stream().map(category -> {
            CategoryVO categoryVO=new CategoryVO();
            categoryVO.setCatid(category.getCatid());
            categoryVO.setImage(category.getDescn());
            categoryVO.setName(category.getName());
            String image= RegexUtils.extractSrc(category.getDescn());
            String information = RegexUtils.extractTextDescription(category.getName());

            categoryVO.setImage("../"+image);
            categoryVO.setName(information);

            return categoryVO;
        }).toList();
        return categoryVOS;
    }
}
