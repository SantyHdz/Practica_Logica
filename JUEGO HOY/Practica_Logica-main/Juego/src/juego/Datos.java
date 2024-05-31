/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

public class Datos {
    private String[][] matrizEstadisticas;
    private int contadorEstadisticas;

    public Datos() {
        matrizEstadisticas = new String[100][4];
        contadorEstadisticas = 0;
    }

    public String[][] getMatrizEstadisticas() {
        return matrizEstadisticas;
    }

    public int getContadorEstadisticas() {
        return contadorEstadisticas;
    }

    public void setMatrizEstadisticas(String[][] matrizEstadisticas) {
        this.matrizEstadisticas = matrizEstadisticas;
    }

    public void setContadorEstadisticas(int contadorEstadisticas) {
        this.contadorEstadisticas = contadorEstadisticas;
    }
}

