package itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import itesm.mx.movilidad_reportedeproblemas.Models.User;
import itesm.mx.movilidad_reportedeproblemas.Services.IWebsiteReader.IWebsiteReader;
import itesm.mx.movilidad_reportedeproblemas.Services.IWebsiteReader.WebsiteReader;

/**
 * Created by Juan Carlos Guzman Islas on 11/16/2017.
 * Based on file by drjuanarturonolazcoflores
 */

public class GetLoginProvider implements ILoginProvider {
    private final static String BASE_URL = "https://alsvdbw01.itesm.mx/autentica/autenticacion";
    private static GetLoginProvider _instance = new GetLoginProvider();
    public  static GetLoginProvider getInstance() {
        return _instance;
    }

    private GetLoginProvider() {}

    private IWebsiteReader _reader = new WebsiteReader();
    private User _user = null;

    @Override
    public void login(String username, String password, final ILoginHandler handler) {
        HttpGet get = new HttpGet(BASE_URL);

        String loginString = "Basic " + new String(Base64.encodeBase64((username + ':' + password).getBytes()));
        get.setHeader("Authorization", loginString);

        _reader.executeGet(HttpClients.createDefault(), get, new IWebsiteReader.IWebsiteHandler() {
            @Override
            public void handle(String content) {
                XmlParser xmlParser = new XmlParser();
                Alumno alumno = null;
                try {
                    alumno = xmlParser.parse(content);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (alumno != null) {
                        _user = new User(alumno.getMatricula(), alumno.getNombre());
                        handler.handle(alumno.getMatricula(), alumno.getNombre(), true);
                    }
                    else
                        handler.handle("","",false);
                }
            }
        });
    }

    @Override
    public User getCurrentUser() {
        return _user;
    }

    @Override
    public void logout() {
        _user = null;
    }

    class XmlParser {

        private final String nameSpace = null;
        String text;
        String nombre;
        boolean persona = false;

        public Alumno parse(String xml) throws XmlPullParserException, IOException {
            Alumno alumno = null;
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                factory.setNamespaceAware(true);
                parser.setInput(new StringReader(xml));

                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagname = parser.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if (tagname.equalsIgnoreCase("alumno")) {
                                alumno = new Alumno();
                            } else if (tagname.equalsIgnoreCase("persona")) {
                                persona = true;
                            }
                            break;

                        case XmlPullParser.TEXT:
                            text = parser.getText();
                            break;

                        case XmlPullParser.END_TAG:
                            if (tagname.equalsIgnoreCase("nombre") && persona) {
                                nombre = text;
                            } else if (tagname.equalsIgnoreCase("apellidoPaterno") && persona) {
                                nombre = nombre + text;
                            } else if (tagname.equalsIgnoreCase("apellidoMaterno") && persona) {
                                nombre = nombre + text;
                                alumno.setNombre(nombre);
                            } else if (tagname.equalsIgnoreCase("matricula")) {
                                alumno.setMatricula(text);
                            }
                            break;

                        default:
                            break;
                    }
                    eventType = parser.next();
                }

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return alumno;
        }
    }

    class Alumno {
        String matricula;
        String nombre;
        String campus;
        String carrera;

        public Alumno() {
            this.matricula = null;
            this.nombre = null;
            this.campus = null;
            this.carrera = null;
        }

        public Alumno(String nombre, String matricula, String campus, String carrera) {
            this.matricula = matricula;
            this.nombre = nombre;
            this.campus = campus;
            this.carrera = carrera;
        }

        public Alumno(String nombre, String matricula) {
            this.matricula = matricula;
            this.nombre = nombre;
            this.campus = campus;
            this.carrera = carrera;
        }

        public String getMatricula() {
            return matricula;
        }

        public void setMatricula(String matricula) {
            this.matricula = matricula;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCampus() {
            return campus;
        }

        public void setCampus(String campus) {
            this.campus = campus;
        }

        public String getCarrera() {
            return carrera;
        }

        public void setCarrera(String carrera) {
            this.carrera = carrera;
        }

        public void display() {
            System.out.println("Nombre = " + nombre);
            System.out.println("Matricila = " + matricula);
        }
    }
}
