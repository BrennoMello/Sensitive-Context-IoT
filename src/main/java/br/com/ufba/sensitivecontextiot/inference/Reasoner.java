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
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;

/**
 *
 * @author Brenno Mello <brennodemello.bm at gmail.com>
 */
public class Reasoner {
    private final static Logger LOGGER = Logger.getLogger(Reasoner.class.getName());
    
    private String fusekiServer = "DataSetWater.owl";
    private String prefix;
    private String adressPrefix;
    private OntModel ontModel = null;

    
    public void reasoner(){
                 String SOURCE = "http://purl.org/iot/ontology/fiesta-iot";
		 String NS = SOURCE + "#";
                
    		 LOGGER.info("Rationing on the model");
    		
                 ontModel = getOntologyModel(fusekiServer);
    		 //Model data = FileManager.get().loadModel(fusekiServer,"TTL");
		 
                 
                                  
    		 GenericRuleReasoner reasoner = null;
		    
		 //PrintUtil.registerPrefix(prefix, adressPrefix);
		    
		 String rules;
                 rules = "[rule1:  (?sensor rdf:type http://purl.org/iot/ontology/fiesta-iot#Water_Flow) (?sensor http://purl.oclc.org/NET/ssnx/ssn#madeObservation ?mdobserv) (?mdobserv http://purl.oclc.org/NET/ssnx/ssn#observationResult ?mdObservResult) (?mdObservResult http://purl.org/iot/ontology/fiesta-iot#hasValue ?outputSensor) (?outputSensor http://www.loa.istc.cnr.it/ontologies/DUL.owl#hasDataValue ?value) swrlb:greaterThan(?value, 0) -> (?sensor http://purl.org/iot/ontology/fiesta-iot#fiesta-iot:hasWaterFlow true)]";
		 
                 LOGGER.info("Getting rules: " + rules);
		    
		    		  	   
		    try{
                       reasoner = new GenericRuleReasoner(Rule.parseRules(rules));
                       
		    }catch(Exception x){
		    	x.printStackTrace();
		    }
		    
		    LOGGER.info("Performing parse in rule");
		   
		    reasoner.setDerivationLogging(true);
		    
		    InfModel inf = ModelFactory.createInfModel(reasoner, ontModel);
                       
                     StmtIterator stmts = ontModel.getIndividual(NS +"WaterFlow01").listProperties();
                     while (stmts.hasNext()) {
                            Statement stmt = stmts.nextStatement();
                            Resource subject = stmt.getSubject();
                            Property predicate = stmt.getPredicate();
                            RDFNode object = stmt.getObject();

                            System.out.print(subject.getLocalName());
                            System.out.print(" " + predicate.getLocalName() + " ");
                            if (object instanceof Resource) {
                                    System.out.print(((Resource) object).getLocalName());
                            } else {
                                    System.out.print(" \"" + object.asLiteral().toString() + "\"");
                            }

                            System.out.println(".");

                    }
                    
                         System.out.println("");
                    
                     inf.write(System.out);
                     
                   
    }
    
    	public static OntModel getOntologyModel(String ontoFile) {
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_TRANS_INF);

		try {
			ontModel.read("/home/brennomello/NetBeansProjects/Sensitive-Context-IoT/Resources/DataSetWater.owl", null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return ontModel;
	}

    
        public static void printInference(InfModel inf, Individual individual, ObjectProperty property) {
                    StmtIterator stmts = inf.listStatements(individual, property, (RDFNode) null);

                    while (stmts.hasNext()) {
                            Statement stmt = stmts.nextStatement();
                            Resource subject = stmt.getSubject();
                            Property predicate = stmt.getPredicate();
                            RDFNode object = stmt.getObject();

                            System.out.print(subject.getLocalName());
                            System.out.print(" " + predicate.getLocalName() + " ");
                            if (object instanceof Resource) {
                                    System.out.print(((Resource) object).getLocalName());
                            } else {
                                    System.out.print(" \"" + object.asLiteral().toString() + "\"");
                            }

                            System.out.println(".");

                    }
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
