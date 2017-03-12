package xyz.barbararodrigues.exerciciorest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by b_r_s on 01/03/2017.
 */

public class RestActivity extends Fragment{

    RestaurantesTask  mTask;
    List<Restaurante> mRestaurantes;
    ListView mListView;
    TextView mTextMensagem;
    ProgressBar mProgressBar;
    ArrayAdapter<Restaurante> mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.activity_restaurantes,null);
        mTextMensagem = (TextView)layout.findViewById(android.R.id.empty);
        mProgressBar = (ProgressBar)layout.findViewById(R.id.progressBar);
        mListView = (ListView)layout.findViewById(R.id.list);
        mListView.setEmptyView(mTextMensagem);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mRestaurantes == null){
            mRestaurantes = new ArrayList<Restaurante>();
        }
        mAdapter = new ArrayAdapter<Restaurante>(getActivity(), android.R.layout.simple_expandable_list_item_1,mRestaurantes);
        mListView.setAdapter(mAdapter);

        if(mTask == null){
            if(RestauranteHttp.temConexao(getActivity())){
                iniciarDownload();
            }else{
                mTextMensagem.setText("Sem conexão");
            }
        }else if(mTask.getStatus() == AsyncTask.Status.RUNNING){
            exibirProgress(true);
        }
    }

    private void exibirProgress(boolean exibir){
        if(exibir){
            mTextMensagem.setText("Baixando informações dos restaurantes...");
        }
        mTextMensagem.setVisibility(exibir ? View.VISIBLE : View.GONE);
        mProgressBar.setVisibility(exibir ? View.VISIBLE: View.GONE);
    }

    public void iniciarDownload(){
        if(mTask == null || mTask.getStatus() != AsyncTask.Status.RUNNING){
            mTask = new RestaurantesTask();
            mTask.execute();
        }
    }

        class RestaurantesTask extends AsyncTask<Void, Void, List <Restaurante>>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                exibirProgress(true);
            }

            @Override
            protected List<Restaurante> doInBackground(Void... strings) {
                return RestauranteHttp.carregarRestaurantes();
            }

            @Override
            protected void onPostExecute(List<Restaurante> restaurantes) {
                super.onPostExecute(restaurantes);
                exibirProgress(false);
                if(restaurantes != null){
                    mRestaurantes.clear();
                    mRestaurantes.addAll(restaurantes);
                    mAdapter.notifyDataSetChanged();
                }else{
                    mTextMensagem.setText("Falha ao obter restaurantes...");
                }
            }
        }

}
