package Coseno;
import java.util.Random;
import java.util.Scanner;

public class Coseno {

    static Random rand = new Random();
    static Scanner read = new Scanner(System.in);
    static int limS = 3;
    static int limI = 0;

  
    public static void main(String[] args) {
        int k;
        
        System.out.println("Calcular el area de 3cos(x) por debajo entre:\nx = 0\nx = pi/2\n");
        System.out.println("Introduce el tama�o de la muestra:\n");
        k = read.nextInt();
        double [] valores = new double[k];
        
        System.out.println("Area segun VM= "+calcularAreaVM(valores, k));
        System.out.println("Area proporcion = "+calcularAreaP(k));
        System.out.println("Area real= "+areaFuncion());
        
        
    }
    
    public static double calcularAreaVM(double [] valores, int k){
        double randomX;
        double randomY;
        int i;
        double total = 0;
        
        for (i=0; i<k; i++){
           randomX = rand.nextDouble()*Math.PI/2 - 0;
           randomY = funcion(randomX);
           valores[i] = randomX * randomY;
           total += valores[i];
        }
        intervaloConfianza(valores);
        return total/k;
    }
    
    public static double calcularAreaP(int k){
        int i;
        double randomX, randomY;
        int validos = 0;
        
        for (i=0; i<k; i++){
            randomX = rand.nextDouble()*Math.PI/2 - 0;
            randomY = rand.nextDouble()*limS - limI;
            if (randomY < funcion(randomX)){
                validos++;
            }
        }
        //System.out.println("validos/k = "+((double)validos/k));
        intervaloConfianzaP((double)validos/k, k);
        return limS*Math.PI/2*validos/k;
    }
    
    public static double funcion(double x){
        return Math.cos(x)*3;
    }
    
    public static double areaFuncion(){
        return 3*(Math.sin(limS)) - 3*(Math.sin(limI));
    }
    
    public static void intervaloConfianza(double [] valores){
        double [] intervalos = new double [2];
        double media = media(valores);
        double cuasiVarianza = cuasiV(valores, media);
        
        intervalos[0] = media - (1.96*(cuasiVarianza/Math.sqrt(valores.length)));
        intervalos[1] = media + (1.96*(cuasiVarianza/Math.sqrt(valores.length)));
        
        System.out.println("Intervalo de confianza: ("+intervalos[0]+", "+intervalos[1]+")");
    }
    
    public static void intervaloConfianzaP(double p, int n){
        double [] intervalos = new double [2];
        System.out.println("p = "+p);
        intervalos[0] = p - (1.96 * Math.sqrt(p*(1-p)/(double)n));
        intervalos[1] = p + (1.96 * Math.sqrt(p*(1-p)/(double)n));
        System.out.println("Intervalo de confianza con Proporcion: ("+intervalos[0]+", "+intervalos[1]+")");
    }
    
    public static double media (double [] valores){
        int i;
        double media = 0.0;
        
        for (i=0; i<valores.length; i++){
            media += valores[i];
        }
        return media/valores.length;
    }
    
    public static double cuasiV(double [] valores, double media){
        double cuasiVarianza = 0.0;
        int i;
        
        for (i=0; i<valores.length; i++){
            cuasiVarianza += Math.pow((valores[i] - media), 2);
        }
        //System.out.println("Casi cuasivarianza = "+cuasiVarianza+"\n");
        System.out.println(valores.length);
        return cuasiVarianza/(valores.length-1);
    }

}
