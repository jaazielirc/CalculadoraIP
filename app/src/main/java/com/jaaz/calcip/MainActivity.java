package com.jaaz.calcip;

/*
 *
 *  Autor: Jaaziel Isai Rebollar Calzada
 *
 */

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText texto1, texto2, texto3, texto4, texto_ms;
    private ImageButton button_equal, button_help;
    private Button[] bt = new Button[12];
    private int x = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto1 = findViewById(R.id.texto1);
        texto2 = findViewById(R.id.texto2);
        texto3 = findViewById(R.id.texto3);
        texto4 = findViewById(R.id.texto4);
        texto_ms = findViewById(R.id.texto_ms);
        button_equal = findViewById(R.id.button_equal);
        button_help = findViewById(R.id.button_help);
        //button_erase = findViewById(R.id.button_erase);
        bt[11] = findViewById(R.id.button_call);
        bt[10] = findViewById(R.id.button_c);
        bt[9] = findViewById(R.id.n_9);
        bt[8] = findViewById(R.id.n_8);
        bt[7] = findViewById(R.id.n_7);
        bt[6] = findViewById(R.id.n_6);
        bt[5] = findViewById(R.id.n_5);
        bt[4] = findViewById(R.id.n_4);
        bt[3] = findViewById(R.id.n_3);
        bt[2] = findViewById(R.id.n_2);
        bt[1] = findViewById(R.id.n_1);
        bt[0] = findViewById(R.id.n_0);
        texto1.setText("");
        texto2.setText("");
        texto3.setText("");
        texto4.setText("");
        texto_ms.setText("");
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);  //abrir teclado en el primer EditText
        botones();
    }

    private void escribeNumero(String numero, EditText et1, EditText et2, EditText et3, EditText et4, EditText etms ){
        if( et1.isFocused() ){
            if( et1.getText().length() == 3 ){
                et2.requestFocus();
            } else {
                et1.getText().append(numero);
            }
        }
        if( et2.isFocused() ){
            if( et2.getText().length() == 3 ){
                et3.requestFocus();
            } else {
                et2.getText().append(numero);
            }
        }
        if( et3.isFocused() ){
            if( et3.getText().length() == 3 ){
                et4.requestFocus();
            } else {
                et3.getText().append(numero);
            }
        }
        if( et4.isFocused() ){
            if( et4.getText().length() == 3 ){
                etms.requestFocus();
            } else {
                et4.getText().append(numero);
            }
        }
        if( etms.isFocused() ){
            if( etms.getText().length() == 2 ){
                button_equal.requestFocus();
            } else {
                etms.getText().append(numero);
            }
        }
    }

    /*private void borrarNumero(EditText et1, EditText et2, EditText et3, EditText et4, EditText etms){
        if( et1.isFocused() ){
            if( et1.getText().length() > 0 ){
                et1.getText().delete(et1.getText().length() - 1, et1.getText().length());
            }
        }
        if( et2.isFocused() ){
            if( et2.getText().length() > 0 ){
                et2.getText().delete(et2.getText().length() - 1, et2.getText().length());
            }
        }
        if( et3.isFocused() ){
            if( et3.getText().length() > 0 ){
                et3.getText().delete(et3.getText().length() - 1, et3.getText().length());
            }
        }
        if( et4.isFocused() ){
            if( et4.getText().length() > 0 ){
                et4.getText().delete(et4.getText().length() - 1, et4.getText().length());
            }
        }
        if( etms.isFocused() ){
            if( etms.getText().length() > 0 ){
                etms.getText().delete(etms.getText().length() - 1, etms.getText().length());
            }
        }
    }*/

    private void showHelp(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogHelp dialogHelp = new DialogHelp();
        dialogHelp.show(fragmentManager, "Ayuda");
    }

    public static class DialogHelp extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String ayuda = "Botón C : Limpia el campo de texto seleccionado" +
                    "\nBotón C* : Limpia todos los campos de texto" +
                    "\nBotón = : Genera el resultado";
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            builder.setMessage(ayuda).setTitle("Ayuda");
            return builder.create();
        }
    }

    private void showError(EditText t1, EditText t2, EditText t3, EditText t4, EditText tms){
        x = 0;
        Toast.makeText(getApplicationContext(), "ms = "+texto_ms.getText().toString(), Toast.LENGTH_SHORT).show();
        if( t1.getText().length()==0||t2.getText().length()==0||t3.getText().length()==0
                ||t4.getText().length()==0||tms.getText().length()==0){
            x = 6;
        } else {
            int t_1 = Integer.parseInt(t1.getText().toString());
            int t_2 = Integer.parseInt(t2.getText().toString());
            int t_3 = Integer.parseInt(t3.getText().toString());
            int t_4 = Integer.parseInt(t4.getText().toString());
            int t_ms = Integer.parseInt(tms.getText().toString());

            if( t_1>255 || t1.getText().length()==0 || t_1 == 0 ){
                t1.setError("Sólo números de\n1 a 255");
                t1.setText("");
                x = 1;
            } else {
                t1.setError(null);
            }
            if( t_2>255 || t2.getText().length()==0 ){
                t2.setError("Sólo números de 0 a 255");
                t2.setText("");
                x = 2;
            } else {
                t2.setError(null);
            }
            if( t_3>255 || t3.getText().length()==0 ){
                t3.setError("Sólo números de 0 a 255");
                t3.setText("");
                x = 3;
            } else {
                t3.setError(null);
            }
            if( t_4>=255 || t_4==0 || t4.getText().length()==0 ){
                t4.setError("Sólo números de 1 a 254");
                t4.setText("");
                x = 4;
            } else {
                t4.setError(null);
            }
            if( t_ms>30 || t_ms==0 || tms.getText().length()==0 ){
                //Toast.makeText(getApplicationContext(), "NOTA: Las máscaras de subred...", Toast.LENGTH_SHORT).show();
                tms.setError("Sólo números de 1 a 30");
                tms.setText("");
                x = 5;
            } else {
                tms.setError(null);
            }

            if(!((t_1<=127 && t_ms>=8) || (t_1<=191 && t_ms>=16) || (t_1<=223 && t_ms>=24) || (t_1<=239 && t_ms>=30)) ){
                tms.setError("Error: se crean super redes");
                tms.setText("");
                x = 100;
                Toast.makeText(getApplicationContext(), "X = "+x, Toast.LENGTH_SHORT).show();
            } else {
                tms.setError(null);
            }
        }
    }

    private void botones(){
        bt[9].setOnClickListener(new View.OnClickListener() { //Numero 9
            @Override
            public void onClick(View v) {
                escribeNumero("9", texto1, texto2, texto3, texto4, texto_ms);
            }
        });
        bt[8].setOnClickListener(new View.OnClickListener() { //Numero 8
            @Override
            public void onClick(View v) {
                escribeNumero("8", texto1, texto2, texto3, texto4, texto_ms);
            }
        });
        bt[7].setOnClickListener(new View.OnClickListener() { //Numero 7
            @Override
            public void onClick(View v) {
                escribeNumero("7", texto1, texto2, texto3, texto4, texto_ms);
            }
        });
        bt[6].setOnClickListener(new View.OnClickListener() { //Numero 6
            @Override
            public void onClick(View v) {
                escribeNumero("6", texto1, texto2, texto3, texto4, texto_ms);
            }
        });
        bt[5].setOnClickListener(new View.OnClickListener() { //Numero 5
            @Override
            public void onClick(View v) {
                escribeNumero("5", texto1, texto2, texto3, texto4, texto_ms);
            }
        });
        bt[4].setOnClickListener(new View.OnClickListener() { //Numero 4
            @Override
            public void onClick(View v) {
                escribeNumero("4", texto1, texto2, texto3, texto4, texto_ms);
            }
        });
        bt[3].setOnClickListener(new View.OnClickListener() { //Numero 3
            @Override
            public void onClick(View v) {
                escribeNumero("3", texto1, texto2, texto3, texto4, texto_ms);
            }
        });
        bt[2].setOnClickListener(new View.OnClickListener() { //Numero 2
            @Override
            public void onClick(View v) {
                escribeNumero("2", texto1, texto2, texto3, texto4, texto_ms);
            }
        });
        bt[1].setOnClickListener(new View.OnClickListener() { //Numero 1
            @Override
            public void onClick(View v) {
                escribeNumero("1", texto1, texto2, texto3, texto4, texto_ms);
            }
        });
        bt[0].setOnClickListener(new View.OnClickListener() { //Numero 0
            @Override
            public void onClick(View v) {
                escribeNumero("0", texto1, texto2, texto3, texto4, texto_ms);
            }
        });

        /*button_erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarNumero(texto1, texto2, texto3, texto4, texto_ms);
            }
        });*/

        button_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHelp();
            }
        });

        button_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showError(texto1, texto2, texto3, texto4, texto_ms);
                if( x != 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Verifique los campos", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), Resultado.class);
                    intent.putExtra("oc1",texto1.getText().toString());
                    intent.putExtra("oc2",texto2.getText().toString());
                    intent.putExtra("oc3",texto3.getText().toString());
                    intent.putExtra("oc4",texto4.getText().toString());
                    intent.putExtra("ocms",texto_ms.getText().toString());
                    /*intent.putExtra("int1",Integer.parseInt(texto1.getText().toString()));
                    intent.putExtra("int2",Integer.parseInt(texto2.getText().toString()));
                    intent.putExtra("int3",Integer.parseInt(texto3.getText().toString()));
                    intent.putExtra("int4",Integer.parseInt(texto4.getText().toString()));
                    intent.putExtra("intms",Integer.parseInt(texto_ms.getText().toString()));*/
                    startActivity(intent);
                }
            }
        });

        bt[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(texto1.isFocused()){
                    texto1.setText("");
                }
                if(texto2.isFocused()){
                    texto2.setText("");
                }
                if(texto3.isFocused()){
                    texto3.setText("");
                }
                if(texto4.isFocused()){
                    texto4.setText("");
                }
                if(texto_ms.isFocused()){
                    texto_ms.setText("");
                }
            }
        });

        bt[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texto1.setText("");
                texto2.setText("");
                texto3.setText("");
                texto4.setText("");
                texto_ms.setText("");
                texto1.requestFocus();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(menu.NONE, 1, menu.NONE, "Acerca de la app");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if( item.getItemId() == 1 ){
            FragmentManager fragmentManager = getSupportFragmentManager();
            Integrantes in = new Integrantes();
            in.show(fragmentManager, "creador");
        }
        return super.onOptionsItemSelected(item);
    }

    public static class Integrantes extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String ayuda = "Jaaziel";
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            builder.setMessage(ayuda).setTitle("Nombre del alumno");
            return builder.create();
        }
    }
}