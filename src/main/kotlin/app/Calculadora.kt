package es.iesraprog2425.pruebaes.app

import es.iesraprog2425.pruebaes.logging.GestorLog
import es.iesraprog2425.pruebaes.model.Operadores
import es.iesraprog2425.pruebaes.ui.IEntradaSalida
import java.io.File
import java.lang.Thread.sleep

class Calculadora(private val ui: IEntradaSalida) {

    private fun pedirNumero(msj: String, msjError: String = "Número no válido!"): Double {
        return ui.pedirDouble(msj) ?: throw InfoCalcException(msjError)
    }

    private fun pedirInfo() = Triple(
        pedirNumero("Introduce el primer número: ", "El primer número no es válido!"),
        Operadores.getOperador(ui.pedirInfo("Introduce el operador (+, -, *, /): ").firstOrNull())
            ?: throw InfoCalcException("El operador no es válido!"),
        pedirNumero("Introduce el segundo número: ", "El segundo número no es válido!")
    )

    fun realizarCalculo(numero1: Double, operador: Operadores, numero2: Double): Double {
        return when (operador) {
            Operadores.SUMA -> numero1 + numero2
            Operadores.RESTA -> numero1 - numero2
            Operadores.MULTIPLICACION -> numero1 * numero2
            Operadores.DIVISION -> {
                if (numero2 == 0.0) {
                    throw IllegalArgumentException("ERROR número inválido.")
                }
                numero1 / numero2
            }
        }
    }

    fun iniciar(ruta: File) {
        do {
            try {
                println("Cargando...")
                sleep(2000)
                ui.limpiarPantalla()
                val (numero1, operador, numero2) = pedirInfo()
                val resultado = realizarCalculo(numero1, operador, numero2)
                ui.mostrar("Resultado: %.2f".format(resultado))
                GestorLog(ui, this).actualizarArchivo(ruta, numero1, operador, numero2)
            } catch (e: NumberFormatException) {
                ui.mostrarError(e.message ?: "Se ha producido un error!")
            }  catch(e: IllegalArgumentException){
                ui.mostrarError(e.message ?: "Se ha producido un error.")
            }catch(e: InfoCalcException){
                ui.mostrarError(e.message ?: "Se ha producido un error.")
            }
        } while (ui.preguntar())
        ui.limpiarPantalla()
    }

}