package es.iesraprog2425.pruebaes

import es.iesraprog2425.pruebaes.app.Calculadora
import es.iesraprog2425.pruebaes.logging.GestorLog
import es.iesraprog2425.pruebaes.ui.Consola
import java.io.File
import java.lang.Thread.sleep
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/*
fun main() {
    val scanner = Scanner(System.`in`)

    println("Introduce el primer número:")
    val numero1 = scanner.nextDouble()
    println("Introduce el operador (+, -, *, /):")
    val operador = scanner.next()[0]
    println("Introduce el segundo número:")
    val numero2 = scanner.nextDouble()

    val resultado = when (operador) {
        '+' -> numero1 + numero2
        '-' -> numero1 - numero2
        '*' -> numero1 * numero2
        '/' -> numero1 / numero2
        else -> "Operador no válido"
    }

    println("Resultado: $resultado")
}
*/

//fun main(args: Array<String>) {
//    Calculadora(Consola()).iniciar()
//}


/*
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    val numLineas = scanner.nextInt()
    scanner.nextLine() // Limpia el salto de línea pendiente

    var resultado = 1

    for (i in 1..numLineas) {
        var suma = 0
        while (scanner.hasNextInt()) {
            suma += scanner.nextInt()
        }
        resultado *= suma
        if (scanner.hasNextLine()) scanner.nextLine() // pasar a la siguiente línea
    }

    println(resultado)
}
*/

fun main(args: Array<String>) {
    val consola = Consola()
    val calculadora = Calculadora(consola)
    val gestor = GestorLog(consola, calculadora)
//    gestor.escribirArchivo(File("src/main/kotlin/logging/log"), 2.0, '/', 2.0)
    if (args.isEmpty()) {
        gestor.verificarDir(File("src/main/kotlin/logging/log"))
        gestor.abrirLogReciente()
    } else if (args.size == 1) {
        gestor.verificarDir(File(args[0]))
        gestor.abrirLogReciente(File(args[0]))
    } else if (args.size == 4) {
        gestor.escribirArchivo(File(args[0]), args[1].toDouble(), args[2][0], args[3].toDouble())
        calculadora.iniciar(File(args[0]))


    } else {
        Consola().mostrarError("ERROR.")
    }

}
