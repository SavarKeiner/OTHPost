/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.service;

import com.ehrmann.viatcheslav.othpost.entity.Customer;
import javax.ejb.Remote;

/**
 *
 * @author Viatcheslav Ehrmann
 */
@Remote
public interface ShippingServiceIF {
    public boolean shipRemote(Customer sender, Customer receiver);
}
