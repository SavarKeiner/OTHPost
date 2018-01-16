/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.service;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Slav
 */
public class TrackingRemoteResponse implements Serializable {
    public String trackingNumber;
    public List<String> statusList;
}
