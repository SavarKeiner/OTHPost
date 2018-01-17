/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.service;

import com.ehrmann.viatcheslav.othpost.entity.Customer;
import com.ehrmann.viatcheslav.othpost.entity.Invoice;
import com.ehrmann.viatcheslav.othpost.entity.Parcel;
import com.ehrmann.viatcheslav.othpost.entity.Postman;
import com.ehrmann.viatcheslav.othpost.entity.Postoffice;
import com.ehrmann.viatcheslav.othpost.entity.Tracking;
import com.ehrmann.viatcheslav.othpost.entity.TrackingStatus;
import com.ehrmann.viatcheslav.othpost.entity.Warehouse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.xml.ws.WebServiceRef;
import services.TransaktionServiceService;

/**
 *
 * @author Slav
 */
@WebService
@RequestScoped
public class ShippingService implements ShippingServiceIF {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/im-lamport_8080/huberbank-1.0-SNAPSHOT/TransaktionService.wsdl")
    private TransaktionServiceService service;
    @PersistenceContext
    private EntityManager em;
    
    @Resource
    private UserTransaction userTransaction;
    
    @Override
    @WebMethod
    @Transactional
    public TrackingRemoteResponse shipRemote(Customer sender, Customer receiver, Invoice.ParcelType type) {
        Customer customerFromDB = isCustomerExisting(sender);
        float price = 0.0f;
        
        
        if(customerFromDB == null){
            customerFromDB = createCustomerRemote(sender);
        }
        
        Invoice invoice = createInvoice(type, 1.0f);
        
        if (type == Invoice.ParcelType.Brief) {
            price = 1.0f;
        } else if (type == Invoice.ParcelType.Packet) {
            price = 2.0f;
        } else if (type == Invoice.ParcelType.Palette) {
            price = 3.0f;
        }
        
        try { //createInvoiceHere
            services.TransaktionService port = service.getTransaktionServicePort();
            services.Konto receiverAccount = new services.Konto();
            receiverAccount.setId(Long.MIN_VALUE);
            services.Konto senderAccount = new services.Konto();
            senderAccount.setId(Long.MAX_VALUE);
            int cent = 0;

            // TODO initialize WS operation arguments here
            services.Transaktion transaktion = new services.Transaktion();
            transaktion.setEuro((int)price);
            transaktion.setCent(cent);
            transaktion.setSender(senderAccount);
            transaktion.setEmpfaenger(receiverAccount);

            port.ueberweise(transaktion);
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }

        
        
        Parcel p = receiveParcelRemote(receiver, invoice);
        Tracking tracking = createTrackingEntryRemote(p);
        
        
        shipParcel(sender, p, tracking);
        
        simulateDelivery(customerFromDB, p);
        
        Tracking forTrackingResponse = em.find(Tracking.class, tracking.getId());
        TrackingRemoteResponse response = new TrackingRemoteResponse();
        response.statusList = new ArrayList<String>();
        response.trackingNumber = tracking.getTrackingNumber();
        
        for(TrackingStatus status : forTrackingResponse.getTrackingStatus()){
            response.statusList.add(status.getStatus());
        }
        
        
        
        /*Customer c = ship.isCustomerExsiting(sendforename, sendsurename, 
                    sendcity, sendstreet, sendstreetNumber, sendiban, Integer.parseInt(sendpostalcode));
        
        if(c == null){
            c = ship.createCustomer(sendforename, sendsurename, 
                sendcity, sendstreet, sendstreetNumber, sendiban, Integer.parseInt(sendpostalcode));
        }
            
        Parcel parcel = ship.receiveParcel(recvforename, recvsurename, recvcity, recvstreet, recvstreetNumber, Integer.parseInt(sendpostalcode), typeParcel);
        
        
        Tracking tracking = trackingService.createTrackingEntry(parcel);
            
        ship.shipParcel(c, parcel, tracking);
              
        remotelogger.logOrder("order created through website with id: " + parcel.getId());
            
        ship.simulateDelivery(c, parcel);*/
        
        return response;
    }
    
    @WebMethod(exclude=true)
    @Transactional
    public Tracking createTrackingEntryRemote(Parcel p){
        Tracking t = new Tracking();
        List<TrackingStatus> ts = new ArrayList<TrackingStatus>();
        String uuid = UUID.randomUUID().toString();
        
        Parcel pd = em.find(Parcel.class, p.getId());   
        
        t.setParcelObj(pd);
        t.setTrackingNumber(uuid);
        t.setTrackingStatus(ts);
        
        em.persist(t);
        
        return t;
    }
    
    @WebMethod(exclude = true)
    @Transactional
    public Parcel receiveParcelRemote(Customer receiver, Invoice i){
        Parcel p = new Parcel();
        
        p.setForename(receiver.getForename());
        p.setSurename(receiver.getSurename());
        p.setCity(receiver.getCity());
        p.setPostalCode(receiver.getPostalcode());
        p.setStreet(receiver.getStreet());
        p.setStreetNumber(receiver.getStreetNumber());
        
        //set real invoice here
        p.setInvoice(i);
        
        em.persist(p);
        
        return p;
    }
    
    @WebMethod(exclude = true)
    @Transactional
    public Customer createCustomerRemote(Customer sender){
        try{
            
        Customer c = new Customer();
        c.setForename(sender.getForename());
        c.setSurename(sender.getSurename());
        c.setCity(sender.getCity());
        c.setPostalcode(sender.getPostalcode());
        c.setStreet(sender.getStreet());
        c.setStreetNumber(sender.getStreetNumber());
        c.setIban(sender.getIban());
        
        em.persist(c);
        return c;
        } catch (Exception e){
            System.out.println("dbg-1: " + e.getMessage() + " |0| " + e.getStackTrace());
        }
        return null;
    }
    
    @WebMethod(exclude = true)
    @Transactional
    public void simulateDelivery(Customer c, Parcel p){
        TypedQuery<Warehouse> q = em.createQuery("SELECT w FROM Warehouse as w", Warehouse.class);
        List<Warehouse> result = q.getResultList();
        
        Collections.shuffle(result);
        Parcel p1 = em.find(Parcel.class, p.getId());
        Tracking t = em.find(Tracking.class, p1.getTrackingObj().getId());
        Customer c1 = em.find(Customer.class, c.getId());
        
        Postoffice office = new Postoffice();
        office.setCity(c1.getCity());
        office.setPostalcode(c1.getPostalcode());
        office.setStreet(c1.getStreet());
        em.persist(office);
 
        TrackingStatus tsf = new TrackingStatus();
        tsf.setStatus("Das Packet wurde an der Annahmestelle " + c1.getCity() + " " + c1.getStreet() + " angenommen");
        em.persist(tsf);
        t.getTrackingStatus().add(tsf);
        em.merge(t);
        
        for(Warehouse w : result){
               
               TrackingStatus ts = new TrackingStatus();
               ts.setWarehouse(w);
               ts.setStatus("Das Packet ist im Lagerhaus in " + w.getCity() + " angekommen");
               em.persist(ts);
               
               t.getTrackingStatus().add(ts);
               em.merge(t);

        }   
        
        TrackingStatus tse = new TrackingStatus();
        tse.setStatus("Das Packet wurde zugestellt an: " + p.getCity() + " " + p.getStreet() + " " + p.getStreetNumber());
        em.persist(tse);
        t.getTrackingStatus().add(tse);
        em.merge(t);
    }

    @WebMethod(exclude = true)
    @Transactional
    public void shipParcel(Customer c, Parcel p, Tracking t){//need invoice
        Tracking t1 = em.find(Tracking.class, t.getId());
        Customer c1 = em.find(Customer.class, c.getId());
        Parcel p1 = em.find(Parcel.class, p.getId());
        
        em.merge(p1);
        
        p1.setCustomerObj(c1);
        p1.setTrackingObj(t1);
        
        em.persist(p1);
    }
    
    @WebMethod(exclude = true)
    @Transactional
    public Parcel receiveParcel(String recvforename, String recvsurename, 
            String recvcity, String recvstreet, String recvstreetNumber, 
            int recpostalCode, Invoice i){
        
        Parcel parcel = new Parcel();
        
        parcel.setForename(recvforename);
        parcel.setSurename(recvsurename);
        parcel.setCity(recvcity);
        parcel.setStreet(recvstreet);
        parcel.setStreetNumber(recvstreetNumber);
        parcel.setPostalCode(recpostalCode);
        
        parcel.setInvoice(i);
        
        em.persist(parcel);
        
        return parcel;
    }
    
    public Invoice createInvoice(Invoice.ParcelType parcelType, float price){
        Invoice i = new Invoice();
        i.setParcelType(parcelType);
        i.setPrice(price);
        i.setPaymentDateTime(LocalDateTime.now().toString());
        return i;
    }
    
    
    @WebMethod(exclude = true)
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

    @WebMethod(exclude = true)
    public Customer isCustomerExisting(Customer c){
        TypedQuery<Customer> q = em.createQuery("SELECT ca FROM Customer as ca WHERE ca.forename = :forename AND ca.surename = :surename AND ca.city = :city AND ca.street = :street AND ca.streetNumber = :streetNumber AND ca.iban = :iban", Customer.class);
        q.setParameter("forename", c.getForename());
        q.setParameter("surename", c.getSurename());
        q.setParameter("city", c.getCity());
        q.setParameter("street", c.getStreet());
        q.setParameter("streetNumber", c.getStreetNumber());
        q.setParameter("iban", c.getIban());
        
        List<Customer> result = q.getResultList();
        if(result.isEmpty())
           return null;
        else 
           return result.get(0);
    }
    
    @WebMethod(exclude = true)
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
    
    @WebMethod(exclude = true)
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
        
        postman1.setEmploymentWarehouse(w1);
        postman2.setEmploymentWarehouse(w2);
        postman3.setEmploymentWarehouse(w3);
       
                
        em.persist(postman1);
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