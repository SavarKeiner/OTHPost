/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.entity.service;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author Slav
 */
@RequestScoped
public class ShippingService {
    @PersistenceContext(unitName="com.ehrmann.viatcheslav_OTHPost_war_1.0-SNAPSHOTPU")
    private EntityManager em;
        
    
}
