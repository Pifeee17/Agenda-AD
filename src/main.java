import java.io.File;
import java.io.IOException;

public class main {

    public static void main(String[] args) throws IOException {

        File fichero = new File("agenda.dat"); 
        Agenda agenda = new Agenda(fichero);

        int opcion;

        do {
            opcion = agenda.dameOpcion(); 

            switch (opcion) {
                case 1:
                    agenda.mostrarPrimerMenu();
                    break;

                case 2:
                    agenda.anhadir();
                    agenda.guardar();
                    break;

                case 3:
                    agenda.consultarContacto();
                    break;

                case 4:
                    agenda.modificar();
                    agenda.guardar();
                    break;

                case 5:
                    agenda.Borrar();
                    agenda.guardar();
                    break;

                case 6:
                    agenda.restaurar();
                    agenda.guardar();
                    break;

                case 7:
                    agenda.mostrar();
                    break;

                case 8:
                    agenda.vaciar();
                    agenda.guardar();
                    break;

                case 9:
                   agenda.mostrarPrimerMenu();
                    break;

                case 10:
                    System.out.println("Saliendo del programa...");
                    break;

                
                default:
                    System.out.println("Opción errónea. Intente de nuevo.");
                    break;
            }

        } while (opcion != 10);
    }
}
