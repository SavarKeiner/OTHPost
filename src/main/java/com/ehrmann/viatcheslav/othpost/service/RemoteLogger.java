/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.service;

/**
 *
 * @author Slav
 */
public class RemoteLogger implements LoggerIF {
    @Override
    public void logOrder(String log) {
        //write to logfile
        System.out.println(log);
    }
}
