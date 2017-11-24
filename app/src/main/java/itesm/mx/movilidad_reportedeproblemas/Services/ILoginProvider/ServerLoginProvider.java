package itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import itesm.mx.movilidad_reportedeproblemas.Models.User;

public class ServerLoginProvider implements ILoginProvider, ILoginProvider.ILoginHandler {
    private ILoginHandler _handler;
    private static final String URL_STRING = "https://alsvdbw01.itesm.mx/autentica/servicio/identidad";
    private static User _user = null;
    private static ServerLoginProvider _instance = new ServerLoginProvider();
    public static ServerLoginProvider getInstance() {
        return _instance;
    }

    private ServerLoginProvider() {
    }

    @Override
    public void login(String username, String password, ILoginHandler handler) {
        if (_user != null) {
            handler.handle(_user.getId(), _user.getName(), true);
            return;
        }

        new LoadCredentials(username, password, handler).execute(URL_STRING);
    }

    @Override
    public User getCurrentUser() {
        return _user;
    }

    @Override
    public void logout() {
        _user = null;
    }

    @Override
    public void handle(String username, String name, boolean result) {
        if (result) {
            _user = new User(username, name);
        }

        _handler.handle(username, name, result);
    }

    @Override
    public Context getContext() {
        return _handler.getContext();
    }

    private class LoadCredentials extends AsyncTask<String, Integer, LoadCredentials.Result> {
        ILoginHandler handler;
        private Alumno alumno;
        private String header;

        LoadCredentials(String username, String password, ILoginHandler handler) {
            this.handler = handler;
            header = username + ":" + password;
        }

        // clase que aloja los resultados del acceso al servicio web
        class Result {
            String inString; // string del xml enviado
            Exception resultException; // excepción generada
            int resultResponseCode; // código de respuesta de la conección

            Result(int responseCode) {
                resultResponseCode = responseCode;
            }
        }

        //Método que se ejecuta antes de lanzar el thread - este está en el main thread
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //Método que ejecuta el thread en el background
        @Override
        protected LoadCredentials.Result doInBackground(String... strings) {
            InputStream in;
            String pathToFile = strings[0];
            HttpsURLConnection httpURLConnection;
            Result result = new Result(0);

            try {
                URL url = new URL(pathToFile);
                httpURLConnection = (HttpsURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");

                //Anexa el header con el usuario y contraseña
                byte[] loginBytes = header.getBytes();
                String loginBuilder = "Basic " +
                        Base64.encodeToString(loginBytes, Base64.DEFAULT);
                httpURLConnection.addRequestProperty("Authorization", loginBuilder);
                httpURLConnection.connect();

                try {
                    result.resultResponseCode = httpURLConnection.getResponseCode();

                    // si responde el servidor con 200 OK
                    if (result.resultResponseCode == HttpURLConnection.HTTP_OK) {
                        in = httpURLConnection.getInputStream();
                        result.inString = readStream(in);
                    }
                } finally {
                    httpURLConnection.disconnect();
                }
            } catch (Exception e) {
                result.resultException = e;
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values.length >= 2) {
                onProgressUpdate(values[0], values[1]);
            }
        }

        @Override
        protected void onPostExecute(LoadCredentials.Result respuesta) {
            XmlParser xmlParser = new XmlParser();

            super.onPostExecute(respuesta);
            try {
                if (respuesta.resultResponseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    handler.handle("", "", false);
                    return;
                } else {
                    if (respuesta.resultResponseCode == HttpURLConnection.HTTP_UNAVAILABLE ||
                            respuesta.resultResponseCode == HttpURLConnection.HTTP_GATEWAY_TIMEOUT) {
                        Toast.makeText(handler.getContext(), "Lo sentimos, no se tuvo conexion con el servidor", Toast.LENGTH_SHORT).show();
                    } else {
                        alumno = xmlParser.parse(respuesta.inString);
                        handler.handle(alumno.getMatricula(), alumno.getNombre(), true);
                        _user = new User(alumno.getMatricula(), alumno.getNombre());
                        return;
                    }
                }
            } catch (Exception e) {
                Log.e("LoadCredentials.onPostExecute", e.toString());
            }

            handler.handle("", "", false);
        }

        private String readStream(InputStream is) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }

        class XmlParser {

            String text;
            String nombre;
            boolean persona = false;

            public Alumno parse(String xml) throws XmlPullParserException, IOException {
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

                } catch (XmlPullParserException | IOException e) {
                    e.printStackTrace();
                }

                return alumno;
            }
        }
    }

    private class Alumno {
        String matricula;
        String nombre;

        Alumno() {
            this.matricula = null;
            this.nombre = null;
        }

        String getMatricula() {
            return matricula;
        }

        void setMatricula(String matricula) {
            this.matricula = matricula;
        }

        String getNombre() {
            return nombre;
        }

        void setNombre(String nombre) {
            this.nombre = nombre;
        }
    }
}
