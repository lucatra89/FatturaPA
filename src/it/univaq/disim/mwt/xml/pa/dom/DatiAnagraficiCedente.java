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
public class DatiAnagraficiCedente implements ElementoFattura{
       
    private IdFiscaleIVA idFiscaleIVA;
    private Anagrafica anagrafica;
    private RegimeFiscale regimeFiscale;

    public DatiAnagraficiCedente() {
        this.idFiscaleIVA = null;
        this.regimeFiscale = null;
        this.regimeFiscale = null;
    }

    public DatiAnagraficiCedente(Element e) throws DataFormatException {
        this();
        loadXML(e);
    }

    public static String getTagName() {
        return "DatiAnagrafici";
    }
    
    
    
    public IdFiscaleIVA getIdFiscaleIVA() {
        return idFiscaleIVA;
    }

    public void setIdFiscaleIVA(IdFiscaleIVA idFiscaleIVA) throws DataFormatException {
        if(idFiscaleIVA == null || idFiscaleIVA.performChecks()){
            throw new DataFormatException("IdFiscaleIva non corretto");
        }
        this.idFiscaleIVA = idFiscaleIVA;
    }

    public Anagrafica getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(Anagrafica anagrafica) throws DataFormatException {
        if(anagrafica == null || anagrafica.performChecks()){
            throw new DataFormatException("Anagrafica non corretta");
        }
        this.anagrafica = anagrafica;
    }

    public RegimeFiscale getRegimeFiscale() {
        return regimeFiscale;
    }

    public void setRegimeFiscale(RegimeFiscale regimeFiscale) throws DataFormatException {
        if(regimeFiscale==null){
            throw new DataFormatException("Il RegimeFiscale non può essere nullo");
        }
        this.regimeFiscale = regimeFiscale;
    }


    @Override
    public final void loadXML(Element from) throws DataFormatException {
        this.anagrafica = new Anagrafica(from);
        this.idFiscaleIVA = new IdFiscaleIVA(from);
        try{
            this.regimeFiscale = RegimeFiscale.valueOf(from.getElementsByTagName("RegimeFiscale").item(0).getTextContent());
        }catch(IllegalArgumentException e) {
            throw new DataFormatException("Il regime fiscale non è valido");
        }
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if(!this.performChecks()){
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+ getTagName());
        }
        to.writeStartElement(getTagName());
        this.idFiscaleIVA.saveXML(to);
        this.anagrafica.saveXML(to);
        to.writeStartElement("RegimeFiscale");
        to.writeCharacters(this.regimeFiscale.name());
        to.writeEndElement();
        to.writeEndElement();
    }

    @Override
    public final boolean performChecks() {
        return this.anagrafica!=null && this.idFiscaleIVA!=null && this.regimeFiscale!=null;
    }
    
}
