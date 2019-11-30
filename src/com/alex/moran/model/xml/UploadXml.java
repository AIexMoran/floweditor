
package com.alex.moran.model.xml;

import com.alex.moran.model.component.Component;
import com.alex.moran.model.component.Component.ComponentCircle;
import com.alex.moran.model.component.ThreatComponent;
import com.alex.moran.service.ComponentService;
import java.io.File;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UploadXml {

    private static UploadXml instance;

    private UploadXml() {

    }

    public static UploadXml getUploadXml() {
        if (instance == null) {
            instance = new UploadXml();
        }
        return instance;
    }

    public void readXml(File file) {
        try {
            Document doc = getDocument(file);
            uploadDoc(doc);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void uploadDoc(Document doc) {
        ComponentService.getComponentService().clearPane();
        doc.getDocumentElement().normalize();

        NodeList components = doc.getElementsByTagName("component");
        HashMap<Integer, Component> componentMap = new HashMap<>();
        for (int i = 0; i < components.getLength(); i++) {
            Node componentNode = components.item(i);
            if (componentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element component = (Element) componentNode;
                double x = Double.parseDouble(component.getElementsByTagName("x").item(0).getTextContent());
                double y = Double.parseDouble(component.getElementsByTagName("y").item(0).getTextContent());
                int id = Integer.parseInt(component.getElementsByTagName("id").item(0).getTextContent());
                int color = Integer.parseInt(component.getElementsByTagName("color").item(0).getTextContent());
                String text = component.getElementsByTagName("text").item(0).getTextContent();
                componentMap.put(id, new ThreatComponent(x, y, text, color));
                ComponentService.getComponentService().loadComponent(componentMap.get(id));
            }
        }
        NodeList componentPairs = doc.getElementsByTagName("componentPair");
        for (int i = 0; i < componentPairs.getLength(); i++) {
            Node componentPairNode = componentPairs.item(i);
            if (componentPairNode.getNodeType() == Node.ELEMENT_NODE) {
                Element componentPairElement = (Element) componentPairNode;
                int firstComponentId = Integer.parseInt(componentPairElement.getElementsByTagName("firstComponent").item(0).getTextContent());
                int secondComponentId = Integer.parseInt(componentPairElement.getElementsByTagName("secondComponent").item(0).getTextContent());
                int firstCircleId = Integer.parseInt(componentPairElement.getElementsByTagName("firstCircle").item(0).getAttributes().getNamedItem("id").getNodeValue());
                int secondCircleId = Integer.parseInt(componentPairElement.getElementsByTagName("secondCircle").item(0).getAttributes().getNamedItem("id").getNodeValue());
                boolean firstUp = Boolean.parseBoolean(componentPairElement.getElementsByTagName("firstCircle").item(0).getTextContent());
                boolean secondUp = Boolean.parseBoolean(componentPairElement.getElementsByTagName("secondCircle").item(0).getTextContent());
                ComponentCircle firstComponentCircle = componentMap.get(firstComponentId).getComponentCircle(firstUp, firstCircleId);
                ComponentCircle secondComponentCircle = componentMap.get(secondComponentId).getComponentCircle(secondUp, secondCircleId);
                ComponentService.getComponentService().addPairComponentsWithCircles(componentMap.get(firstComponentId), componentMap.get(secondComponentId), firstComponentCircle, secondComponentCircle);
            }
        }
    }

    private static Document getDocument(File file) throws Exception {
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setValidating(false);
            DocumentBuilder builder = f.newDocumentBuilder();
            return builder.parse(file);
        } catch (Exception exception) {
            String message = "XML parsing error!";
            throw new Exception(message);
        }
    }
}
