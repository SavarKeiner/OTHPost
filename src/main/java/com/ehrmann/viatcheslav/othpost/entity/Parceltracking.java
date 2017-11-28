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
import javax.persistence.Id;

/**
 *
 * @author Slav
 */
@Entity
public class Parceltracking implements Serializable{
    @Id private long parcelTrackingID;
    private long parcelID;
    private String parcelTrackingNumber;
    
    @ElementCollection
    private List<String> timestamp = new ArrayList<String>();
    @ElementCollection
    private List<Integer> location = new ArrayList<Integer>();
    @ElementCollection
    private List<String> status = new ArrayList<String>();
}
