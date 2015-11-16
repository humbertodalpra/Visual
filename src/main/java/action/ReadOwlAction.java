package action;

import com.google.gson.Gson;
import controller.Action;
import model.Link;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.OntologyDAO;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

public class ReadOwlAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        
        OntologyDAO.getInstance().loadOntology();
        ArrayList<Link> reply = new ArrayList<>();
        Set<OWLNamedIndividual> individuosList = OntologyDAO.getInstance().getOntology().getIndividualsInSignature();
        for (OWLNamedIndividual individual : individuosList) {
            String name = individual.toStringID();
            name = removeUri(name);
            Map<OWLObjectPropertyExpression, Set<OWLIndividual>> individualMap = individual.getObjectPropertyValues(OntologyDAO.getInstance().getOntology());
            Set<OWLObjectPropertyExpression> propertiesSet = individualMap.keySet();
            for (OWLObjectPropertyExpression property : propertiesSet) {
                Set<OWLIndividual> individualSet = individualMap.get(property);
                for (OWLIndividual individualProperty : individualSet) {
                    String objectName = individualProperty.toStringID();
                    objectName = removeUri(objectName);
                    reply.add(new Link(name, objectName, "suit"));
                }
            }
        }
        
        Gson gson = new Gson();
        String json = gson.toJson(reply);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(json);
        } catch (IOException ex) {
            Logger.getLogger(ReadOwlAction.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private String removeUri(String completeUri){
        String uri = OntologyDAO.getInstance().getUri();
        return completeUri = completeUri.substring(uri.length(), completeUri.length());
    }
}
