package com.manaco.model;

/**
 * Created by vinove on 8/11/16.
 */
public class ListModel {

    String id="";
    String name="";
    String created_at="";
    String updated_at="";
    String name_fr="";
    String name_it="";

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

    public String getName_fr() {
        return name_fr;
    }

    public void setName_fr(String name_fr) {
        this.name_fr = name_fr;
    }

    public String getName_it() {
        return name_it;
    }

    public void setName_it(String name_it) {
        this.name_it = name_it;
    }
}
