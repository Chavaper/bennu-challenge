import java.util.Scanner;

class Challenge {
    public static final int FIN = 6; 
    

    public static void main(String []args){
        try{
            Scanner cin = new Scanner(System.in);
            Programa programa = new Programa();

            while(true){
                int opcion = programa.escogerMenu(cin);
                if(opcion==FIN) break;
                programa.operar(opcion, cin);
            }
        }catch(Exception e){
            System.out.print("*** EXCEPTION: "+ e);
        }
        
    }
}
