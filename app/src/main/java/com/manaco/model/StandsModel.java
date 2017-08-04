package com.manaco.model;

import java.util.ArrayList;

/**
 * Created by vinove on 10/11/16.
 */
public class StandsModel {

    private String id = "";
    private String name = "";
    private String description = "";
    private String description_fr = "";
    private String description_it = "";
    private String created_at = "";
    private String updated_at = "";
    private String category_id = "";
    private String logo_path = "";
    private String stand_ref = "";
    private String website_url = "";
    private String address_id = "";
    private String status_id="";
    private String deleted_at="";
    private String user_id="";
    private String stand_id="";


    private ArrayList<AddressModel> addressModelArrayList = new ArrayList<>();
    private ArrayList<CategoryModel> categoryModelArrayList = new ArrayList<>();



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription_fr() {
        return description_fr;
    }

    public void setDescription_fr(String description_fr) {
        this.description_fr = description_fr;
    }

    public String getDescription_it() {
        return description_it;
    }

    public void setDescription_it(String description_it) {
        this.description_it = description_it;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getStand_ref() {
        return stand_ref;
    }

    public void setStand_ref(String stand_ref) {
        this.stand_ref = stand_ref;
    }

    public String getWebsite_url() {
        return website_url;
    }

    public void setWebsite_url(String website_url) {
        this.website_url = website_url;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getStand_id() {
        return stand_id;
    }

    public void setStand_id(String stand_id) {
        this.stand_id = stand_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public ArrayList<AddressModel> getAddressModelArrayList() {
        return addressModelArrayList;
    }

    public void setAddressModelArrayList(AddressModel addressModel) {
        addressModelArrayList.add(addressModel);
    }

    public ArrayList<CategoryModel> getCategoryModelArrayList() {
        return categoryModelArrayList;
    }

    public void setCategoryModelArrayList(CategoryModel categoryModel) {
        categoryModelArrayList.add(categoryModel);
    }
}
