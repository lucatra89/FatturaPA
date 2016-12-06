/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa.dom;

import it.univaq.disim.mwt.xml.pa.util.DateConverter;
import java.util.Date;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author lucatraini
 */
public class DatiTrasporto implements ElementoFattura{
    
    private DateConverter dateConverter;
    private DatiAnagraficiVettore datiAnagraficiVettore;
    private Date dataOraConsegna;

    public static String getTagName(){
        return "DatiTrasporto";
    }

    public DatiTrasporto() {
        this.dateConverter = new DateConverter();
        this.dataOraConsegna = null;
        this.datiAnagraficiVettore = null;
    }
    
    public DatiTrasporto(Element element) throws DataFormatException{
        this();
        loadXML(element);
    }

    public DatiAnagraficiVettore getDatiAnagraficiVettore() {
        return datiAnagraficiVettore;
    }

    public void setDatiAnagraficiVettore(DatiAnagraficiVettore datiAnagraficiVettore) throws DataFormatException {
        if(datiAnagraficiVettore != null && !this.datiAnagraficiVettore.performChecks()){
            throw new DataFormatException("I dati anagrafici del vettore non sono nel formato corretto");
        }
        this.datiAnagraficiVettore = datiAnagraficiVettore;
    }

    public Date getDataOraConsegna() {
        return dataOraConsegna;
    }

    public void setDataOraConsegna(Date dataOraConsegna) {
        this.dataOraConsegna = dataOraConsegna;
    }
    
    @Override
    public final void loadXML(Element from) throws DataFormatException {
        NodeList datiAnaNodeList = from.getElementsByTagName(DatiAnagraficiVettore.getTagName());
        if(datiAnaNodeList.getLength()==1){
            this.datiAnagraficiVettore = new DatiAnagraficiVettore((Element)datiAnaNodeList.item(0));
        }
        NodeList dataConsegnaNodeList = from.getElementsByTagName("DataOraConsegna");
        
        if(dataConsegnaNodeList.getLength() == 1){
            this.dataOraConsegna = this.dateConverter.parseDateTime(dataConsegnaNodeList.item(0).getTextContent());
        }
        
    }

    @Override
    public void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if(!this.performChecks()){
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+  getTagName());
        }
        
        to.writeStartElement(getTagName());
        
        if(this.datiAnagraficiVettore!= null){
            this.datiAnagraficiVettore.saveXML(to);
        }
        
        if(this.dataOraConsegna!= null){
            to.writeStartElement("DataOraConsegna");
            to.writeCharacters(this.dateConverter.formatDateTime(this.dataOraConsegna));
            to.writeEndElement();
        }
        to.writeEndElement();
    }

    @Override
    public boolean performChecks() {
        return true;
    }
    
}
