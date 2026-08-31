import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Programa para la manipulación y análisis de vectores (arreglos) en Java.
 * 
 * Cumple con los siguientes requerimientos:
 * 1. Llenado y validación de un vector de 15 enteros (rango 10 - 100).
 * 2. Visualización del vector.
 * 3. Búsqueda de un valor específico.
 * 4. Determinación del valor máximo y mínimo.
 * 5. Identificación de múltiplos de un número X.
 * 6. Cálculo de la suma total y promedio.
 * 7. Generación de un nuevo vector con elementos superiores al promedio.
 * 
 */
public class ManipulacionVectores {

    // Constantes para configuración del programa
    private static final int TAMANIO_VECTOR = 15;
    private static final int RANGO_MIN = 10;
    private static final int RANGO_MAX = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] vectorPrincipal = null;
        boolean salir = false;

        while (!salir) {
            imprimirEncabezado("MENÚ DE OPCIONES - MANIPULACIÓN DE VECTORES");
            System.out.println(" 1. Llenar vector (" + TAMANIO_VECTOR + " números entre " + RANGO_MIN + " y " + RANGO_MAX + ")");
            System.out.println(" 2. Mostrar vector actual");
            System.out.println(" 3. Buscar un valor en el vector");
            System.out.println(" 4. Determinar número mayor y menor");
            System.out.println(" 5. Identificar múltiplos de un número X");
            System.out.println(" 6. Calcular suma total y promedio");
            System.out.println(" 7. Crear nuevo vector con elementos mayores al promedio");
            System.out.println(" 0. Salir del programa");
            System.out.println("--------------------------------------------------------------------------------");
            System.out.print(" Seleccione una opción: ");

            if (!scanner.hasNextInt()) {
                System.out.println("\n [Error] Por favor, ingrese un número de opción válido.");
                scanner.next(); // Limpiar entrada inválida
                continue;
            }

            int opcion = scanner.nextInt();

            // Si se selecciona una opción que requiere el vector y no ha sido llenado, generar uno aleatorio
            if (opcion >= 2 && opcion <= 7 && vectorPrincipal == null) {
                System.out.println("\n [Aviso] No se ha llenado el vector manualmente. Se generará automáticamente con valores aleatorios:");
                vectorPrincipal = generarVectorAleatorio(TAMANIO_VECTOR, RANGO_MIN, RANGO_MAX);
                mostrarVector("Vector actual", vectorPrincipal);
            }

            switch (opcion) {
                case 1:
                    vectorPrincipal = llenarVector(scanner, TAMANIO_VECTOR, RANGO_MIN, RANGO_MAX);
                    mostrarVector("Vector original cargado exitosamente", vectorPrincipal);
                    break;

                case 2:
                    mostrarVector("Vector actual", vectorPrincipal);
                    break;

                case 3:
                    buscarValorEnVector(scanner, vectorPrincipal);
                    break;

                case 4:
                    determinarMayorYMenor(vectorPrincipal);
                    break;

                case 5:
                    identificarMultiplos(scanner, vectorPrincipal);
                    break;

                case 6:
                    int sumaTotal = calcularSuma(vectorPrincipal);
                    double promedio = (double) sumaTotal / vectorPrincipal.length;
                    imprimirSeccion("CALCULO DE SUMA Y PROMEDIO");
                    System.out.println(" • Suma total de los elementos : " + sumaTotal);
                    System.out.printf(" • Promedio de los elementos   : %.2f%n", promedio);
                    break;

                case 7:
                    int suma = calcularSuma(vectorPrincipal);
                    double prom = (double) suma / vectorPrincipal.length;
                    crearVectorEncimaPromedio(vectorPrincipal, prom);
                    break;

                case 0:
                    salir = true;
                    imprimirEncabezado("¡GRACIAS POR USAR EL PROGRAMA! HASTA PRONTO");
                    break;

                default:
                    System.out.println("\n [Error] Opción no válida. Ingrese un número entre 0 y 7.");
                    break;
            }
        }

        scanner.close();
    }

    // =========================================================================
    // MÉTODOS Y FUNCIONES MODULARES
    // =========================================================================

    /**
     * Solicita datos al usuario para llenar un vector garantizando que cada
     * valor esté dentro del rango especificado [min, max].
     *
     * @param scanner Objeto Scanner para lectura de consola.
     * @param tamanio Cantidad de elementos del vector.
     * @param min     Límite inferior inclusivo.
     * @param max     Límite superior inclusivo.
     * @return Arreglo de enteros con los valores válidos ingresados.
     */
    public static int[] llenarVector(Scanner scanner, int tamanio, int min, int max) {
        imprimirSeccion("1. LLENADO DEL VECTOR (15 NÚMEROS ENTRE " + min + " Y " + max + ")");
        int[] vector = new int[tamanio];

        for (int i = 0; i < tamanio; i++) {
            boolean entradaValida = false;
            while (!entradaValida) {
                System.out.printf(" Ingrese el número para la posición [%d/%d]: ", (i + 1), tamanio);
                
                // Validación de tipo de dato
                if (!scanner.hasNextInt()) {
                    System.out.println("   [Error] Debe ingresar un número entero válido.");
                    scanner.next(); // Limpiar entrada inválida
                    continue;
                }

                int valor = scanner.nextInt();

                // Validación de rango
                if (valor >= min && valor <= max) {
                    vector[i] = valor;
                    entradaValida = true;
                } else {
                    System.out.printf("   [Error] El número %d está fuera del rango permitido (%d - %d). Intente de nuevo.%n",
                            valor, min, max);
                }
            }
        }
        return vector;
    }

    /**
     * Muestra de forma estética los elementos de un vector en consola.
     *
     * @param mensaje Descripción o título del vector.
     * @param vector  Arreglo de enteros a imprimir.
     */
    public static void mostrarVector(String mensaje, int[] vector) {
        System.out.println("\n--- " + mensaje + " ---");
        System.out.print(" [ ");
        for (int i = 0; i < vector.length; i++) {
            System.out.print(vector[i] + (i < vector.length - 1 ? ", " : ""));
        }
        System.out.println(" ]");
    }

    /**
     * Solicita un número al usuario y busca todas sus apariciones dentro del vector.
     * Si no existe, notifica al usuario.
     *
     * @param scanner Objeto Scanner para lectura de datos.
     * @param vector  Vector donde se realizará la búsqueda.
     */
    public static void buscarValorEnVector(Scanner scanner, int[] vector) {
        imprimirSeccion("2. BÚSQUEDA DE UN VALOR EN EL VECTOR");
        System.out.print(" Ingrese el número que desea buscar: ");
        
        while (!scanner.hasNextInt()) {
            System.out.println("   [Error] Por favor ingrese un número entero.");
            scanner.next();
            System.out.print(" Ingrese el número que desea buscar: ");
        }
        int numeroABuscar = scanner.nextInt();

        boolean encontrado = false;
        StringBuilder posiciones = new StringBuilder();

        // Ciclo para buscar el número
        for (int i = 0; i < vector.length; i++) {
            if (vector[i] == numeroABuscar) {
                if (encontrado) {
                    posiciones.append(", ");
                }
                posiciones.append(i).append(" (posición visual: ").append(i + 1).append(")");
                encontrado = true;
            }
        }

        if (encontrado) {
            System.out.printf("   -> El número %d SE ENCUENTRA en el índice/posición: [%s]%n",
                    numeroABuscar, posiciones.toString());
        } else {
            System.out.printf("   -> El número %d NO se encuentra en el vector.%n", numeroABuscar);
        }
    }

    /**
     * Recorre el vector para encontrar tanto el valor máximo como el valor mínimo.
     *
     * @param vector Vector a evaluar.
     */
    public static void determinarMayorYMenor(int[] vector) {
        imprimirSeccion("3. DETERMINACIÓN DE VALOR MAYOR Y MENOR");

        // Inicializamos con el primer elemento
        int mayor = vector[0];
        int menor = vector[0];

        // Ciclo para comparar cada elemento
        for (int i = 1; i < vector.length; i++) {
            if (vector[i] > mayor) {
                mayor = vector[i];
            }
            if (vector[i] < menor) {
                menor = vector[i];
            }
        }

        System.out.println(" • Número MAYOR en el vector: " + mayor);
        System.out.println(" • Número MENOR en el vector: " + menor);
    }

    /**
     * Solicita un número X y encuentra todos los elementos del vector que sean múltiplos de X.
     *
     * @param scanner Objeto Scanner para lectura.
     * @param vector  Vector donde se evaluarán los múltiplos.
     */
    public static void identificarMultiplos(Scanner scanner, int[] vector) {
        imprimirSeccion("4. IDENTIFICACIÓN DE MÚLTIPLOS DE UN NÚMERO X");
        System.out.print(" Ingrese el número X para buscar sus múltiplos: ");
        
        while (!scanner.hasNextInt()) {
            System.out.println("   [Error] Ingrese un número entero válido.");
            scanner.next();
            System.out.print(" Ingrese el número X para buscar sus múltiplos: ");
        }
        int x = scanner.nextInt();

        if (x == 0) {
            System.out.println("   [Aviso] El 0 no es un divisor válido para evaluar múltiplos.");
            return;
        }

        int contadorMultiplos = 0;
        System.out.printf(" Múltiplos de %d encontrados en el vector:%n ", x);

        for (int valor : vector) {
            if (valor % x == 0) {
                System.out.print("[" + valor + "] ");
                contadorMultiplos++;
            }
        }

        if (contadorMultiplos == 0) {
            System.out.printf("%n   -> No hay múltiplos de %d en el vector.%n", x);
        } else {
            System.out.printf("%n   -> Total de múltiplos encontrados: %d%n", contadorMultiplos);
        }
    }

    /**
     * Calcula la suma acumulada de todos los elementos del vector mediante un ciclo.
     *
     * @param vector Arreglo de enteros.
     * @return Suma total entera.
     */
    public static int calcularSuma(int[] vector) {
        int suma = 0;
        for (int num : vector) {
            suma += num;
        }
        return suma;
    }

    /**
     * Calcula los valores mayores al promedio, crea un nuevo vector con la dimensión exacta
     * requerida y muestra sus resultados.
     *
     * @param vectorOriginal Vector base de datos.
     * @param promedio       Valor promedio calculado.
     */
    public static void crearVectorEncimaPromedio(int[] vectorOriginal, double promedio) {
        imprimirSeccion("6. ELEMENTOS POR ENCIMA DEL PROMEDIO (" + String.format("%.2f", promedio) + ")");

        // Primer recorrido: Contar cuántos números superan el promedio para definir el tamaño del nuevo vector
        int cantidadMayores = 0;
        for (int valor : vectorOriginal) {
            if (valor > promedio) {
                cantidadMayores++;
            }
        }

        // Si no existen elementos mayores al promedio
        if (cantidadMayores == 0) {
            System.out.println("   -> No hay números mayores que el promedio en el vector.");
            return;
        }

        // Crear el nuevo vector con el tamaño exacto necesario
        int[] vectorMayores = new int[cantidadMayores];
        int indiceNuevo = 0;

        // Segundo recorrido: Copiar los elementos que cumplen la condición
        for (int valor : vectorOriginal) {
            if (valor > promedio) {
                vectorMayores[indiceNuevo] = valor;
                indiceNuevo++;
            }
        }

        // Mostrar los resultados del nuevo vector
        System.out.println(" • Cantidad de elementos por encima del promedio: " + cantidadMayores);
        mostrarVector("Nuevo vector con números > promedio", vectorMayores);
    }

    /**
     * Genera un vector con valores aleatorios dentro del rango [min, max].
     *
     * @param tamanio Cantidad de elementos del vector.
     * @param min     Límite inferior inclusivo.
     * @param max     Límite superior inclusivo.
     * @return Arreglo de enteros con valores aleatorios.
     */
    public static int[] generarVectorAleatorio(int tamanio, int min, int max) {
        Random random = new Random();
        int[] vector = new int[tamanio];
        for (int i = 0; i < tamanio; i++) {
            vector[i] = random.nextInt((max - min) + 1) + min;
        }
        return vector;
    }

    // =========================================================================
    // UTILIDADES DE FORMATO EN CONSOLA
    // =========================================================================

    private static void imprimirEncabezado(String titulo) {
        System.out.println("\n================================================================================");
        System.out.println("           " + titulo);
        System.out.println("================================================================================");
    }

    private static void imprimirSeccion(String seccion) {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(" " + seccion);
        System.out.println("--------------------------------------------------------------------------------");
    }
}
