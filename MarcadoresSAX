package marcadores;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MarcadoresSAX {
	public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            File outputFile = new File("analisis_sax.xml");
            FileWriter writer = new FileWriter(outputFile);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<analisis>\n");

            DefaultHandler handler = new DefaultHandler() {
                int level = 0;
                boolean isEvento = false;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    for (int i = 0; i < level; i++) System.out.print("  ");
                    System.out.println(qName);
                    level++;

                    if (qName.equals("evento")) {
                        isEvento = true;
                        String id = attributes.getValue("id");
                        String nombre = attributes.getValue("nombre");
                        System.out.println("Evento - ID: " + id + ", Nombre: " + nombre);
                        try {
                            writer.write("  <evento id=\"" + id + "\" nombre=\"" + nombre + "\"/>\n");
                        } catch (IOException e) {
                            throw new SAXException("Error al escribir en el archivo: " + e.getMessage());
                        }
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    level--;
                    if (qName.equals("marcadores")) {
                        try {
                            writer.write("</analisis>");
                            writer.close();
                            System.out.println("Archivo 'analisis_sax.xml' generado correctamente.");
                        } catch (IOException e) {
                            throw new SAXException("Error al cerrar el archivo: " + e.getMessage());
                        }
                    }
                }
            };

            saxParser.parse(new File("marcadores.xml"), handler);

        } catch (ParserConfigurationException e) {
            System.err.println("Error de configuración del parser: " + e.getMessage());
        } catch (SAXException e) {
            System.err.println("Error en el procesamiento SAX: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        }
    }

}
