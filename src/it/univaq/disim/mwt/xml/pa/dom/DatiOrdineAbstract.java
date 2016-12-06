/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa.dom;

import it.univaq.disim.mwt.xml.pa.util.DateConverter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author lucatraini
 */
public abstract class DatiOrdineAbstract implements ElementoFattura{
    
    private DateConverter dateConverter;
            
    private Collection<Integer> riferimentiNumeroLinea;
    private String idDocumento;
    private String numItem;
    private String codiceCUP;
    private String codiceCIG;
    private Date data;

    public DatiOrdineAbstract() {
        this.dateConverter= new DateConverter();
        
        this.riferimentiNumeroLinea = new ArrayList<>();
        this.idDocumento = null;
        this.numItem = null;
        this.codiceCIG = null;
        this.codiceCUP = null;
        this.data = null;
        
    }

    public DatiOrdineAbstract(Element element) throws DataFormatException {
        this();
        this.loadXML(element);
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    public Collection<Integer> getRiferimentiNumeroLinea() {
        return riferimentiNumeroLinea;
    }

    public void setRiferimentiNumeroLinea(Collection<Integer> riferimentiNumeroLinea) throws DataFormatException {
        if(riferimentiNumeroLinea == null || riferimentiNumeroLinea.size()>4 || riferimentiNumeroLinea.size()==0){
            throw new DataFormatException("I riferimentiNumeroLinea non sono nel formato corretto");
        }
        this.riferimentiNumeroLinea = riferimentiNumeroLinea;
    }

    public String getIdDocumento() {
        
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) throws DataFormatException {
        if(idDocumento== null || idDocumento.length() > 20){
            throw new DataFormatException("L'idDocumento non è nel formato corretto");
        }
        this.idDocumento = idDocumento;
    }

    public String getNumItem() {
        return numItem;
    }

    public void setNumItem(String numItem) throws DataFormatException {
        if(numItem!= null && numItem.length() > 20){
            throw new DataFormatException("Il numItem non è nel formato corretto");
        }
        this.numItem = numItem;
    }

    public String getCodiceCUP() {
        return codiceCUP;
    }

    public void setCodiceCUP(String codiceCUP) throws DataFormatException {
        if(codiceCUP!= null && codiceCUP.length() > 15){
            throw new DataFormatException("Il codiceCUP non è nel formato corretto");
        }
        this.codiceCUP = codiceCUP;
    }

    public String getCodiceCIG() {
        return codiceCIG;
    }

    public void setCodiceCIG(String codiceCIG) throws DataFormatException {
        if(codiceCIG!= null && codiceCIG.length() > 15){
            throw new DataFormatException("Il codiceCIG non è nel formato corretto");
        }
        
        this.codiceCIG = codiceCIG;
    }
    

    @Override
    public final void loadXML(Element from) throws DataFormatException {
        NodeList rnlNodeList = from.getElementsByTagName("RiferimentoNumeroLinea");
        NodeList numItemNodeList = from.getElementsByTagName("NumItem");
        NodeList codiceCUPNodeList = from.getElementsByTagName("CodiceCUP");
        NodeList codiceCIGNodeList = from.getElementsByTagName("CodiceCIG");
        NodeList dataNodeList = from.getElementsByTagName("Data");
        
        for (int i = 0; i < rnlNodeList.getLength(); i++) {
            this.riferimentiNumeroLinea.add(Integer.parseInt(rnlNodeList.item(0).getTextContent()));
        }
        this.idDocumento = from.getElementsByTagName("IdDocumento").item(0).getTextContent();
        
        if(numItemNodeList.getLength()==1){
            this.numItem = numItemNodeList.item(0).getTextContent();
        }
        
        if(codiceCUPNodeList.getLength()==1){
            this.codiceCUP = codiceCUPNodeList.item(0).getTextContent();
        }
        
        if(codiceCIGNodeList.getLength()==1){
            this.codiceCIG = codiceCIGNodeList.item(0).getTextContent();
        }
        
        if(dataNodeList.getLength()==1){
            this.data = this.dateConverter.parseDate(dataNodeList.item(0).getTextContent());
        }
        
        
    }

    
    protected void saveBody(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        for (Integer riferimentoNumeroLinea : riferimentiNumeroLinea) {
            to.writeStartElement("RiferimentoNumeroLinea");
            to.writeCharacters(riferimentoNumeroLinea.toString());
            to.writeEndElement();
        }

        to.writeStartElement("IdDocumento");
        to.writeCharacters(this.idDocumento);
        to.writeEndElement();
        
        if(this.data!=null){
            to.writeStartElement("Data");
            to.writeCharacters(this.dateConverter.formatDate(this.data));
            to.writeEndElement();
        }

        if(this.numItem!=null){
            to.writeStartElement("NumItem");
            to.writeCharacters(this.numItem);
            to.writeEndElement();
        }

        if(this.codiceCUP!=null){
            to.writeStartElement("CodiceCUP");
            to.writeCharacters(this.codiceCUP);
            to.writeEndElement();
        }

        if(this.codiceCIG!=null){
            to.writeStartElement("CodiceCIG");
            to.writeCharacters(this.codiceCIG);
            to.writeEndElement();
        }
    }

    @Override
    public boolean performChecks() {
        return this.riferimentiNumeroLinea!=null && this.riferimentiNumeroLinea.size()<4 && this.riferimentiNumeroLinea.size()>0 && this.idDocumento!=null;
    }
    
    
    
}
