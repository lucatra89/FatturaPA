/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa.dom;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author lucatraini
 */
public interface ElementoFattura {
    

    void loadXML(org.w3c.dom.Element from) throws DataFormatException;

    void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException;
    
    boolean performChecks();
}
