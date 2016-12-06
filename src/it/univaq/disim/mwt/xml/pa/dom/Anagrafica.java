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
public class Anagrafica implements ElementoFattura{
    
    private String denominazione;

    public Anagrafica() {
         
        this.denominazione = null;
    }
    
    public Anagrafica(Element element) throws DataFormatException {
        this();
        this.loadXML(element);
    }

    public static String getTagName() {
        return "Anagrafica";
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) throws DataFormatException {
        if(denominazione!=null && (denominazione.length() > 80)){
            throw new DataFormatException("Anagrafica: Denominazione troppo lunga");
        }
        this.denominazione = denominazione;
    }
    
    @Override
    public final void loadXML(Element from) throws DataFormatException {
        NodeList deNodeList = from.getElementsByTagName("Denominazione");
        if(deNodeList.getLength() == 1){
            this.denominazione = deNodeList.item(0).getTextContent();
        }
    }

    @Override
    public  final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if(!performChecks()){
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+ getTagName());
        }else{
            to.writeStartElement(getTagName());
            
            if(this.denominazione != null){
                to.writeStartElement("Denominazione");
                to.writeCharacters(this.denominazione);
                to.writeEndElement();
            }
            to.writeEndElement();
        }
    }

    @Override
    public boolean performChecks() {
        return true;
    }
    
}
