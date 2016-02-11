package com.example.cho.testsqlite;

/**
 * Created by cho on 2016-02-11.
 */
public class Product {

    private int _id;
    private String _productname;
    private int _quantity;

    public Product(){
    }

    public Product(int id,String productname,int quantity){
        this._id=id;
        this._productname=productname;
        this._quantity=quantity;
    }
    public Product(String productname,int quantity){
        this._productname=productname;
        this._quantity=quantity;
    }

    public void setId(int id){
        this._id=id;
    }
    public int getId(){
        return this._id;
    }
    public void setProductName(String productName){
        this._productname=productName;
    }
    public String getProductName(){
        return this._productname;
    }

    public void setQuantity(int quantity){
        this._quantity=quantity;
    }
    public int getQuantity(){
        return this._quantity;
    }
}