/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Viatcheslav Ehrmann
 */
@Entity
public class Warehouse implements Serializable {
    @GeneratedValue( strategy=GenerationType.AUTO)
    @Id private Integer warehouseID;

    public Integer getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(Integer warehouseID) {
        this.warehouseID = warehouseID;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(int postalcode) {
        this.postalcode = postalcode;
    }

    public List<Postoffice> getPostofficeList() {
        return postofficeList;
    }

    public void setPostofficeList(List<Postoffice> postofficeList) {
        this.postofficeList = postofficeList;
    }

    public List<Postman> getPostmanList() {
        return postmanList;
    }

    public void setPostmanList(List<Postman> postmanList) {
        this.postmanList = postmanList;
    }
    
    private String street;
    private String streetNumber;
    private String city;
    private int postalcode;
    
    @ElementCollection
    private List<Postoffice> postofficeList;
    @ElementCollection
    private List<Postman> postmanList;
}
