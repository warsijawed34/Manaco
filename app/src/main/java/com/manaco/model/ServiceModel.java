package com.manaco.model;

import java.util.ArrayList;

/**
 * Created by vinove on 24/11/16.
 */

public class ServiceModel {
    //
    private String id = "";
    private String name = "";
    private String name_fr = "";
    private String name_it = "";
    private String created_at = "";
    private String updated_at = "";


    private String serviceType_id = "";
    private String address_id = "";
    private String status_id = "";
    private String deleted_at = "";





    private ArrayList<AddressModel> addressModelArrayList = new ArrayList<>();
    private ArrayList<CategoryModel> serviceTypeModelArrayList = new ArrayList<>();

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

    public String getName_it() {
        return name_it;
    }

    public void setName_it(String name_it) {
        this.name_it = name_it;
    }

    public String getName_fr() {
        return name_fr;
    }

    public void setName_fr(String name_fr) {
        this.name_fr = name_fr;
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

    public String getServiceType_id() {
        return serviceType_id;
    }

    public void setServiceType_id(String serviceType_id) {
        this.serviceType_id = serviceType_id;
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


    public ArrayList<AddressModel> getAddressModelArrayList() {
        return addressModelArrayList;
    }

    public void setAddressModelArrayList(AddressModel addressModel) {
        addressModelArrayList.add(addressModel);
    }



    public ArrayList<CategoryModel> getServiceTypeModelArrayList() {
        return serviceTypeModelArrayList;
    }

    public void setServiceTypeModelArrayList(CategoryModel categoryModel) {
        serviceTypeModelArrayList.add(categoryModel);
    }

}
