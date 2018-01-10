/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Slav
 */
@Entity
public class Tracking implements Serializable {
    @GeneratedValue( strategy=GenerationType.AUTO)
    @Id private long trackingID;
    private long parcelID;
    private String trackingNumber;
    @ElementCollection
    private List<TrackingStatus> trackingStatus;

    public long getTrackingID() {
        return trackingID;
    }

    public void setTrackingID(long trackingID) {
        this.trackingID = trackingID;
    }

    public long getParcelID() {
        return parcelID;
    }

    public void setParcelID(long parcelID) {
        this.parcelID = parcelID;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public List<TrackingStatus> getTrackingStatus() {
        return trackingStatus;
    }

    public void setTrackingStatus(List<TrackingStatus> trackingStatus) {
        this.trackingStatus = trackingStatus;
    }
}
