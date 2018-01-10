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
 * @author Slav
 */
@Entity
public class TrackingStatus implements Serializable {
    @GeneratedValue( strategy=GenerationType.AUTO)
    @Id private long trackingStatusID;

    public long getTrackingStatusID() {
        return trackingStatusID;
    }

    public void setTrackingStatusID(long trackingID) {
        this.trackingStatusID = trackingID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public long getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(long warehouseID) {
        this.warehouseID = warehouseID;
    }
    
    private String status;
    private String timestamp;
    private long warehouseID;
}
