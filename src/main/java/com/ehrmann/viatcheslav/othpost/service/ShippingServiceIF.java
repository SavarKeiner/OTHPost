/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.service;

import com.ehrmann.viatcheslav.othpost.entity.Customer;
import com.ehrmann.viatcheslav.othpost.entity.Invoice;

/**
 *
 * @author Viatcheslav Ehrmann
 */
public interface ShippingServiceIF {
    public TrackingRemoteResponse shipRemote(Customer sender, Customer receiver, Invoice.ParcelType type);
}
