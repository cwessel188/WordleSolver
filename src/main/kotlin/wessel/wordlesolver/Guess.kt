package wessel.wordlesolver

data class Guess(
    val letters: List<Letter> // always five
)

data class Letter(
    val value: Char,
    val color: Color,
    val index: Int
) {

}

enum class Color {
    GREY,
    YELLOW,
    GREEN
}