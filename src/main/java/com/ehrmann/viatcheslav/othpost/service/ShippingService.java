/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.service;

import com.ehrmann.viatcheslav.othpost.entity.Customer;
import com.ehrmann.viatcheslav.othpost.entity.Parcel;
import com.ehrmann.viatcheslav.othpost.entity.Postman;
import com.ehrmann.viatcheslav.othpost.entity.Postoffice;
import com.ehrmann.viatcheslav.othpost.entity.Tracking;
import com.ehrmann.viatcheslav.othpost.entity.TrackingStatus;
import com.ehrmann.viatcheslav.othpost.entity.Warehouse;
import com.ehrmann.viatcheslav.othpost.ui.ShippingModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

/**
 *
 * @author Slav
 */

@RequestScoped
public class ShippingService {
    @PersistenceContext
    private EntityManager em;
    
    @Resource
    private UserTransaction userTransaction;
 
    @Transactional
    public void simulateDelivery(Parcel p){
        TypedQuery<Warehouse> q = em.createQuery("SELECT w FROM Warehouse as w", Warehouse.class);
        List<Warehouse> result = q.getResultList();
        
        Collections.shuffle(result);
        Parcel p1 = em.find(Parcel.class, p.getParcelID());
        Tracking t = em.find(Tracking.class, p1.getTrackingObj().getTrackingID());
        
        for(Warehouse w : result){
               
               TrackingStatus ts = new TrackingStatus();
               ts.setWarehouseID(w.getWarehouseID());
               ts.setStatus("Das Packet ist im Lagerhaus in " + w.getCity() + " angekommen");
               em.persist(ts);
               
               t.getTrackingStatus().add(ts);
               em.merge(t);

        }        
    }

    @Transactional
    public void shipParcel(Customer c, Parcel p, Tracking t){//need invoice
        //em.getTransaction().begin();

        //p.setCustomerID(c.getCustomerID());
        //p.setTrackingID(t.getTrackingID());
        
        Tracking t1 = em.find(Tracking.class, t.getTrackingID());
        Customer c1 = em.find(Customer.class, c.getCustomerID());
        
        Parcel p1 = em.find(Parcel.class, p.getParcelID());
        em.merge(p1);
        
        p1.setCustomerObj(c1);
        p1.setTrackingObj(t1);
        
        em.persist(p1);

        //p.setInvoiceID(i.getInvoiceID());
        
        //em.getTransaction().commit();
        
        
        //simulateDelivery(p);
        //do shipping
    }
    
    @Transactional
    public Parcel receiveParcel(String recvforename, String recvsurename, 
            String recvcity, String recvstreet, String recvstreetNumber, 
            int recpostalCode, ShippingModel.ParcelType type){
        
        Parcel parcel = new Parcel();
        
        parcel.setForename(recvforename);
        parcel.setSurename(recvsurename);
        parcel.setCity(recvcity);
        parcel.setStreet(recvstreet);
        parcel.setStreetNumber(recvstreetNumber);
        parcel.setPostalCode(recpostalCode);
        
        em.persist(parcel);
        
        return parcel;
    }
    
    @Transactional
    public Customer createCustomer(String forename, String surename, String city, 
            String street, String streetNumber, String iban, int postalcode){
            
        Customer c = new Customer();
        c.setForename(forename);
        c.setSurename(surename);
        c.setCity(city);
        c.setStreet(street);
        c.setStreetNumber(streetNumber);
        c.setIban(iban);
        c.setPostalcode(postalcode);
        
        em.persist(c);
        
        return c;
    }

    
    public Customer isCustomerExsiting(String forename, String surename, String city, 
            String street, String streetNumber, String iban, int postalcode){
        TypedQuery<Customer> q = em.createQuery("SELECT ca FROM Customer as ca WHERE ca.forename = :forename AND ca.surename = :surename AND ca.city = :city AND ca.street = :street AND ca.streetNumber = :streetNumber AND ca.iban = :iban", Customer.class);
        q.setParameter("forename", forename);
        q.setParameter("surename", surename);
        q.setParameter("city", city);
        q.setParameter("street", street);
        q.setParameter("streetNumber", streetNumber);
        q.setParameter("iban", iban);
        //q.setParameter("postalcode", postalcode);
        List<Customer> result = q.getResultList();
        
        initWarehouse();
        
       if(q.getResultList().isEmpty())
           return null;
       else 
           return q.getResultList().get(0);
       
    }
    
    private void isInitWarehouse(){
        TypedQuery<Warehouse> q = em.createQuery("SELECT w FROM Warehouse as w", Warehouse.class);
        List<Warehouse> result = q.getResultList();    
                
        if(result.isEmpty()){
            initWarehouse();
        }
    }
    
    @Transactional
    private void initWarehouse(){
        TypedQuery<Warehouse> q = em.createQuery("SELECT w FROM Warehouse as w", Warehouse.class);
        List<Warehouse> result = q.getResultList();    
                
        if(!result.isEmpty()){
            return;
        }
        
        try{
        userTransaction.begin();
                
        List<Postman> pl1 = new ArrayList<Postman>();
        List<Postman> pl2 = new ArrayList<Postman>();
        List<Postman> pl3 = new ArrayList<Postman>();
        
        List<Postoffice> po1 = new ArrayList<Postoffice>();
        List<Postoffice> po2 = new ArrayList<Postoffice>();
        List<Postoffice> po3 = new ArrayList<Postoffice>();
        
        Warehouse w1 = new Warehouse();
        Warehouse w2 = new Warehouse();
        Warehouse w3 = new Warehouse();
        
        Postman postman1 = new Postman();
        Postman postman2 = new Postman();
        Postman postman3 = new Postman();
        
        w1.setStreet("Am Horn");
        w1.setStreetNumber("1");
        w1.setCity("Darmstadt");
        w1.setPostalcode(64283);
        
        postman1.setForename("Max");
        postman1.setSurename("Musterman");
        postman1.setCity("Darmstadt");
        postman1.setStreet("Am Horn");
        postman1.setStreetNumber("3c");
        postman1.setEmplyoeeNumber(1);
        
        postman1.setWarehouseID(w1.getWarehouseID());
        em.persist(postman1);
        
        pl1.add(postman1);
        
        
        w1.setPostmanList(pl1);
        w1.setPostofficeList(po1);
        
        
        w2.setStreet("Bogenstraße");
        w2.setStreetNumber("1a");
        w2.setCity("Amberg");
        w2.setPostalcode(92224);
        
        postman2.setForename("Alfred");
        postman2.setSurename("Alfonso");
        postman2.setCity("Amberg");
        postman2.setStreet("Bogenstraße");
        postman2.setStreetNumber("3c");
        postman2.setEmplyoeeNumber(2);
        
        pl2.add(postman2);
        
        w2.setPostmanList(pl2);
        w2.setPostofficeList(po2);
        
        
        w3.setStreet("Spitzwegstraße");
        w3.setStreetNumber("2b");
        w3.setCity("Regensburg");
        w3.setPostalcode(92050);
        
        postman3.setForename("Peter");
        postman3.setSurename("Pan");
        postman3.setCity("Regensburg");
        postman3.setStreet("Spitzwegstraße");
        postman3.setStreetNumber("12");
        postman3.setEmplyoeeNumber(3);
        
        pl3.add(postman3);
        
        w3.setPostmanList(pl3);
        w3.setPostofficeList(po3);
        

        postman2.setWarehouseID(w2.getWarehouseID());
        postman3.setWarehouseID(w3.getWarehouseID());
       
        em.persist(postman2);
        em.persist(postman3);
        
        em.persist(w1);
        em.persist(w2);
        em.persist(w3);
        
        userTransaction.commit();
        } catch (Exception e){
            
        }
        

        
        
    }
}