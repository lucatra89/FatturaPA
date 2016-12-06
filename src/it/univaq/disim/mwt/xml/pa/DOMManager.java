/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa;

import it.univaq.disim.mwt.xml.pa.dom.DataFormatException;
import it.univaq.disim.mwt.xml.pa.dom.Fattura;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author lucatraini
 */
public class DOMManager {
    public static Fattura loadXML(File file){
        FatturaErrorHandler errorHandler = new FatturaErrorHandler(); 
        Fattura fattura = null;
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schemaFactory.setErrorHandler(errorHandler);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(false);
        dbf.setValidating(false);
        try {

            Schema s = schemaFactory.newSchema(new File("fatturapa_v1.1.xsd"));
            javax.xml.validation.Validator v = s.newValidator();
            v.setErrorHandler(errorHandler);
            v.validate(new StreamSource(file));
            if (!errorHandler.hasErrors()) {

            DocumentBuilder parser = dbf.newDocumentBuilder();
            parser.setErrorHandler(errorHandler);
            Document doc = parser.parse(file);
            if (!errorHandler.hasErrors()) {
                fattura = new Fattura(doc.getDocumentElement());
            }
        }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(DOMManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataFormatException ex) {
            Logger.getLogger(DOMManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fattura;
    }
    
    public static void saveXML( Fattura fattura, File file)  throws UnsupportedEncodingException, FileNotFoundException, XMLStreamException, DataFormatException {
        OutputStreamWriter output = null;
        try {
            output = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = xof.createXMLStreamWriter(output);
            writer.writeStartDocument("utf-8", "1.0");
            fattura.saveXML(writer);
            writer.writeEndDocument();
        } finally {
            try {
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(DOMManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
