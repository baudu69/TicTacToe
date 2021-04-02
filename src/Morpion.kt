class Morpion {
    var j1IA = false
    var j2IA = false
    private val plateau = arrayOf(charArrayOf('_', '_', '_'), charArrayOf('_', '_', '_'), charArrayOf('_', '_', '_'))

    private fun afficherPlateau() {
        for (i in 0..2) {
            for (j in 0..2) {
                print(plateau[i][j].toString() + " ")
            }
            println()
        }
        println()
    }



    private fun isGridFull(): Boolean {
        for (i in 0..2) for (j in 0..2) if (plateau[i][j] == '_') return false
        return true
    }

    private fun isGameOver(): Boolean {
        return when {
            evaluate(plateau, 'x', 'o') == 10 -> {
                print("Joueur 1 a gg")
                true
            }
            evaluate(plateau, 'x', 'o') == -10 -> {
                print("Joueur 2 a gg")
                true
            }
            else -> {
                isGridFull()
            }
        }
    }

    companion object {
        /**
         * @param b board
         * @return 10 si j1 a gg, -10 si j2 a gg, 0 si rien encore
         */
        fun evaluate(b: Array<CharArray>, joueur: Char, opposant: Char): Int {
            // Pour chaque ligne
            for (row in 0..2) {
                //Si 3 caracteres identiques
                if (b[row][0] == b[row][1] && b[row][1] == b[row][2]) {
                    //Joueur a gg
                    if (b[row][0] == 'x') return +10 else if (b[row][0] == 'o') return -10
                }
            }

            // Pour chaque colonne
            for (col in 0..2) {
                //Si 3 caracteres identiques
                if (b[0][col] == b[1][col] && b[1][col] == b[2][col]) {
                    //Joueur a gg
                    if (b[0][col] == joueur) return +10 else if (b[0][col] == opposant) return -10
                }
            }

            //Test diagonale gauche
            if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
                if (b[0][0] == joueur) return +10 else if (b[0][0] == opposant) return -10
            }

            //Test diagonale droite
            if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
                if (b[0][2] == joueur) return +10 else if (b[0][2] == opposant) return -10
            }

            //Aucun joueur a gg
            return 0
        }
    }

    fun Lancer() {
        afficherPlateau()
        do {
            if (!j1IA) {
                print("C'est au tour de J1 : ")
                val (a, b) = readLine()!!.split(' ')
                plateau[a.toInt()][b.toInt()] = 'x'
            } else {
                val MMJ1 = MinMax('x', 'o')
                val bestMove = MMJ1.findBestMove(plateau)
                if (bestMove.col == -1)
                    break
                plateau[bestMove.row][bestMove.col] = 'x'
            }
            afficherPlateau()
            if (isGameOver()) break
            if (!j2IA) {
                print("C'est au tour de J2 : ")
                val (a, b) = readLine()!!.split(' ')
                plateau[a.toInt()][b.toInt()] = 'o'
            } else {
                val MMJ2 = MinMax('o', 'x')
                val bestMove = MMJ2.findBestMove(plateau)
                if (bestMove.col == -1)
                    break
                plateau[bestMove.row][bestMove.col] = 'o'
            }
            afficherPlateau()
        } while (!isGameOver())


    }
}

