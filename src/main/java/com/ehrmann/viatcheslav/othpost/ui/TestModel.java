/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.ui;


import com.ehrmann.viatcheslav.othpost.entity.service.ShippingService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Slav
 */
@Named
@SessionScoped
public class TestModel implements Serializable {
    @Inject
    private ShippingService ship;
    
    private String name = "test name";
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

