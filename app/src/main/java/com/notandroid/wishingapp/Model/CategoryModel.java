package com.notandroid.wishingapp.Model;

public class CategoryModel {
    String categoryName;
    String id;
    public CategoryModel() {
    }

    public CategoryModel( String id,String categoryName) {
        this.categoryName = categoryName;
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
