package action;

import config.Ontology;
import controller.Action;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.util.iterator.ExtendedIterator;

public class ReadOwlAction extends Action {

    //private ArrayList<String> 
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        //
        ExtendedIterator<Individual> individuals = Ontology.getInstance().getModel().listIndividuals();
        while (individuals.hasNext()) {
            OntResource individuo = individuals.next();
            String line = individuo.getLocalName();
            //resposta.add(line);
        }

    }

}
