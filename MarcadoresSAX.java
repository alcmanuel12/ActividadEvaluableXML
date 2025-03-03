package ae_parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MarcadoresSAX {
	public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            EventHandler handler = new EventHandler();
            saxParser.parse(new File("marcadores.xml"), handler);
            
            // Mostrar eventos encontrados
            System.out.println("\nEventos encontrados:");
            for (Evento evento : handler.getEventos()) {
                System.out.println("Evento - ID: " + evento.id + ", Nombre: " + evento.nombre);
            }
            
        } catch (ParserConfigurationException e) {
            System.err.println("Error en la configuración del parser: " + e.getMessage());
        } catch (SAXException e) {
            System.err.println("Error SAX: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        }
    }
}

//Clase manejadora de eventos SAX
class EventHandler extends DefaultHandler {
    private int nivel = 0;
    private ArrayList <Evento> eventos = new ArrayList<>();
    
 // Método que se llama al inicio de cada elemento
    public void IncioElemento(String uri, String localName, String qName, Attributes attributes) {
        String indent = "  ".repeat(nivel);
        System.out.println(indent + "Nodo: " + qName);
        
        if (qName.equals("evento")) {
            String id = attributes.getValue("id");
            String nombre = attributes.getValue("nombre");
            eventos.add(new Evento(id, nombre));
        }
        nivel++;
    }
 // Método que se llama al final de cada elemento
    public void ElementoFinal(String uri, String localName, String qName) {
        nivel--;
    }
    
    public ArrayList<Evento> getEventos() {
        return eventos;
    }
}

class Evento {
    String id;
    String nombre;
    
    Evento(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

}
