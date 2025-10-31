import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class Agenda {

    private ArrayList<Contacto> contactos;
    private File fichero;

    boolean encontrado = false;

    public Agenda(File fichero) {
        this.fichero = fichero;
        this.contactos = new ArrayList<>();
    }

    public void cabecera() {
        System.out.println("Nombre\t\tTelefono\t\tEmail");
        System.out.println("=============================================");
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
        String nombre;
        
       while (true) {
        System.out.print("Introduce nombre: ");
       nombre = Leer.datoString();
        if (nombre.matches("[A-Z].*[a-z]")){
        break;
        }else {
            System.out.println("El formato del nombre no es correcto. Inténtalo de nuevo.");
        }
       }  
        
        String mail;
        //Creamos bucle infinito hasta que el email sea correcto
       while (true) { 
        System.out.print("Introduce email: ");
        mail = Leer.datoString();

        if (mail.contains("@") && mail.contains(".") && mail.length()>7) {
            break; //Valida el email
        } else {
            System.out.println("El formato de email no es correcto. Inténtalo de nuevo.");
        }
    }

        String telefono;
        while (true) {
            System.out.print("Introduce teléfono: ");
            telefono = Leer.datoString();
            if (telefono.matches("[0-9]{9}")) {  //Valida que tenga 9 dígitos
                break;
            }else{
                System.out.println("El formato del número de telefono no es correcto.");
            }
        }
        

        contactos.add(new Contacto(nombre, telefono, mail));
        System.out.println("Contacto añadido correctamente.");
        guardar();
    }

    public void modificar() {
        mostrar();
        System.out.print("Introduce el índice del contacto a modificar: ");
        int i = Leer.datoInt();

        if (i < 0 || i >= contactos.size()) {
            System.err.println("Índice no válido.");
            return;
        }
        String nuevoNombre;
        while (true) {
        System.out.print("Introduce nombre: ");
       nuevoNombre = Leer.datoString();
        if (nuevoNombre.matches("[A-Z].*[a-z]")){
        break;
        }else {
            System.out.println("El formato del nombre no es correcto. Inténtalo de nuevo.");
        }
       }  
        System.out.print("Introduce nuevo nombre: ");
        nuevoNombre = Leer.datoString();

         String nuevoEmail;
       while (true) {
        System.out.print("Introduce email: ");
        nuevoEmail = Leer.datoString();

        if (nuevoEmail.contains("@") && nuevoEmail.contains(".")) {
            break; //Valida el email
        } else {
            System.out.println("El formato de email no es correcto. Inténtalo de nuevo.");
        }
    }

        String nuevoTelefono;
        while (true) {
            System.out.print("Introduce teléfono: ");
            nuevoTelefono = Leer.datoString();
            if (nuevoTelefono.matches("[0 - 9]") || nuevoTelefono.length()==9) {
                break;
            }else{
                System.out.println("El formato del número de telefono no es correcto.");
            }
        }

        Contacto contactoNuevo = new Contacto(nuevoNombre, nuevoTelefono, nuevoEmail);
        contactos.set(i, contactoNuevo);

        System.out.println("Contacto modificado correctamente.");
    }

    public void consultarContacto() {
    mostrar(); 
    System.out.print("Introduce el nombre del contacto a buscar: ");
    String nombreBuscado = Leer.datoString();

    boolean encontrado = false;

    for (Contacto c : contactos) {
        if (c.getNombre().toLowerCase().contains(nombreBuscado.toLowerCase())) {
            if (!encontrado) {
                System.out.println("Contactos encontrados:");
            }
            c.mostrarContacto();
            encontrado = true;
        }
    }

    if (!encontrado) {
        System.out.println("No se ha encontrado ningún contacto con ese nombre.");
    }
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

public void Borrar() {
    mostrar();

    String rutaCarpeta = "F:/Dam2/AD/Java/Repaso_DAM1/Agenda";
    String nombreArchivo = "ContactosBorrados.dat";

    File carpeta = new File(rutaCarpeta);
    File archivo = new File(carpeta, nombreArchivo);

    if (!carpeta.exists()) {
        carpeta.mkdirs();
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {

        System.out.println("Introduce el nombre del contacto a borrar: ");
        String nombreBorrar = Leer.datoString();

        //Buscar todos los contactos que coincidan
        ArrayList<Contacto> coincidencias = new ArrayList<>();
        for (Contacto c : contactos) {
            //El método equalsIgnoreCase se utiliza para comparar dos cadenas de texto ignorando si están en mayúsculas o minúsculas. Devuelve un boolean.
            if (c.getNombre().equalsIgnoreCase(nombreBorrar)) {
                coincidencias.add(c);
            }
        }

        if (coincidencias.isEmpty()) {
            System.out.println("No se encontró ningún contacto con ese nombre.");
            return;
        }

        //Si hay más de uno, preguntar cuál borrar
        if (coincidencias.size() > 1) {
            System.out.println("Se encontraron varios contactos con ese nombre:");
            for (int i = 0; i < coincidencias.size(); i++) {
                System.out.print((i + 1) + ". ");
                coincidencias.get(i).mostrarContacto();
            }

            System.out.print("Elige el índice del contacto a borrar: ");
            int opcion = Leer.datoInt();

            if (opcion < 1 || opcion > coincidencias.size()) {
                System.out.println("Opción inválida. No se borró ningún contacto.");
                return;
            }

            Contacto elegido = coincidencias.get(opcion - 1);

            //Borrar el elegido de la lista principal
            contactos.remove(elegido);

            //Guardar el borrado en ContactosBorrados.dat
            bw.write(elegido.getNombre() + "," + elegido.getTelefono() + "," + elegido.getEmail());
            bw.newLine();

            System.out.println("Contacto borrado correctamente.");

        } else {
            // Solo hay un contacto con ese nombre
            Contacto c = coincidencias.get(0);
            contactos.remove(c);
            bw.write(c.getNombre() + "," + c.getTelefono() + "," + c.getEmail());
            bw.newLine();
            System.out.println("Contacto borrado correctamente.");
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

   public void restaurar() {
    String rutaCarpeta = "F:/Dam2/AD/Java/Repaso_DAM1/Agenda";
    String nombreArchivo = "ContactosBorrados.dat";
    File archivo = new File(rutaCarpeta, nombreArchivo);

    if (!archivo.exists()) {
        System.out.println("No hay contactos borrados para restaurar.");
        return;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        ArrayList<String> lineas = new ArrayList<>();
        String linea;

        while ((linea = br.readLine()) != null) {
            lineas.add(linea);
        }

        if (lineas.isEmpty()) {
            System.out.println("No hay contactos borrados para restaurar.");
            return;
        }

        System.out.println("Contactos borrados:");
        for (int i = 0; i < lineas.size(); i++) {
            System.out.println((i + 1) + ". " + lineas.get(i));
        }

        System.out.print("Elige el índice del contacto a restaurar: ");
        int opcion = Leer.datoInt();

        if (opcion < 1 || opcion > lineas.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        String contactoTexto = lineas.get(opcion - 1);
        System.out.println("Restaurando: " + contactoTexto);

        //Parseo simple por comas 
        String[] partes = contactoTexto.split(",");
        if (partes.length == 3) {
            String nombre = partes[0].trim();
            String telefono = partes[1].trim();
            String email = partes[2].trim();

            Contacto c = new Contacto(nombre, telefono, email);
            contactos.add(c);
            guardar();
            System.out.println("Contacto restaurado correctamente.");

            //Eliminar el contacto restaurado del archivo de borrados
            lineas.remove(opcion - 1);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
                for (String l : lineas) {
                    bw.write(l);
                    bw.newLine();
                }
            }

            System.out.println("Contacto eliminado de ContactosBorrados.dat.");
        } else {
            System.out.println("Formato de contacto incorrecto: " + contactoTexto);
        }

    } catch (IOException e) {
        System.out.println("Error al leer el archivo de borrados: " + e.getMessage());
    }
}

    public static void copiaDeSeguridad(File fichero){
    if (fichero == null || !fichero.exists()) {
        System.out.println("No se ha encontrado el archivo de la agenda.");
        return;
    }

    // Carpeta donde se guardarán las copias
    String carpetaCopias = "F:/Dam2/AD/CopiasAgenda/";
    File carpeta = new File(carpetaCopias);
    if (!carpeta.exists()) {
        carpeta.mkdirs();
    }

    // Nombre base del archivo
    String nombreBase = "PruebaCopiaAgenda";

    // Obtener fecha y hora actuales
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm");
    String fechaHora = sdf.format(new Date());

    // Crear ruta final de la copia
    String rutaCopia = carpetaCopias + nombreBase + "_" + fechaHora;

    try {
        Path origen = Paths.get(fichero.getAbsolutePath());
        Path destino = Paths.get(rutaCopia);

        Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("La copia de seguridad fue realizada con éxito: " + rutaCopia);
    } catch (IOException e) {
        System.out.println("Error al crear la copia de seguridad.");
        e.printStackTrace();
    }
}

    public static void restaurarCopiaDeSeguridad(File fichero) {
    String carpetaCopias = "F:/Dam2/AD/CopiasAgenda/";
    File carpeta = new File(carpetaCopias);

    if (!carpeta.exists()) {
        System.out.println("No existe la carpeta de copias de seguridad.");
        return;
    }

    File[] copias = carpeta.listFiles();
    if (copias == null || copias.length == 0) {
        System.out.println("No hay copias de seguridad disponibles.");
        return;
    }

    System.out.println("Copias de Seguridad disponibles:");
    for (int i = 0; i < copias.length; i++) {
        System.out.println((i + 1) + ". " + copias[i].getName());
    }

    Scanner sc = new Scanner(System.in);
    int opcion = -1;

    while (true) {
        System.out.print("Escriba el número de la copia que desea restaurar: ");
        String entrada = sc.nextLine();

        try {
            opcion = Integer.parseInt(entrada);

            if (opcion < 1 || opcion > copias.length) {
                System.out.println("Número inválido. Intente de nuevo.");
            } else {
                break; 
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida. Debe introducir un número.");
        }
    }

    File copiaElegida = copias[opcion - 1];

    try {
        Files.copy(copiaElegida.toPath(), fichero.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Se restauró la copia: " + copiaElegida.getName());
    } catch (IOException e) {
        System.out.println("Error al restaurar la copia.");
        e.printStackTrace();
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
        String opcion;

        do{
            primerMenu();
            System.out.println("Elige una opcion: ");
            opcion = Leer.datoString();

            switch (opcion){
                case "1":
                llenar();
                guardar();
                break;

                case "2":
                vaciar();
                guardar();
                break;

                case "3":
                System.out.println("Volviendo al menu principal...");
                break;

                default:
                System.out.println("Opcion erronea. Intentao de novo.");
            }
        } while (!opcion.equals("3"));
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
    String opcion;

    do {
        ultimoMenu();
        System.out.print("Elige una opción: ");
        opcion = Leer.datoString();

        switch (opcion) {
            case "1":
               informacionDelArchivo(fichero);
                break;
            case "2":
                copiaDeSeguridad(fichero);
                break;
            case "3":
                restaurarCopiaDeSeguridad(fichero);
                break;
            case "4":
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción no válida. Intenta de nuevo.");
             }
        } while (!opcion.equals("4"));
    }

    public String dameOpcion() {
		String opcion;

		segundoMenu();
		opcion = Leer.datoString();
		System.out.println();
		return opcion;
	}
}