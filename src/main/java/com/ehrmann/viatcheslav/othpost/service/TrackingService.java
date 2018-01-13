/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.service;
import com.ehrmann.viatcheslav.othpost.entity.Customer;
import com.ehrmann.viatcheslav.othpost.entity.Parcel;
import com.ehrmann.viatcheslav.othpost.entity.Tracking;
import com.ehrmann.viatcheslav.othpost.entity.TrackingStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.ejb.Asynchronous;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
/**
 *
 * @author Slav
 */
@RequestScoped
public class TrackingService {
    @PersistenceContext
    private EntityManager em;
    
    
    @Transactional
    public List<TrackingStatus> getTrackingStatusList(String trackingNumber){
        TypedQuery<Tracking> qz = em.createQuery("SELECT t FROM Tracking as t WHERE t.trackingNumber = :number", Tracking.class);
        qz.setParameter("number", trackingNumber);
        List<Tracking> trackingResult = qz.getResultList();
        
        if(!trackingResult.isEmpty())
            return trackingResult.get(0).getTrackingStatus();
        else
            return null;
    }
    
    @Transactional
    public Tracking createTrackingEntry(Parcel p){
        Tracking t = new Tracking();
        List<TrackingStatus> ts = new ArrayList<TrackingStatus>();
        String uuid = UUID.randomUUID().toString();

        t.setParcelObj(p);
        t.setTrackingNumber(uuid);
        t.setTrackingStatus(ts);
        
        em.persist(t);
        
        return t;
    }
}
