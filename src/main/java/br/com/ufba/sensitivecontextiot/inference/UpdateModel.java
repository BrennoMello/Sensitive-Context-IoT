/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufba.sensitivecontextiot.inference;

import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.update.UpdateAction;

/**
 *
 * @author brenno
 */
public class UpdateModel {
    
    public synchronized void updateTripleStore(String tripleStoreURI, Model model, String adressModel) {

        UpdateAction.parseExecute(tripleStoreURI, model);
        DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(adressModel);
        accessor.putModel(model);

    }
}
