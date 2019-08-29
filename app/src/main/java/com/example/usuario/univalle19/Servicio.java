package com.example.usuario.univalle19;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.univalle19.BaseDatos.Conexion;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Servicio.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Servicio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Servicio extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Conexion con;
    SQLiteDatabase db;

    FirebaseDatabase database;
    DatabaseReference myRef;

    View rootView;
    Button btnId, btnAll;
    ///api
    ApiEstudiantes api;
    ///urls
    String IP="https://programa2.net/univalle"; //DIRECCION BASE, EN ESTE CASO LOCAMENTE, PODRÌA SER UN SERVER
    String    GET=IP+"/obtener_alumnos.php"; // EN EL DOCUMENTO ROOT DEL HOST LOCAL/ ODRIA SER EL DOCUIMENT ROOT DEL SEVER EN WWW
    String GET_ID=IP+"/obtener_alumno_por_id.php";
    String UPDATE=IP+"/actualizar_alumno.php";
    String DELETE=IP+"/borrar_alumno.php";
    String INSERT=IP+"/insertar_alumno.php";
    ///urls
    TextView resultado;
    ///api

    private OnFragmentInteractionListener mListener;

    public Servicio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Servicio.
     */
    // TODO: Rename and change types and number of parameters
    public static Servicio newInstance(String param1, String param2) {
        Servicio fragment = new Servicio();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        con = new Conexion(getActivity().getApplicationContext(),"univalle",null,1);
        // para manipular el objeto debemos hacer lo siguiente:
        db = con.getWritableDatabase();
        if(con!=null){
            Toast.makeText(getContext(),"Database create",Toast.LENGTH_LONG).show();
        }
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        myRef.setValue("Hello, World!");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_servicio, container, false);
        //pendiente acceder a compoennte de la UI del padre (actividad)
        // View tvFlag2 = (EditText) getActivity().findViewById(R.id.tvFlag);
        ///LE PERTENECEN AL FRAGMENTO. EN LA UI DEL FRAGMENTO
        btnId = rootView.findViewById(R.id.btnEstudianteId);
        btnAll = rootView.findViewById(R.id.btnAllstudents);
        resultado = rootView.findViewById(R.id.tvResult);
        btnId.setOnClickListener(this);
        btnAll.setOnClickListener(this);
        ///LE PERTENECEN AL FRAGMENTO. EN LA UI DEL FRAGMENTO
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void consumirApi(String tipoConsulta, String opcion){
        api = new ApiEstudiantes();
        api.execute(tipoConsulta,opcion);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAllstudents:
                this.consumirApi(GET,"2");
                break;
            case R.id.btnEstudianteId:
                this.consumirApi(GET,"2");
                break;
            default:
                break;
        }
    }

    public void writeFirebase(String  name, String id){
        //myRef.child("estudiantes").setValue(name);

        myRef.child("estudiantes").child(id).setValue(name);
    }

    public class ApiEstudiantes extends AsyncTask<String,String,String>{

        String nombre="";
        String direccion="";
        Integer opcion=0;
        String resultadoApi = "";

        @Override
        protected String doInBackground(String... params) {
            //volley
            URL url = null;
            String devuelve="";
            String cadena = params[0];
            if(params[1]=="1"){//actualizar
                publishProgress(params[1]);
            }else if(params[1]=="2"){//Consulta uno
                publishProgress(params[1]);
                try {
                    url = new URL(cadena);
                    System.out.println(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                    //connection.setHeader("content-type", "application/json");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK){

                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados

                        int resultJSON = respuestaJSON.getInt("estado");   // estado es el nombre del campo en el JSON

                        System.out.println("......++"+resultJSON);

                        if (resultJSON==1){      // hay alumnos a mostrar
                            // System.out.println("gsdgsdfsdfsdfsdf");
                            JSONArray alumnosJSON = respuestaJSON.getJSONArray("alumnos");   // estado es el nombre del campo en el JSON
                            for(int i=0;i<alumnosJSON.length();i++){
                                devuelve = devuelve + alumnosJSON.getJSONObject(i).getString("idAlumno") + " " +
                                        alumnosJSON.getJSONObject(i).getString("nombre") + " " +
                                        alumnosJSON.getJSONObject(i).getString("direccion") + "\n";
                                System.out.println(devuelve);
                                String query = "INSERT INTO estudiantes (nombre,direccion) values ('"+alumnosJSON.getJSONObject(i).getString("nombre")+"','"+alumnosJSON.getJSONObject(i).getString("direccion")+"');";
                                db.execSQL(query);  /// mundo de lo autocontenido y relacional
                                writeFirebase(alumnosJSON.getJSONObject(i).getString("nombre"),alumnosJSON.getJSONObject(i).getString("idAlumno"));
                                publishProgress(devuelve);
                            }
                        }
                        else if (resultJSON==2){
                            devuelve = "No hay alumnos";
                        }
                    }else{
                        devuelve = "conexion no exitosa";
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ///https://www.youtube.com/watch?v=Fs0vKEcWz6c
                return devuelve;

            }else if(params[1]=="3"){//Consulta por id
                publishProgress(params[1]);
            }else if(params[1]=="4"){//eliminar
                publishProgress(params[1]);
            }else if(params[1]=="5"){//registrar
                publishProgress(params[1]);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            resultado.append("resultado: "+values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            resultado.append("---<<<"+s);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
