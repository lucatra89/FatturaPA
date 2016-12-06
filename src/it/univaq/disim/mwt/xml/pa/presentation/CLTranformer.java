/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.mwt.xml.pa.presentation;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author lucatraini
 */
public class CLTranformer {
    private static File readFile(){
	String src=null;
  	File file=null;

        System.out.println("Inserisci il path della fattura da visualizzare");
	BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(System.in));
  	try {
            src=bufferedReader.readLine();
	}catch (IOException e) {
            System.out.println("Problema della lettura della stringa d'input");
	}
        
  	if( src != null && !src.isEmpty()){
            file= new File(src);
        }
        if(file!=null && !file.exists()){
            System.out.println("Il path del file non è corretto");
        }
        
        return file;
    }
    
    public static void main(String[] args) {
        
        File file = readFile();
        
        if(file==null){
            System.out.println("Errore nel caricamento del file");
            System.exit(-1);
        }
        
        FatturaTransformer transformer = new FatturaXSLTTransformer(new File("fatturanice.xsl"));
        
        
        transformer.transform(file, file.getName().replaceAll("xml$", "html"));
        

        
    }
}
