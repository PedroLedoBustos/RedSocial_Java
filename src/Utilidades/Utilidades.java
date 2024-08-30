package Utilidades;

import java.util.Scanner;

public class Utilidades {
    static Scanner scanner= new Scanner(System.in);

    public static String leerString(String dato){
        System.out.println(dato);
        return scanner.nextLine(); 
    }

    public static Integer leerInteger(String dato){
        while (true) {
            try {
                System.out.println(dato);
                String respuesta= scanner.nextLine();
                Integer respInt= Integer.parseInt(respuesta);
                return respInt;
            } catch (Exception e) {
                System.out.println("Escoge una opción válida");
            }

        }
    }

    public static Float leerFloat(String dato){
        while (true) {
            try {
                System.out.println(dato);
                String respuesta= scanner.nextLine();
                Float respFloat= Float.parseFloat(respuesta);
                return respFloat;
            } catch (Exception e) {
                System.out.println("Escoge una opción válida");
            }

        }
    }
}
