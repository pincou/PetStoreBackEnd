package com.example.petstorebackend.AccountLogAndRegister.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petstorebackend.AccountLogAndRegister.Util.RegexUtils;
import com.example.petstorebackend.AccountLogAndRegister.VO.*;
import com.example.petstorebackend.AccountLogAndRegister.common.CommonResponse;
import com.example.petstorebackend.AccountLogAndRegister.entity.Inventory;
import com.example.petstorebackend.AccountLogAndRegister.entity.Item;
import com.example.petstorebackend.AccountLogAndRegister.entity.Product;
import com.example.petstorebackend.AccountLogAndRegister.persistence.InventoryMapper;
import com.example.petstorebackend.AccountLogAndRegister.persistence.ItemMapper;
import com.example.petstorebackend.AccountLogAndRegister.persistence.ProductMapper;
import com.example.petstorebackend.AccountLogAndRegister.service.GetProductDetailService;
import com.example.petstorebackend.AccountLogAndRegister.service.GetProductService;
import com.example.petstorebackend.AccountLogAndRegister.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private GetProductService getProductService;

    @Autowired
    private GetProductDetailService getProductDetailService;
    /**
     * 添加product，需要对productVO中的信息进行处理，加入product表中
     * @author Luo
     * @param productVO 产品信息
     * @return 是否添加成功
     */
    @Override
    public boolean addProduct(ProductVO productVO) {
        Product product = new Product();
        product.setProductid(productVO.getProductid());
        product.setCategory(productVO.getCategory());
        product.setName(productVO.getName());
        //<image src="images/fish4.gif">Salt Water fish from Australia
        String description = "<image src=\"images/"+productVO.getImage()+"\">"+productVO.getInformation();
        product.setDescn(description);

        int insert = productMapper.insert(product);
        if(insert == 0){
            return false;
        }
        return true;
    }
    /**
     * 删除product，从product表中删除
     * @param productId 产品id
     * @return 是否删除成功
     */
    @Override
    public boolean deleteProduct(String productId) {
        return false;
    }

    /**
     * 更新product，更新product表中的信息，修改图片的问题还我在thinking。。。。
     * @author Luo
     * @param productVO 产品信息
     * @return 是否更新成功
     */
    @Override
    public boolean updateProduct(ProductVO productVO) {

        Product product = new Product();
        product.setProductid(productVO.getProductid());
        product.setCategory(productVO.getCategory());
        product.setName(productVO.getName());
        //<image src="images/fish4.gif">Salt Water fish from Australia
        String description = "<image src=\"images/"+productVO.getImage()+"\">"+productVO.getInformation();
        product.setDescn(description);

        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<Product>().eq("productid", productVO.getProductid());
        int update = productMapper.update(product,updateWrapper);
        if(update == 0){
            return false;
        }
        return true;
    }

    /**
     * 获取现在已有的Product的基本信息：id+种类，用作添加item时供选择的下拉列表（controller中返回的是json数据）
     * @author Luo
     * @return ProductPreview列表
     */
    @Override
    public List<ProductPreview> getProductsBasicInfo() {
        List<Product> products = productMapper.selectList(null);
        List<ProductPreview> productPreviews = new ArrayList<>();
        for (Product product : products) {
            ProductPreview productPreview = new ProductPreview();
            productPreview.setProductId(product.getProductid());
            productPreview.setCategory(product.getCategory());
            productPreviews.add(productPreview);
        }
        return productPreviews;
    }

    /**
     * 分页获取product的详细信息，用于展示在thymeleaf页面，需要注意对图片路径的处理（可以用我写好了的utils包下的正则表达式工具类）
     * @author Luo
     * @param page
     * @param page_size
     * @return ProductVO列表(具体信息)
     */
    @Override
    public CommonResponse<List<Product>> getProductsByPage(Integer page, Integer page_size) {
        Page<Product> productPage = new Page<>(page, page_size);
        productPage = productMapper.selectPage(productPage, null);
        List<Product> products = productPage.getRecords();
        List<ProductVO> productVOS = products.stream().map(product -> {
            ProductVO productVO = new ProductVO();
            productVO.setProductid(product.getProductid());
            productVO.setCategory(product.getCategory());
            productVO.setName(product.getName());
            String image = RegexUtils.extractSrc(product.getDescn());
            String information = RegexUtils.extractTextDescription(product.getDescn());

            productVO.setImage("../"+image);
            productVO.setInformation(information);

            return productVO;
        }).toList();

        return CommonResponse.createForSuccess(2000,"success",products);
    }

    /**
     * 获取product的详细信息，用于展示在修改某个具体的product的弹出框中，通用需要注意对图片路径的处理，实现上是上面一个方法的简化版
     * @param productId
     * @return  ProductVO具体信息
     */
    @Override
    public CommonResponse<GetProductDetail> getProductDetail(String productId) {
        Product product = productMapper.selectById(productId);
        Item item = getProductDetailService.GetItem(productId);
        Inventory inventory = getProductDetailService.GetInventory(productId);
        GetProductDetail getProductDetail = new GetProductDetail();
        getProductDetail.setProduct_id(productId);
        getProductDetail.setDescription(item.getAttr1());
        getProductDetail.setItem_id(item.getItemid());
        getProductDetail.setImage(product.getDescn());
        getProductDetail.setPrice(item.getListprice());
        getProductDetail.setStock(inventory.getQty());
        getProductDetail.setManufacturer(item.getSupplier());
        if(product == null){
            return CommonResponse.createForSuccess(2000,"success",getProductDetail);
        }
        else {
            return CommonResponse.createForError(1001,"Invalid product ID");
        }
    }


    /**
     * 获取product的基本信息，用于展示在修改某个具体的product的弹出框中，通用需要注意对图片路径的处理，实现上是上面一个方法的简化版
     * @param productId
     * @return  ProductVO基本信息
     */
    @Override
    public CommonResponse<GetProduct> getProduct(String productId) {
        ItemDetail itemDetail = getProductService.GetItem(productId);
        Inventory inventory = getProductService.GetInventory(productId);
        GetProduct getProduct = new GetProduct();
        getProduct.setItem_id(itemDetail.getItemId());
        getProduct.setPrice(itemDetail.getList_price());
        getProduct.setStock(inventory.getQty());
        Product product = productMapper.selectById(productId);
        if(product == null){
            return CommonResponse.createForError(1001,"Invalid product ID");
        }
        else {
            ProductVO productVO = new ProductVO();
            productVO.setCategory(product.getCategory());
            productVO.setProductid(product.getProductid());
            productVO.setInformation(product.getDescn());
            return CommonResponse.createForSuccess(2000,"success",getProduct);
        }

    }
}
