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
public class DatiGenerali implements ElementoFattura{
    
    
    private DatiGeneraliDocumento datiGeneraliDocumento;
    private Collection<DatiOrdineAcquisto> datiOrdineAcquisti;
    private Collection<DatiContratto> datiContratti;
    private Collection<DatiConvenzione> datiConvenzioni;
    private Collection<DatiRicezione> datiRicezioni;
    private DatiTrasporto datiTrasporto;

    public DatiGenerali() {
        
        this.datiGeneraliDocumento = null;
        this.datiOrdineAcquisti = new ArrayList<>();
        this.datiContratti = new ArrayList<>();
        this.datiRicezioni = new ArrayList<>();
        this.datiConvenzioni = new ArrayList<>();
    }
    
    public DatiGenerali(Element element) throws DataFormatException {
        this();
        loadXML(element);
    }

    public static String getTagName() {
        return "DatiGenerali";
    }

    public DatiGeneraliDocumento getDatiGeneraliDocumento() {
        return datiGeneraliDocumento;
    }

    public void setDatiGeneraliDocumento(DatiGeneraliDocumento datiGeneraliDocumento) throws DataFormatException {
        if(datiGeneraliDocumento== null || !datiGeneraliDocumento.performChecks()){
            throw new DataFormatException("I dati generali del documento non sono nel formato corretto");
        }
        this.datiGeneraliDocumento = datiGeneraliDocumento;
    }

    public Collection<DatiOrdineAcquisto> getDatiOrdineAcquisti() {
        return datiOrdineAcquisti;
    }

    public void setDatiOrdineAcquisti(Collection<DatiOrdineAcquisto> datiOrdineAcquisti) throws DataFormatException {
        if(datiOrdineAcquisti== null || !performCollectionChecks(datiOrdineAcquisti)){
            throw new DataFormatException("I dati ordine acquisti non sono nel formato corretto");
        }
        this.datiOrdineAcquisti = datiOrdineAcquisti;
    }

    public Collection<DatiContratto> getDatiContratti() {
        return datiContratti;
    }

    public void setDatiContratti(Collection<DatiContratto> datiContratti) throws DataFormatException {
        if(datiContratti== null || !performCollectionChecks(datiContratti)){
            throw new DataFormatException("I dati contratti non sono nel formato corretto");
        }
        this.datiContratti = datiContratti;
    }

    public Collection<DatiConvenzione> getDatiConvenzioni() {
        return datiConvenzioni;
    }

    public void setDatiConvenzioni(Collection<DatiConvenzione> datiConvenzioni) throws DataFormatException {
        if(datiConvenzioni== null || !performCollectionChecks(datiConvenzioni)){
            throw new DataFormatException("I dati convenzioni non sono nel formato corretto");
        }
        this.datiConvenzioni = datiConvenzioni;
    }

    public Collection<DatiRicezione> getDatiRicezioni() {
        return datiRicezioni;
    }

    public void setDatiRicezioni(Collection<DatiRicezione> datiRicezioni) throws DataFormatException {
        if(datiRicezioni== null || !performCollectionChecks(datiRicezioni)){
            throw new DataFormatException("I dati ricezione non sono nel formato corretto");
        }
        this.datiRicezioni = datiRicezioni;
    }

    public DatiTrasporto getDatiTrasporto() {
        return datiTrasporto;
    }

    public void setDatiTrasporto(DatiTrasporto datiTrasporto) throws DataFormatException {
        if(datiTrasporto!= null && !datiTrasporto.performChecks()){
            throw new DataFormatException("I dati ricezione non sono nel formato corretto");
        }
        this.datiTrasporto = datiTrasporto;
    }
    
    

    @Override
    public final void loadXML(Element from) throws DataFormatException {
        NodeList datiTrasportoNodeList = from.getElementsByTagName(DatiTrasporto.getTagName());
        NodeList ordineAcquistiNodeList = from.getElementsByTagName(DatiOrdineAcquisto.getTagName());
        NodeList datiContrattoNodeList = from.getElementsByTagName(DatiContratto.getTagName());
        NodeList datiRicezioneNodeList = from.getElementsByTagName(DatiRicezione.getTagName());
        NodeList datiConvenzioneNodeList = from.getElementsByTagName(DatiConvenzione.getTagName());
        
        this.datiGeneraliDocumento = new DatiGeneraliDocumento((Element)from.getElementsByTagName(DatiGeneraliDocumento.getTagName()).item(0));
        
        for (int i = 0; i < ordineAcquistiNodeList.getLength(); i++) {
            this.datiOrdineAcquisti.add(new DatiOrdineAcquisto((Element)ordineAcquistiNodeList.item(i)));
        }
        
        for (int i = 0; i < datiContrattoNodeList.getLength(); i++) {
            this.datiContratti.add(new DatiContratto((Element)datiContrattoNodeList.item(i)));
        }
        
        for (int i = 0; i < datiRicezioneNodeList.getLength(); i++) {
            this.datiRicezioni.add(new DatiRicezione((Element)datiRicezioneNodeList.item(i)));
        }
        for (int i = 0; i < datiConvenzioneNodeList.getLength(); i++) {
            this.datiConvenzioni.add(new DatiConvenzione((Element)datiConvenzioneNodeList.item(i)));
        }
        
        if(datiTrasportoNodeList.getLength()==1){
            this.datiTrasporto = new DatiTrasporto((Element)datiTrasportoNodeList.item(0));
        }
        
        
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if(!performChecks()){
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+  getTagName());
        }
        
        to.writeStartElement(getTagName());
        
        this.datiGeneraliDocumento.saveXML(to);
        
        Collection<Collection<? extends DatiOrdineAbstract>> collection = new ArrayList<>();
        collection.add(datiOrdineAcquisti);
        collection.add(datiContratti);
        collection.add(datiConvenzioni);
        collection.add(datiRicezioni);
        
        for (Collection<? extends DatiOrdineAbstract> datiCollection : collection) {
            for (DatiOrdineAbstract datiOrdine : datiCollection) {
                datiOrdine.saveXML(to);
            }
        }
        
        if(this.datiTrasporto!=null){
            this.datiTrasporto.saveXML(to);
        }
        
        to.writeEndElement();
    }
    
    private boolean performCollectionChecks(Collection < ? extends ElementoFattura> collection){
        for (ElementoFattura ef : collection) {
            if(!ef.performChecks()){
                return false;
            }
        }
        
        return true;
    }

    @Override
    public final boolean performChecks() {
        return this.datiGeneraliDocumento!=null  && this.datiContratti!=null 
                && this.datiRicezioni!=null && this.datiOrdineAcquisti!=null && this.datiConvenzioni!=null;
    }
    
}
