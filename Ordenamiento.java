import java.util.Arrays;

public class Ordenamiento {
    public Ordenamiento(){}

    public double ordenarSimple(Double[] arr){
        
        long iniTiempo = System.nanoTime();
        Arrays.sort(arr);
        long finTiempo = System.nanoTime();
        
        return (finTiempo - iniTiempo)/1_000_000.0;
    }


    public double ordenarParalelo(Double[] arr){
        
        long iniTiempo = System.nanoTime();
        Arrays.parallelSort(arr);
        long finTiempo = System.nanoTime();
        
        return (finTiempo - iniTiempo)/1_000_000.0;
    }


    public double ordenarQuickSort(Double[] arr){
        
        long iniTiempo = System.nanoTime();
        qSort(arr,0,arr.length-1);
        long finTiempo = System.nanoTime();
        
        return (finTiempo - iniTiempo)/1_000_000.0;
    }


    private void qSort(Double[] arr,int inf,int sup){
        int limite;

        if(inf>=sup) return;
        
        intercambiar(arr, inf, (inf+sup)/2);
        limite = inf;

        for(int i=inf+1; i<=sup; i++)
            if( arr[inf] > arr[i] )
                intercambiar(arr, ++limite, i);

        intercambiar(arr, inf, limite);
        qSort(arr, inf, limite-1);
        qSort(arr, limite+1, sup);
    }


    private void intercambiar(Double[] arr,int i,int j){
        Double temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    public double ordenarHeapSort(Double[] arr){
        heapSort(arr);
        return 0;
    }

    private void heapSort(Double[] arr) {
        int n = arr.length;

        // Construir el max-heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Extraer elementos uno por uno
        for (int i = n - 1; i > 0; i--) {
            double temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    private void heapify(Double[] arr, int n, int i) {
        int mayor = i;
        int izquierda = 2 * i + 1;
        int derecha = 2 * i + 2;

        if (izquierda < n && arr[izquierda] > arr[mayor]) {
            mayor = izquierda;
        }

        if (derecha < n && arr[derecha] > arr[mayor]) {
            mayor = derecha;
        }

        if (mayor != i) {
            double temp = arr[i];
            arr[i] = arr[mayor];
            arr[mayor] = temp;

            heapify(arr, n, mayor);
        }
    }


    public double ordenarBubbleSort(Double[] arr){
        long iniTiempo = System.nanoTime();
        for(int i=0; i<arr.length-1; i++){
            for(int j=i+1; j<arr.length; j++){
                if(arr[i]>arr[j])
                    intercambiar(arr, i, j);
            }
        }
        long finTiempo = System.nanoTime();
        
        return (finTiempo - iniTiempo)/1_000_000.0;
    }
}
