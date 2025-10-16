import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Agenda {

    private ArrayList<Contacto> contactos;
    private File fichero;
    private DataOutputStream flujoSalida;
    private DataInputStream flujoEntrada;

    public Agenda(File fichero) {
        this.fichero = fichero;
        this.contactos = new ArrayList<>();
    }

    public void cabecera() {
        System.out.println("\tNombre\tTelefono\tEmail");
        System.out.println("\t=============================");
    }

    public void llenar() {
        contactos.clear();
        contactos.add(new Contacto("Ismael", "600225475", "ismael.couso.10@gmail.com"));
        contactos.add(new Contacto("Diego", "696543972", "diegosantosgonzalez7@gmail.com"));
        contactos.add(new Contacto("Javier", "741258639", "javierportosin@gmail.com"));
        contactos.add(new Contacto("Diego", "521463207", "diegopiferrer19@gmail.com"));
        contactos.add(new Contacto("Alex", "123456789", "ailavirgen@gmail.com"));
        System.out.println("Agenda creada con contactos de ejemplo.");
    }

    public void vaciar() {
        contactos.clear();
        System.out.println("Agenda vaciada.");
    }

    public void mostrar() {
        if (contactos.isEmpty()) {
            System.out.println("Agenda vacía.");
        } else {
            cabecera();
            int x = 0;
            for (Contacto c : contactos) {
                System.out.print(x + ".- ");
                c.mostrarContacto();
                x++;
            }
        }
    }

    public void anhadir() {
        System.out.print("Introduce nombre: ");
        String nombre = Leer.datoString();

        System.out.print("Introduce email: ");
        String mail = Leer.datoString();

        System.out.print("Introduce teléfono: ");
        String telefono = Leer.datoString();

        contactos.add(new Contacto(nombre, telefono, mail));
        System.out.println("Contacto añadido correctamente.");
    }

    public void modificar() {
        mostrar();
        System.out.print("Introduce el índice del contacto a modificar: ");
        int i = Leer.datoInt();

        if (i < 0 || i >= contactos.size()) {
            System.err.println("Índice no válido.");
            return;
        }

        System.out.print("Introduce nuevo nombre: ");
        String nuevoNombre = Leer.datoString();

        System.out.print("Introduce nuevo teléfono: ");
        String nuevoTelefono = Leer.datoString();

        System.out.print("Introduce nuevo email: ");
        String nuevoEmail = Leer.datoString();

        Contacto contactoNuevo = new Contacto(nuevoNombre, nuevoTelefono, nuevoEmail);
        contactos.set(i, contactoNuevo);

        System.out.println("Contacto modificado correctamente.");
    }

    public void restaurar() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fichero))) {
            contactos.clear();
            while (true) {
                String nombre = dis.readUTF();
                String telefono = dis.readUTF();
                String email = dis.readUTF();
                contactos.add(new Contacto(nombre, telefono, email));
            }
        } catch (EOFException e) {
            System.out.println("Contactos restaurados desde el archivo.");
        } catch (IOException e) {
            System.err.println("Error al restaurar el fichero.");
        }
    }

    public Contacto buscarPorNombre() {
        System.out.print("Introduce el nombre del contacto a buscar: ");
        String nombreBuscado = Leer.datoString();

        for (Contacto c : contactos) {
            if (c.getNombre().equalsIgnoreCase(nombreBuscado)) {
                System.out.println("Contacto encontrado:");
                c.mostrarContacto();
                return c;
            }
        }
        System.out.println("No se ha encontrado ningún contacto con ese nombre.");
        return null;
    }


    private void menu() {
		System.out.println("\n  M E N U ");
		System.out.println("  ====================");
		System.out.println("1.- CREAR Agenda");
		System.out.println("2.- ANHADIR Contacto");
		System.out.println("3.- CONSULTAR Contacto");
		System.out.println("4.- MODIFICAR Contacto");
		System.out.println("5.- BORRAR Contacto");
		System.out.println("6.- RESTAURAR Contacto");
        System.out.println("7.- VER Contactos");
        System.out.println("8.- VACIAR Agenda");
        System.out.println("9.- VER MAS OPCIONES");
		System.out.println("10.- FINAL");
		System.out.print("Pulsa opcion: ");
	}


    /*public int dameOpcion() {
		int opcion;

		menu();
		opcion = Leer.datoInt();
		System.out.println();
		return opcion;
	}
}
*/
