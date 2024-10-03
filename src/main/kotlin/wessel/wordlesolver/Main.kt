import wessel.wordlesolver.Solver

fun main() {
        // load word list
    val wordlist = Solver.loadWordList()
        // wessel.wordlesolver.Solver.solve

    val guess = Solver.createTestGuess()
    Solver.printGuess(guess)
    val possibleSolutions = Solver().resolveGuess(wordlist, guess)
    Solver.printWordList(possibleSolutions)
}