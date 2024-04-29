package com.example.petstorebackend.AccountLogAndRegister.service;

import com.example.petstorebackend.AccountLogAndRegister.VO.CategoryPreview;
import com.example.petstorebackend.AccountLogAndRegister.VO.CategoryVO;

import java.util.List;

public interface CategoryService {
    boolean addCategory(CategoryVO categoryVO);
    List<CategoryPreview> getCategoriesBasicInfo();
    List<CategoryVO> getCategoriesByPage(Integer page, Integer size);
}
