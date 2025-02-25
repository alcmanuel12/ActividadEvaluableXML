package marcadores;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class Marcadores {
	
	
	public static void main(String[] args) {
		
		try {
			
            // Configurar el parser DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("marcadores.xml"));

            // Normalizar el documento
            doc.getDocumentElement().normalize();

            // Mostrar jerarquía y estructura
            System.out.println("Estructura del XML:");
            printNode(doc.getDocumentElement(), 0);

            // Listar nodos "evento"
            NodeList eventos = doc.getElementsByTagName("evento");
            System.out.println("\nNodos 'evento' encontrados:");
            Document outputDoc = builder.newDocument();
            Element root = outputDoc.createElement("analisis");
            outputDoc.appendChild(root);

            for (int i = 0; i < eventos.getLength(); i++) {
                Element evento = (Element) eventos.item(i);
                String id = evento.getAttribute("id");
                String nombre = evento.getAttribute("nombre");
                System.out.println("Evento - ID: " + id + ", Nombre: " + nombre);

                // Crear nodo en el nuevo XML
                Element eventoOutput = outputDoc.createElement("evento");
                eventoOutput.setAttribute("id", id);
                eventoOutput.setAttribute("nombre", nombre);
                root.appendChild(eventoOutput);
            }

            // Guardar el resultado en un nuevo archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Formatear
            DOMSource source = new DOMSource(outputDoc);
            StreamResult result = new StreamResult(new File("analisis_dom.xml"));
            transformer.transform(source, result);
            System.out.println("Archivo 'analisis_dom.xml' generado correctamente.");

        } catch (ParserConfigurationException e) {
            System.err.println("Error de configuración del parser: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    // Método para mostrar la jerarquía
    private static void printNode(Node node, int level) {
        for (int i = 0; i < level; i++) System.out.print("  ");
        System.out.println(node.getNodeName());
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                printNode(child, level + 1);
            }
        }
    }
}
