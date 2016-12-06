/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author lucatraini
 */
class FatturaErrorHandler implements ErrorHandler{
    private int errors;
    private String messages;

    public FatturaErrorHandler() {
        errors = 0;
    }
    
    
    public String getMessages(){
        return messages;
    }
    
    
    private void appendMessage(SAXParseException saxpe){
       if(messages == null){
           messages = saxpe.getMessage();
       }else{
           messages = messages + "\n" + saxpe.getMessage();
       }
    }
    public boolean hasErrors() {
        return errors > 0;
    }

    @Override
    public void warning(SAXParseException saxpe) throws SAXException {

    }

    @Override
    public void error(SAXParseException saxpe) throws SAXException {
        appendMessage(saxpe);
        errors++;
        throw saxpe;
    }

    @Override
    public void fatalError(SAXParseException saxpe) throws SAXException {
        appendMessage(saxpe);
        errors++;
        throw saxpe;
    }
    
}
