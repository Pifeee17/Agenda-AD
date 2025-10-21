import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Agenda {

    private ArrayList<Contacto> contactos;
    private File fichero;

    boolean encontrado = false;

    public Agenda(File fichero) {
        this.fichero = fichero;
        this.contactos = new ArrayList<>();
    }

    public void cabecera() {
        System.out.println("\tNombre\t\tTelefono\t\tEmail");
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
    System.out.print("Introduce el nombre del contacto a buscar: ");
    String buscarNombre = Leer.datoString();

    boolean nombreEncontrado = false;

    if (fichero == null) {
        System.err.println("No se ha establecido fichero para la agenda.");
        return;
    }

    if (!fichero.exists()) {
        System.err.println("El fichero " + fichero.getAbsolutePath() + " no existe.");
        return;
    }

    try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(fichero)))) {
        
        while (true) {
            String nombre = dis.readUTF();
            String telefono = dis.readUTF();
            String email = dis.readUTF();

            if (nombre.equalsIgnoreCase(buscarNombre)) {
                contactos.add(new Contacto(nombre, telefono, email));
                nombreEncontrado = true;
                break; 
            }
        }
    } catch (IOException e) {
        if (!nombreEncontrado) {
            System.out.println("No se encontró el contacto: " + buscarNombre);
        } else {
            System.out.println("Contacto restaurado correctamente.");
        }
    }
}

    public Contacto consultarContacto() {
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

    public void guardar() {
    if (fichero == null) {
        System.err.println("No se ha establecido fichero para la agenda.");
        return;
    }

    try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fichero)))) {
        for (Contacto c : contactos) {
            dos.writeUTF(c.getNombre());
            dos.writeUTF(c.getTelefono());
            dos.writeUTF(c.getEmail());
            
        }
        System.out.println("Agenda guardada correctamente en: " + fichero.getAbsolutePath());
    } catch (IOException e) {
        System.err.println("Error al guardar la agenda: " + e.getMessage());
    }
}

public void Borrar(){

     String rutaCarpeta = "F:/Dam2/AD/Java/Repaso_DAM1/Agenda";
        String nombreArchivo = "ContactosBorrados.dat";

        File carpeta = new File(rutaCarpeta);
        File archivo = new File(carpeta, nombreArchivo);

        if (!carpeta.exists()) {
        carpeta.mkdirs();
    }

    boolean encontrado = false;

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) { 

        System.out.println("Introduce el nombre del contacto a borrar: ");
        String nombreBorrar = Leer.datoString();

        Iterator<Contacto> it = contactos.iterator();
        while (it.hasNext()) {
            Contacto c = it.next();
            if (c.getNombre().equalsIgnoreCase(nombreBorrar)) {
                System.out.println("Contacto encontrado:");
                c.mostrarContacto();
                it.remove();
                encontrado = true;

                bw.write("Nombre: "+c.getNombre() + "  Telefono: " + c.getTelefono() + "  Email: " + c.getEmail());
                bw.newLine();

                System.out.println("Contacto borrado correctamente.");
                break;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró ningún contacto con ese nombre.");
        }

    } catch (IOException e) {
        System.out.println("Error al escribir el archivo: " + e.getMessage());
    }
    
}
    public static void informacionDelArchivo(File fichero){
        System.out.println("\tI N F O  F I C H E R O ");
        System.out.println(" ================================");

        if(fichero.exists()){
            System.out.println("Ubicacion: " + fichero.getAbsolutePath());
            System.out.println("Tamaño: " + fichero.length() + " bytes");
            System.out.println("Permisos: Lectura: " + fichero.canRead() + "; Escritura: " + fichero.canWrite() + "; Ejecución: " + fichero.canExecute());
            Long ultimaModificacion = fichero.lastModified();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            System.out.println("Ultima modificacion: " + sdf.format(new Date(ultimaModificacion)));

        }
    }

    private void primerMenu() {
        System.out.println("\tC R E A R  A G E N D A ");
        System.out.println(" ============================= ");
        System.out.println("1.- CREAR Agenda LLENA.");
        System.out.println("2.- CREAR Agenda VACIA.");
        System.out.println("3.- Volviendo al menu principal.");
    }

    public void mostrarPrimerMenu() throws IOException{
        int opcion;

        do{
            primerMenu();
            System.out.println("Elige una opcion: ");
            opcion = Leer.datoInt();

            switch (opcion){
                case 1:
                llenar();
                guardar();
                break;

                case 2:
                vaciar();
                guardar();
                break;

                case 3:
                System.out.println("Volviendo al menu principal...");
                break;

                default:
                System.out.println("Opcion erronea. Intentao de novo.");
            }
        } while (opcion!=3);
    }

    private void segundoMenu() {
		System.out.println("\tA G E N D A ");
		System.out.println("  =======================");
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

    private void ultimoMenu() {
        System.out.println("\tM A S  O P C I O N E S");
        System.out.println(" ================================");
        System.out.println("1.- Mostrar informacion del archivo.");
        System.out.println("2.- Hacer copia de seguridad.");
        System.out.println("3.- Restaurar copia de seguridad.");
        System.out.println("4.- Volver al menu principal.");
    }

    public void mostrarUltimoMenu() throws IOException {
    int opcion;

    do {
        ultimoMenu();
        System.out.print("Elige una opción: ");
        opcion = Leer.datoInt();

        switch (opcion) {
            case 1:
               informacionDelArchivo(fichero);
                break;
            case 2:
                System.out.println("Opcion de copia de seguridad no implementada.");
                break;
            case 3:
                System.out.println("Opcion de restaurar copia de seguridad no implementada.");
                break;
            case 4:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción no válida. Intenta de nuevo.");
        }
    } while (opcion != 4);
}

    public int dameOpcion() {
		int opcion;

		segundoMenu();
		opcion = Leer.datoInt();
		System.out.println();
		return opcion;
	}
}

