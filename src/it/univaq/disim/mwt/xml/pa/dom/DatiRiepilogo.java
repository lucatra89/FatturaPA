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
public class DatiRiepilogo implements ElementoFattura{
    
    private Float aliquotaIVA;
    private Float imponibileImporto;
    private Float imposta;
    private EsigibilitaIVA esigibilitaIVA;

    public DatiRiepilogo() {
        this.aliquotaIVA = null;
        this.imponibileImporto = null;
        this.esigibilitaIVA = null;
        this.imposta = null;
    }
    
    public DatiRiepilogo(Element element) throws DataFormatException {
        this();
        loadXML(element);
    }
    
    public static String getTagName(){
        return "DatiRiepilogo";
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

    public Float getImponibileImporto() {
        return imponibileImporto;
    }

    public void setImponibileImporto(Float imponibileImporto) throws DataFormatException {
        if(imponibileImporto == null || imponibileImporto<0){
            throw new DataFormatException("L'imponibile non è nel formato corretto");
        }
        this.imponibileImporto = imponibileImporto;
    }

    public Float getImposta() {
        return imposta;
    }

    public void setImposta(Float imposta) throws DataFormatException {
        if(imposta == null || imposta<0){
            throw new DataFormatException("L'imposta non è nel formato corretto");
        }
        this.imposta = imposta;
    }

    public EsigibilitaIVA getEsigibilitaIVA() {
        return esigibilitaIVA;
    }

    public void setEsigibilitaIVA(EsigibilitaIVA esigibilitaIVA) {
        this.esigibilitaIVA = esigibilitaIVA;
    }

    @Override
    public final void loadXML(Element from) throws DataFormatException {
        NodeList esIVANodeList = from.getElementsByTagName("EsigibilitaIVA");
        
        this.aliquotaIVA = Float.parseFloat(from.getElementsByTagName("AliquotaIVA").item(0).getTextContent());
        this.imponibileImporto = Float.parseFloat(from.getElementsByTagName("ImponibileImporto").item(0).getTextContent());
        this.imposta = Float.parseFloat(from.getElementsByTagName("Imposta").item(0).getTextContent());
        
        if(esIVANodeList.getLength()==1){
            this.esigibilitaIVA = EsigibilitaIVA.valueOf(esIVANodeList.item(0).getTextContent());
        }
        
    }

    @Override
    public void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if(!performChecks()){
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+  getTagName());
        }
        
        to.writeStartElement(getTagName());
        
        to.writeStartElement("AliquotaIVA");
        to.writeCharacters(String.format(Locale.US,"%.2f",this.aliquotaIVA));
        to.writeEndElement();
        
        to.writeStartElement("ImponibileImporto");
        to.writeCharacters(String.format(Locale.US,"%.2f",this.imponibileImporto));
        to.writeEndElement();
        
        to.writeStartElement("Imposta");
        to.writeCharacters(String.format(Locale.US,"%.2f",this.imposta));
        to.writeEndElement();
        
        if(this.esigibilitaIVA!=null){
            to.writeStartElement("EsigibilitaIVA");
            to.writeCharacters(this.esigibilitaIVA.name());
            to.writeEndElement();
        }
        
        to.writeEndElement();
    }

    @Override
    public boolean performChecks() {
        return this.aliquotaIVA!=null && this.imponibileImporto!=null && this.imposta!=null;
    }
    
    
    
}
