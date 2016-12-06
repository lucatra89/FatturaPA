/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa.dom;

import it.univaq.disim.mwt.xml.pa.util.DateConverter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author lucatraini
 */
public class DatiGeneraliDocumento implements ElementoFattura{
    
    
    private DateConverter dateConverter;
    
    private TipoDocumento tipoDocumento;
    private Currency divisa;
    private Date data;
    private String numero;
    private Collection<String> causali;

    public DatiGeneraliDocumento() {
        this.dateConverter = new DateConverter();
        
        this.causali = new ArrayList<>();
        this.data = null;
        this.numero = null;
        this.divisa = null;
    }
    
    public DatiGeneraliDocumento(Element e) throws DataFormatException {
        this();
        loadXML(e);
    }

    public static String getTagName() {
        return "DatiGeneraliDocumento";
    }

    
    
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Currency getDivisa() {
        return divisa;
    }

    public void setDivisa(Currency divisa) {
        this.divisa = divisa;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) throws DataFormatException {
        if(!numero.matches("[0-9]{1,20}")){
            throw new DataFormatException("Il numero non ha un formato adatto");
        }
        this.numero = numero;
    }

    public Collection<String> getCausali() {
        return causali;
    }

    public void setCausali(Collection<String> causali) throws DataFormatException {
        for (String causale : causali) {
            if(causale.length()>200){
                throw new DataFormatException("La causale è troppo lunga");
            }
        }
        
        this.causali = causali;
    }
    
    public void addCausale(String causale) throws DataFormatException{
        if(causale.length()>200){
            throw new DataFormatException("La causale è troppo lunga");
        }
        this.causali.add(numero);
    }
    
    

    @Override
    public final void loadXML(Element from) throws DataFormatException {
        NodeList causaliNodeList = from.getElementsByTagName("Causale");
        
        this.tipoDocumento = TipoDocumento.valueOf(from.getElementsByTagName("TipoDocumento").item(0).getTextContent());
        
        this.data = this.dateConverter.parseDate(from.getElementsByTagName("Data").item(0).getTextContent());
        
        this.divisa = Currency.getInstance(from.getElementsByTagName("Divisa").item(0).getTextContent());
           
        this.numero = from.getElementsByTagName("Numero").item(0).getTextContent();
        for (int i = 0; i < causaliNodeList.getLength(); i++) {
            causali.add(causaliNodeList.item(i).getTextContent());
        }
        
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if (!performChecks()) {
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+ getTagName());
        }
        
        to.writeStartElement(getTagName());
        
        to.writeStartElement("TipoDocumento");
        to.writeCharacters(this.tipoDocumento.name());
        to.writeEndElement();

        to.writeStartElement("Divisa");
        to.writeCharacters(this.divisa.toString());
        to.writeEndElement();
        
        to.writeStartElement("Data");
        to.writeCharacters(this.dateConverter.formatDate(this.data));
        to.writeEndElement();        
        
        to.writeStartElement("Numero");
        to.writeCharacters(this.numero);
        to.writeEndElement();
        
        for (String causale : causali) {
            to.writeStartElement("Causale");
            to.writeCharacters(causale);
            to.writeEndElement();
        }

        to.writeEndElement();
    }

    @Override
    public final boolean performChecks() {
        return this.data!=null && this.divisa!=null && this.tipoDocumento!=null && this.numero!=null ;
    }
    
    
    
    
    
}
