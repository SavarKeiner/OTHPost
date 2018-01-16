/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.ui;


import com.ehrmann.viatcheslav.othpost.entity.Customer;
import com.ehrmann.viatcheslav.othpost.entity.Invoice;
import com.ehrmann.viatcheslav.othpost.entity.Invoice.ParcelType;
import com.ehrmann.viatcheslav.othpost.entity.Parcel;
import com.ehrmann.viatcheslav.othpost.entity.Tracking;
import com.ehrmann.viatcheslav.othpost.service.LoggerIF;
import com.ehrmann.viatcheslav.othpost.service.LoggerType;
import com.ehrmann.viatcheslav.othpost.service.Loggers;
import com.ehrmann.viatcheslav.othpost.service.ShippingService;
import com.ehrmann.viatcheslav.othpost.service.TrackingService;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Slav
 */
@Named
@RequestScoped
public class ShippingModel implements Serializable {
    @Inject
    private ShippingService ship;
    @Inject
    private TrackingService trackingService;

    @Inject
    @Loggers(LoggerType.Web)
    private LoggerIF weblogger;
    
    @Inject
    @Loggers(LoggerType.Remote)
    private LoggerIF remotelogger;
    
    public String getSendforename() {
        return sendforename;
    }

    public void setSendforename(String sendforename) {
        this.sendforename = sendforename;
    }

    public String getSendsurename() {
        return sendsurename;
    }

    public void setSendsurename(String sendsurename) {
        this.sendsurename = sendsurename;
    }

    public String getSendcity() {
        return sendcity;
    }

    public void setSendcity(String sendcity) {
        this.sendcity = sendcity;
    }

    public String getSendstreet() {
        return sendstreet;
    }

    public void setSendstreet(String sendstreet) {
        this.sendstreet = sendstreet;
    }

    public String getSendstreetNumber() {
        return sendstreetNumber;
    }

    public void setSendstreetNumber(String sendstreetNumber) {
        this.sendstreetNumber = sendstreetNumber;
    }

    public String getSendiban() {
        return sendiban;
    }

    public void setSendiban(String sendiban) {
        this.sendiban = sendiban;
    }

    public String getSendpostalcode() {
        return sendpostalcode;
    }

    public void setSendpostalcode(String sendpostalcode) {
        this.sendpostalcode = sendpostalcode;
    }

    public String getRecvforename() {
        return recvforename;
    }

    public void setRecvforename(String recvforename) {
        this.recvforename = recvforename;
    }

    public String getRecvsurename() {
        return recvsurename;
    }

    public void setRecvsurename(String recvsurename) {
        this.recvsurename = recvsurename;
    }

    public String getRecvcity() {
        return recvcity;
    }

    public void setRecvcity(String recvcity) {
        this.recvcity = recvcity;
    }

    public String getRecvstreet() {
        return recvstreet;
    }

    public void setRecvstreet(String recvstreet) {
        this.recvstreet = recvstreet;
    }

    public String getRecvstreetNumber() {
        return recvstreetNumber;
    }

    public void setRecvstreetNumber(String recvstreetNumber) {
        this.recvstreetNumber = recvstreetNumber;
    }

    public String getRecvpostalcode() {
        return recvpostalcode;
    }

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
    
    private String parcelTrackingNumber = "";
    private List<Invoice.ParcelType> parcelTypeList = Arrays.asList(Invoice.ParcelType.Brief, Invoice.ParcelType.Packet, Invoice.ParcelType.Palette);
    private Invoice.ParcelType typeParcel;
    
    public String getParcelTrackingNumber() {
        return parcelTrackingNumber;
    }

    public void setParcelTrackingNumber(String parcelTrackingNumber) {
        this.parcelTrackingNumber = parcelTrackingNumber;
    }

    public ParcelType getTypeParcel() {
        return typeParcel;
    }

    public void setTypeParcel(ParcelType typeParcel) {
        this.typeParcel = typeParcel;
    }

    public List<ParcelType> getParcelTypeList() {
        return parcelTypeList;
    }

    public void setParcelTypeList(List<ParcelType> parcelTypeList) {
        this.parcelTypeList = parcelTypeList;
    }
    
    public void createOrder(){
        if(verifyInputs()){
            Customer c = ship.isCustomerExsiting(sendforename, sendsurename, 
                    sendcity, sendstreet, sendstreetNumber, sendiban, Integer.parseInt(sendpostalcode));
            
            if(c == null){
                c = ship.createCustomer(sendforename, sendsurename, 
                    sendcity, sendstreet, sendstreetNumber, sendiban, Integer.parseInt(sendpostalcode));
            }
            
            Parcel parcel = ship.receiveParcel(recvforename, recvsurename, recvcity, recvstreet, recvstreetNumber, Integer.parseInt(sendpostalcode), typeParcel);
            Tracking tracking = trackingService.createTrackingEntry(parcel);
            //createInvoice
            
            setParcelTrackingNumber(tracking.getTrackingNumber());
            ship.shipParcel(c, parcel, tracking);
              
            weblogger.logOrder("order created through website with id: " + parcel.getId());
            
            ship.simulateDelivery(c, parcel);
        }
    }
    
    private boolean verifyInputs(){
        if(sendforename == "" || sendsurename== ""|| sendcity== "" || sendstreet== "" || sendstreetNumber== "" || 
                sendiban== "" || sendpostalcode== "" || recvforename== "" || recvsurename== "" || 
                recvcity== "" || recvstreet== "" || recvstreetNumber== "" || recvpostalcode== "" ){
                return false;
        } else{
            return true;
        }
    }
}