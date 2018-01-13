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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Slav
 */
@Entity
public class Tracking implements Serializable {
    @GeneratedValue( strategy=GenerationType.AUTO)
    @Id private long trackingID;
    private String trackingNumber;
    @ElementCollection
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval=true)
    private List<TrackingStatus> trackingStatus;

    public long getTrackingID() {
        return trackingID;
    }

    public void setTrackingID(long trackingID) {
        this.trackingID = trackingID;
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
    
    @OneToOne(mappedBy="trackingObj")
    private Parcel parcelObj;

    public Parcel getParcelObj() {
        return parcelObj;
    }

    public void setParcelObj(Parcel parcelObj) {
        this.parcelObj = parcelObj;
    }
}
