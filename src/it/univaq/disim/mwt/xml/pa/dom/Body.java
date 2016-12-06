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
public class Body implements ElementoFattura{
    
    
    private DatiGenerali datiGenerali;
    private DatiBeniServizi datiBeniServizi;
    private Collection<DatiPagamento> datiPagamenti;
    

    public Body() {
        this.datiGenerali = null;
        this.datiBeniServizi = null;
        this.datiPagamenti = new ArrayList<>();
    }
    
    public Body(Element body) throws DataFormatException {
        this();
        loadXML(body);
    }

    public DatiGenerali getDatiGenerali() {
        return datiGenerali;
    }

    public void setDatiGenerali(DatiGenerali datiGenerali) throws DataFormatException {
        if(datiGenerali==null || !datiGenerali.performChecks()){
            throw new DataFormatException("I dati generali non sono del formato corretto");
        }
        this.datiGenerali = datiGenerali;
    }

    public DatiBeniServizi getDatiBeniServizi() {
        return datiBeniServizi;
    }

    public void setDatiBeniServizi(DatiBeniServizi datiBeniServizi) throws DataFormatException {
        if(datiBeniServizi ==null || !datiBeniServizi.performChecks()){
            throw new DataFormatException("I dati beni e servizi non sono del formato corretto");
        }
        this.datiBeniServizi = datiBeniServizi;
    }

    public Collection<DatiPagamento> getDatiPagamenti() {
        return datiPagamenti;
    }

    public void setDatiPagamenti(Collection<DatiPagamento> datiPagamenti) throws DataFormatException {
        if(datiPagamenti ==null || !datiPagamentiCheck(datiPagamenti)){
            throw new DataFormatException("I dati dei pagamenti non sono del formato corretto");
        }
        
        this.datiPagamenti = datiPagamenti;
    }
    
    public void addDatiPagamento(DatiPagamento datiPagamento)throws DataFormatException {
        if(datiPagamento ==null || !datiPagamento.performChecks()){
            throw new DataFormatException("I dati del pagamento non sono del formato corretto");
        }
        
        this.datiPagamenti.add(datiPagamento);
        
    }
    
    public static String getTagName() {
        return "FatturaElettronicaBody";
    }


    @Override
    public final void loadXML(Element from) throws DataFormatException {
        NodeList pagamentiNodeList = from.getElementsByTagName(DatiPagamento.getTagName());
        
        this.datiGenerali = new DatiGenerali((Element)from.getElementsByTagName(DatiGenerali.getTagName()).item(0));
        this.datiBeniServizi = new DatiBeniServizi((Element)from.getElementsByTagName(DatiBeniServizi.getTagName()).item(0));
        
        for (int i = 0; i < pagamentiNodeList.getLength(); i++) {
            this.datiPagamenti.add(new DatiPagamento((Element)pagamentiNodeList.item(i)));
            
        }
        
        
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if (!performChecks()) {
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+  getTagName());
        }
        
       to.writeStartElement(getTagName());
       
       this.datiGenerali.saveXML(to);
       this.datiBeniServizi.saveXML(to);
       
        for (DatiPagamento datiPagamento : datiPagamenti) {
            datiPagamento.saveXML(to);
        }
       
       to.writeEndElement();
    }

    @Override
    public  final boolean performChecks() {
        return this.datiGenerali!=null && this.datiBeniServizi!=null && this.datiPagamenti!=null;

    }

    private boolean datiPagamentiCheck(Collection<DatiPagamento> datiPagamenti) {
        for (DatiPagamento datiPagamento : datiPagamenti) {
            if(!datiPagamento.performChecks()){
                return false;
            }
        }
        return true;
    }

    
}
