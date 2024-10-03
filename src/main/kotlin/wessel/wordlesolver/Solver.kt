package wessel.wordlesolver

class Solver {

    companion object {

        fun loadWordList(): List<String> {

            val wordlist = this::class.java.getResource("/answerslist.txt")
                ?.readText()?.split("\\s+".toRegex())
            if (wordlist != null) {
                println("Loaded ${wordlist.size} words into the list of possible answers.")
            }
            else
                println("Unable to load word list.")

            return wordlist ?: emptyList()
        }

        fun createTestGuess(): Guess {
            // answer: SCRUB
            return Guess(
                listOf(
                    Letter('m', Color.GREEN, 0),
                    Letter('r', Color.GREY, 1),
                    Letter('e', Color.YELLOW, 2),
                    Letter('m', Color.YELLOW, 3),
                    Letter('d', Color.YELLOW, 4),
                )
            )
        }

        fun printGuess(guess: Guess) {
            val sb = StringBuilder()

            for (letter in guess.letters)
                when (letter.color) {
                    Color.GREEN ->
                        sb.append("\u001b[32m${letter.value}\u001B[0m ")
                    Color.YELLOW ->
                        sb.append("\u001b[33m${letter.value}\u001B[0m ")
                    Color.GREY ->
                        sb.append("${letter.value} ")
                }
            println("Your guess: $sb")
        }

        fun printWordList(words: List<String>) {
            println("${words.size} words left in the wordlist.")
            if (words.size < 35) {
                for (word in words) {
                    print("$word ")
                }
            }
        }
    }

    fun resolveGuess(wordlist: List<String>, guess: Guess): List<String> {
        var remainingWords = wordlist

        // for each letter in the guess,
        guess.letters.forEach { letter ->
            // if yellow, eliminate all words don't contain
            remainingWords = when (letter.color) {
                Color.YELLOW ->
                    remainingWords.filter { it.contains(letter.value) }
                        // if letter is yellow, can't be in this position in the answer
                        .filter { it[letter.index] != letter.value }

                Color.GREEN ->
                    remainingWords.filter {
                        it[letter.index] == letter.value
                    }

                // if grey, eliminate all words that miss that letter
                Color.GREY ->
                    remainingWords.filter { !it.contains(letter.value) }

            }
        }
        return remainingWords
    }
}