package com.example.petstorebackend.AccountLogAndRegister.service;

import com.example.petstorebackend.AccountLogAndRegister.VO.GetProductDetail;
import com.example.petstorebackend.AccountLogAndRegister.VO.ProductPreview;
import com.example.petstorebackend.AccountLogAndRegister.VO.ProductVO;
import com.example.petstorebackend.AccountLogAndRegister.common.CommonResponse;
import com.example.petstorebackend.AccountLogAndRegister.VO.GetProduct;
import com.example.petstorebackend.AccountLogAndRegister.entity.Product;

import java.util.List;

public interface ProductService {
    boolean addProduct(ProductVO productVO);
    boolean deleteProduct(String productId);
    boolean updateProduct(ProductVO productVO);

    List<ProductPreview> getProductsBasicInfo();
    CommonResponse<List<Product>> getProductsByPage(Integer page, Integer size);
    CommonResponse<GetProductDetail> getProductDetail(String productId);

    CommonResponse<GetProduct> getProduct(String productId);
}
