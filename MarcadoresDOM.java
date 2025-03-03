package ae_parsers;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class MarcadoresDOM {
	
	
	public static void main(String[] args) {
		
		try {
            // Crear el parser DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // Leer el archivo XML
            Document doc = builder.parse(new File("marcadores.xml"));
            doc.getDocumentElement().normalize();
            
            // Analizar la estructura
            System.out.println("Elemento raíz: " + doc.getDocumentElement().getNodeName());
            AnalizarNodo(doc.getDocumentElement(), 0);
            
            // Buscar nodos "evento"
            NodeList eventos = doc.getElementsByTagName("evento");
            System.out.println("\nEventos encontrados:");
            for (int i = 0; i < eventos.getLength(); i++) {
                Element evento = (Element) eventos.item(i);
                String id = evento.getAttribute("id");
                String nombre = evento.getAttribute("nombre");
                System.out.println("Evento - ID: " + id + ", Nombre: " + nombre);
            }
            
            // Crear nuevo XML con el resultado
            GuardarXML(doc);
            
            
           //Errores posibles
        } catch (ParserConfigurationException e) {
            System.err.println("Error en la configuración del parser: " + e.getMessage());
        } catch (SAXException e) {
            System.err.println("Error SAX: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        } catch (TransformerException e) {
            System.err.println("Error al transformar XML: " + e.getMessage());
        }
    }
    
    private static void AnalizarNodo(Node node, int level) {
        String indent = "  ".repeat(level);
        System.out.println(indent + "Nodo: " + node.getNodeName());
        
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
            	AnalizarNodo(child, level + 1);
            }
        }
    }
    
    private static void GuardarXML(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "Si");
        
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("analisis_output.xml"));
        transformer.transform(source, result);
    }
}
