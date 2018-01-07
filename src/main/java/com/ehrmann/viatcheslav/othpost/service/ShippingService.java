/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.service;

import com.ehrmann.viatcheslav.othpost.entity.Customer;
import com.ehrmann.viatcheslav.othpost.entity.Parcel;
import java.util.List;
import javax.ejb.Asynchronous;
import javax.enterprise.context.RequestScoped;
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
public class ShippingService {
    @PersistenceContext(unitName="com.ehrmann.viatcheslav_OTHPost_war_1.0-SNAPSHOTPU")
    private EntityManager em;
 
    
    @Asynchronous
    public void shipParcel(Customer c, String forename, String surename, String city, 
            String street, String streetNumber, int postalcode){
        
        Parcel parcel = new Parcel();
        //do shipping
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
        TypedQuery<Customer> q = em.createQuery("SELECT c FROM"+ " Customer as c WHERE c.forename = :forename "
                + "AND c.surename = :surename AND c.city = :city AND c.street = :street"
                + "AND c.streetNumber = :streetNumber AND c.iban = :iban", Customer.class);
        q.setParameter("forename", forename);
        q.setParameter("surename", surename);
        q.setParameter("city", city);
        q.setParameter("street", street);
        q.setParameter("streetNumber", streetNumber);
        q.setParameter("iban", iban);
        q.setParameter("postalcode", postalcode);
        List<Customer> result = q.getResultList();
        
       if(q.getResultList().isEmpty())
           return null;
       else 
           return q.getResultList().get(0);
    }
}