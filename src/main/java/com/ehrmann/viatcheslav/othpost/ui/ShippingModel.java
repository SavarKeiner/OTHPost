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
import com.ehrmann.viatcheslav.othpost.service.ShippingServiceIF;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceRef;
import services.TransaktionServiceService;

/**
 *
 * @author Slav
 */
@Named
@RequestScoped
public class ShippingModel implements Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/im-lamport_8080/huberbank-1.0-SNAPSHOT/TransaktionService.wsdl")
    private TransaktionServiceService service;
    @Inject
    private ShippingServiceIF ship;

    @Inject
    @Loggers(LoggerType.Web)
    private LoggerIF weblogger;
        
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
    
    private String bankStatus = "";

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
    
    public String getBankStatus() {
        return bankStatus;
    }

    public void setBankStatus(String bankStatus) {
        this.bankStatus = bankStatus;
    }
    
    public String createOrder(){
        if(verifyInputs()){
            float price = 0;
            boolean didBankFail = false;
            
            Customer c = ship.isCustomerExsiting(sendforename, sendsurename, 
                    sendcity, sendstreet, sendstreetNumber, sendiban, Integer.parseInt(sendpostalcode));
            
            if(c == null){
                c = ship.createCustomer(sendforename, sendsurename, 
                    sendcity, sendstreet, sendstreetNumber, sendiban, Integer.parseInt(sendpostalcode));
            }
            
            if(typeParcel == ParcelType.Brief)
                price = 1.0f;
            else if(typeParcel == ParcelType.Packet)
                price = 2.0f;
            else if(typeParcel == ParcelType.Palette)
                price = 3.0f;
            
            Invoice invoice = ship.createInvoice(typeParcel, price);
            try { //createInvoice
                services.TransaktionService port = service.getTransaktionServicePort();
                services.Konto receiverAccount = new services.Konto();
                receiverAccount.setId((long)202);
                services.Konto senderAccount = new services.Konto();
                senderAccount.setId(Long.parseLong(this.getSendiban()));
                int cent = 0;
                
                // TODO initialize WS operation arguments here
                services.Transaktion transaktion = new services.Transaktion();
                transaktion.setEuro((int)price);
                transaktion.setCent(cent);
                transaktion.setSender(senderAccount);
                transaktion.setEmpfaenger(receiverAccount);
                
                port.ueberweise(transaktion);
            } catch (services.TransaktionFailedException_Exception ex) {
                this.setBankStatus("Die Kontonummer ist der Huber Bank nicht bekannt");
            } catch (Exception e){
                this.setBankStatus("Die Huber Bank ist zurzeit nicht erreichbar");
            }
            
            Parcel parcel = ship.receiveParcel(recvforename, recvsurename, recvcity, recvstreet, recvstreetNumber, Integer.parseInt(sendpostalcode), invoice);
            Tracking tracking = ship.createTrackingEntry(parcel);            
           
            
            

            
            
            
            setParcelTrackingNumber(tracking.getTrackingNumber());
            ship.shipParcel(c, parcel, tracking);
              
            weblogger.logOrder("order created through website with id: " + parcel.getId());
            
            ship.simulateDelivery(c, parcel);
        }
        return "order.xhtml";
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