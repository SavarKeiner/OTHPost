/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Viatcheslav Ehrmann
 */
@Entity
public class Postman implements Serializable {
    @GeneratedValue( strategy=GenerationType.AUTO)
    @Id private long postmanID;

    public long getPostmanID() {
        return postmanID;
    }

    public void setPostmanID(long postmanID) {
        this.postmanID = postmanID;
    }

    public long getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(long warehouseID) {
        this.warehouseID = warehouseID;
    }

    public long getEmplyoeeNumber() {
        return emplyoeeNumber;
    }

    public void setEmplyoeeNumber(long emplyoeeNumber) {
        this.emplyoeeNumber = emplyoeeNumber;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }
    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
    private long warehouseID;
    private long emplyoeeNumber;
    private String forename;
    private String surename;
    private String city;
    private String street;
    private String streetNumber;
    
}
