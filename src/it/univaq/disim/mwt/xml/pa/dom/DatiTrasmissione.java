/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa.dom;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author lucatraini
 */
public class DatiTrasmissione implements ElementoFattura{
    
    
    private IdTrasmittente idTrasmittente;
    private String progressivoInvio;
    private String formatoTrasmissione;
    private String codiceDestinatario;
    private ContattiTrasmittente contattiTrasmittente;

    public DatiTrasmissione() {
        this.idTrasmittente = null;
        this.progressivoInvio = null;
        this.formatoTrasmissione = null;
        this.codiceDestinatario = null;
        this.contattiTrasmittente = null;
    }

    public static String getTagName() {
        return "DatiTrasmissione";
    }

    
    
    public IdTrasmittente getIdTrasmittente() {
        return idTrasmittente;
    }

    public void setIdTrasmittente(IdTrasmittente idTrasmittente) throws DataFormatException {
        if(idTrasmittente == null){
            throw new DataFormatException("L'IdTrasmittente non può essere nullo");
        }
        this.idTrasmittente = idTrasmittente;
    }

    public String getProgressivoInvio() {
        return progressivoInvio;
    }

    public void setProgressivoInvio(String progressivoInvio) throws DataFormatException {
        if(progressivoInvio == null || progressivoInvio.length() >10){
            throw new DataFormatException("Il formato del progressivoInvio non è valido");
        }

        this.progressivoInvio = progressivoInvio;
    }

    public String getFormatoTrasmissione() {
        return formatoTrasmissione;
    }

    public void setFormatoTrasmissione(String formatoTrasmissione) throws DataFormatException {
        if((formatoTrasmissione == null) || (formatoTrasmissione.length()!=5)){
            throw new DataFormatException("Il formatoTrasmissione non è valido");
        }
        this.formatoTrasmissione = formatoTrasmissione;
    }

    public String getCodiceDestinatario() {
        return codiceDestinatario;
    }

    public void setCodiceDestinatario(String codiceDestinatario) throws DataFormatException {
        if(codiceDestinatario == null || codiceDestinatario.length()!=6){
            throw new DataFormatException("Il codiceDestinatario non può essere nullo");
        }
        this.codiceDestinatario = codiceDestinatario;
    }

    public ContattiTrasmittente getContattiTrasmittente() {
        return contattiTrasmittente;
    }

    public void setContattiTrasmittente(ContattiTrasmittente contattiTrasmittente) {
        this.contattiTrasmittente = contattiTrasmittente;
    }

    
    public DatiTrasmissione(Element e) throws DataFormatException {
        this();
        loadXML(e);
    }    
    @Override
    public final void loadXML(Element from) throws DataFormatException {
        Element idTrasmittenteEl = (Element) from.getElementsByTagName("IdTrasmittente").item(0);
        NodeList contattiTrasmittenteEls = from.getElementsByTagName(ContattiTrasmittente.getTagName());
        this.idTrasmittente = new IdTrasmittente(idTrasmittenteEl);
        this.progressivoInvio =  from.getElementsByTagName("ProgressivoInvio").item(0).getTextContent();
        this.formatoTrasmissione =  from.getElementsByTagName("FormatoTrasmissione").item(0).getTextContent();
        this.codiceDestinatario =  from.getElementsByTagName("CodiceDestinatario").item(0).getTextContent();
        
        if(contattiTrasmittenteEls.getLength() == 1){
            this.contattiTrasmittente =new ContattiTrasmittente((Element)contattiTrasmittenteEls.item(0));
        }
        
    }

    @Override
    public final void saveXML(XMLStreamWriter to) throws XMLStreamException, DataFormatException {
        if (!performChecks()) {
            throw new DataFormatException("Errore nell'elemento " + this.getClass().getName() + "con tagName "+ getTagName());
        }
        
        to.writeStartElement(getTagName());
        
        this.getIdTrasmittente().saveXML(to);
        
        to.writeStartElement("ProgressivoInvio");
        to.writeCharacters(this.getProgressivoInvio());
        to.writeEndElement();
        
        to.writeStartElement("FormatoTrasmissione");
        to.writeCharacters(this.getFormatoTrasmissione());
        to.writeEndElement();
        
        to.writeStartElement("CodiceDestinatario");
        to.writeCharacters(this.getCodiceDestinatario());
        to.writeEndElement();
        
        if(this.getContattiTrasmittente() != null){
            this.getContattiTrasmittente().saveXML(to);
        }

        to.writeEndElement();
    }

    @Override
    public final boolean performChecks() {
        return this.getCodiceDestinatario()!= null && this.getFormatoTrasmissione()!=null && this.getIdTrasmittente()!=null && this.getProgressivoInvio()!=null;
    }
    
}
