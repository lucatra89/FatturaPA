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
public class IdFiscaleIVA implements ElementoFattura{

    private Locale idPaese;
    private String idCodice;

    public IdFiscaleIVA() {
    }
    
    public IdFiscaleIVA(Element e) throws DataFormatException {
        this();
        loadXML(e);
    }

    public static String getTagName() {
        return "IdFiscaleIVA";
    }

    public Locale getIdPaese() {
        return idPaese;
    }

    public void setIdPaese(Locale idPaese) {
        this.idPaese = idPaese;
    }

    public String getIdCodice() {
        return idCodice;
    }

    public void setIdCodice(String idCodice) throws DataFormatException {
        if(idCodice == null){
            throw new DataFormatException("L'IdCodice non può essere nullo");
        }
        this.idCodice = idCodice;
    }
    
    

    @Override
    public final void loadXML(Element from) throws DataFormatException {
        this.idCodice = from.getElementsByTagName("IdCodice").item(0).getTextContent();
        this.idPaese = Locale.forLanguageTag(from.getElementsByTagName("IdPaese").item(0).getTextContent());
        
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if (!performChecks()) {
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+ getTagName());
        }
        to.writeStartElement(getTagName());
        
        to.writeStartElement("IdPaese");
        to.writeCharacters(this.idPaese.toString().toUpperCase());
        to.writeEndElement();
        
        to.writeStartElement("IdCodice");
        to.writeCharacters(this.idCodice);
        to.writeEndElement();
        
        to.writeEndElement();
    }

    @Override
    public final boolean performChecks() {
        return (this.idCodice != null && this.idPaese != null);
    }
    
}
