package com.example.petstorebackend.AccountLogAndRegister.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petstorebackend.AccountLogAndRegister.Util.RegexUtils;
import com.example.petstorebackend.AccountLogAndRegister.VO.ItemDetail;
import com.example.petstorebackend.AccountLogAndRegister.VO.ItemSummary;
import com.example.petstorebackend.AccountLogAndRegister.entity.Inventory;
import com.example.petstorebackend.AccountLogAndRegister.entity.Item;
import com.example.petstorebackend.AccountLogAndRegister.entity.Product;
import com.example.petstorebackend.AccountLogAndRegister.persistence.*;
import com.example.petstorebackend.AccountLogAndRegister.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private SignOnMapper signOnMapper;

//    public List<ItemSummary> getItemList() {
//        return null;
//    }

    /**
     * @param page       页码
     * @param size       每页大小
     * @param supplierId 供应商id
     * @return 分页后的商品列表
     * @author Luo
     */
    public List<ItemSummary> getItemListByPage(Integer page, Integer size, Integer supplierId) {
        QueryWrapper<Item> supplierId_equ = new QueryWrapper<Item>().eq("supplier", supplierId);
        Page<Item> itemPage = new Page<>(page, size);
        itemPage = itemMapper.selectPage(itemPage, supplierId_equ);
        List<Item> items = itemPage.getRecords();

        return items.stream().map(item -> {
            ItemSummary itemSummary = new ItemSummary();
            String itemid = item.getItemid();
            String productid = item.getProductid();

            itemSummary.setItemId(itemid);
            itemSummary.setProduct(productid);
            itemSummary.setPrice(item.getListprice());

            QueryWrapper<Product> productid_equ = new QueryWrapper<Product>().select("category").eq("productid", productid);
            Product product = productMapper.selectOne(productid_equ);
            itemSummary.setCategory(product.getCategory());

            QueryWrapper<Inventory> itemid_equ = new QueryWrapper<Inventory>().select("qty").eq("itemid", itemid);
            Inventory inventory = inventoryMapper.selectOne(itemid_equ);
            itemSummary.setQuantity(inventory.getQty());

            return itemSummary;
        }).toList();
    }

    /**
     * 根据商品id获取商品详情
     * @param itemId 商品id
     * @return 商品详情
     * @author Luo
     */
    @Override
    public ItemDetail getItemDetail(String itemId) {
        QueryWrapper<Item> itemid_equ = new QueryWrapper<Item>().eq("itemid", itemId);
        Item item = itemMapper.selectOne(itemid_equ);
        ItemDetail itemDetail = new ItemDetail();
        itemDetail.setItemId(item.getItemid());
        itemDetail.setProduct(item.getProductid());
        itemDetail.setList_price(item.getListprice());
        itemDetail.setUnit_cost(item.getUnitcost());

        QueryWrapper<Inventory> inventoryQueryWrapper = new QueryWrapper<Inventory>().select("qty").eq("itemid", item.getItemid());
        Inventory inventory = inventoryMapper.selectOne(inventoryQueryWrapper);
        itemDetail.setQuantity(inventory.getQty());

        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<Product>().select("descn,category").eq("productid", item.getProductid());
        Product product = productMapper.selectOne(productQueryWrapper);
        //通过正则表达式获取图片信息，但是由于路径问题，还需要对图片路径进行处理（加上../）
        String descn = RegexUtils.extractTextDescription(product.getDescn());
        String image = RegexUtils.extractSrc(product.getDescn());

        itemDetail.setCategory(product.getCategory());
        itemDetail.setImage("../" + image);
        itemDetail.setDescription(item.getAttr1() + " " + descn);

        return itemDetail;

    }

    /**
     * 被ItemController调用，用于修改商品信息。
     * @author luo
     * @param itemDetail 包含要修改的商品信息的对象
     * @return 修改是否成功的boolean信息
     * @author
     */
    @Override
    public boolean updateItem(ItemDetail itemDetail,Integer supplierId) {
        Item item = new Item();
        item.setItemid(itemDetail.getItemId());
        item.setProductid(itemDetail.getProduct());
        item.setListprice(itemDetail.getList_price());
        item.setUnitcost(itemDetail.getUnit_cost());
        item.setAttr1(itemDetail.getDescription());
        item.setSupplier(supplierId);
        item.setStatus("P");
        UpdateWrapper<Item> itemid = new UpdateWrapper<Item>().eq("itemid", itemDetail.getItemId());
        itemMapper.update(item,itemid);


        Inventory inventory = new Inventory();
        inventory.setItemid(itemDetail.getItemId());
        inventory.setQty(itemDetail.getQuantity());
        UpdateWrapper<Inventory> itemid1 = new UpdateWrapper<Inventory>().eq("itemid", itemDetail.getItemId());
        inventoryMapper.update(inventory,itemid1);

        return true;
    }

    /**
     * 被ItemController调用，用于下架商品。
     * @author Luo
     * @param itemId 商品ID
     * @return boolean，表示是否删除成功
     * @author
     */
    public boolean removeItem(String itemId) {
        QueryWrapper<Item> itemid_Item_table = new QueryWrapper<Item>().eq("itemid", itemId);
        QueryWrapper<Inventory> itemid_Inventory_table = new QueryWrapper<Inventory>().eq("itemid", itemId);
        itemMapper.delete(itemid_Item_table);
        inventoryMapper.delete(itemid_Inventory_table);
        return true;
    }

    /**
     * 被ItemController调用，用于搜索商品,还没写完
     * @author
     * @param searchString 搜索关键词
     * @param page    当前页数
     * @param size    每页显示数量
     * @return List<ItemSummary> 搜索结果列表
     * @author
     */
    @Override
    public List<ItemSummary> searchItems(String searchString, int page, int size) {
        List<ItemSummary> summaries=new ArrayList<>();
        searchString = searchString.replace("+", " ");
        QueryWrapper<Product> queryWrapperProduct = new QueryWrapper<>();
        queryWrapperProduct.like("name", "%" + searchString + "%");

        return null;
    }



    /**
     * 被 ItemController 调用，用于添加商品。
     * @author Luo
     * @param itemDetail 包含要添加的商品信息的对象
     * @param supplierId 供应商id
     * @return boolean，表示是否添加成功
     */
    public boolean addItem(ItemDetail itemDetail, Integer supplierId) {
        Item item = new Item();
        item.setItemid(itemDetail.getItemId());
        item.setProductid(itemDetail.getProduct());
        item.setListprice(itemDetail.getList_price());
        item.setUnitcost(itemDetail.getUnit_cost());
        item.setAttr1(itemDetail.getDescription());
        item.setStatus("P");
        item.setSupplier(supplierId);
        itemMapper.insert(item);

        Inventory inventory = new Inventory();
        inventory.setItemid(itemDetail.getItemId());
        inventory.setQty(itemDetail.getQuantity());
        inventoryMapper.insert(inventory);

        return true;
    }
}
