/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehrmann.viatcheslav.othpost.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Slav
 */
@MappedSuperclass
public class LongIdEntity implements Serializable{
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected long id;

    public long getId() {
        return this.id;
    }


}
