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
import org.w3c.dom.NodeList;

/**
 *
 * @author lucatraini
 */
public class Sede implements ElementoFattura{
    
    
    private String indirizzo;
    private String numeroCivico;
    private String cap;
    private String comune;
    private String provincia;
    private Locale nazione;

    public Sede() {
        
        this.indirizzo = null;
        this.numeroCivico = null;
        this.cap = null;
        this.comune = null;
        this.provincia = null;
        this.nazione = null;
    }

    public Sede(Element e) throws DataFormatException {
        this();
        loadXML(e);
    }

    public static String getTagName() {
        return "Sede";
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) throws DataFormatException {
        if(indirizzo == null || indirizzo.length()>60){
            throw new DataFormatException("L'indirizzo non ha un formato valido");
        }
        this.indirizzo = indirizzo;
    }

    public String getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(String numeroCivico) throws DataFormatException {
        if(numeroCivico !=null && numeroCivico.length()>8){
            throw new DataFormatException("Il numero civico non ha un formato valido");
        }
        this.numeroCivico = numeroCivico;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) throws DataFormatException {
        if(cap == null || cap.length()!=5){
            throw new DataFormatException("Il CAP non ha un formato valido");
        }
        this.cap = cap;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) throws DataFormatException {
        if(comune == null || comune.length()>60){
            throw new DataFormatException("Il comune non ha un formato valido");
        }
        this.comune = comune;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) throws DataFormatException {
        if(provincia != null && provincia.length()!=2){
            throw new DataFormatException("La provincia non ha un formato valido");
        }
        this.provincia = provincia;
    }

    public Locale getNazione() {
        return nazione;
    }

    public void setNazione(Locale nazione) throws DataFormatException {
        if(nazione != null ){
            throw new DataFormatException("La nazione non ha un formato valido");
        }
        this.nazione = nazione;
    }
    
    @Override
    public final void loadXML(Element from) throws DataFormatException {
        NodeList numCivNodeList = from.getElementsByTagName("NumeroCivico");
        NodeList provNodeList = from.getElementsByTagName("Provincia");
        
        this.indirizzo = from.getElementsByTagName("Indirizzo").item(0).getTextContent();
        this.comune = from.getElementsByTagName("Comune").item(0).getTextContent();
        this.cap = from.getElementsByTagName("CAP").item(0).getTextContent();
        this.nazione = Locale.forLanguageTag(from.getElementsByTagName("Nazione").item(0).getTextContent());
        
        if(numCivNodeList.getLength() == 1){
            this.numeroCivico = numCivNodeList.item(0).getTextContent();
        }
        
        if(provNodeList.getLength() == 1){
            this.provincia = provNodeList.item(0).getTextContent();
        }
        
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if (!performChecks()) {
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+ getTagName());
        }
        
        to.writeStartElement(getTagName());
        
        to.writeStartElement("Indirizzo");
        to.writeCharacters(this.indirizzo);
        to.writeEndElement();
        
        if(this.numeroCivico!=null){
            to.writeStartElement("NumeroCivico");
            to.writeCharacters(this.numeroCivico);
            to.writeEndElement();
        }
        
        to.writeStartElement("CAP");
        to.writeCharacters(this.cap);
        to.writeEndElement();
        
        to.writeStartElement("Comune");
        to.writeCharacters(this.comune);
        to.writeEndElement();
        
        if(this.provincia!=null){
            to.writeStartElement("Provincia");
            to.writeCharacters(this.provincia);
            to.writeEndElement();
        }

        to.writeStartElement("Nazione");
        to.writeCharacters(this.nazione.toString().toUpperCase());
        to.writeEndElement();
        
        to.writeEndElement();
    }

    @Override
    public final boolean performChecks() {
        return this.indirizzo!= null && this.comune !=null && this.nazione != null && this.cap !=null;
    }
    
}
