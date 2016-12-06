/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa.dom;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Element;

/**
 *
 * @author lucatraini
 */
public class CessionarioCommittente implements ElementoFattura{

    private DatiAnagraficiCessionario datiAnagrafici;
    private Sede sede;
    
    public CessionarioCommittente() {
        this.datiAnagrafici = null;
        this.sede = null;
    }
    
    public CessionarioCommittente(Element e) throws DataFormatException {
        this();
        this.loadXML(e);
    }
    
    public static String getTagName() {
        return "CessionarioCommittente";
    }
    
    public DatiAnagraficiCessionario getDatiAnagrafici() {
        return datiAnagrafici;
    }

    public void setDatiAnagrafici(DatiAnagraficiCessionario datiAnagrafici) throws DataFormatException {
        if(datiAnagrafici == null || !datiAnagrafici.performChecks()){
            throw new DataFormatException("I dati anagrafici del cessionario committente non hanno un formato corretto");
        }
        this.datiAnagrafici = datiAnagrafici;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) throws DataFormatException {
        if(sede == null || !sede.performChecks()){
            throw new DataFormatException("La sede del cessionario committente non hanno un formato corretto");
        }
        this.sede = sede;
    }
    
    
    
    @Override
    public final void loadXML(Element from) throws DataFormatException {
        
        this.datiAnagrafici = new DatiAnagraficiCessionario((Element)from.getElementsByTagName("DatiAnagrafici").item(0));
        this.sede = new Sede((Element)from.getElementsByTagName("Sede").item(0));

    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if(!performChecks()){
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+ getTagName());
        }
        
        to.writeStartElement(getTagName());
        
        this.datiAnagrafici.saveXML(to);
        this.sede.saveXML(to);
        
        to.writeEndElement();
    }

    @Override
    public final boolean performChecks() {
        return this.datiAnagrafici != null && this.sede != null ;
    }
    
}
