/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.ccsr.s2w.coap;

import uk.ac.surrey.ee.ccsr.s2w.coap.resource.CoapDeleteDescription;
import uk.ac.surrey.ee.ccsr.s2w.coap.resource.CoapQueryDescription;
import uk.ac.surrey.ee.ccsr.s2w.coap.resource.CoapDiscoverDescription;
import uk.ac.surrey.ee.ccsr.s2w.coap.resource.CoapRegisterDescription;
import uk.ac.surrey.ee.ccsr.s2w.coap.resource.HelloWorldResource;
import uk.ac.surrey.ee.ccsr.s2w.coap.resource.CoapLookupDescription;
import uk.ac.surrey.ee.ccsr.s2w.coap.resource.CoapUpdateDescription;
import ch.ethz.inf.vs.californium.server.Server;
import java.io.File;
import java.util.concurrent.Executors;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import uk.ac.surrey.ee.ccsr.s2w.config.*;
import uk.ac.surrey.ee.ccsr.s2w.model.iota.restlet.RestReqApplication;

/**
 *
 * @author te0003
 */
public class CoapServerContextListener implements ServletContextListener {

    public Server server;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // start the thread

        System.out.println("intializing CoAP server");
        System.out.println("context-path: " + sce.getServletContext().getContextPath());
        System.out.println("real-path: " + sce.getServletContext().getRealPath(File.separator));

        server = new Server();
        server.setExecutor(Executors.newScheduledThreadPool(4));

        //test
        server.add(new HelloWorldResource("hello"));

        //s2w resources
        
        //register
        server.add(new CoapRegisterDescription("repository" + RestReqApplication.registerPrefix + ResourceConfigParams.descTypeLinkSuffix));
        server.add(new CoapRegisterDescription("repository" + RestReqApplication.registerPrefix + EntityConfigParams.descTypeLinkSuffix));
        server.add(new CoapRegisterDescription("repository" + RestReqApplication.registerPrefix + ServiceConfigParams.descTypeLinkSuffix));
        //lookup
        server.add(new CoapLookupDescription("repository"+ RestReqApplication.lookupPrefix+ResourceConfigParams.descTypeLinkSuffix));
        server.add(new CoapLookupDescription("repository"+ RestReqApplication.lookupPrefix+EntityConfigParams.descTypeLinkSuffix));
        server.add(new CoapLookupDescription("repository"+ RestReqApplication.lookupPrefix+ServiceConfigParams.descTypeLinkSuffix));
        //update
        server.add(new CoapUpdateDescription("repository"+ RestReqApplication.updatePrefix+ResourceConfigParams.descTypeLinkSuffix));
        server.add(new CoapUpdateDescription("repository"+ RestReqApplication.updatePrefix+EntityConfigParams.descTypeLinkSuffix));
        server.add(new CoapUpdateDescription("repository"+ RestReqApplication.updatePrefix+ServiceConfigParams.descTypeLinkSuffix));        
        //delete
        server.add(new CoapDeleteDescription("repository"+ RestReqApplication.deletePrefix+ResourceConfigParams.descTypeLinkSuffix));
        server.add(new CoapDeleteDescription("repository"+ RestReqApplication.deletePrefix+EntityConfigParams.descTypeLinkSuffix));
        server.add(new CoapDeleteDescription("repository"+ RestReqApplication.deletePrefix+ServiceConfigParams.descTypeLinkSuffix));
        //sparql
        server.add(new CoapQueryDescription("repository"+ RestReqApplication.queryPrefix+ResourceConfigParams.descTypeLinkSuffix));
        server.add(new CoapQueryDescription("repository"+ RestReqApplication.queryPrefix+EntityConfigParams.descTypeLinkSuffix));
        server.add(new CoapQueryDescription("repository"+ RestReqApplication.queryPrefix+ServiceConfigParams.descTypeLinkSuffix));
        //discover
        server.add(new CoapDiscoverDescription("repository"+ RestReqApplication.discoverPrefix+ResourceConfigParams.descTypeLinkSuffix));
        server.add(new CoapDiscoverDescription("repository"+ RestReqApplication.discoverPrefix+EntityConfigParams.descTypeLinkSuffix));
        server.add(new CoapDiscoverDescription("repository"+ RestReqApplication.discoverPrefix+ServiceConfigParams.descTypeLinkSuffix));

        server.start();

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // stop the thread

        server.stop();
        System.out.println("Context Listener for CoAP server Destroyed");

    }
}
