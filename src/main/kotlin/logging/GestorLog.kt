package es.iesraprog2425.pruebaes.logging

import es.iesraprog2425.pruebaes.app.Calculadora
import es.iesraprog2425.pruebaes.model.Operadores
import es.iesraprog2425.pruebaes.ui.IEntradaSalida
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class GestorLog(private val consola: IEntradaSalida, private val calculadora: Calculadora) {

    fun verificarDir(ruta: File) {
        if (!ruta.exists()) {
            ruta.mkdirs()
            consola.mostrar("Ruta ./log creada.")
        }
    }

    fun abrirLogReciente(ruta: File = File("src/main/kotlin/logging/log")) {
        verificarDir(ruta)
        val archivos = ruta.listFiles()
        if (archivos.isNotEmpty()) {
            var ultimoModificado: Long = 0
            var archivoALeer: File? = null
            for (archivo in archivos) {
                val fecha = archivo.lastModified()
                if (fecha > ultimoModificado) {
                    ultimoModificado = fecha
                    archivoALeer = archivo
                }

            }
            println(archivoALeer?.readLines())
        } else {
            consola.mostrarError("No existen ficheros de Log")
        }
    }


    fun escribirArchivo(ruta: File, num1: Double, simbolo: Char, num2: Double) {
        verificarDir(ruta)
        val archivoNuevo = File(
            ruta.path + "/log" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".txt"
        )
        archivoNuevo.createNewFile()
        val operador = Operadores.getOperador(simbolo)
        if (operador != null) {
            archivoNuevo.writeText(
                LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy -> HH:mm:ss")) + " \tOperación -> " + num1.toString() + " " + operador.simbolos.firstOrNull() + " " + num2.toString() + " = " + calculadora.realizarCalculo(
                    num1,
                    operador,
                    num2
                ) + "\n"
            )
        }
    }


    fun actualizarArchivo(ruta: File, num1: Double, operador: Operadores, num2: Double) {
        verificarDir(ruta)
        val archivos = ruta.listFiles()
        var archivoAModificar: File? = null
        if (archivos.isNotEmpty()) {
            var ultimoModificado: Long = 0
            for (archivo in archivos) {
                val fecha = archivo.lastModified()
                if (fecha > ultimoModificado) {
                    ultimoModificado = fecha
                    archivoAModificar = archivo
                }

            }
        }
        if (operador != null) {
            archivoAModificar?.appendText(
                LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy -> HH:mm:ss")) + " \tOperación -> " + num1.toString() + " " + operador.simbolos.firstOrNull() + " " + num2.toString() + " = " + calculadora.realizarCalculo(
                    num1,
                    operador,
                    num2
                ) + "\n"
            )
        }
    }
}
