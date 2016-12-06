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
public class DatiAnagraficiVettore implements ElementoFattura{

    private IdFiscaleIVA idFiscaleIVA;
    private Anagrafica anagrafica;

    public DatiAnagraficiVettore() {
    }
    
    public DatiAnagraficiVettore(Element e) throws DataFormatException {
        this();
        loadXML(e);
    }
    
    public static String getTagName(){
        return "DatiAnagraficiVettore";
    }

    public IdFiscaleIVA getIdFiscaleIVA() {
        return idFiscaleIVA;
    }

    public void setIdFiscaleIVA(IdFiscaleIVA idFiscaleIVA) throws DataFormatException {
        if(idFiscaleIVA == null || !idFiscaleIVA.performChecks()){
            throw new DataFormatException("L'id Fiscale non è nel formato corretto");
        }
        this.idFiscaleIVA = idFiscaleIVA;
    }

    public Anagrafica getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(Anagrafica anagrafica) throws DataFormatException {
        if(anagrafica == null || !anagrafica.performChecks()){
            throw new DataFormatException("L'anagrafica non è nel formato corretto");
        }
        this.anagrafica = anagrafica;
    }
    
    
    @Override
    public final void loadXML(Element from) throws DataFormatException {
        this.anagrafica = new Anagrafica((Element)from.getElementsByTagName(Anagrafica.getTagName()).item(0));
        this.idFiscaleIVA = new IdFiscaleIVA((Element)from.getElementsByTagName(IdFiscaleIVA.getTagName()).item(0));
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if (!performChecks()) {
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+  getTagName());
        }
        
        to.writeStartElement(getTagName());
        
        this.idFiscaleIVA.saveXML(to);
        this.anagrafica.saveXML(to);
        
        to.writeEndElement();
    }

    @Override
    public final boolean performChecks() {
        return this.idFiscaleIVA!=null && this.anagrafica!=null ;
    }
    
}
