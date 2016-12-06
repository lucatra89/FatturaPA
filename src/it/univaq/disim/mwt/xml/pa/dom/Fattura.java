/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa.dom;

import java.util.ArrayList;
import java.util.Collection;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author lucatraini
 */
public class Fattura implements ElementoFattura{
   
    
    private Header header;
    private Collection<Body> bodies;
    
    public Fattura() {
        this.header = null;
        this.bodies = new ArrayList<>();
    }
    public Fattura(Element element) throws DataFormatException {
        this();
        this.loadXML(element);
    }
    public Header getHeader() {
        return header;
    }
    

    public void setHeader(Header header) throws DataFormatException {
        if(header==null || !header.performChecks()){
            throw new DataFormatException("L'header non è nel formato corretto");
        }
        this.header = header;
    }

    public Collection<Body> getBodies() {
        return bodies;
    }
    

    public void setBodies(Collection<Body> bodies) throws DataFormatException {

        if(bodies==null || !this.checkBodies(bodies)){
            throw new DataFormatException("I body non sono nel formato corretto");
        }
        this.bodies = bodies;
    }
    
    private boolean checkBodies(Collection<Body> bodies){
        for (Body body : bodies) {
            if(!body.performChecks()){
                return false;
            }
        }
        return true;
    }
    

    public static String getTagName() {
        return "FatturaElettronica";
    }
    
    @Override
    public final void loadXML(Element from) throws DataFormatException {
        Element headerEl  = (Element) from.getElementsByTagName(Header.getTagName()).item(0);
        NodeList bodyNodeList  =  from.getElementsByTagName(Body.getTagName());
         this.header = new Header(headerEl);
         
        for (int i = 0; i < bodyNodeList.getLength(); i++) {
            this.bodies.add(new Body((Element)bodyNodeList.item(i)));
        }
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if (!performChecks()) {
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+ Fattura.getTagName());
        }
        
        to.writeStartElement("p", Fattura.getTagName(), "http://www.fatturapa.gov.it/sdi/fatturapa/v1.1");
        to.writeNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");
        to.writeNamespace("p","http://www.fatturapa.gov.it/sdi/fatturapa/v1.1");
        to.writeNamespace("ds","http://www.w3.org/2000/09/xmldsig#");
        to.writeAttribute("versione", "1.1");
        
        this.header.saveXML(to);
        for (Body body : this.bodies) {
            body.saveXML(to);
        }

        to.writeEndElement();
        }

    @Override
    public boolean performChecks() {
        return (this.bodies != null && this.header != null && this.bodies.size()>0);

    }


    
 }
    
    
 
