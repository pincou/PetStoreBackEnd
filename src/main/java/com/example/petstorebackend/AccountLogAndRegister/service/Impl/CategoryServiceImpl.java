package com.example.petstorebackend.AccountLogAndRegister.service.Impl;

import com.example.petstorebackend.AccountLogAndRegister.VO.CategoryPreview;
import com.example.petstorebackend.AccountLogAndRegister.VO.CategoryVO;
import com.example.petstorebackend.AccountLogAndRegister.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public boolean addCategory(CategoryVO categoryVO) {
        return false;
    }

    @Override
    public List<CategoryPreview> getCategoriesBasicInfo() {
        return null;
    }

    @Override
    public List<CategoryVO> getCategoriesByPage(Integer page, Integer size) {
        return null;
    }
}
