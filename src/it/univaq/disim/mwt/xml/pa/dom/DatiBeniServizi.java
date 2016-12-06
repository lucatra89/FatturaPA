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
public class DatiBeniServizi implements ElementoFattura{
    
    private Collection<DettaglioLinee> dettaglioLinee;
    private Collection<DatiRiepilogo> datiRiepilogo;

    public DatiBeniServizi() {
        this.dettaglioLinee = new ArrayList<>();
        this.datiRiepilogo = new ArrayList<>();
    }
    
    public DatiBeniServizi(Element element) throws DataFormatException {
        this();
        loadXML(element);
    }
    
    public static String getTagName(){
        return "DatiBeniServizi";
    }

    public Collection<DettaglioLinee> getDettaglioLinee() {
        return dettaglioLinee;
    }

    public void setDettaglioLinee(Collection<DettaglioLinee> dettaglioLinee) throws DataFormatException {
        if(dettaglioLinee==null){
                throw new DataFormatException("I dettagli linee non sono nel formato corretto");
        }
        
        for (DettaglioLinee linee : dettaglioLinee) {
            if(!linee.performChecks()){
                throw new DataFormatException("I dettagli della linea non sono nel formato corretto");
            }
        }        
        if(dettaglioLinee.isEmpty()){
            throw new DataFormatException("Deve esserci almeno una linea");
        }
        this.dettaglioLinee = dettaglioLinee;
    }

    public Collection<DatiRiepilogo> getDatiRiepilogo() {
        return datiRiepilogo;
    }

    public void setDatiRiepilogo(Collection<DatiRiepilogo> datiRiepilogo) throws DataFormatException {
        if(datiRiepilogo==null){
                throw new DataFormatException("I dati riepilogo non sono nel formato corretto");
        }
        
        for (DatiRiepilogo riepilogo : datiRiepilogo) {
            if(!riepilogo.performChecks()){
                throw new DataFormatException("I dati di riepilogo non è nel formato corretto");
            }
        }        
        if(datiRiepilogo.isEmpty()){
            throw new DataFormatException("Deve esserci almeno un dato riepilogo");
        }
        this.datiRiepilogo = datiRiepilogo;
    }
    
    public  void addDatiRiepilogo(DatiRiepilogo datiRiepilogo) throws DataFormatException{
        if(datiRiepilogo==null || !datiRiepilogo.performChecks()){
                throw new DataFormatException("I dati di riepilogo non è nel formato corretto");
        }
        
        this.datiRiepilogo.add(datiRiepilogo);
    }
    
    public  void addDettaglioLinea(DettaglioLinee dettaglioLinea) throws DataFormatException{
        if(dettaglioLinea==null || !dettaglioLinea.performChecks()){
                throw new DataFormatException("Il dettaglio linea non è nel formato corretto");
        }
        
        this.dettaglioLinee.add(dettaglioLinea);
    }
    
    @Override
    public final void loadXML(Element from) throws DataFormatException {
        NodeList lineeNodeList = from.getElementsByTagName(DettaglioLinee.getTagName());
        NodeList riepilogoNodeList = from.getElementsByTagName(DatiRiepilogo.getTagName());
    
        for (int i = 0; i < lineeNodeList.getLength(); i++) {
            this.dettaglioLinee.add(new DettaglioLinee((Element)lineeNodeList.item(i)));
        }
        
        for (int i = 0; i < riepilogoNodeList.getLength(); i++) {
            this.datiRiepilogo.add(new DatiRiepilogo((Element)riepilogoNodeList.item(i)));
        }
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if(!performChecks()){
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+  getTagName());
        }
        to.writeStartElement(getTagName());
        for (DettaglioLinee dettaglioLinea : dettaglioLinee) {
            dettaglioLinea.saveXML(to);
        }
        for (DatiRiepilogo riepilogo : datiRiepilogo) {
            riepilogo.saveXML(to);
        }
        to.writeEndElement();
        
    }

    @Override
    public final boolean performChecks() {
        return this.datiRiepilogo!=null && this.dettaglioLinee!=null && (this.datiRiepilogo.size()>0) && (this.dettaglioLinee.size()>0);
    }
    
}
