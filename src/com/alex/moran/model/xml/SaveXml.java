
package com.alex.moran.model.xml;

import com.alex.moran.model.component.Component;
import com.alex.moran.model.component.ComponentPair;
import com.alex.moran.service.ComponentService;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class SaveXml {

    private DocumentBuilder builder;

    private static SaveXml instance;

    private SaveXml() {

    }

    public static SaveXml getSaveXml() {
        if (instance == null) {
            instance = new SaveXml();
        }
        return instance;
    }

    public void saveXml(String fileName) {
        init();
        try {
            writeXml(fileName);
        } catch (Exception e) {

        }
    }

    public void init() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void writeXml(String fileName) throws TransformerException, IOException {
        Document doc = builder.newDocument();
        Element rootElement = doc.createElement("floweditor");
        Element componentsElement = doc.createElement("components");
        for (Component component : ComponentService.getComponentService().getComponents()) {
            System.out.println("component");
            Element componentElement = doc.createElement("component");
            Element idElement = doc.createElement("id");
            Element xElement = doc.createElement("x");
            Element yElement = doc.createElement("y");
            Element textElement = doc.createElement("text");
            Element colorElement = doc.createElement("color");

            textElement.appendChild(doc.createTextNode(component.getLabel().getText()));
            colorElement.appendChild(doc.createTextNode(component.getColor() + ""));
            xElement.appendChild(doc.createTextNode(component.getGridPane().getLayoutX() + ""));
            yElement.appendChild(doc.createTextNode(component.getGridPane().getLayoutY() + ""));
            idElement.appendChild(doc.createTextNode(component.hashCode() + ""));
            componentElement.appendChild(idElement);
            componentElement.appendChild(xElement);
            componentElement.appendChild(yElement);
            componentElement.appendChild(textElement);
            componentElement.appendChild(colorElement);
            componentsElement.appendChild(componentElement);
        }
        Element componentPairsElement = doc.createElement("componentPairs");
        for (ComponentPair componentPair : ComponentService.getComponentService().getPairs()) {
            Element componentPairElement = doc.createElement("componentPair");
            Element firstComponentElement = doc.createElement("firstComponent");
            Element secondComponentElement = doc.createElement("secondComponent");
            Element firstCircleElement = doc.createElement("firstCircle");
            Element secondCircleElement = doc.createElement("secondCircle");
            firstCircleElement.setAttribute("id", componentPair.getFirstComponentCircle().getId() + "");
            secondCircleElement.setAttribute("id", componentPair.getSecondComponentCircle().getId() + "");
            firstCircleElement.appendChild(doc.createTextNode(componentPair.getFirstComponentCircle().isUp() + ""));
            secondCircleElement.appendChild(doc.createTextNode(componentPair.getSecondComponentCircle().isUp() + ""));
            firstComponentElement.appendChild(doc.createTextNode(componentPair.getFirstComponent().hashCode() + ""));
            secondComponentElement.appendChild(doc.createTextNode(componentPair.getSecondComponent().hashCode() + ""));
            componentPairElement.appendChild(firstComponentElement);
            componentPairElement.appendChild(secondComponentElement);
            componentPairElement.appendChild(firstCircleElement);
            componentPairElement.appendChild(secondCircleElement);
            componentPairsElement.appendChild(componentPairElement);
        }
        rootElement.appendChild(componentsElement);
        rootElement.appendChild(componentPairsElement);
        
        doc.appendChild(rootElement);

        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(fileName)));

    }

}
