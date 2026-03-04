import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EjemploExcepciones {

    // ====== Excepción personalizada CHECKED ======
    static class EdadInvalidaException extends Exception {
        public EdadInvalidaException(String mensaje) {
            super(mensaje);
        }
    }

    // ====== Excepción personalizada UNCHECKED ======
    static class SaldoInsuficienteException extends RuntimeException {
        public SaldoInsuficienteException(String mensaje) {
            super(mensaje);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== DEMO EXCEPCIONES EN JAVA ===");

        // 1) ArithmeticException (unchecked)
        int a = 10;
        int b = 0;

        int resultado = 0;
        try {
            resultado = a/b;
           // divide entre cero
            System.out.println("Resultado: " + resultado);
        } catch (ArithmeticException e) {
            resultado= -1;
            System.out.println("Hola has dividido por 0" + e.getMessage());

        } finally {
            System.out.println(resultado);
        }

        // 2) NullPointerException (unchecked)
        String texto = null;

        try {
            System.out.println(texto.length()); // NPE
        } catch (NullPointerException e) {
            System.out.println("[NullPointerException] No puedes usar un objeto null.");
        }

        // 3) NumberFormatException (unchecked)
        try {
            String numero = "12A";
            int n = Integer.parseInt(numero); // error de formato
            System.out.println("Número: " + n);
        } catch (NumberFormatException e) {
            System.out.println("[NumberFormatException] Formato numérico inválido.");
        }

        // 4) ArrayIndexOutOfBoundsException (unchecked)
        try {
            int[] arr = {1, 2, 3};
            System.out.println(arr[5]); // índice inválido
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("[ArrayIndexOutOfBoundsException] Índice fuera de rango.");
        }

        // 5) IllegalArgumentException (unchecked) + throw manual
        try {
            validarEdad(-3); // lanza excepción manualmente
        } catch (IllegalArgumentException e) {
            System.out.println("[IllegalArgumentException] " + e.getMessage());
        }

        // 6) Excepción personalizada CHECKED + throws/throw
        try {
            registrarEdad(150); // edad inválida según regla
        } catch (EdadInvalidaException e) {
            System.out.println("[EdadInvalidaException] " + e.getMessage());
        }

        // 7) Excepción personalizada UNCHECKED
        try {
            retirarDinero(100, 150);
        } catch (SaldoInsuficienteException e) {
            System.out.println("[SaldoInsuficienteException] " + e.getMessage());
        }

        // 8) Checked Exception (IOException) con archivo
        // Si no existe el archivo, FileReader lanzará FileNotFoundException (subclase de IOException)
        try {
            leerPrimeraLinea("no_existe.txt");
        } catch (IOException e) {
            System.out.println("[IOException] Error de lectura: " + e.getMessage());
        } finally {
            System.out.println("[finally] Este bloque se ejecuta siempre.");
        }

        // 9) Multi-catch (capturar varias en un mismo catch)
        try {
            String valor = null;
            if (Math.random() > 0.5) {
                Integer.parseInt("XX"); // NumberFormatException
            } else {
                valor.length(); // NullPointerException
            }
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("[Multi-catch] Capturada: " + e.getClass().getSimpleName());
        }

        // 10) Error (demostración SOLO conceptual)
        // NO es recomendable forzar OutOfMemoryError/StackOverflowError en clase real.
        try {
            provocarErrorControladoDemo();
        } catch (StackOverflowError e) {
            System.out.println("[StackOverflowError] Error grave (demostración).");
        } catch (Error e) {
            System.out.println("[Error] Tipo de error grave: " + e.getClass().getSimpleName());
        }

        System.out.println("=== FIN DEMO ===");
    }

    // ----- Método con throw (unchecked) -----
    public static void validarEdad(int edad) {
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa.");
        } else if (edad > 112){
            throw new IllegalArgumentException("La edad no puede ser mayor a 112.");
        }
        System.out.println("Edad válida: " + edad);
    }

    // ----- Método con throws (checked personalizada) -----
    public static void registrarEdad(int edad) throws EdadInvalidaException {
        if (edad < 0 || edad > 120) {
            throw new EdadInvalidaException("Edad fuera de rango permitido: " + edad);
        }
        System.out.println("Edad registrada correctamente: " + edad);
    }

    // ----- Método que lanza unchecked personalizada -----
    public static void division( int buscado){
        int  aux [] = {1,2,3, 4};
        for (int i = 0; i < aux.length; i++) {
            if (aux[i] == buscado) {
                return;
            }
        }
        throw new RuntimeException();



    }


    public static void retirarDinero(double saldo, double cantidad) {
        if (cantidad > saldo) {
            throw new SaldoInsuficienteException(
                    "Saldo=" + saldo + ", cantidad solicitada=" + cantidad
            );
        }
        System.out.println("Retirada realizada. Nuevo saldo: " + (saldo - cantidad));
    }

    // ----- Método con throws IOException (checked) -----
    public static void leerPrimeraLinea(String ruta) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(ruta));
            System.out.println("Primera línea: " + br.readLine());
        } finally {
            // cerrar recurso manualmente (forma clásica)
            if (br != null) {
                br.close();
            }
        }
    }

    // ----- Demo de Error (muy controlada) -----
    public static void provocarErrorControladoDemo() {
        // OJO: esto es SOLO para mostrar que Error existe.
        // Lanzarlo manualmente aquí es más seguro que causar uno real.
        throw new StackOverflowError("Simulación de error grave");
    }
}
