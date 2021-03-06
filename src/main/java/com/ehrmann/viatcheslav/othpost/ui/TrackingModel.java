/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.ui;

import com.ehrmann.viatcheslav.othpost.entity.TrackingStatus;
import com.ehrmann.viatcheslav.othpost.entity.Warehouse;
import com.ehrmann.viatcheslav.othpost.service.ShippingService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.TypedQuery;

/**
 *
 * @author Slav
 */
@Named
@RequestScoped
public class TrackingModel implements Serializable {
    //@Inject
    //private ShippingService ship;
    @Inject
    private ShippingService ship;
    
    private List<TrackingStatus> trackingList;

    private String trackingNumber;

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    
    public List<TrackingStatus> getTrackingList() {
        return trackingList;
    }

    public void setTrackingList(List<TrackingStatus> trackingList) {
        this.trackingList = trackingList;
    }
    
    public void getParcelStatus(){
        //trackingList = trackingService.getTrackingStatusList();
        
        if(trackingNumber == null || trackingNumber.equals("")){
            //return "tracking.xhtml";
        } else {
            List<TrackingStatus> statusList = ship.getTrackingStatusList(trackingNumber);
            if(statusList != null){
                this.setTrackingList(statusList);
            } else {
                statusList = new ArrayList<TrackingStatus>();
                TrackingStatus status = new TrackingStatus();
                status.setStatus("Zu dieser Packetverfolgungsnummer gibt es kein Packet");
                statusList.add(status);
                setTrackingList(statusList);
            }
        }
         
        //return "tracking.xhtml";
    }
}
