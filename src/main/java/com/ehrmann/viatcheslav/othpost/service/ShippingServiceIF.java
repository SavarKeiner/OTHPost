/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.service;

import com.ehrmann.viatcheslav.othpost.entity.Customer;
import com.ehrmann.viatcheslav.othpost.entity.Invoice;
import com.ehrmann.viatcheslav.othpost.entity.Parcel;
import com.ehrmann.viatcheslav.othpost.entity.Tracking;
import com.ehrmann.viatcheslav.othpost.entity.TrackingStatus;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.transaction.Transactional;

/**
 *
 * @author Viatcheslav Ehrmann
 */
public interface ShippingServiceIF {

    public TrackingRemoteResponse shipRemote(Customer sender, Customer receiver, Invoice.ParcelType type);
    
    public Tracking createTrackingEntryRemote(Parcel p);
    
    public Parcel receiveParcelRemote(Customer receiver, Invoice i);
    
    public Customer createCustomerRemote(Customer sender);
    
    public void simulateDelivery(Customer c, Parcel p);
    
    public void shipParcel(Customer c, Parcel p, Tracking t);
    
    public Parcel receiveParcel(String recvforename, String recvsurename, 
            String recvcity, String recvstreet, String recvstreetNumber, 
            int recpostalCode, Invoice i);
    
    public Invoice createInvoice(Invoice.ParcelType parcelType, float price);
    
    public Customer createCustomer(String forename, String surename, String city, 
            String street, String streetNumber, String iban, int postalcode);
    
    public Customer isCustomerExisting(Customer c);
    
    public Customer isCustomerExsiting(String forename, String surename, String city, 
            String street, String streetNumber, String iban, int postalcode);
    
    public List<TrackingStatus> getTrackingStatusList(String trackingNumber);
    
    public Tracking createTrackingEntry(Parcel p);
    
}
