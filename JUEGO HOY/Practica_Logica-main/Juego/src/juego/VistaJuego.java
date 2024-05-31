/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;
import javax.swing.JOptionPane;

public class VistaJuego {
    private LogicaJuego logicaJuego;

    public VistaJuego() {
        logicaJuego = new LogicaJuego();
    }

    public void iniciarJuego() {
        boolean continuarJugando = true;

        while (continuarJugando) {
            logicaJuego.reiniciarJuego();

            String nombreJugador = JOptionPane.showInputDialog(null, "Introduce tu nombre:");
            if (nombreJugador == null) {
                return;
            }

            boolean juegoGanado = false;
            int intentos = 0;

            while (intentos < 12 && !juegoGanado) {
                String entradaUsuario = JOptionPane.showInputDialog(null, "Introduce un número de 4 cifras:");
                if (entradaUsuario == null) { 
                    return;
                }
                if (entradaUsuario.length() != 4 || !entradaUsuario.matches("\\d{4}")) {
                    JOptionPane.showMessageDialog(null, "Entrada no válida. Por favor, introduce un número de 4 cifras.");
                    continue;
                }

                // Validación de dígitos repetidos
                boolean digitosRepetidos = false;
                for (int i = 0; i < entradaUsuario.length(); i++) {
                    for (int j = i + 1; j < entradaUsuario.length(); j++) {
                        if (entradaUsuario.charAt(i) == entradaUsuario.charAt(j)) {
                            digitosRepetidos = true;
                            break;
                        }
                    }
                }
                if (digitosRepetidos) {
                    JOptionPane.showMessageDialog(null, "Entrada no válida. Por favor, no repitas ningún dígito.");
                    continue;
                }

                int[] resultado = logicaJuego.evaluarIntento(entradaUsuario);
                intentos++;

                // Actualización inmediata de matrices
                logicaJuego.actualizarMatrices(entradaUsuario, resultado);

                JOptionPane.showMessageDialog(null, "Matriz de Códigos:\n" + obtenerMatrizComoString(logicaJuego.obtenerMatrizCodigo(), 1) + "\nMatriz de Resultados y Aproximaciones:\n" + obtenerMatrizResultadosAproximaciones(logicaJuego.obtenerMatrizResultado(), resultado) + "\nIntentos restantes: " + (12 - intentos));

                if (resultado[0] == 4) {
                    juegoGanado = true;
                    JOptionPane.showMessageDialog(null, "¡Felicidades, " + nombreJugador + "! Has adivinado el código en " + intentos + " intentos.");
                } else if (intentos == 12) {
                    JOptionPane.showMessageDialog(null, "Has agotado todos los intentos y no has adivinado el código.");
                }
            }

            logicaJuego.registrarResultadoJuego(nombreJugador, juegoGanado, intentos);
            int opcionEstadisticas = JOptionPane.showConfirmDialog(null, "¿Deseas ver las estadísticas del juego?", "Estadísticas", JOptionPane.YES_NO_OPTION);
            if (opcionEstadisticas == JOptionPane.YES_OPTION) {
                mostrarEstadisticas();
            }

            int opcionContinuar = JOptionPane.showConfirmDialog(null, "¿Deseas jugar de nuevo?", "Nuevo Juego", JOptionPane.YES_NO_OPTION);
            continuarJugando = (opcionContinuar == JOptionPane.YES_OPTION);
        }
    }

    private String obtenerMatrizComoString(int[][] matriz, int inicio) {
        StringBuilder sb = new StringBuilder();
        for (int i = inicio; i < matriz.length; i++) {
            for (int valor : matriz[i]) {
                sb.append(String.format("%-5s", valor)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private String obtenerMatrizResultadosAproximaciones(int[][] matrizResultados, int[] resultadoActual) {
        StringBuilder sb = new StringBuilder();
        sb.append("Aciertos   | Aproximaciones\n");
        for (int i = 0; i <matrizResultados.length; i++) {
            sb.append(String.format("%-10s | %-10s\n", matrizResultados[i][0], matrizResultados[i][1]));
        }
        sb.append(String.format("\nEn el ultimo intento:\nAciertos: %d | Aproximaciones: %d", resultadoActual[0], resultadoActual[1]));
        return sb.toString();
    }

    public void mostrarEstadisticas() {
        String estadisticas = logicaJuego.obtenerEstadisticas();
        JOptionPane.showMessageDialog(null, estadisticas);
    }
        public void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            String opcion = JOptionPane.showInputDialog(null, "Seleccione una opción:\n1) Jugar\n2) Ver estadísticas\n3) Salir");

            switch (opcion) {
                case "1":
                    iniciarJuego();
                    break;
                case "2":
                    mostrarEstadisticas();
                    break;
                case "3":
                    salir = true;
                    JOptionPane.showMessageDialog(null, "Gracias por jugar. ¡Hasta luego!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, seleccione 1, 2 o 3.");
            }
        }
    }
}


