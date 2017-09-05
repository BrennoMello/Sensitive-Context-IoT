/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufba.sensitivecontextiot.inference;


import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author brenno
 */
public class Main {
    public static void main(String [] args) throws IOException, URISyntaxException{
                
        ReasonerContext reasoner = new ReasonerContext();
        reasoner.printContext();
        
    }
}
