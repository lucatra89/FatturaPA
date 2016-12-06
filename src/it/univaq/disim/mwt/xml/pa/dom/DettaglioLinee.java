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
public class DettaglioLinee implements ElementoFattura{
    
    private Integer numeroLinea;
    private String descrizione;
    private Float quantita;
    private Float prezzoUnitario;
    private Float prezzoTotale;
    private Float aliquotaIVA;

    public DettaglioLinee() {
        this.aliquotaIVA = null;
        this.prezzoTotale = null;
        this.prezzoUnitario = null;
        this.descrizione = null;
        this.quantita = null;
        this.numeroLinea = null;
        
    }

    public DettaglioLinee(Element element) throws DataFormatException {
        this();
        loadXML(element);
    }

    public Integer getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(Integer numeroLinea) throws DataFormatException {
        if(numeroLinea == null){
            throw new DataFormatException("Il numero linea non può essere nullo");
        }
        this.numeroLinea = numeroLinea;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) throws DataFormatException {
        if(descrizione == null || descrizione.length()>0){
            throw new DataFormatException("La descrizione non è nel giusto formato");
        }
        this.descrizione = descrizione;
    }

    public Float getQuantita() {
        return quantita;
    }

    public void setQuantita(Float quantita) throws DataFormatException {
        if(quantita != null &&  quantita<=0){
            throw new DataFormatException("La quantità deve  essere un numero positivo");
        }
        this.quantita = quantita;
    }

    public Float getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public void setPrezzoUnitario(Float prezzoUnitario) throws DataFormatException {
        if(prezzoUnitario == null || prezzoUnitario<=0){
            throw new DataFormatException("Il prezzo unitario non è nel formato corretto");
        }
        this.prezzoUnitario = prezzoUnitario;
    }

    public Float getPrezzoTotale() {
        return prezzoTotale;
    }

    public void setPrezzoTotale(Float prezzoTotale) throws DataFormatException {
        if(prezzoTotale == null || prezzoTotale<=0){
            throw new DataFormatException("Il prezzo totale non è nel formato corretto");
        }
        this.prezzoTotale = prezzoTotale;
    }

    public Float getAliquotaIVA() {
        return aliquotaIVA;
    }

    public void setAliquotaIVA(Float aliquotaIVA) throws DataFormatException {
        if(aliquotaIVA == null || aliquotaIVA<0){
            throw new DataFormatException("L'aliquota IVA non è nel formato corretto");
        }
        this.aliquotaIVA = aliquotaIVA;
    }
   
    
    
    public static String getTagName(){
        return "DettaglioLinee";
    }

    @Override
    public final void loadXML(Element from) throws DataFormatException {
        NodeList quantitaNodeList = from.getElementsByTagName("Quantita");
        
        this.numeroLinea = Integer.parseInt(from.getElementsByTagName("NumeroLinea").item(0).getTextContent());
        this.descrizione = from.getElementsByTagName("Descrizione").item(0).getTextContent();
        this.prezzoUnitario = Float.parseFloat(from.getElementsByTagName("PrezzoUnitario").item(0).getTextContent());
        this.prezzoTotale = Float.parseFloat(from.getElementsByTagName("PrezzoTotale").item(0).getTextContent());
        this.aliquotaIVA = Float.parseFloat(from.getElementsByTagName("AliquotaIVA").item(0).getTextContent());
        
        if(quantitaNodeList.getLength() == 1){
            this.quantita = Float.parseFloat(from.getElementsByTagName("Quantita").item(0).getTextContent());
        }
        
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if(!performChecks()){
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+  getTagName());
        }
        
        to.writeStartElement(getTagName());
        
        to.writeStartElement("NumeroLinea");
        to.writeCharacters(this.numeroLinea.toString());
        to.writeEndElement();

        to.writeStartElement("Descrizione");
        to.writeCharacters(this.descrizione);
        to.writeEndElement();

        if(this.quantita != null){
            to.writeStartElement("Quantita");
            to.writeCharacters( String.format(Locale.US,"%.2f", this.quantita));
            to.writeEndElement();
        }
        
        
        to.writeStartElement("PrezzoUnitario");
        to.writeCharacters(String.format(Locale.US,"%.2f",this.prezzoUnitario));
        to.writeEndElement();

        to.writeStartElement("PrezzoTotale");
        to.writeCharacters(String.format(Locale.US,"%.2f",this.prezzoTotale));
        to.writeEndElement();

        to.writeStartElement("AliquotaIVA");
        to.writeCharacters(String.format(Locale.US,"%.2f",this.aliquotaIVA));
        to.writeEndElement();
        
        to.writeEndElement();
    }

    @Override
    public final boolean performChecks() {
        return this.numeroLinea!=null && this.descrizione!=null && this.aliquotaIVA!=null && this.prezzoUnitario!=null && this.prezzoTotale!=null;
    }
    
}
