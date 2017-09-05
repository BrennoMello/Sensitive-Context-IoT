/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufba.sensitivecontextiot.inference;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import org.mindswap.pellet.jena.PelletReasonerFactory;

/**
 *
 * @author Brenno Mello <brennodemello.bm at gmail.com>
 */
public class ReasonerContext {

    private String fusekiServer = "/DataSetWater.owl";
    private String prefix;
    private String adressPrefix;
    private OntModel ontModel = null;

    public ReasonerContext(){
        this.adressPrefix = "http://purl.org/iot/ontology/fiesta-iot#";
    }
    
    
    public InfModel getInfModel() {

        ontModel = getOntologyModel(fusekiServer);

        Reasoner reasoner = PelletReasonerFactory.theInstance().create();
        reasoner.setDerivationLogging(true);

        InfModel inf = ModelFactory.createInfModel(reasoner, ontModel);

        return inf;
    }

    public OntModel getOntModel(){
        ontModel = getOntologyModel(fusekiServer);

        Reasoner reasoner = PelletReasonerFactory.theInstance().create();
        reasoner.setDerivationLogging(true);

        InfModel inf = ModelFactory.createInfModel(reasoner, ontModel);

        return ontModel;
    }
    public static OntModel getOntologyModel(String ontoFile) {
        OntModel ontModel = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);

        try {
            ontModel.read(ReasonerContext.class.getResourceAsStream(ontoFile), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ontModel;
    }
    
    public void printContext(){
        OntModel ont = getOntModel();
        InfModel inf = getInfModel();
        
        
        Individual WaterFlow = ont.getIndividual(getAdressPrefix() + "WaterFlow01");
	DatatypeProperty hasWaterFlow = ont.getDatatypeProperty(getAdressPrefix() + "hasWaterFlow");
        
        boolean stateFlow = false;
	Statement statemantFlow = WaterFlow.getProperty(hasWaterFlow);
        if(statemantFlow != null){
            stateFlow = statemantFlow.getBoolean();
        }
        
        Individual imageAlert = ont.getIndividual(getAdressPrefix() + "ImageSensorAlert_01");
	DatatypeProperty hasAlertImage = ont.getDatatypeProperty(getAdressPrefix() + "hasAlert");
        
        boolean stateImage = false;
	Statement statemantImage = imageAlert.getProperty(hasAlertImage);
        if(statemantImage != null){
            stateImage = statemantImage.getBoolean();
        }
        
        System.out.println("---------------------Facts Water----------------------------");
        System.out.println("Existe Fluxo: "+stateFlow);
        System.out.println("Alerta vazamento: "+stateImage);
        System.out.println("------------------------------------------------------------");
        
        
        Individual airCondi = ont.getIndividual(getAdressPrefix() + "AirConditioning_01");
	DatatypeProperty outputAirCondi = ont.getDatatypeProperty(getAdressPrefix() + "OutputValue");
        
        int tempAir = 0;
	Statement statemantAir = airCondi.getProperty(outputAirCondi);
        if(statemantAir != null){
           tempAir = statemantAir.getInt();
        }
        
        System.out.println("-----------------Facts of Temperature-----------------------");
        System.out.println("Temperatura Ambiente: "+tempAir);
        System.out.println("------------------------------------------------------------");
        
        
        
    }
    
    public static void printInference(InfModel inf, Individual individual, ObjectProperty property) {
        StmtIterator stmts = inf.listStatements(individual, property, (RDFNode) null);

        while (stmts.hasNext()) {
            Statement stmt = stmts.nextStatement();
            Resource subject = stmt.getSubject();
            Resource predicate = stmt.getPredicate();
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
