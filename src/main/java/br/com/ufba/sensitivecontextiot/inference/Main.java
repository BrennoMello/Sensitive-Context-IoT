/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufba.sensitivecontextiot.inference;


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
    public static void main(String [] args){
        String NS = "urn:x-hp-jena:eg/";

        // Build a trivial example data set
        Model rdfsExample = ModelFactory.createDefaultModel();
        Property p = rdfsExample.createProperty(NS, "p");
        Property q = rdfsExample.createProperty(NS, "q");
        rdfsExample.add(p, RDFS.subPropertyOf, q);
        rdfsExample.createResource(NS+"a").addProperty(p, "foo");
        
        InfModel inf = ModelFactory.createRDFSModel(rdfsExample);
        
        Resource a = inf.getResource(NS+"a");
        System.out.println("Statement: " + a.getProperty(q));
        
        
    }
}
