import java.util.*
import kotlin.jvm.JvmStatic

class MinMax
/**
 * @param joueur caractere du joueur a jouer
 * @param opposant caractere de l'opposant
 */(private val joueur: Char, private val opposant: Char) {

    /**
     * @param board Tableau
     * @return true si on peut jouer, false sinon
     */
    private fun isMovesLeft(board: Array<CharArray>): Boolean {
        for (i in 0..2) for (j in 0..2) if (board[i][j] == '_') return true
        return false
    }



    /**
     * @param board Tableau
     * @param depth Profondeur de l'arbre
     * @param isMax defini sur un move max ou un move min
     * @return true si on peut jouer, false sinon
     */
    private fun minimax(board: Array<CharArray>, depth: Int, isMax: Boolean): Int {
        //On verifie si c'est finis
        val score = Morpion.evaluate(board, joueur, opposant)

        //Si j1 a gg
        if (score == 10)
            return score

        // Si j2 a gg
        if (score == -10)
            return score

        //Si le jeu est finis sur match nul
        if (!isMovesLeft(board)) return 0

        // Partie Max
        return if (isMax) {
            var best = -1000

            // On teste toutes les cases
            for (i in 0..2) {
                for (j in 0..2) {
                    //On verifie si la piece est jouable
                    if (board[i][j] == '_') {
                        // On tente de faire jouer J1 ici
                        board[i][j] = joueur

                        //Appel recursif et renvoie le meilleur coup entre celui actuel et celui de l'appel
                        best = best.coerceAtLeast(minimax(board, depth + 1, false))

                        // On annule le coup et on passe au suivant
                        board[i][j] = '_'
                    }
                }
            }
            best
        } else {
            var best = 1000
            for (i in 0..2) {
                for (j in 0..2) {
                    if (board[i][j] == '_') {
                        board[i][j] = opposant
                        best = best.coerceAtMost(minimax(board, depth + 1, true))
                        board[i][j] = '_'
                    }
                }
            }
            best
        }
    }

    /**
     * @param board jeu
     * @return objet qui contient le meilleur coup
     */
    fun findBestMove(board: Array<CharArray>): Move {
        //Initialisation
        var bestVal = -1000
        var bestMove = Move(-1, -1)
        bestMove.row = -1
        bestMove.col = -1
        var lesMouvements = ArrayList<Move>()

        //Test toutes les cellules une par une
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == '_') {
                    //On joue dessus
                    board[i][j] = joueur

                    //On cherche la valeur du mouv
                    val moveVal = minimax(board, 0, false)




                    // Si le move est mieux, on reset la liste
                    if (moveVal > bestVal) {
                        bestMove.row = i
                        bestMove.col = j
                        bestVal = moveVal
                        lesMouvements = ArrayList()
                        val unMov = Move(i, j)
                        lesMouvements.add(unMov)
                    }
                    //Si le move est pareil, on l'ajoute a la liste
                    else if (moveVal == bestVal) {
                        val unMov = Move(i, j)
                        lesMouvements.add(unMov)
                    }
                    //On annule le move que l'on vient de faire
                    board[i][j] = '_'
                }
            }
        }

        //On parcours la liste des coups possible et on en tire un au pif
        if (lesMouvements.size > 0) {
            val rand = Random()
            bestMove = lesMouvements[rand.nextInt(lesMouvements.size)]
        }
        return bestMove
    }
}