import wessel.wordlesolver.Solver

fun main() {
        // load word list
    val wordlist = Solver.loadWordList()
        // wessel.wordlesolver.Solver.solve

    Solver().resolveGuess(wordlist, Solver.createTestGuess())
}