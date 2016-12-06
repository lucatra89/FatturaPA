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
public class ContattiTrasmittente implements ElementoFattura{
    private String telefono;
    private String email;

    public ContattiTrasmittente() {

        this.email = null;
        this.telefono = null;
    }

    ContattiTrasmittente(Element element) throws DataFormatException {
        this();
        loadXML(element);
    }

    public static String getTagName() {
        return "ContattiTrasmittente";   

    }

    
    
    public String getTelefono() {
    
        return telefono;
    }

    public void setTelefono(String telefono) throws DataFormatException {
        if(telefono!= null && telefono.length()>12){
            throw new DataFormatException("Il numero di telefono è troppo lungo");
        }
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws DataFormatException {
        if(email!= null && email.length()>256){
            throw new DataFormatException("L'email è troppo lunga");
        }
        this.email = email;
    }

    @Override
    public final void loadXML(Element from) throws DataFormatException {
        NodeList telefonoNodelist = from.getElementsByTagName("Telefono");
        NodeList emailNodelist = from.getElementsByTagName("Email");
        if(emailNodelist.getLength() == 1){
            this.setEmail(emailNodelist.item(0).getTextContent());
        }
        if(telefonoNodelist.getLength() == 1){
            this.setTelefono(telefonoNodelist.item(0).getTextContent());
        }
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if (!performChecks()) {
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+ getTagName());
        }
        to.writeStartElement(getTagName());
        if(this.getTelefono() != null){
            to.writeStartElement("Telefono");
            to.writeCharacters(this.getTelefono());
            to.writeEndElement();
        }
        if(this.getEmail()!= null){
            to.writeStartElement("Email");
            to.writeCharacters(this.getEmail());
            to.writeEndElement();
        }
                
        to.writeEndElement();
    }

    @Override
    public final boolean performChecks() {
        return true;

    }
    
}
