import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Programa {
    /*Los numeros manejados deben estar en un intervalo de numeros,
    * por defecto, que estén entre <Integer.MIN_VALUE; Integer.MAX_VALUE>
    */
    public static final int INFERIOR = -15;
    public static final int SUPERIOR = 15;
    public static final double MAXDIF = 0.001;

    public Programa() {
    }

    public int escogerMenu(Scanner cin) {
        System.out.println("Opcion");
        System.out.println("----------------------");
        System.out.println("0 - Menu");
        System.out.println("1 - Genera nuevo archivo");
        System.out.println("2 - Lee archivo generado");
        System.out.println("3 - Ordena archivo");
        System.out.println("4 - Lee archivo ordenado");
        System.out.println("5 - Buscar numero en archivo");
        System.out.println("6 - Salir");
        System.out.println();

        return obtenerOpcion(cin,0,6);
    }

    private int obtenerOpcion(Scanner cin, int opcionMinima,int opcionMaxima){
        while(true){
            System.out.print(">> Seleccione una opcion: ");
            int opcion = cin.nextInt();
            if(enIntervalo(opcion, opcionMinima, opcionMaxima)) return opcion;
            System.out.println("ERROR: Eliga una opcion valida");
        }
    }


    public void operar(int opcion, Scanner cin) throws IOException {
        if (opcion == 0) return;
        
        if (opcion > 6) System.out.println("Ingrese una opcion valida");
        else if (opcion == 1) generarNuevoArchivo("numerosOriginales.txt", cin);
        else if (opcion == 2) leerArchivo("numerosOriginales.txt");
        else if (opcion == 3) ordenarArchivo("numerosOriginales.txt","numerosOrdenada.txt", cin);
        else if (opcion == 4) leerArchivo("numerosOrdenada.txt");
        else buscarNumeroEnArchivo("numerosOrdenada.txt", cin);
    }




    private void generarNuevoArchivo(String nombreArchivo, Scanner cin) throws IOException {
        long iniTiempo = System.nanoTime();
        
        int n = solicitarCantidad(cin); // Se pide y solicita una cantidad correcta de numeros

        FileWriter arch = new FileWriter(nombreArchivo);
        Random random = new Random();

        // Se escriben numeros aleatorios en el archivo de salida
        for (int i = 0; i < n; i++)
            arch.write(random.nextDouble(INFERIOR, SUPERIOR) + "\n");
        arch.close();

        long finTiempo = System.nanoTime();
        double tiempo = (finTiempo - iniTiempo)/1_000_000.0;
        System.out.printf("Tiempo generando archivo: %.6f ms \n",tiempo);
    }


    private int solicitarCantidad(Scanner cin) {
        while (true) {
            System.out.print("¿Cuantos numeros quiere generar?: ");
            int n = cin.nextInt(); //se lee la cantidad de enteros

            // La cantidad ingresada debe tener sentido
            if (enIntervalo(n, 0, Integer.MAX_VALUE)) return n; //Si tiene sentido, devolvemos el valor ingresado

            if (n < 0) System.out.println("ERROR: No se aceptan cantidades negativas");
            else System.out.printf("ERROR: La cantidad debe estar entre %d y %d", INFERIOR, SUPERIOR); //Cantidad fuera del intervalo
        }
    }


    private boolean enIntervalo(int n,int inf,int sup){
        return inf<=n && n<=sup;
    }


    private void leerArchivo(String nombreArchivo) throws IOException {
        long iniTiempo = System.nanoTime();
        
        Scanner arch = new Scanner(new FileReader(nombreArchivo));
        while (arch.hasNext()) {
            double num = arch.nextDouble();
            System.out.printf("%.6f%n", num);
        }
        System.out.println();
        arch.close();

        long finTiempo = System.nanoTime();
        double tiempo = (finTiempo - iniTiempo)/1_000_000.0;
        System.out.printf("Tiempo leyendo archivo: %.6f ms \n",tiempo);
    }




    private void ordenarArchivo(String nombArchSinOrdenar, String nombreArchOrdenados, Scanner cin) throws IOException {
        FileWriter archOrdenado = new FileWriter(nombreArchOrdenados);
        
        //1. Obtenemos los numeros del archivo
        Double[] arr = leerData(nombArchSinOrdenar);

        //2. Solicitamos el metodo de ordenamiento, y ordenamos
        int metodo = seleccionarMetodo(cin);
        double tiempo = obtenerTiempoDeOrdenamiento(arr,metodo);

        //3. Guardamos los datos ordenados en el archivo de ordenados
        for(int i=0; i<arr.length; i++) 
            archOrdenado.write(arr[i] + "\n");

        System.out.printf("Tiempo de ordenamiento: %.6f ms %n", tiempo);

        archOrdenado.close();
    }

    private Double[] leerData(String nombreArch) throws IOException{
        Scanner archSinOrdenar = new Scanner(new FileReader(nombreArch));
        ArrayList<Double> arrNum = new ArrayList<Double>();
        
        while(archSinOrdenar.hasNext()) arrNum.add(archSinOrdenar.nextDouble());  
        
        Double[] arr = arrNum.toArray(new Double[arrNum.size()]);

        archSinOrdenar.close();
        return arr;
    }

    private double obtenerTiempoDeOrdenamiento(Double[] arr,int metodo){
        Ordenamiento sorting = new Ordenamiento();

        if(metodo==1) return sorting.ordenarSimple(arr);
        else if(metodo==2) return sorting.ordenarParalelo(arr);
        else if(metodo==3) return sorting.ordenarQuickSort(arr);
        else if(metodo==4) return sorting.ordenarHeapSort(arr);
        return sorting.ordenarBubbleSort(arr);
    }


    private int seleccionarMetodo(Scanner cin){
        System.out.println("Eliga un metodo de ordenamiento:");
        System.out.println("1- Java Sort");
        System.out.println("2- Java ParallelSort");
        System.out.println("3- Java QuickSort");
        System.out.println("4- Java HeapSort");
        System.out.println("5- Java BubbleSort");
        System.out.println("");
        
        return obtenerOpcion(cin,1,5);
    }

    private void buscarNumeroEnArchivo(String nombreArchivo, Scanner cin) throws IOException {
        long iniTiempo = System.nanoTime();
        
        Double[] arr = leerData(nombreArchivo);
        
        System.out.print("¿Que numero esta buscsando?: ");
        double k = cin.nextDouble();
        
        int pos = busquedaBinaria(arr, k, 0,arr.length-1);

        long finTiempo = System.nanoTime();
        double tiempo = (finTiempo - iniTiempo)/1_000_000.0;
        System.out.printf("Tiempo buscando numero: %.6f ms \n\n",tiempo);

        if(pos>-1) System.out.print("El numero " + k + " FUE ENCONTRADO en la posicion " + pos + " \n");
        else System.out.printf("El numero %.2f NO fue encontrado \n",k,pos);
    }

    private int busquedaBinaria(Double[] arr,double k,int ini,int fin){
        if(ini>fin) return -1;

        int medio = (ini+fin)/2;
        if( esIgual(arr[medio], k) ) return medio;
        
        if( k < arr[medio] ) return busquedaBinaria(arr, k, ini, medio-1);
        return busquedaBinaria(arr, k, medio+1, fin);
    }

    private boolean esIgual(double a,double b){
        return Math.abs(a - b)<=MAXDIF; 
    }
}
