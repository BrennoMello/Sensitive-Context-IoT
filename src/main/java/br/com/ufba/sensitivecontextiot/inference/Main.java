/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufba.sensitivecontextiot.inference;


import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;

/**
 *
 * @author brenno
 */
public class Main {
    public static void main(String [] args) throws IOException, URISyntaxException{
                
        Reasoner reasoner = new Reasoner();
        reasoner.setAdressPrefix("http://www.loa-cnr.it/ontologies/DUL.owl#");
        reasoner.setPrefix("j.0");
        reasoner.reasoner();
    }
}
