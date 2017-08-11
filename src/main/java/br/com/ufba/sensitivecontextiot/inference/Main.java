/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufba.sensitivecontextiot.inference;

/**
 *
 * @author brenno
 */
public class Main {
    
    public static void main(String[] args) {
        Reasoner reasoner = new Reasoner();
        reasoner.setAdressPrefix("http://purl.org/iot/ontology/fiesta-iot#");
        reasoner.setPrefix("fie");
        reasoner.reasoner();
    }
}
