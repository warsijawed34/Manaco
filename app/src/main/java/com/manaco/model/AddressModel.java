package com.manaco.model;

/**
 * Created by vinove on 10/11/16.
 */
public class AddressModel {
    private String id = "";
    private String street = "";
    private String city = "";
    private String state = "";
    private String postal_code = "";
    private String lat = "";
    private String lng = "";
    private String country_id = "";
    private String is_primary = "";
    private String is_billing = "";
    private String is_shipping = "";
    private String created_at = "";
    private String updated_at = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getIs_primary() {
        return is_primary;
    }

    public void setIs_primary(String is_primary) {
        this.is_primary = is_primary;
    }

    public String getIs_billing() {
        return is_billing;
    }

    public void setIs_billing(String is_billing) {
        this.is_billing = is_billing;
    }

    public String getIs_shipping() {
        return is_shipping;
    }

    public void setIs_shipping(String is_shipping) {
        this.is_shipping = is_shipping;
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
}
