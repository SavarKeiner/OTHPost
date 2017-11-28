/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Viatcheslav Ehrmann
 */
@Entity
public class Parcel implements Serializable {
    @Id private long parcelID;
    private long customerID;
    private long invoiceID;
    private String parcelTrackingID;
    private String parcelTrackingNumber;
    //private long warehouseID;
    private long postmanID;
    private String receipientForeName;
    private String receipientSureName;
    private int postalCode;
    private String recCity;
    private String recStreet;
    private String recStreetNumber;
    private int waightInGramm;

    
}
