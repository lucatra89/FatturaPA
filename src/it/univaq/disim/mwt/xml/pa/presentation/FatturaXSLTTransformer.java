/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa.presentation;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author lucatraini
 */
public class FatturaXSLTTransformer  implements FatturaTransformer{
    
    private Transformer transformer;

    public FatturaXSLTTransformer(File xsl) {
            TransformerFactory transformerFactory= TransformerFactory.newInstance();
        try {
            this.transformer=transformerFactory.newTransformer(new StreamSource(xsl));
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(FatturaXSLTTransformer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    public void transform(File src, String dest) {
        try {
            transformer.transform(new StreamSource(src), new StreamResult(dest));
        } catch (TransformerException ex) {
            Logger.getLogger(FatturaXSLTTransformer.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
