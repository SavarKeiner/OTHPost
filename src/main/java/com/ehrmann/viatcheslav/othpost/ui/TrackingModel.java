/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.ui;

import com.ehrmann.viatcheslav.othpost.entity.TrackingStatus;
import com.ehrmann.viatcheslav.othpost.entity.Warehouse;
import com.ehrmann.viatcheslav.othpost.service.ShippingService;
import com.ehrmann.viatcheslav.othpost.service.TrackingService;
import java.io.Serializable;
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
@SessionScoped
public class TrackingModel implements Serializable {
    @Inject
    private ShippingService ship;
    @Inject
    private TrackingService trackingService;
    
    private List<TrackingStatus> trackingList;

    public List<TrackingStatus> getTrackingList() {
        return trackingList;
    }

    public void setTrackingList(List<TrackingStatus> trackingList) {
        this.trackingList = trackingList;
    }
    
    public String test(){
        //trackingList = trackingService.getTest();
        
        this.setTrackingList(trackingService.getTest());
        
        
        return "tracking.xhtml";
    }
}
