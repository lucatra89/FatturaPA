/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa.dom;

import it.univaq.disim.mwt.xml.pa.util.DateConverter;
import java.util.Date;
import java.util.Locale;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author lucatraini
 */
public class DettaglioPagamento implements ElementoFattura{
    private DateConverter converter;
    
    private ModalitaPagamento modalitaPagamento;
    private Date dataScadenzaPagamento;
    private Float importoPagamento;

    public DettaglioPagamento() {
        this.converter = new DateConverter();
        
        this.modalitaPagamento = null;
        this.dataScadenzaPagamento = null;
        this.importoPagamento = null;
    }
    
    public DettaglioPagamento(Element element) throws DataFormatException {
        this();
        loadXML(element);
    }
    
    public static String getTagName(){
        return "DettaglioPagamento";
    }

    public ModalitaPagamento getModalitaPagamento() {
        return modalitaPagamento;
    }

    public void setModalitaPagamento(ModalitaPagamento modalitaPagamento) throws DataFormatException {
        if(modalitaPagamento==null){
            throw new DataFormatException("La modalita pagamento non può essere nulla");
        }
        this.modalitaPagamento = modalitaPagamento;
    }

    public Date getDataScadenzaPagamento() {
        return dataScadenzaPagamento;
    }

    public void setDataScadenzaPagamento(Date dataScadenzaPagamento) {
        this.dataScadenzaPagamento = dataScadenzaPagamento;
    }


    public Float getImportoPagamento() {
        return importoPagamento;
    }

    public void setImportoPagamento(Float importoPagamento) throws DataFormatException {
        if(importoPagamento==null || importoPagamento<0){
            throw new DataFormatException("L'importo del pagamento non è nel formato corretto");
        }
        this.importoPagamento = importoPagamento;
    }
    
    

    @Override
    public final void loadXML(Element from) throws DataFormatException {
        NodeList dataNodeList = from.getElementsByTagName("DataScadenzaPagamento");
        
        this.modalitaPagamento = ModalitaPagamento.valueOf( from.getElementsByTagName("ModalitaPagamento").item(0).getTextContent());
        this.importoPagamento = Float.parseFloat(from.getElementsByTagName("ImportoPagamento").item(0).getTextContent());
        
        
        if(dataNodeList.getLength() == 1){
            this.dataScadenzaPagamento = this.converter.parseDate(dataNodeList.item(0).getTextContent());
        }
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if(!performChecks()){
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+  getTagName());
        }
        
        to.writeStartElement(getTagName());
        
        to.writeStartElement("ModalitaPagamento");
        to.writeCharacters(this.modalitaPagamento.name());
        to.writeEndElement();
        
        if(this.importoPagamento != null){
            to.writeStartElement("DataScadenzaPagamento");
            to.writeCharacters(this.converter.formatDate(this.dataScadenzaPagamento));
            to.writeEndElement();
        }
        
        to.writeStartElement("ImportoPagamento");
        to.writeCharacters(String.format(Locale.US,"%.2f",this.importoPagamento));
        to.writeEndElement();
        
        to.writeEndElement();
        
    }

    @Override
    public final boolean performChecks() {
        return this.modalitaPagamento!=null && this.importoPagamento != null;
    }
    
}
