package com.example.adrian.serviciosoap;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends Activity {

    private Button boton;

    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = (Button)findViewById(R.id.boton);

        resultado = (TextView)findViewById(R.id.resultados);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Consulta consulta = new Consulta();
                consulta.execute();
            }
        });
    }

    private class Consulta extends AsyncTask<String,String,Boolean> {

        String res;

        protected Boolean doInBackground(String... params) {

            boolean resul = true;

            final String NAMESPACE = "http://www.webservicex.net/";
            final String URL="http://www.webservicex.net/country.asmx";
            final String METHOD_NAME = "GetCountries";
            final String SOAP_ACTION = "http://www.webserviceX.NET/GetCountries";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

           // request.addProperty("GetCountriesResult" , params[0]);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            HttpTransportSE transporte = new HttpTransportSE(URL);

            try
            {
                transporte.call(SOAP_ACTION, envelope);

                SoapPrimitive resultados = (SoapPrimitive) envelope.getResponse();
                 res = resultados.toString();


            }
            catch (Exception e)
            {
                resul = false;
            }

            return resul;
        }

        protected void onPostExecute(Boolean result) {

            if (result)
            {

                    resultado.setText(res);

            }
        }
    }
}
