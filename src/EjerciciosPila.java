import java.util.Stack;

/**
 * Practica de Pilas (Stack)
 * Aqui hice los dos ejercicios que se pidieron:
 * 1. revisarSintaxis: revisa que una cadena con { } [ ] ( ) este bien escrita.
 * 2. ordenarConPila: ordena un vector de numeros usando una pila.
 */
public class EjerciciosPila {

    /**
     * Metodo 1.
     * Recibe una cadena (por ejemplo "{[()]}") y revisa que los simbolos
     * de apertura y cierre esten completos y bien acomodados.
     *
     * Como lo hice:
     * - Voy leyendo la cadena letra por letra.
     * - Si encuentro un simbolo de apertura ( { [ ( ), lo guardo en la pila.
     * - Si encuentro un simbolo de cierre ( } ] ) ), reviso cual fue el
     *   ultimo simbolo que guarde (el de hasta arriba de la pila) y checo
     *   que sea su pareja correcta.
     * - Si la pila esta vacia y llega un simbolo de cierre, ya es un error
     *   porque no hay nada que cerrar.
     * - Si un simbolo de cierre no hace pareja con el ultimo que guarde,
     *   tambien es un error.
     * - Al final, si la pila quedo vacia, quiere decir que todo se cerro
     *   bien y la cadena es correcta.
     *
     * Regresa true si la cadena esta bien escrita (Correcto) y false si
     * tiene algun error (Error).
     */
    public boolean revisarSintaxis(String cadena) {
        Stack<Character> pila = new Stack<Character>();

        for (int i = 0; i < cadena.length(); i++) {
            char actual = cadena.charAt(i);

            if (actual == '(' || actual == '[' || actual == '{') {
                // Simbolo de apertura: lo guardo en la pila
                pila.push(actual);

            } else if (actual == ')' || actual == ']' || actual == '}') {
                // Simbolo de cierre: reviso que tenga pareja
                if (pila.isEmpty()) {
                    // No hay nada guardado para cerrar -> Error
                    return false;
                }

                char ultimoGuardado = pila.pop();
                if (!esPareja(ultimoGuardado, actual)) {
                    // No es la pareja que corresponde -> Error
                    return false;
                }
            }
            // Si es cualquier otro caracter (letra, numero, +, -, etc.)
            // lo ignoro, porque no afecta si los simbolos estan bien puestos.
        }

        // Si al final no quedo nada en la pila, todo cerro bien.
        return pila.isEmpty();
    }

    // Metodo chiquito que me ayuda a saber si dos simbolos son pareja
    private boolean esPareja(char apertura, char cierre) {
        if (apertura == '(' && cierre == ')') return true;
        if (apertura == '[' && cierre == ']') return true;
        if (apertura == '{' && cierre == '}') return true;
        return false;
    }

    /**
     * Metodo 2.
     * Recibe un vector de numeros enteros y regresa una Pila con esos
     * numeros ordenados de menor a mayor.
     *
     * Como lo hice:
     * - Uso dos pilas: una llamada "pila" donde meto los numeros del
     *   vector tal como vienen, y otra llamada "auxiliar" que es donde
     *   voy a ir dejando los numeros ya ordenados.
     * - Saco un numero de "pila" y lo comparo con lo que hay hasta
     *   arriba de "auxiliar".
     * - Si arriba de "auxiliar" hay un numero mas chico que el que estoy
     *   acomodando, lo regreso a "pila" para que no se quede en el lugar
     *   equivocado, y sigo comparando hasta encontrar donde si va.
     * - Asi, cuando ya no queda nada en "pila", la pila "auxiliar" queda
     *   ordenada: hasta arriba el numero mas chico, y hasta abajo el mas
     *   grande. Por eso, si se van sacando (pop) los numeros uno por uno,
     *   van saliendo de menor a mayor.
     */
    public Stack<Integer> ordenarConPila(int[] vector) {
        Stack<Integer> pila = new Stack<Integer>();
        for (int i = 0; i < vector.length; i++) {
            pila.push(vector[i]);
        }

        Stack<Integer> auxiliar = new Stack<Integer>();

        while (!pila.isEmpty()) {
            int temp = pila.pop();

            // Mientras el numero de hasta arriba de "auxiliar" sea mas
            // chico que "temp", lo regreso a "pila" para reacomodarlo.
            while (!auxiliar.isEmpty() && auxiliar.peek() < temp) {
                pila.push(auxiliar.pop());
            }

            auxiliar.push(temp);
        }

        return auxiliar;
    }

    // ---------------------------------------------------------
    // De aqui para abajo solo es el main que hice para PROBAR
    // que los dos metodos de arriba funcionen bien.
    // ---------------------------------------------------------
    public static void main(String[] args) {
        EjerciciosPila ejercicios = new EjerciciosPila();

        System.out.println("===== METODO 1: revisarSintaxis =====");

        String[] pruebas = {
                "{[()]}",
                "{[(]}]",
                "{[}]",
                "((()))",
                "(()",
                ")(",
                "(a+b)*[c-d]",
                ""
        };

        for (String prueba : pruebas) {
            boolean resultado = ejercicios.revisarSintaxis(prueba);
            String mensaje = resultado ? "Correcto" : "Error";
            String textoMostrar = prueba.equals("") ? "(cadena vacia)" : prueba;
            System.out.println(textoMostrar + "   ---->   " + mensaje);
        }

        System.out.println();
        System.out.println("===== METODO 2: ordenarConPila =====");

        int[] numeros1 = {8, 3, 15, 1, 9, 4};
        probarOrden(ejercicios, numeros1);

        int[] numeros2 = {-5, 10, 0, 10, -3};
        probarOrden(ejercicios, numeros2);
    }

    // Metodo de apoyo solo para el main, para no repetir codigo al probar
    private static void probarOrden(EjerciciosPila ejercicios, int[] numeros) {
        System.out.print("Vector original:                  ");
        for (int n : numeros) {
            System.out.print(n + " ");
        }
        System.out.println();

        Stack<Integer> pilaOrdenada = ejercicios.ordenarConPila(numeros);

        System.out.print("Pila ordenada (de menor a mayor): ");
        while (!pilaOrdenada.isEmpty()) {
            System.out.print(pilaOrdenada.pop() + " ");
        }
        System.out.println();
        System.out.println();
    }
}
