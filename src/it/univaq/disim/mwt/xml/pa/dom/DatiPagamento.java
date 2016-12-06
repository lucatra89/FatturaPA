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
public class DatiPagamento implements ElementoFattura{
    
    private CondizioniPagamento condizioniPagamento;
    private Collection<DettaglioPagamento> dettaglioPagamenti;
    
    public DatiPagamento() {
        this.condizioniPagamento = null;
        this.dettaglioPagamenti = new ArrayList<>();
    }
    
    DatiPagamento(Element element) throws DataFormatException {
        this();
        this.loadXML(element);
    }
    
    public static String getTagName() {
        return "DatiPagamento";
    }


    public CondizioniPagamento getCondizioniPagamento() {
        return condizioniPagamento;
    }

    public void setCondizioniPagamento(CondizioniPagamento condizioniPagamento) throws DataFormatException {
        if(condizioniPagamento==null){
            throw new DataFormatException("La condizione di pagamento non può essere nulla");
        }

        this.condizioniPagamento = condizioniPagamento;
    }

    public Collection<DettaglioPagamento> getDettaglioPagamenti() {
        return dettaglioPagamenti;
    }

    public void setDettaglioPagamenti(Collection<DettaglioPagamento> dettaglioPagamenti) throws DataFormatException {
        if(this.dettaglioPagamenti==null || this.dettaglioPagamenti.size()>0 || !checkDettagliPagamento(dettaglioPagamenti)){
            throw new DataFormatException("I dettagli di pagamento non sono nel formato corretto");
        }
        this.dettaglioPagamenti = dettaglioPagamenti;
    }
    
    public void addDettaglioPagamento(DettaglioPagamento dettaglioPagamento)throws DataFormatException{
        if(dettaglioPagamento == null || !dettaglioPagamento.performChecks()){
            throw new DataFormatException("Il dettaglio di pagamento non è nel formato corretto");
        }
        
        this.dettaglioPagamenti.add(dettaglioPagamento);
    }

    
    
    @Override
    public final void loadXML(Element from) throws DataFormatException {
        NodeList pagamentiNodeList = from.getElementsByTagName("DettaglioPagamento");
        this.condizioniPagamento = CondizioniPagamento.valueOf(from.getElementsByTagName("CondizioniPagamento").item(0).getTextContent());
        
        for (int i = 0; i < pagamentiNodeList.getLength(); i++) {
            this.dettaglioPagamenti.add(new DettaglioPagamento((Element)pagamentiNodeList.item(i)));
        }
        
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if(!performChecks()){
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+  getTagName());
        }
        
        to.writeStartElement(getTagName());
        
        to.writeStartElement("CondizioniPagamento");
        to.writeCharacters(this.condizioniPagamento.name());
        to.writeEndElement();
        
        for (DettaglioPagamento dettaglioPagamento : dettaglioPagamenti) {
            dettaglioPagamento.saveXML(to);
        }
        
        to.writeEndElement();
    }

    @Override
    public final boolean performChecks() {
        return this.condizioniPagamento!= null && this.dettaglioPagamenti!=null && this.dettaglioPagamenti.size()>0;
    }

    private boolean checkDettagliPagamento(Collection<DettaglioPagamento> dettaglioPagamenti) {
        for (DettaglioPagamento dettaglioPagamento : dettaglioPagamenti) {
            if(!dettaglioPagamento.performChecks()){
                return false;
            }
        }
        return true;
    }
    
}
