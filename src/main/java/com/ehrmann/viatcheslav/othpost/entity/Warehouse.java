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
public class Warehouse implements Serializable {
    @Id private Integer warehouseID;
    private long postmanID;
    private String street;
    private String streetNumber;
    private String city;
    private int postalcode;
}
