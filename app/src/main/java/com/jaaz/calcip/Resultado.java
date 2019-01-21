package com.jaaz.calcip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Resultado extends AppCompatActivity {
    TextView resultado;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado_calc);

        resultado = findViewById(R.id.res);
        bundle = getIntent().getExtras();
        textoFinal();
    }

    private String calculaClase(){
        //char[] arrClase = toBinario(Integer.parseInt(bundle.getString("oc1"))).toCharArray(); // convierte texto1 en arreglo de char
        String tipo = toBinario( Integer.parseInt(bundle.getString("oc1")) );
        char[] arrClase = tipo.toCharArray(); // convierte texto1 en arreglo de char

        if( arrClase[0] == '0' )
            return "A";

        if( arrClase[0] == '1' && arrClase[1] == '0' )
            return "B";

        if( arrClase[0] == '1' && arrClase[1] == '1' && arrClase[2] == '0' )
            return  "C";
        //if( arrClase[0] == '1' && arrClase[1] == '1' && arrClase[2] == '0' && arrClase[3] == '0' )  return  "D";

        return null;
    }
    //-----------------------------------------------------------------------------------------
    private String toBinario(int numero){   // convierte de decimal a binario
        String bin = Integer.toBinaryString(numero);
        StringBuilder builder = new StringBuilder(bin);
        StringBuilder reversa = new StringBuilder(builder.reverse().toString());

        while( reversa.length() < 8 ){
            reversa.append("0");
        }
        StringBuilder builder1 = new StringBuilder(reversa.toString());
        bin = builder1.reverse().toString();

        return bin;
    }
    //-----------------------------------------------------------------------------------------
    private int obtenerUnos(String ms){ //obtiene # de unos de una Mascara de subred
        char[] arrMascaraSubred = ms.toCharArray();
        int numeroDeUnos = 0;
        for( int i=0; i<ms.length(); i++ ){
            if( arrMascaraSubred[i] == '1' ){
                numeroDeUnos++;
            }
        }
        return numeroDeUnos;
    }

    private int obtenerCeros(String ms){ //obtiene # de ceros de la Mascara de subred
        char[] arrMascaraSubred = ms.toCharArray();
        int numeroDeCeros = 0;
        for( int i=0; i<ms.length(); i++ ){
            if( arrMascaraSubred[i] == '0' ){
                numeroDeCeros++;
            }
        }
        return numeroDeCeros;
    }
    //---------------------------------------------------------------------------------------------
    private String calculaSubredBinario(String ip_propuesta, String m_subred){ //calcula la subred entre la IP y la Mascara de subred
        StringBuilder sb = new StringBuilder();
        char[] ip = ip_propuesta.toCharArray();
        char[] ms = m_subred.toCharArray();

        for(int i=0; i<32; i++ ){
            if( ip[i]=='1' && ms[i]=='1' ){
                sb.append("1");
            } else {
                sb.append("0");
            }
        }
        return sb.toString();
    }
    //---------------------------------------------------------------------------------------------
    private String calculaSubredDecimal(){
        String subred_binario = calculaSubredBinario(generaIpBinario(), generaMascaraSubredPropuestaBinario(
                Integer.parseInt(bundle.getString("ocms"))));

        String[] subarray = splitByNumber(subred_binario);

        assert subarray != null;
        String oc1 = Integer.parseInt(subarray[0], 2)+".";
        String oc2 = Integer.parseInt(subarray[1], 2)+".";
        String oc3 = Integer.parseInt(subarray[2], 2)+".";
        String oc4 = Integer.parseInt(subarray[3], 2)+"";

        return oc1+oc2+oc3+oc4;
    }
    //-------------------------------------------------------------------------------------------------
    /*public String[] calculaSubredDecimalArray(){
        String subred_binario = calculaSubredBinario(generaIpBinario(), generaMascaraSubredPropuestaBinario(
                Integer.parseInt(bundle.getString("ocms"))));

        String[] subarray = splitByNumber(subred_binario);

        subarray[0] = Integer.parseInt(subarray[0], 2)+"";
        subarray[1] = Integer.parseInt(subarray[1], 2)+"";
        subarray[2] = Integer.parseInt(subarray[2], 2)+"";
        subarray[3] = Integer.parseInt(subarray[3], 2)+"";

        return subarray;
    }*/

    private String calculaBroadcast() {
        String subred_binario = calculaSubredBinario(generaIpBinario(), generaMascaraSubredPropuestaBinario(
                Integer.parseInt(bundle.getString("ocms"))));

        //Toast.makeText(getApplicationContext(), "Subred = "+subred_binario+"\n"+subred_binario.length(), Toast.LENGTH_SHORT).show();
        StringBuilder sb = new StringBuilder();
        char[] mitad1, mitad2, subredArray;
        mitad1 = new char[Integer.parseInt(bundle.getString("ocms"))]; //tamaño, parte del host
        mitad2 = new char[32 - Integer.parseInt(bundle.getString("ocms"))]; //parte restante despues del host
        subredArray = subred_binario.toCharArray();

        for( int i=0; i<Integer.parseInt(bundle.getString("ocms")); i++ ) { //de 0 a la parte de host
            mitad1[i] = subredArray[i];
            sb.append(mitad1[i]);
        }

        for( int i=0; i<32-Integer.parseInt(bundle.getString("ocms")); i++ ){ //del host al final (ceros)
            mitad2[i] += '1';
            sb.append(mitad2[i]);
        }
        //Toast.makeText(this,"Semibroadcast2 = "+semiBroadcast+"\nBroadcast length = "+semiBroadcast.length()
        //      +"\nMitad2.length = "+mitad2.length,Toast.LENGTH_LONG).show();

        String[] broadcastFinal = splitByNumber(sb.toString());

        assert broadcastFinal != null;
        String oc1 = Integer.parseInt(broadcastFinal[0], 2)+".";
        String oc2 = Integer.parseInt(broadcastFinal[1], 2)+".";
        String oc3 = Integer.parseInt(broadcastFinal[2], 2)+".";
        String oc4 = Integer.parseInt(broadcastFinal[3], 2)+"";
        //Toast.makeText(getApplicationContext(),"Mitad IP = "+mitad1.length,Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),"Mitad broadcast = "+mitad2.length,Toast.LENGTH_SHORT).show();

        return oc1+oc2+oc3+oc4;

    }
    //---------------------------------------------------------------------------------------------------------------
    private String[] splitByNumber(String s) {  //divide subred_binario en 4 partes iguales de 8 bits
        int size = 8;
        if(s == null) return null;
        int chunks = s.length() / size + ((s.length() % size > 0) ? 1 : 0);
        String[] arr = new String[chunks];
        for(int i = 0, j = 0, l = s.length(); i < l; i += size, j++)
            arr[j] = s.substring(i, Math.min(l, i + size));
        return arr;
    }
    //-----------------------------------------------------------------------------------------------------------------
    private String generaIpBinario(){  //genera la IP completa (campos ya validados) en binario
        return toBinario(Integer.parseInt(bundle.getString("oc1")))
                +toBinario(Integer.parseInt(bundle.getString("oc2")))
                +toBinario(Integer.parseInt(bundle.getString("oc3")))
                +toBinario(Integer.parseInt(bundle.getString("oc4")));
    }
    //-----------------------------------------------------------------------------------------------------------------
    /*public String generaMascaraSubredOmisionDecimal(String clase){  //genera la máscara de subred correspondiente por omision en decimal
        String mascara_omision = "";
        /*switch(mascara_omision){
            case "A":
        }
        if( clase.equals("A") ){
            mascara_omision = "255.0.0.0";
        }
        if( clase.equals("B") ){
            mascara_omision = "255.255.0.0";
        }
        if( clase.equals("C") ){
            mascara_omision = "255.255.255.0";
        }
        return mascara_omision;
    }*/

    private String generaMascaraSubredOmisionBinario(String clase){
        String mascara_omision = "";
        if( clase.equals("A") ){
            mascara_omision = "11111111000000000000000000000000";
        }
        if( clase.equals("B") ){
            mascara_omision = "11111111111111110000000000000000";
        }
        if( clase.equals("C") ){
            mascara_omision = "11111111111111111111111100000000";
        }
        if( clase.equals("E") ){
            mascara_omision = "11111111111111111111111111111111";
        }
        return mascara_omision;
    }

    private String generaMascaraSubredPropuestaBinario(int numMascara){   //genera la máscara de subred propuesta de texto_ms en BINARIO
        StringBuilder ms = new StringBuilder();
        for( int i=0; i<numMascara; i++){
            ms.append("1");
        }
        if( ms.length() != 32 ){
            while( ms.length() < 32){
                ms.append("0");
            }
        }
        return ms.toString();
    }

    private String generaMascaraSubredPropuestaDecimal(){
        String msprop = generaMascaraSubredPropuestaBinario(Integer.parseInt(bundle.getString("ocms")));
        String [] msp = splitByNumber(msprop);

        String msPropuesta = Integer.parseInt(msp[0], 2)+".";
        msPropuesta += Integer.parseInt(msp[1], 2)+".";
        msPropuesta += Integer.parseInt(msp[2], 2)+".";
        msPropuesta += Integer.parseInt(msp[3], 2);

        return msPropuesta;
    }

    private void textoFinal(){ // Genera todos los elementos
        String ipCompleta = "Dirección IP\n"+bundle.getString("oc1")+"."+bundle.getString("oc2")+"."+bundle.getString("oc3")+"."
                +bundle.getString("oc4");
        String msPropuesta = "\nMáscara de subred\n"+generaMascaraSubredPropuestaDecimal();
        String clase = "\nClase: "+calculaClase();
        String identificacion = "\nDirección IP de identificación\n"+calculaSubredDecimal();

        /*String[] b = new String[4];
        b[0] = calculaSubredDecimalArray()[0]+".";
        b[1] = calculaSubredDecimalArray()[1]+".";
        b[2] = calculaSubredDecimalArray()[2]+".";
        b[3] = "255";*/
        String broadcast = "\nDirección broadcast\n"+calculaBroadcast();
        //String broadcast = "";
        calculaBroadcast();

        int msp = obtenerUnos(generaMascaraSubredPropuestaBinario(Integer.parseInt(bundle.getString("ocms"))));
        int mso = obtenerUnos(generaMascaraSubredOmisionBinario(calculaClase()));
        int mss = msp - mso;
        String noSubredes = "\n# de subredes disponibles: "+(int)Math.pow(2,mss);

        int msceros = obtenerCeros(generaMascaraSubredPropuestaBinario(Integer.parseInt(bundle.getString("ocms"))));
        String noIPs = "\n# de direcciones IP disponibles\n"+(int)Math.pow(2,msceros);
        String fin = ipCompleta+"\n"+msPropuesta+"\n"+clase+"\n"+identificacion+"\n"+broadcast+"\n"+noSubredes+"\n"+noIPs;
        resultado.setText(fin);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    /*   Dirección IP (decimal)----------------------------Ok
     *   Máscara de subred propuesta (decimal)-------------Ok
     *   Subred entre IP y Ms (decimal)--------------------Ok
     *   Inicio de la subred (decimal)---------------------Ok
     *   Broadcast de la subred (decimal)------------------Ok
     *   Clase de la dirección IP (texto)------------------Ok
     *   Cantidad de direcciones IP disponibles (numero)---Ok
     *   Cantidad de host disponibles (numero)-------------Ok
     *   Cantidad de máscaras de subred (numero)-----------Ok
     * */
}
