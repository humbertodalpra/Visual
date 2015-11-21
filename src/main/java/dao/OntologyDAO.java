package dao;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.Reasoner.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class OntologyDAO {

    private static OntologyDAO instance = new OntologyDAO();

    private String file = "C:/Users/041784/Documents/NetBeansProjects/Visual/ex2.owl";
    private String uri = "http://www.w3.org/ns/prov#";
    private OWLOntology ontology;
    OWLOntologyManager manager;

    private OntologyDAO() {

    }

    public static OntologyDAO getInstance() {
        return instance;
    }

    public void loadOntology() {
        manager = OWLManager.createOWLOntologyManager();
        File inputOntologyFile = new File(file);
        try {
            ontology = manager.loadOntologyFromOntologyDocument(inputOntologyFile);
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(OntologyDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Não foi possível carregar a ontologia.");
        }
    }

    public void reasonOntology() {
        ReasonerFactory factory = new ReasonerFactory();
        Configuration configuration = new Configuration();
        configuration.throwInconsistentOntologyException = false;
        OWLReasoner reasoner = factory.createReasoner(ontology, configuration);
        System.out.println("Is Ontology consistent? " + reasoner.isConsistent());

    }

    public OWLOntology getOntology() {
        return ontology;
    }

    public String getFile() {
        return file;
    }

    public String getUri() {
        return uri;
    }
}
