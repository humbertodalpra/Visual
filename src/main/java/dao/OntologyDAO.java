package dao;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;

public class OntologyDAO {

    private static OntologyDAO instance = new OntologyDAO();

    private String inferredFile = "C:/Users/041784/Documents/NetBeansProjects/Visual/final2Ex1.owl";
    private String file = "C:/Users/041784/Documents/NetBeansProjects/Visual/ex1.owl";
    private String uri = "http://www.w3.org/ns/prov#";
    private OWLOntology inferredOntology;
    private OWLOntology ontology;
    private OWLOntologyManager inferredManager;
    private OWLOntologyManager manager;

    private OntologyDAO() {
        inferredManager = OWLManager.createOWLOntologyManager();
        File inputOntologyFile = new File(inferredFile);
        try {
            inferredOntology = inferredManager.loadOntologyFromOntologyDocument(inputOntologyFile);
        } catch (OWLOntologyCreationException ex) {
            System.err.println("Não foi possível carregar a ontologia.");
            System.err.println(ex.getMessage());
            Logger.getLogger(OntologyDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public static OntologyDAO getInstance() {
        return instance;
    }

    public OWLOntology getOntology() {
        return inferredOntology;
    }

    public OWLOntology getInferredOntology() {
        inferredManager = OWLManager.createOWLOntologyManager();
        File inputOntologyInferredFile = new File(file);
        try {
            inferredOntology = inferredManager.loadOntologyFromOntologyDocument(inputOntologyInferredFile);
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(OntologyDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("NÃ£o foi possÃ­vel carregar a ontologia inferida.");
        }
        return inferredOntology;
    }

    public String getFile() {
        return inferredFile;
    }

    public String getInferredFile() {
        return inferredFile;
    }

    public String getUri() {
        return uri;
    }
}
