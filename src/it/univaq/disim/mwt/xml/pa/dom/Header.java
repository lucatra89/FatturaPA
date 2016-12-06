/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa.dom;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Element;

/**
 *
 * @author lucatraini
 */
public class Header implements ElementoFattura{

    
    private DatiTrasmissione datiTrasmissione;  
    private CedentePrestatore cedentePrestatore;
    private CessionarioCommittente cessionarioCommittente;

    public Header() {
        
        this.cedentePrestatore = null;
        this.datiTrasmissione = null;
        this.cessionarioCommittente = null;
    }

    Header(Element e) throws DataFormatException {
        this();
        this.loadXML(e);
    }


    public static String getTagName() {
        return "FatturaElettronicaHeader";
    }

    
    
    public DatiTrasmissione getDatiTrasmissione() {
        return datiTrasmissione;
    }

    public void setDatiTrasmissione(DatiTrasmissione datiTrasmissione) {
        this.datiTrasmissione = datiTrasmissione;
    }

    public CedentePrestatore getCedentePrestatore() {
        return cedentePrestatore;
    }

    public void setCedentePrestatore(CedentePrestatore cedentePrestatore) {
        this.cedentePrestatore = cedentePrestatore;
    }

    public CessionarioCommittente getCessionarioCommittente() {
        return cessionarioCommittente;
    }

    public void setCessionarioCommittente(CessionarioCommittente cessionarioCommittente) {
        this.cessionarioCommittente = cessionarioCommittente;
    }
    

    @Override
    public final void  loadXML(Element from) throws DataFormatException {
        Element cedentePrestatoreEl = (Element)from.getElementsByTagName(CedentePrestatore.getTagName()).item(0);
        Element datiTrasmissioneEl = (Element)from.getElementsByTagName(DatiTrasmissione.getTagName()).item(0);
        Element cessionarioCommittenteEl = (Element)from.getElementsByTagName(CessionarioCommittente.getTagName()).item(0);
        
        this.datiTrasmissione = new DatiTrasmissione(datiTrasmissioneEl);
        this.cedentePrestatore = new CedentePrestatore(cedentePrestatoreEl);
        this.cessionarioCommittente = new CessionarioCommittente(cessionarioCommittenteEl);
        
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if (!performChecks()) {
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+ getTagName());
        }
        to.writeStartElement(getTagName());
        this.datiTrasmissione.saveXML(to);
        this.cedentePrestatore.saveXML(to);
        this.cessionarioCommittente.saveXML(to);
        to.writeEndElement();

    }

    @Override
    public boolean performChecks() {
        return (this.cedentePrestatore!=null && this.cessionarioCommittente!=null && this.datiTrasmissione != null
                && this.cedentePrestatore.performChecks() && this.cessionarioCommittente.performChecks() && this.datiTrasmissione.performChecks());
    }

    
}
