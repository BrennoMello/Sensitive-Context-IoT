/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufba.sensitivecontextiot.inference;

import org.apache.jena.rdf.model.*;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.logging.Logger;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.PrintUtil;
import org.apache.jena.reasoner.rulesys.Rule;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Properties;

import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Derivation;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.PrintUtil;
import java.util.logging.Logger;

/**
 *
 * @author Brenno Mello <brennodemello.bm at gmail.com>
 */
public class Reasoner {
    private final static Logger LOGGER = Logger.getLogger(Reasoner.class.getName());
    
    private String fusekiServer = "model.ttl";
    private String prefix;
    private String adressPrefix;
    
    public void reasoner(){
        
    		 LOGGER.info("Rationing on the model");
    		
    		 Model data = FileManager.get().loadModel(fusekiServer,"Turtle");
		 
    		 GenericRuleReasoner reasoner = null;
		    
		 PrintUtil.registerPrefix(prefix, adressPrefix);
		    
		 String rules = "[rule1: (?a j.0:hasDataValue ?b) greaterThan(?b, 36) -> (?a highTemperature true)]";
		    
		 LOGGER.info("Getting rules: " + rules);
		    
		    		  	   
		    try{
                       reasoner = new GenericRuleReasoner(Rule.parseRules(rules));
		    }catch(Exception x){
		    	x.printStackTrace();
		    }
		    
		    LOGGER.info("Performing parse in rule");
		   
		    reasoner.setDerivationLogging(true);
		    
		    InfModel inf = ModelFactory.createInfModel(reasoner, data);
		    
		    PrintWriter out = new PrintWriter(System.out);
	
                    for (StmtIterator i = inf.listStatements(); i.hasNext();) {
                            Statement s = i.nextStatement();
                            for (Iterator<Derivation> id = inf.getDerivation(s); id.hasNext(); ) {
                                           LOGGER.info("Statement is " + s);
                                       Derivation deriv = (Derivation) id.next();
                                       deriv.printTrace(out, true);
                               RDFNode object = s.getObject();
                               LOGGER.info("Object is " + object.toString());
                               //updateModel(data, s.getSubject());
                           }
                        }
                    
                    out.flush();

    }

    public String getFusekiServer() {
        return fusekiServer;
    }

    public void setFusekiServer(String fusekiServer) {
        this.fusekiServer = fusekiServer;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAdressPrefix() {
        return adressPrefix;
    }

    public void setAdressPrefix(String adressPrefix) {
        this.adressPrefix = adressPrefix;
    }
}
