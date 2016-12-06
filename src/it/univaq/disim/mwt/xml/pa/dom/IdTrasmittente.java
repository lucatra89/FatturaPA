/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa.dom;

import java.util.Locale;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Element;

/**
 *
 * @author lucatraini
 */
public class IdTrasmittente implements ElementoFattura{
    
    private Locale idPaese;
    private String IdCodice;

    public IdTrasmittente() {
        this.IdCodice = null;
        this.idPaese = null;
    }
    
    public IdTrasmittente(Element e) throws DataFormatException {
        this();
        loadXML(e);
    }

    public static String getTagName() {
        return "IdTrasmittente";
    }

    public Locale getIdPaese() {
        return idPaese;
    }

    public void setIdPaese(Locale idPaese) {
        this.idPaese = idPaese;
    }

    public String getIdCodice() {
        return IdCodice;
    }

    public void setIdCodice(String IdCodice) throws DataFormatException {
        if (IdCodice == null || IdCodice.length()>28){
            throw new DataFormatException("Il formato di IdCodice non è valido");
        }
        this.IdCodice = IdCodice;
    }

    @Override
    public final void loadXML(Element from) throws DataFormatException {
        this.idPaese = Locale.forLanguageTag(from.getElementsByTagName("IdPaese").item(0).getTextContent());
        this.IdCodice = from.getElementsByTagName("IdCodice").item(0).getTextContent();
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if (!performChecks()) {
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+ getTagName());
        }
        to.writeStartElement(getTagName());
        
        to.writeStartElement("IdPaese");
        to.writeCharacters(this.getIdPaese().toString().toUpperCase());
        to.writeEndElement();
        
        to.writeStartElement("IdCodice");
        to.writeCharacters(this.getIdCodice());
        to.writeEndElement();
        
        to.writeEndElement();        
    }

    @Override
    public final boolean performChecks() {
        return (this.getIdCodice() !=null && this.getIdPaese()!= null);
    }
    
    
}
