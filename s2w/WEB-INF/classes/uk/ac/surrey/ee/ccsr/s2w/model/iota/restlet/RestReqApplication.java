/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.ccsr.s2w.model.iota.restlet;

import uk.ac.surrey.ee.ccsr.s2w.model.iota.restlet.resource.DeleteDescription;
import uk.ac.surrey.ee.ccsr.s2w.model.iota.restlet.resource.RegisterDescription;
import uk.ac.surrey.ee.ccsr.s2w.model.iota.restlet.resource.QueryDescription;
import uk.ac.surrey.ee.ccsr.s2w.model.iota.restlet.resource.SanityCheck;
import uk.ac.surrey.ee.ccsr.s2w.model.iota.restlet.resource.UpdateDescription;
import uk.ac.surrey.ee.ccsr.s2w.model.iota.restlet.resource.DiscoverDescription;
import uk.ac.surrey.ee.ccsr.s2w.model.iota.restlet.resource.LookupDescription;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import uk.ac.surrey.ee.ccsr.s2w.config.EntityConfigParams;
import uk.ac.surrey.ee.ccsr.s2w.config.ResourceConfigParams;
import uk.ac.surrey.ee.ccsr.s2w.config.ServiceConfigParams;
import uk.ac.surrey.ee.ccsr.s2w.model.iota.restlet.resource.RegisterMultiple;

public class RestReqApplication extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    
    public static final String restletPath = "/repository";
    
    public static final String registerPrefix = "/register";
    public static final String lookupPrefix = "/lookup";
    public static final String updatePrefix = "/update";
    public static final String deletePrefix = "/delete";
    public static final String queryPrefix = "/query/sparql";
    public static final String discoverPrefix = "/discover";
    
    
    @Override
    public synchronized Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a new instance of HelloWorldResource.
        Router router = new Router(getContext());
        
        router.attach("/getVersion", SanityCheck.class);
        
        router.attach(registerPrefix+ResourceConfigParams.descTypeLinkSuffix, RegisterMultiple.class);
        router.attach(registerPrefix+EntityConfigParams.descTypeLinkSuffix, RegisterMultiple.class);
        router.attach(registerPrefix+ServiceConfigParams.descTypeLinkSuffix, RegisterMultiple.class);
                                        
        router.attach(registerPrefix+ResourceConfigParams.descTypeLinkSuffix+"/{objectID}", RegisterDescription.class);
        router.attach(registerPrefix+EntityConfigParams.descTypeLinkSuffix+"/{objectID}", RegisterDescription.class);
        router.attach(registerPrefix+ServiceConfigParams.descTypeLinkSuffix+"/{objectID}", RegisterDescription.class);
        
        router.attach(lookupPrefix+ResourceConfigParams.descTypeLinkSuffix+"/{objectID}", LookupDescription.class);
        router.attach(lookupPrefix+EntityConfigParams.descTypeLinkSuffix+"/{objectID}", LookupDescription.class);
        router.attach(lookupPrefix+ServiceConfigParams.descTypeLinkSuffix+"/{objectID}", LookupDescription.class);

        router.attach(updatePrefix+ResourceConfigParams.descTypeLinkSuffix+"/{objectID}", UpdateDescription.class);
        router.attach(updatePrefix+EntityConfigParams.descTypeLinkSuffix+"/{objectID}", UpdateDescription.class);
        router.attach(updatePrefix+ServiceConfigParams.descTypeLinkSuffix+"/{objectID}", UpdateDescription.class);
        
        router.attach(deletePrefix+ResourceConfigParams.descTypeLinkSuffix+"/{objectID}", DeleteDescription.class);
        router.attach(deletePrefix+EntityConfigParams.descTypeLinkSuffix+"/{objectID}", DeleteDescription.class);
        router.attach(deletePrefix+ServiceConfigParams.descTypeLinkSuffix+"/{objectID}", DeleteDescription.class);

        router.attach(queryPrefix+ResourceConfigParams.descTypeLinkSuffix, QueryDescription.class);
        router.attach(queryPrefix+EntityConfigParams.descTypeLinkSuffix, QueryDescription.class);
        router.attach(queryPrefix+ServiceConfigParams.descTypeLinkSuffix, QueryDescription.class);
        
        router.attach(discoverPrefix+ResourceConfigParams.descTypeLinkSuffix, DiscoverDescription.class);
        router.attach(discoverPrefix+EntityConfigParams.descTypeLinkSuffix, DiscoverDescription.class);
        router.attach(discoverPrefix+ServiceConfigParams.descTypeLinkSuffix, DiscoverDescription.class);
        
        router.attach(discoverPrefix+"/status", DiscoverDescription.class);
        
        return router;
    }

}
