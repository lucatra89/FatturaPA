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
public class CedentePrestatore implements ElementoFattura{
    
    private DatiAnagraficiCedente anagraficiCedente;
    private Sede sede;

    public CedentePrestatore() {
        this.anagraficiCedente = null;
        this.sede = null;
    }
    
    public CedentePrestatore(Element element) throws DataFormatException {
        this();
        this.loadXML(element);
    }

    public static String getTagName() {
        return "CedentePrestatore";
    }

    public DatiAnagraficiCedente getAnagraficiCedente() {
        return anagraficiCedente;
    }

    public void setAnagraficiCedente(DatiAnagraficiCedente anagraficiCedente) throws DataFormatException {
        if(anagraficiCedente==null || !anagraficiCedente.performChecks()){
            throw new DataFormatException("Il formato dei dati anagrafici del cedente non è corretto");
        }
        this.anagraficiCedente = anagraficiCedente;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) throws DataFormatException {
        if(sede==null || !sede.performChecks()){
            throw new DataFormatException("Il formato della sede non è corretto");
        }
        this.sede = sede;
    }
    
    @Override
    public final void loadXML(Element from) throws DataFormatException {
        this.anagraficiCedente = new DatiAnagraficiCedente((Element)from.getElementsByTagName("DatiAnagrafici").item(0));
        this.sede = new Sede((Element)from.getElementsByTagName("Sede").item(0));
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if(!performChecks()){
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+ getTagName());
        }
        
        to.writeStartElement(getTagName());
        this.anagraficiCedente.saveXML(to);
        this.sede.saveXML(to);
        to.writeEndElement();
        
    }

    @Override
    public boolean performChecks() {
        return (this.anagraficiCedente != null && this.sede != null );

    }
    
}
