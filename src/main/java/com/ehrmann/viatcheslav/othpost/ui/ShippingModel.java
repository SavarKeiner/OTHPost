/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.ui;


import com.ehrmann.viatcheslav.othpost.entity.Customer;
import com.ehrmann.viatcheslav.othpost.entity.Parcel;
import com.ehrmann.viatcheslav.othpost.entity.Tracking;
import com.ehrmann.viatcheslav.othpost.service.ShippingService;
import com.ehrmann.viatcheslav.othpost.service.TrackingService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Slav
 */
@WebService
@Named
@SessionScoped
public class ShippingModel implements Serializable {
    @Inject
    private ShippingService ship;
    @Inject
    private TrackingService trackingService;

    @WebMethod(exclude = true)
    public String getSendforename() {
        return sendforename;
    }

    @WebMethod(exclude = true)
    public void setSendforename(String sendforename) {
        this.sendforename = sendforename;
    }

    @WebMethod(exclude = true)
    public String getSendsurename() {
        return sendsurename;
    }

    @WebMethod(exclude = true)
    public void setSendsurename(String sendsurename) {
        this.sendsurename = sendsurename;
    }

    @WebMethod(exclude = true)
    public String getSendcity() {
        return sendcity;
    }

    @WebMethod(exclude = true)
    public void setSendcity(String sendcity) {
        this.sendcity = sendcity;
    }

    @WebMethod(exclude = true)
    public String getSendstreet() {
        return sendstreet;
    }

    @WebMethod(exclude = true)
    public void setSendstreet(String sendstreet) {
        this.sendstreet = sendstreet;
    }

    @WebMethod(exclude = true)
    public String getSendstreetNumber() {
        return sendstreetNumber;
    }

    @WebMethod(exclude = true)
    public void setSendstreetNumber(String sendstreetNumber) {
        this.sendstreetNumber = sendstreetNumber;
    }

    @WebMethod(exclude = true)
    public String getSendiban() {
        return sendiban;
    }

    @WebMethod(exclude = true)
    public void setSendiban(String sendiban) {
        this.sendiban = sendiban;
    }

    @WebMethod(exclude = true)
    public String getSendpostalcode() {
        return sendpostalcode;
    }

    @WebMethod(exclude = true)
    public void setSendpostalcode(String sendpostalcode) {
        this.sendpostalcode = sendpostalcode;
    }

    @WebMethod(exclude = true)
    public String getRecvforename() {
        return recvforename;
    }

    @WebMethod(exclude = true)
    public void setRecvforename(String recvforename) {
        this.recvforename = recvforename;
    }

    @WebMethod(exclude = true)
    public String getRecvsurename() {
        return recvsurename;
    }

    @WebMethod(exclude = true)
    public void setRecvsurename(String recvsurename) {
        this.recvsurename = recvsurename;
    }

    @WebMethod(exclude = true)
    public String getRecvcity() {
        return recvcity;
    }

    @WebMethod(exclude = true)
    public void setRecvcity(String recvcity) {
        this.recvcity = recvcity;
    }

    @WebMethod(exclude = true)
    public String getRecvstreet() {
        return recvstreet;
    }

    @WebMethod(exclude = true)
    public void setRecvstreet(String recvstreet) {
        this.recvstreet = recvstreet;
    }

    @WebMethod(exclude = true)
    public String getRecvstreetNumber() {
        return recvstreetNumber;
    }

    @WebMethod(exclude = true)
    public void setRecvstreetNumber(String recvstreetNumber) {
        this.recvstreetNumber = recvstreetNumber;
    }

    @WebMethod(exclude = true)
    public String getRecvpostalcode() {
        return recvpostalcode;
    }

    @WebMethod(exclude = true)
    public void setRecvpostalcode(String recvpostalcode) {
        this.recvpostalcode = recvpostalcode;
    }
    
    private String sendforename= "";
    private String sendsurename= "";
    private String sendcity= "";
    private String sendstreet= "";
    private String sendstreetNumber= "";
    private String sendiban= "";
    private String sendpostalcode= "";
    
    private String recvforename= "";
    private String recvsurename= "";
    private String recvcity= "";
    private String recvstreet= "";
    private String recvstreetNumber= "";
    private String recvpostalcode= "";
    
    public enum ParcelType {
        letter, smallParcel, bigParcel 
    }
    
    @WebMethod(exclude = true)
    public void doTest(){
        
        //ship.createTempUser("max", "musterman", "regensburg", "landshuterstraße", "1a", "5678-1234", 93050);
        //System.out.println("dbg-2");
        
        //ship.isUserExsiting("max", "musterman", "regensburg", "landshuterstraße", "1a", "5678-1234", 93050);
        
        //this.setName("14212");
    }
    
    @WebMethod(exclude = true)
    public void createOrder(){
        if(verifyInputs()){
            Customer c = ship.isCustomerExsiting(sendforename, sendsurename, 
                    sendcity, sendstreet, sendstreetNumber, sendiban, Integer.parseInt(sendpostalcode));
            
            if(c == null){
                c = ship.createCustomer(sendforename, sendsurename, 
                    sendcity, sendstreet, sendstreetNumber, sendiban, Integer.parseInt(sendpostalcode));
            }
            
            Parcel parcel = ship.receiveParcel(recvforename, recvsurename, recvcity, recvstreet, recvstreetNumber, Integer.parseInt(sendpostalcode));
            Tracking tracking = trackingService.createTrackingEntry(parcel);
            //createInvoice
            
            
            
            ship.shipParcel(c, parcel, tracking);
            ship.simulateDelivery(parcel);
        }
    }
    
    @WebMethod(exclude = true)
    private boolean verifyInputs(){
        if(sendforename == "" || sendsurename== ""|| sendcity== "" || sendstreet== "" || sendstreetNumber== "" || 
                sendiban== "" || sendpostalcode== "" || recvforename== "" || recvsurename== "" || 
                recvcity== "" || recvstreet== "" || recvstreetNumber== "" || recvpostalcode== "" ){
                return false;
        } else{
            return true;
        }
    }

    @WebMethod
    public boolean shipRemote(Customer sender, Customer receiver, ParcelType type) {
        if("max".equals(receiver.getForename()))
            return false;
        
        return true;
    }
}

