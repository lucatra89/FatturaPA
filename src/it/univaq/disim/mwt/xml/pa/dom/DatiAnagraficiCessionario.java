/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa.dom;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author lucatraini
 */
public class DatiAnagraficiCessionario implements ElementoFattura{
    

    private String codiceFiscale;
    
    private Anagrafica anagrafica;

    public DatiAnagraficiCessionario() {
        this.anagrafica = null;
        this.codiceFiscale = null;
    }
    
    public DatiAnagraficiCessionario(Element e) throws DataFormatException {
        this();
        loadXML(e);
    }

    public static String getTagName() {
        return "DatiAnagrafici";
    }

    
    
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) throws DataFormatException {
        if(codiceFiscale!=null && (codiceFiscale.length()<11 || codiceFiscale.length() > 16)){
            throw new DataFormatException("Il codice fiscale non ha una lunghezza valida");
        }
        
        this.codiceFiscale = codiceFiscale;
    }

    public Anagrafica getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(Anagrafica anagrafica) throws DataFormatException {
        if(!anagrafica.performChecks()){
            throw new DataFormatException("L'anagrafica non ha un formato valido");
        }
        this.anagrafica = anagrafica;
    }
    
    
    
    @Override
    public final void loadXML(Element from) throws DataFormatException {
        NodeList CFNodeList = from.getElementsByTagName("CodiceFiscale");
        if(CFNodeList.getLength() == 1){
            this.codiceFiscale = CFNodeList.item(0).getTextContent();
        }
        this.anagrafica = new Anagrafica((Element)from.getElementsByTagName("Anagrafica").item(0));
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if(!performChecks()){
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+ getTagName());
        }
        
        to.writeStartElement(getTagName());
        
        if(this.codiceFiscale!=null){
            to.writeStartElement("CodiceFiscale");
            to.writeCharacters(this.codiceFiscale);
            to.writeEndElement();
        }
        
        this.anagrafica.saveXML(to);
        
        to.writeEndElement();
    }

    @Override
    public final boolean performChecks() {
        return this.anagrafica!=null ;
    }
    
    
}
