package es.iesraprog2425.pruebaes.logging

import es.iesraprog2425.pruebaes.model.Operadores
import es.iesraprog2425.pruebaes.ui.Consola
import es.iesraprog2425.pruebaes.ui.IEntradaSalida
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class GestorLog(private val consola: IEntradaSalida) {

    fun verificarDir(ruta: File): Boolean {
        if (!ruta.exists()) {
            ruta.mkdirs()
            consola.mostrar("Ruta ./log creada.")
            return true
        } else {
            return false
        }
    }

    fun abrirLogReciente(ruta: File = File("src/main/kotlin/logging/log")) {
        if (!verificarDir(ruta)) {
            val archivos = ruta.listFiles()
            if (archivos != null) {
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
    }

    fun escribirArchivo(ruta: File, num1: Double, simbolo: Char, num2: Double){
        if(verificarDir(ruta)){
            val archivoNuevo = File(ruta.name + "log" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".txt")
            archivoNuevo.writeText(num1.toString() + Operadores.getOperador(simbolo).toString() + num2.toString())
        }
    }
}