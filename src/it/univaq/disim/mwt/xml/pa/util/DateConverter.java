/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lucatraini
 */
public class DateConverter {
    private final SimpleDateFormat dateParser;
    private final SimpleDateFormat dateTimeParser;

    public DateConverter() {
        this.dateParser = new SimpleDateFormat("yyyy-MM-dd");
        this.dateTimeParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    }
    
    public Date parseDate(String text){
        try {
            return this.dateParser.parse(text);
        } catch (ParseException ex) {
            Logger.getLogger(DateConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String formatDate(Date date){
        return this.dateParser.format(date);
    }
    
    
    public Date parseDateTime(String text){
        try {
            return this.dateTimeParser.parse(text);
        } catch (ParseException ex) {
            Logger.getLogger(DateConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String formatDateTime(Date date){
        return this.dateTimeParser.format(date);
    }
    
    
    
}
