/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa;

import it.univaq.disim.mwt.xml.pa.dom.Body;
import it.univaq.disim.mwt.xml.pa.dom.CedentePrestatore;
import it.univaq.disim.mwt.xml.pa.dom.DataFormatException;
import it.univaq.disim.mwt.xml.pa.dom.DatiAnagraficiVettore;
import it.univaq.disim.mwt.xml.pa.dom.Fattura;
import it.univaq.disim.mwt.xml.pa.dom.RegimeFiscale;
import it.univaq.disim.mwt.xml.pa.dom.Sede;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;
import javax.xml.stream.XMLStreamException;
/*
 * @author lucatraini
 */
public class Tester {
    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, XMLStreamException, DataFormatException {
        Fattura fattura = DOMManager.loadXML(new File("fattura.xml"));
        CedentePrestatore cedentePrestatore =fattura.getHeader().getCedentePrestatore();
        Sede sede = cedentePrestatore.getSede();
        sede.setComune("Giulianova");
        sede.setProvincia("TE");
        sede.setCap("6402");
        sede.setIndirizzo("Via Garibaldi 64");
        cedentePrestatore.getAnagraficiCedente().setRegimeFiscale(RegimeFiscale.RF12);
        
        Collection<Body>bodies = fattura.getBodies();
        
        for (Body body : bodies) {
            DatiAnagraficiVettore datiAnagraficiVettore = body.getDatiGenerali().getDatiTrasporto().getDatiAnagraficiVettore();
            datiAnagraficiVettore.getAnagrafica().setDenominazione("DHL s.p.a.");
            body.getDatiGenerali().getDatiTrasporto().setDataOraConsegna(new Date());
        }
        
        
        DOMManager.saveXML(fattura,  new File("test.xml"));
        
        
        fattura = DOMManager.loadXML(new File("test.xml"));
                        
    }
}
