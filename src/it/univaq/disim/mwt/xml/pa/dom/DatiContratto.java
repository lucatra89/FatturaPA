/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa.dom;

import java.util.ArrayList;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Element;

/**
 *
 * @author lucatraini
 */
public class DatiContratto extends DatiOrdineAbstract{

    public DatiContratto() {
        super();
    }

    DatiContratto(Element element) throws DataFormatException {
        super(element);
    }
    
    public static String getTagName(){
        return "DatiContratto";
    }

    @Override
    public void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if (!performChecks()) {
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+  getTagName());
        }

        to.writeStartElement(getTagName());

        this.saveBody(to);
            
        to.writeEndElement();

    
    }
    
    
    

    
    
}
