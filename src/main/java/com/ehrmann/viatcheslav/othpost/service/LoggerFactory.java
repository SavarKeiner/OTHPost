/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.service;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

/**
 *
 * @author Slav
 */
@RequestScoped
public class LoggerFactory implements Serializable {
    private LoggerType loggerType;
    
    @Produces
    @Loggers(LoggerType.Web)
    public WebLogger getWebLogger(){
        return new WebLogger();
    }
    
    @Produces
    @Loggers(LoggerType.Remote)
    public RemoteLogger getRemoteLogger(){
        return new RemoteLogger();
    }
}
