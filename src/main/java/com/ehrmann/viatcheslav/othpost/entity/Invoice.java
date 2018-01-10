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
public class Invoice implements Serializable {
    @GeneratedValue( strategy=GenerationType.AUTO)
    @Id private long invoiceID;
    private long parcelID;
    private float price;
    private int parcelType;
    private String creationDateTime;
    private String paymentDateTime;
}
