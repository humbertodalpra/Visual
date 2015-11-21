package dao;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import model.Graph;
import model.Node;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

public class GraphDAO {

    private static GraphDAO instance = new GraphDAO();
    private Graph graph;

    private GraphDAO() {
        graph = new Graph();
        OntologyDAO.getInstance().loadOntology();
        Set<OWLNamedIndividual> individuosList = OntologyDAO.getInstance().getOntology().getIndividualsInSignature();
        for (OWLNamedIndividual individual : individuosList) {
            Set<OWLClassExpression> types = individual.getTypes(OntologyDAO.getInstance().getOntology());
            if (!types.isEmpty()) {
                String name = individual.getIRI().getFragment();
                String type = types.toString();
                type = filterType(type);
                graph.addNode(new Node(name, type));
            }
        }
        for (OWLNamedIndividual individual : individuosList) {
            Set<OWLClassExpression> types = individual.getTypes(OntologyDAO.getInstance().getOntology());
            if (!types.isEmpty()) {
                String name = individual.getIRI().getFragment();
                String type = types.toString();
                type = filterType(type);
                Node source = new Node(name, type);
                ArrayList<Node> targets = new ArrayList<>();
                Map<OWLObjectPropertyExpression, Set<OWLIndividual>> individualMap = individual.getObjectPropertyValues(OntologyDAO.getInstance().getOntology());
                Set<OWLObjectPropertyExpression> propertiesSet = individualMap.keySet();
                for (OWLObjectPropertyExpression property : propertiesSet) {
                    Set<OWLIndividual> individualSet = individualMap.get(property);
                    for (OWLIndividual individualProperty : individualSet) {
                        Set<OWLClassExpression> targetTypes = individualProperty.getTypes(OntologyDAO.getInstance().getOntology());
                        if (!targetTypes.isEmpty()) {
                            String targetName = individualProperty.asOWLNamedIndividual().getIRI().getFragment();
                            String targetType = targetTypes.toString();
                            targetType = filterType(targetType);
                            targets.add(new Node(targetName, targetType));
                        }
                    }
                }
                if (!targets.isEmpty()) {
                    graph.addLinks(source, targets);
                }
            }
        }
    }

    public static GraphDAO getInstance() {
        return instance;
    }

    public Graph readGraph() {
        return graph;
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
