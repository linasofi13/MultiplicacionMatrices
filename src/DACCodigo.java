import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.lang.*;

// para graficar
import java.awt.Color;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author htrefftz
 */
public class DACCodigo extends JFrame {
    JFreeChart chart;
    public static long[] tiemposTradicional = new long[13];
    public static long[] tiemposStrassen = new long[13];

    public static int[] potenciasDe2 = {2, 4, 8, 16, 32, 64, 128, 256, 512};

    public DACCodigo() {
        super("Grafica Algoritmos");
        setSize(800, 600);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        crearGrafico();

        ChartPanel panel = new ChartPanel(chart, false);
        panel.setBounds(10, 20, 760, 520);
        add(panel);

        setVisible(true);

    }
    /**
     * Recursive algorithm for matrix multiplication based on
     * Divide and Conquer
     *
     * @param A First Matrix
     * @param B Second Matrix
     * @return Result of multiplying A x B
     */
    public static int[][] multiply(int[][] A, int[][] B) {
        int n = A.length;

        if (n == 1) {
            int[][] C = new int[1][1];
            C[0][0] = A[0][0] * B[0][0];
            return C;
        } else {
            int[][] A11 = new int[n / 2][n / 2];
            int[][] A12 = new int[n / 2][n / 2];
            int[][] A21 = new int[n / 2][n / 2];
            int[][] A22 = new int[n / 2][n / 2];
            int[][] B11 = new int[n / 2][n / 2];
            int[][] B12 = new int[n / 2][n / 2];
            int[][] B21 = new int[n / 2][n / 2];
            int[][] B22 = new int[n / 2][n / 2];

            split(A, A11, A12, A21, A22);
            split(B, B11, B12, B21, B22);

            int[][] C11 = add(normalMultiplication(A11, B11), normalMultiplication(A12, B21));
            int[][] C12 = add(normalMultiplication(A11, B12), normalMultiplication(A12, B22));
            int[][] C21 = add(normalMultiplication(A21, B11), normalMultiplication(A22, B21));
            int[][] C22 = add(normalMultiplication(A21, B12), normalMultiplication(A22, B22));

            int[][] C = new int[n][n];
            join(C11, C12, C21, C22, C);

            return C;
        }
    }

    /**
     * @param A First Matrix
     * @param B Second Matrix
     * @return Result of multiplying A x B
     */
    public static int[][] strassenMultiply(int[][] A, int[][] B) {
        int n = A.length;

        if (n == 1) {
            int[][] C = new int[1][1];
            C[0][0] = A[0][0] * B[0][0];
            return C;
        } else {
            int[][] A11 = new int[n / 2][n / 2];
            int[][] A12 = new int[n / 2][n / 2];
            int[][] A21 = new int[n / 2][n / 2];
            int[][] A22 = new int[n / 2][n / 2];
            int[][] B11 = new int[n / 2][n / 2];
            int[][] B12 = new int[n / 2][n / 2];
            int[][] B21 = new int[n / 2][n / 2];
            int[][] B22 = new int[n / 2][n / 2];

            split(A, A11, A12, A21, A22);
            split(B, B11, B12, B21, B22);


            int[][] S01 = add(A11, A22);
            int[][] S02 = add(B11, B22);
            int[][] S03 = add(A21, A22);
            int[][] S04 = subtract(B12, B22);
            int[][] S05 = subtract(B21, B11);
            int[][] S06 = add(A11, A12);
            int[][] S07 = subtract(A11, A21);
            int[][] S08 = add(B11, B12);
            int[][] S09 = subtract(A12, A22);
            int[][] S10 = add(B21, B22);

            int[][] P01 = multiply(S01, S02);
            int[][] P02 = multiply(S03, B11);
            int[][] P03 = multiply(A11, S04);
            int[][] P04 = multiply(A22, S05);
            int[][] P05 = multiply(S06, B22);
            int[][] P06 = multiply(S07, S08);
            int[][] P07 = multiply(S09, S10);


            int[][] C11 = subtract(add(add(P01, P04), P07), P05);
            int[][] C12 = add(P03, P05);
            int[][] C21 = add(P02, P04);
            int[][] C22 = subtract(subtract(add(P01, P03), P02), P06);

            int[][] C = new int[n][n];
            join(C11, C12, C21, C22, C);

            return C;
        }
    }

    /**
     * Return the matrix resulting from A - B
     *
     * @param A First matrix
     * @param B Second matrix
     * @return First - Second matrices
     */
    private static int[][] subtract(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] - B[i][j];
            }
        }

        return C;
    }


    /**
     * Splits matrix A into 4 sub-blocks of equal size.
     * <p>
     * System.arraycopy is a built-in method in Java that can be used to copy arrays.
     * It takes the following parameters:
     * src - the source array to copy from.
     * srcPos - the starting position in the source array from where to start copying.
     * dest - the destination array to copy to.
     * destPos - the starting position in the destination array where to start copying to.
     * length - the number of elements to copy.
     *
     * @param A   Original matrix to divide
     * @param A11 Upper-left block
     * @param A12 Upper-right block
     * @param A21 Lower-left block
     * @param A22 Lower-right block
     */

    private static void split(int[][] A, int[][] A11, int[][] A12, int[][] A21, int[][] A22) {
        int n = A.length / 2;

        for (int i = 0; i < n; i++) {
            System.arraycopy(A[i], 0, A11[i], 0, n);
            System.arraycopy(A[i], n, A12[i], 0, n);
            System.arraycopy(A[i + n], 0, A21[i], 0, n);
            System.arraycopy(A[i + n], n, A22[i], 0, n);
        }
    }

    /**
     * Joins C11, C12, C21 and C22 to a resulting C matrix
     *
     * @param C11 Upper-left block
     * @param C12 Upper-right block
     * @param C21 Lower-left block
     * @param C22 Lower-right block
     * @param C   Resulting matrix
     */
    private static void join(int[][] C11, int[][] C12, int[][] C21, int[][] C22, int[][] C) {
        int n = C11.length;

        for (int i = 0; i < n; i++) {
            System.arraycopy(C11[i], 0, C[i], 0, n);
            System.arraycopy(C12[i], 0, C[i], n, n);
            System.arraycopy(C21[i], 0, C[i + n], 0, n);
            System.arraycopy(C22[i], 0, C[i + n], n, n);
        }
    }

    /**
     * Adds two matrices of the same size
     *
     * @param A First matrix
     * @param B Second matrix
     * @return result of adding A + B
     */
    private static int[][] add(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }

        return C;
    }

    /**
     * Multiplication using the textbook method
     *
     * @param a First matrix
     * @param b Second matrix
     * @return Result of multiplying a * b
     */
    public static int[][] normalMultiplication(int[][] a, int[][] b) {
        int n = a.length;
        int[][] c = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int acum = 0;
                for (int k = 0; k < n; k++) {
                    acum += a[i][k] * b[k][j];
                }
                c[i][j] = acum;
            }
        }
        return c;
    }

    /**
     * @param size The dimesions (rows and columns) of the matrix
     * @return The random matrix
     */
    public static int[][] RandomMatrix(int size) {
        int[][] matrix = new int[size][size];
        Random rand = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rand.nextInt(10); // Genera un número aleatorio entre 0 y 9
            }
        }

        return matrix;
    }
    /**
     * @param matriz1 The first matrix
     * @param matriz2 The second matrix
     * @return the value result of the comparison of the two matrices
     */
    public static boolean comparison(int[][] matriz1, int[][] matriz2) {
        if (matriz1.length != matriz2.length || matriz1[0].length != matriz2[0].length) {
            // Si las dimensiones de las matrices son distintas, no son iguales
            return false;
        }

        for (int i = 0; i < matriz1.length; i++) {
            for (int j = 0; j < matriz1[0].length; j++) {
                if (matriz1[i][j] != matriz2[i][j]) {
                    // Si los elementos de las matrices son distintos, no son iguales
                    return false;
                }
            }
        }

        // Si llegamos hasta aquí es porque las matrices son iguales
        return true;
    }
    /**
     * Este método recibe como parámetro un número entero y retorna true si el número es una potencia de 2
     * y false en caso contrario. Esto se logra al hacer una operación bit a bit utilizando el operador & y la expresión (num - 1).
     * Esta expresión produce un número que tiene los mismos bits que num, excepto por el bit más a la derecha que cambia de 1 a 0.
     * Si num es una potencia de 2, entonces solo tendrá un bit en 1 y al restarle 1 se convierte en un número con todos los bits en 1.
     * Al hacer la operación bit a bit con (num - 1) se debe obtener 0, lo que significa que num es una potencia de 2.
     *
     * @param num
     * @return
     */
    public static boolean esPotenciaDeDos(int num) {
        return (num & (num - 1)) == 0;
    }
    /**
     * @param matriz
     * @return
     */
    public static boolean matrizEsPotenciaDeDos(int[][] matriz) {
        int n = matriz.length;
        int m = matriz[0].length;
        return esPotenciaDeDos(n) && esPotenciaDeDos(m);
    }

    /**
     * @param matriz
     * @return
     */
    public static int[][] completarConCeros(int[][] matriz) {
        int n = matriz.length;
        int m = matriz[0].length;
        int maxDimension = Math.max(n, m);
        int newDimension = Integer.highestOneBit(maxDimension) << 1;

        int[][] nuevaMatriz = new int[newDimension][newDimension];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                nuevaMatriz[i][j] = matriz[i][j];
            }
        }
        return nuevaMatriz;
    }

    /**
     * @param matriz The matrix from which we want to remove the unnecessary data
     * @return The matrix without the zeros
     */
    public static int[][] eliminateZeros(int[][] matriz) {
        int m = matriz.length;
        int n = matriz[0].length;

        // Calculamos la cantidad de filas y columnas que tienen al menos un elemento distinto de cero
        int cantFilasNoCero = 0;
        int cantColumnasNoCero = 0;
        for (int i = 0; i < m; i++) {
            boolean filaNoCero = false;
            for (int j = 0; j < n; j++) {
                if (matriz[i][j] != 0) {
                    filaNoCero = true;
                    break;
                }
            }
            if (filaNoCero) {
                cantFilasNoCero++;
            }
        }
        for (int j = 0; j < n; j++) {
            boolean columnaNoCero = false;
            for (int i = 0; i < m; i++) {
                if (matriz[i][j] != 0) {
                    columnaNoCero = true;
                    break;
                }
            }
            if (columnaNoCero) {
                cantColumnasNoCero++;
            }
        }
        // Si la cantidad de filas o columnas no cero es cero, retornamos una matriz de cero
        if (cantFilasNoCero == 0 || cantColumnasNoCero == 0) {
            return new int[1][1];
        }
        // Creamos la nueva matriz con las dimensiones adecuadas
        int[][] nuevaMatriz = new int[cantFilasNoCero][cantColumnasNoCero];

        // Copiamos los elementos no cero de la matriz original a la nueva matriz
        int filaNuevaMatriz = 0;
        for (int i = 0; i < m; i++) {
            boolean filaNoCero = false;
            int columnaNuevaMatriz = 0;
            for (int j = 0; j < n; j++) {
                if (matriz[i][j] != 0) {
                    nuevaMatriz[filaNuevaMatriz][columnaNuevaMatriz] = matriz[i][j];
                    columnaNuevaMatriz++;
                    filaNoCero = true;
                }
            }
            if (filaNoCero) {
                filaNuevaMatriz++;
            }
        }

        return nuevaMatriz;
    }

    public static int[][] obtenerResultado(int[][] A, int[][] B, String algoritmo) {

        if (algoritmo.equals("Tradicional")) {

            int[][] C = normalMultiplication(A, B);

            long endTime = System.nanoTime();

            return C;

        } else {
            if (matrizEsPotenciaDeDos(A) && matrizEsPotenciaDeDos(B)) {

                int[][] C = strassenMultiply(A, B);

                return C;

            } else if ((matrizEsPotenciaDeDos(A) == false) && (matrizEsPotenciaDeDos(B) == false)) {
                int[][] newMatrixA = completarConCeros(A);
                int[][] newMatrixB = completarConCeros(B);

                int[][] C = eliminateZeros(strassenMultiply(A, B));

                return C;

            } else if ((matrizEsPotenciaDeDos(A) == true) && (matrizEsPotenciaDeDos(B) == false)) {
                int[][] newMatrixB = completarConCeros(B);

                int[][] C = eliminateZeros(strassenMultiply(A, B));

                return C;

            } else if ((matrizEsPotenciaDeDos(A) == false) && (matrizEsPotenciaDeDos(B) == true)) {
                int[][] newMatrixB = completarConCeros(A);

                int[][] C = eliminateZeros(strassenMultiply(A, B));

                return C;
            }
            return A; // matriz
        }

    }
    public static long tiempoNanosegundos(int[][] A, int[][] B, String algoritmo) {
        if (algoritmo.equals("Tradicional")) {

            long startTime = System.nanoTime();

            int[][] C = normalMultiplication(A, B);

            long endTime = System.nanoTime();
            long totalTime = endTime - startTime;


            return totalTime;

        } else {
            if (matrizEsPotenciaDeDos(A) && matrizEsPotenciaDeDos(B)) {
                long startTime = System.nanoTime();

                int[][] C = strassenMultiply(A, B);

                long endTime = System.nanoTime();
                long totalTime = endTime - startTime;


                return totalTime;
            } else if ((matrizEsPotenciaDeDos(A) == false) && (matrizEsPotenciaDeDos(B) == false)) {
                int[][] newMatrixA = completarConCeros(A);
                int[][] newMatrixB = completarConCeros(B);

                long startTime = System.nanoTime();

                int[][] C = eliminateZeros(strassenMultiply(A, B));

                long endTime = System.nanoTime();
                long totalTime = endTime - startTime;


                return totalTime;

            } else if ((matrizEsPotenciaDeDos(A) == true) && (matrizEsPotenciaDeDos(B) == false)) {
                int[][] newMatrixB = completarConCeros(B);

                long startTime = System.nanoTime();

                int[][] C = eliminateZeros(strassenMultiply(A, B));

                long endTime = System.nanoTime();
                long totalTime = endTime - startTime;

                return totalTime;

            } else if ((matrizEsPotenciaDeDos(A) == false) && (matrizEsPotenciaDeDos(B) == true)) {
                int[][] newMatrixB = completarConCeros(A);

                long startTime = System.nanoTime();

                int[][] C = eliminateZeros(strassenMultiply(A, B));

                long endTime = System.nanoTime();
                long totalTime = endTime - startTime;

                return totalTime;
            }
            return 0;
        }

    }
    public void crearGrafico() {
        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
        int numero = 2;

        for(int i = 0; i < 9; i++) {

            String numerostr = String.valueOf(numero);
            dataset1.addValue(tiemposTradicional[i], "Tradicional", ""+numerostr);//tiempos para tradicional

            dataset1.addValue(tiemposStrassen[i], "Strassen", "" + numerostr);// tiempos para strassen
            numero = numero + numero;
        }

        chart = ChartFactory.createBarChart(
                "Tiempos de ejecucion para diferentes nxn", // chart title
                "Algoritmos de Multiplicacion de Matrices", // domain axis label
                "Tiempo en Nanosegundos", // range axis label
                dataset1, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true,
                false
        );

        ValueAxis axis2 = new NumberAxis3D("Tiempo en Nanosegundos");

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeAxis(1, axis2);
        plot.setDataset(1, dataset1);
        plot.mapDatasetToRangeAxis(1, 1);

        CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
        renderer2.setSeriesPaint(0, Color.red);
        plot.setRenderer(1, renderer2);
    }
    public static void realizarOperacion(int[][] A, int[][] B, int tamano){
        String size = Integer.toString(tamano);
        System.out.println(" > Para un tamano de " + size + "x" + size +" la comparacion es: ");
        System.out.println("El tiempo de ejecucion usando el metodo tradicional es de: " + tiempoNanosegundos(A, B, "Tradicional") + " nanosegundos.");
        System.out.println("El tiempo de ejecucion usando el metodo Strassen es de: " + tiempoNanosegundos(A, B, "Strassen") + " nanosegundos.");
        int[][] C = obtenerResultado(A, B, "Tradicional");
        int[][] D = obtenerResultado(A, B, "Strassen");
        System.out.println("Las matrices resultantes son: ");
        System.out.println(Arrays.deepToString(C));
        System.out.println("-------------------------------");
        System.out.println(Arrays.deepToString(D));
        System.out.println("-------------------------------");
        if (comparison(C, D)){
            System.out.println("Las matrices resultantes son iguales");
        }else {
            System.out.println("Las matrices resultantes no son iguales");
        }
    }

    public static void mostrarMenuPredeterminado(){
        int[] potencias = {2, 4, 8, 16, 32, 64, 128, 256, 512};
        Scanner scan = new Scanner(System.in);
        System.out.println("-- Seleecione un tamaño predeterminado: --");
        System.out.println(" 1 | 2x2");
        System.out.println(" 2 | 4x4");
        System.out.println(" 3 | 8x8");
        System.out.println(" 4 | 16x16");
        System.out.println(" 5 | 32x32");
        System.out.println(" 6 | 64x64");
        System.out.println(" 7 | 128x128");
        System.out.println(" 8 | 256x256");
        System.out.println(" 9 | 512x512");

        int opcion = scan.nextInt();
        int tamano = potencias[opcion-1];
        int[][] A = RandomMatrix(tamano);
        int[][] B = RandomMatrix(tamano);

        System.out.println("--------- Matriz 1: -------");
        System.out.println(Arrays.deepToString(A));
        System.out.println("--------- Matriz 2: -------");
        System.out.println(Arrays.deepToString(B));
        realizarOperacion(A, B, tamano);

        }

    public static void menu() {

        int[] potencias = {2, 4, 8, 16, 32, 64, 128, 256, 512, 1024};

        Scanner scan = new Scanner(System.in);
        boolean condicion = true;
        while (condicion) {
            System.out.println("-------Comparacion entre Algoritmo Naive y Strassen------");
            System.out.println("-- Seleccione una opcion: --");
            System.out.println("-- | 1 | Escoger un tamano predeterminado --");
            System.out.println("-- | 2 | Realizar grafico para varios valores --");
            System.out.println("-- | 3 | Salir --");

            int opcion_1 = scan.nextInt();

            if (opcion_1 == 1) {
                mostrarMenuPredeterminado();
            } else if (opcion_1 == 2) {
                System.out.println("El grafico es el siguiente: ");
                for (int i = 0; i < 9; i++) {
                    int tamano2 = potenciasDe2[i];
                    int[][] C = RandomMatrix(tamano2);
                    int[][] D = RandomMatrix(tamano2);
                    tiemposTradicional[i] = tiempoNanosegundos(C, D, "Tradicional");
                    tiemposStrassen[i] = tiempoNanosegundos(C, D, "Strassen");
                }
                DACCodigo demo = new DACCodigo();
            } else if (opcion_1 == 3) {
                condicion = false;
            } else {
                System.out.println("Opcion invalida");
            }
        }
    }


    public static void main(String[] args) {
/*
        int[][] A = {{2, 3, 4, 5}, {4, 5, 6, 7}, {7, 8, 9, 10}};
        int[][] B = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {1, 1, 1, 1}};
        int[][] C = completarConCeros(A);
        System.out.println(Arrays.deepToString(C));
        int[][] D = eliminateZeros(C);
        System.out.println(Arrays.deepToString(D));
        */
        menu();
       // DACCodigo demo = new DACCodigo();
    }

}