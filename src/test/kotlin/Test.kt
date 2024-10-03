import org.junit.jupiter.api.Assertions.assertEquals
import wessel.wordlesolver.Color
import wessel.wordlesolver.Guess
import wessel.wordlesolver.Letter
import wessel.wordlesolver.Solver
import org.junit.jupiter.api.Test

class WordleSolverTest {

    @Test
    fun doubleLetterTest() {
        val guess = Guess(
            listOf(
                Letter('m', Color.GREEN, 0),
                Letter('r', Color.GREY, 1),
                Letter('e', Color.YELLOW, 2),
                Letter('m', Color.YELLOW, 3),
                Letter('d', Color.YELLOW, 4),
            )
        )

        val wordlist = Solver.loadWordList()

        val result = Solver().resolveGuess(wordlist, guess)

        assertEquals(listOf("modem"), result)
    }
}