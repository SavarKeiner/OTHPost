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
    private String trackingID;
    private String forename;
    private String surename;
    private int postalCode;
    private String city;
    private String street;
    private String sforetreetNumber;
    private int waightInGramm;
}
