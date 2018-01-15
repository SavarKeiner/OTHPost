/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.ui;

import com.ehrmann.viatcheslav.othpost.entity.Invoice.ParcelType;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Slav
 */
@FacesConverter("selectType")
public class ParcelTypeConverter implements Converter {
    
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {                
        if(value==null)
            return "";
        
        if("Brief".equals(value))
            return ParcelType.Brief;
        else if ("Packet".equals(value))
            return ParcelType.Packet;    
        else if ("Palette".equals(value))
            return ParcelType.Palette;
        else
            return "";
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null)
            return null;
        else if(((ParcelType)value).toString().equals("Brief"))
            return "Brief";
        else if(((ParcelType)value).toString().equals("Packet"))
            return "Packet";
        else if(((ParcelType)value).toString().equals("Palette"))
            return "Palette";
        
        return "";
    }
    
}