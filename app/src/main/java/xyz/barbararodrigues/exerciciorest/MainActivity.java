package xyz.barbararodrigues.exerciciorest;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{
        //implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button btnMessage = (Button) findViewById(R.id.btnMessage);
        //btnMessage.setOnClickListener(this);

    }

//    private View.OnClickListener btnMessageOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            //RestauranteHttp.carregarRestaurantes();
//            //Toast.makeText(MainActivity.this, "Hello World",
//                    //Toast.LENGTH_LONG).show();
//        }
//    };


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btnMessage:
//                //Intent segunda = new Intent(this, RestActivity.class);
//                //startActivity(segunda);
//                setContentView(R.layout.activity_restaurantes);
//                break;
//        }
//    }
}
