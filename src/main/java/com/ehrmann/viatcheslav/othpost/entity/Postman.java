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
public class Postman implements Serializable {
    @Id private long postmanID;
    private long warehouseID;
    private long emplyoeeNumber;
    private String forename;
    private String surename;
    
}
