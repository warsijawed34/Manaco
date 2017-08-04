package com.manaco.model;

/**
 * Created by vinove on 21/11/16.
 */

public class StandsInfoModel {
    private String id="";
    private String title="";
    private  String title_fr="";
    private String title_it="";
    private String description="";
    private String description_fr="";
    private String description_it="";
    private String created_at="";
    private String updated_at="";
    private String stand_id="";
    private String  information_id="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_fr() {
        return title_fr;
    }

    public void setTitle_fr(String title_fr) {
        this.title_fr = title_fr;
    }

    public String getTitle_it() {
        return title_it;
    }

    public void setTitle_it(String title_it) {
        this.title_it = title_it;
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

    public void setDescription_it(String description_itc) {
        this.description_it = description_itc;
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

    public String getStand_id() {
        return stand_id;
    }

    public void setStand_id(String stand_id) {
        this.stand_id = stand_id;
    }

    public String getInformation_id() {
        return information_id;
    }

    public void setInformation_id(String information_id) {
        this.information_id = information_id;
    }

}
