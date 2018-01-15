/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
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
public class Tracking extends LongIdEntity {
    private String trackingNumber;
    @ElementCollection
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST, orphanRemoval=true)
    private List<TrackingStatus> trackingStatus;

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
