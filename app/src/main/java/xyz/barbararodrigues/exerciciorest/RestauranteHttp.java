package xyz.barbararodrigues.exerciciorest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by b_r_s on 28/02/2017.
 */

public class RestauranteHttp {

    public static final String WEBSERVICE_URL = "http://52.67.199.16:8080/estabelecimentos";

    private static HttpURLConnection connectar(String urlArquivo, boolean doOutput) throws IOException{
        final int SEGUNDOS = 1000;
        URL url = new URL(urlArquivo);
        HttpURLConnection conexao = (HttpURLConnection)url.openConnection();
        conexao.setReadTimeout(10* SEGUNDOS);
        conexao.setConnectTimeout(15* SEGUNDOS);
        conexao.setRequestMethod("GET");
        conexao.setDoInput(true);
        conexao.setDoOutput(doOutput);
        if(doOutput){
            conexao.addRequestProperty("Content-Type", "application/json");
        }
        conexao.connect();
        return conexao;
    }

    public static boolean temConexao(Context ctx){
        ConnectivityManager cm = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info= cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static List<Restaurante> carregarRestaurantes(){
        try {

            HttpURLConnection conexao = connectar(WEBSERVICE_URL, false);
            int resposta = conexao.getResponseCode();
            if(resposta == HttpURLConnection.HTTP_OK){
                InputStream is = conexao.getInputStream();
                Log.e("TESTE", "is" + is);
                JSONArray json = new JSONArray(bytesParaString(is));
                return lerJsonRestaurantes(json);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<Restaurante> lerJsonRestaurantes(JSONArray json) throws JSONException{
        List<Restaurante> listaDeRestaurantes = new ArrayList<Restaurante>();
        //Log.e("TESTE", "is" + json);
        //JSONArray jsonRestaurantes = new JSONArray(json);
        //Log.e("TESTE", "is" + json);
        for(int i = 0; i < json.length(); i++){
            //Log.e("TESTE", "is" + json.length());
            JSONObject e = json.getJSONObject(i);

            Restaurante restaurante = new Restaurante(e.getString("restaurante"));
            //Log.e("TESTE", "is" + restaurante.restaurante);
            listaDeRestaurantes.add(restaurante);
        }
        //Log.e("TESTE", "is" + listaDeRestaurantes);
        return listaDeRestaurantes;
    }

    private static String bytesParaString(InputStream is) throws IOException{
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
        int bytesLidos;
        while ((bytesLidos = is.read(buffer)) != -1){
            bufferzao.write(buffer,0,bytesLidos);
        }
        return new String(bufferzao.toByteArray(),"UTF-8");
    }
}
