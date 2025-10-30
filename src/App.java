import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {

        File fichero = new File("agenda.dat"); 
        Agenda agenda = new Agenda(fichero);

        String opcion;

        do {
            opcion = agenda.dameOpcion(); 

            switch (opcion) {
                case "1":
                    agenda.mostrarPrimerMenu();
                    break;

                case "2":
                    agenda.anhadir();
                    break;

                case "3":
                    agenda.consultarContacto();
                    break;

                case "4":
                    agenda.modificar();
                    agenda.guardar();
                    break;

                case "5":
                    agenda.Borrar();
                    agenda.guardar();
                    break;

                case "6":
                    agenda.restaurar();
                    break;

                case "7":
                    agenda.mostrar();
                    break;

                case "8":
                    agenda.vaciar();
                    agenda.guardar();
                    break;

                case "9":
                   agenda.mostrarUltimoMenu();
                    break;

                case "10":
                    System.out.println("Saliendo del programa...");
                    break;

                
                default:
                    System.out.println("Opción errónea. Intente de nuevo.");
                    break;
            }

        } while (!opcion.equals("10"));
    }
}