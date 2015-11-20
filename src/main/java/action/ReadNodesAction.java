package action;

import com.google.gson.Gson;
import controller.Action;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Node;
import model.OntologyDAO;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

public class ReadNodesAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        OntologyDAO.getInstance().loadOntology();
        ArrayList<Node> reply = new ArrayList<>();
        Set<OWLNamedIndividual> individuosList = OntologyDAO.getInstance().getOntology().getIndividualsInSignature();
        for (OWLNamedIndividual individual : individuosList) {
            Set<OWLClassExpression> types = individual.getTypes(OntologyDAO.getInstance().getOntology());
            if (!types.isEmpty()) {
                String name = individual.getIRI().getFragment();
                String type = types.toString();
                type = filterType(type);
                reply.add(new Node(name, type));
            }
        }
        System.err.println(Integer.toString(reply.size()));
        Gson gson = new Gson();
        String json = gson.toJson(reply);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            System.out.printf(json);
            response.getWriter().write(json);
        } catch (IOException ex) {
            Logger.getLogger(ReadNodesAction.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String filterType(String type) {
        String reply;
        String[] array;
        array = type.split("#");
        if (array.length > 1) {
            reply = array[1];
        } else {
            reply = array[0];
        }
        return reply.substring(0, reply.length() - 2);
    }
}
