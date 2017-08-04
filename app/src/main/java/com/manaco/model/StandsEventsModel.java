package com.manaco.model;

import java.util.ArrayList;

/**
 * Created by vinove on 21/11/16.
 */

public class StandsEventsModel {
    private String id = "";
    private String name = "";
    private String description = "";
    private String description_fr = "";
    private String description_it = "";
    private String start_dateTime = "";
    private String end_dateTime = "";
    private String stand_id = "";
    private String created_at = "";
    private String updated_at = "";
    private String address_id = "";
    private String status_id = "";
    private String deleted_at = "";
    private String notes = "";
    private String notes_fr = "";
    private String notes_it = "";

    private ArrayList<AddressModel> addressModelArrayList = new ArrayList<>();
    private ArrayList<StandsModel> standsModelArrayList = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStart_dateTime() {
        return start_dateTime;
    }

    public void setStart_dateTime(String start_dateTime) {
        this.start_dateTime = start_dateTime;
    }

    public String getEnd_dateTime() {
        return end_dateTime;
    }

    public void setEnd_dateTime(String end_dateTime) {
        this.end_dateTime = end_dateTime;
    }

    public String getStand_id() {
        return stand_id;
    }

    public void setStand_id(String stand_id) {
        this.stand_id = stand_id;
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

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes_fr() {
        return notes_fr;
    }

    public void setNotes_fr(String notes_fr) {
        this.notes_fr = notes_fr;
    }

    public String getNotes_it() {
        return notes_it;
    }

    public void setNotes_it(String notes_it) {
        this.notes_it = notes_it;
    }

    public ArrayList<AddressModel> getAddressModelArrayList() {
        return addressModelArrayList;
    }

    public void setAddressModelArrayList(AddressModel addressModel) {
        addressModelArrayList.add(addressModel);
    }

    public ArrayList<StandsModel> getStandsModelArrayList() {
        return standsModelArrayList;
    }
    public void setStandsModelArrayList(StandsModel standsModelModel) {
        standsModelArrayList.add(standsModelModel);
    }
}
