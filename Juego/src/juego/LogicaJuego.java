/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;
import java.text.SimpleDateFormat;
import java.util.Random;

public class LogicaJuego {
    private int[][] matrizCodigo;
    private int[][] matrizResultado;
    private Datos datos;
    private int codigoSecreto;

     public LogicaJuego() {
        inicializarMatrices();
        datos = new Datos();
        generarCodigoSecreto();
    }
     
   private void inicializarMatrices() {
        matrizCodigo = new int[13][4];
        matrizResultado = new int[12][2];
    }

    public void reiniciarJuego() {
        inicializarMatrices();
        generarCodigoSecreto();
    }

    private void generarCodigoSecreto() {
    Random aleatorio = new Random();
    int[] digitos = new int[4];
    boolean[] usado = new boolean[10];

    int primerDigito = aleatorio.nextInt(9) + 1;
    digitos[0] = primerDigito;
    usado[primerDigito] = true;
    for (int i = 1; i < 4; i++) {
        int nuevoDigito;
        do {
            nuevoDigito = aleatorio.nextInt(10);
        } while (nuevoDigito == 0 || usado[nuevoDigito]);
        usado[nuevoDigito] = true;
        digitos[i] = nuevoDigito;
    }

    codigoSecreto = digitos[0] * 1000 + digitos[1] * 100 + digitos[2] * 10 + digitos[3];
    matrizCodigo[0] = digitos;
}

    public int[] evaluarIntento(String entradaUsuario) {
    int[] intento = convertirCadenaAEnteros(entradaUsuario);
    int aciertos = 0, aproximaciones = 0;

    for (int i = 0; i < 4; i++) {
        if (intento[i] == matrizCodigo[0][i]) {
            aciertos++;
        } else if (contiene(matrizCodigo[0], intento[i])) {
            aproximaciones++;
        }
    }

    return new int[]{aciertos, aproximaciones};
}

private boolean contiene(int[] array, int valor) {
    for (int i : array) {
        if (i == valor) {
            return true;
        }
    }
    return false;
}

private int[] convertirCadenaAEnteros(String entradaUsuario) {
    int[] intento = new int[entradaUsuario.length()];
    for (int i = 0; i < entradaUsuario.length(); i++) {
        intento[i] = Character.digit(entradaUsuario.charAt(i), 10);
    }
    return intento;
}


    public void actualizarMatrices(String entradaUsuario, int[] resultado) {
    int indiceIntento = obtenerCantidadIntentos();
    for (int i = 0; i < 4; i++) {
        matrizCodigo[indiceIntento][i] = entradaUsuario.charAt(i) - '0';
    }
    matrizResultado[indiceIntento - 1][0] = resultado[0];
    matrizResultado[indiceIntento - 1][1] = resultado[1];
}

    public void registrarResultadoJuego(String nombreJugador, boolean juegoGanado, int intentos) {
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    String fecha = formatoFecha.format(System.currentTimeMillis());
    String resultado = juegoGanado ? String.valueOf(intentos) : "No adivinó";

    datos.getMatrizEstadisticas()[datos.getContadorEstadisticas()][0] = nombreJugador;
    datos.getMatrizEstadisticas()[datos.getContadorEstadisticas()][1] = fecha;
    datos.getMatrizEstadisticas()[datos.getContadorEstadisticas()][2] = resultado;
    datos.getMatrizEstadisticas()[datos.getContadorEstadisticas()][3] = String.valueOf(codigoSecreto);
    datos.setContadorEstadisticas(datos.getContadorEstadisticas() + 1);
}
    
    public String obtenerEstadisticas() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre      Fecha       Intento    Código secreto\n");
        for (int i = 0; i < datos.getContadorEstadisticas(); i++) {
            sb.append(String.format("%-12s%-12s%-12s%-15s\n",
                    datos.getMatrizEstadisticas()[i][0], datos.getMatrizEstadisticas()[i][1], datos.getMatrizEstadisticas()[i][2], datos.getMatrizEstadisticas()[i][3]));
        }
        return sb.toString();
    }

    public int obtenerCantidadIntentos() {
        int contador = 0;
        for (int i = 1; i < 13; i++) {
            if (matrizCodigo[i][0] != 0) {
                contador++;
            }
        }
        return contador + 1;
    }

    public int obtenerCodigoSecreto() {
        return codigoSecreto;
    }

    public int[][] obtenerMatrizCodigo() {
        return matrizCodigo;
    }

    public int[][] obtenerMatrizResultado() {
        return matrizResultado;
    }
}