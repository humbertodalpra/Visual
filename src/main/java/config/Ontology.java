package config;

import org.apache.jena.graph.Triple;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

public class Ontology {

    private static final Ontology instance = new Ontology();
    private final OntModel model = ModelFactory.createOntologyModel(OntModelSpec. OWL_MEM);
    //private final String uri = "http://www.w3.org/ns/prov#";
    private final String uri = "http://www.semanticweb.org/041784/ontologies/2015/7/Olimpiada/";
    //private final String file = "C:/Users/041784/Documents/NetBeansProjects/Visual/ex1.owl";
    private final String file = "C:/Users/041784/Documents/NetBeansProjects/Visual/ex1.owl";
    
    //private final Reasoner reasoner

    private Ontology() {
        FileManager.get().readModel(model, file);
        //model.read(file);
    }

    public static Ontology getInstance() {
        return instance;
    }

    public OntModel getModel() {
        return model;
    }

    public String getUri() {
        return uri;
    }

    public String getFile() {
        return file;
    }

    public String checkUri(String uri) {
        String[] array;
        array = uri.split("/");
        uri = array[array.length - 1];
        array = uri.split("#");
        uri = array[array.length - 1];
        array = uri.split(":");
        uri = array[array.length - 1];
        return uri;
    }

    public String checkTriple(String triple) {
        String[] tripla = triple.split(" ");
        triple = tripla[0] = checkUri(tripla[0]) + " &rightarrow; ";
        triple += tripla[1] = checkUri(tripla[1]) + " &rightarrow; ";
        triple += tripla[2] = checkUri(tripla[2]);
        return triple;
    }

    public String getTarget(Triple triple) {
        String target = triple.toString();
        return "";
    }
}