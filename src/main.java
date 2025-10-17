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
                    agenda.llenar();
                    agenda.guardar();
                    break;

                case 2:
                    agenda.anhadir();
                    break;

                case 3:
                    agenda.buscarPorNombre();
                    break;

                case 4:
                    agenda.modificar();
                    break;

                case 5:
                    System.out.println("Función BORRAR contacto aún no implementada.");
                    break;

                case 6:
                    agenda.restaurar(); //falta hacer que solo restaure los contactos borrados
                    break;

                case 7:
                    agenda.mostrar();
                    break;

                case 8:
                    agenda.vaciar();
                    break;

                case 9:
                    System.out.println("Más opciones próximamente...");
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
