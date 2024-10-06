import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import wessel.wordlesolver.Color
import wessel.wordlesolver.Guess
import wessel.wordlesolver.Letter
import wessel.wordlesolver.Solver
import org.junit.jupiter.api.Test

class WordleSolverTest {

    private var solver =  Solver()
    private lateinit var wordlist : List<String>

    @BeforeEach
    fun init() {
        wordlist = solver.loadWordList("/testlist.txt")
    }

    @Test
    fun `green letter position 5`() {
        val guess = Guess(
            listOf(
                Letter('x', Color.GREY, 0),
                Letter('x', Color.GREY, 1),
                Letter('x', Color.GREY, 2),
                Letter('x', Color.GREY, 3),
                Letter('a', Color.GREEN, 4),
            )
        )

        val expected = listOf(
            "abcda",
            "ababa",
        )

        val result = solver.resolveGuess(wordlist, guess)

        assertEquals(expected, result)
    }

    @Test
    fun `yellow letter position 5`() {
        val guess = Guess(
            listOf(
                Letter('x', Color.GREY, 0),
                Letter('x', Color.GREY, 1),
                Letter('x', Color.GREY, 2),
                Letter('x', Color.GREY, 3),
                Letter('a', Color.GREEN, 4),
            )
        )

        val expected = listOf(
            "abcde",
        )

        val result = solver.resolveGuess(wordlist, guess)

        assertEquals(expected, result)
    }

    @Test
    fun `same letter is green then yellow`() {
        val guess = Guess(
            listOf(
                Letter('a', Color.GREEN, 0),
                Letter('a', Color.YELLOW, 1),
                Letter('x', Color.GREY, 2),
                Letter('x', Color.GREY, 3),
                Letter('x', Color.GREY, 4),
            )
        )

        val expected = listOf(
            "aaaab",
            "abcda",
            "ababa",
        )

        val result = solver.resolveGuess(wordlist, guess)

        assertEquals(expected, result)
    }

    @Test
    fun `same letter is yellow twice`() {
        val guess = Guess(
            listOf(
                Letter('x', Color.GREY, 0),
                Letter('a', Color.YELLOW, 1),
                Letter('x', Color.GREY, 2),
                Letter('a', Color.YELLOW, 3),
                Letter('x', Color.GREY, 4),
            )
        )

        val expected = listOf(
            "abcda",
        )

        val result = solver.resolveGuess(wordlist, guess)

        assertEquals(expected, result)
    }
}