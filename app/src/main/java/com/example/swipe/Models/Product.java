package com.example.swipe.Models;

public class Product {
    String img,product_name,product_type;
    double tax;
    double price;
    public  Product(){

    }

    public Product(double price, String img, String product_name, String product_type, double tax) {
        this.price = price;
        this.img = img;
        this.product_name = product_name;
        this.product_type = product_type;
        this.tax = tax;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }
}

//"image": "",
//        "price": 1212,
//        "product_name": "trial",
//        "product_type": "Product 2",
//        "tax": 8.8