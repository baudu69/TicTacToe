import kotlin.jvm.JvmStatic

object Main {
    // Driver code
    @JvmStatic
    fun main(args: Array<String>) {
        val unMorpion = Morpion()
        unMorpion.j2IA = true
        unMorpion.j1IA = false
        unMorpion.Lancer()
    }
}