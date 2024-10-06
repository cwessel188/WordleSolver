package wessel.wordlesolver

class Solver {

    companion object {

        val defaultWordListLocation = "/answerslist.txt"

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

    fun loadWordList(wordListLocation: String = defaultWordListLocation): List<String> {

        val wordlist = this::class.java.getResource(wordListLocation)
            ?.readText()?.split("\\s+".toRegex())
        if (wordlist != null) {
            println("Loaded ${wordlist.size} words into the list of possible answers.")
        } else
            println("Unable to load word list.")

        return wordlist ?: emptyList()
    }

    fun resolveGuess(wordlist: List<String>, guess: Guess): List<String> {
        var remainingWords = wordlist

        // for each letter in the guess,
        // if the letter is green, process it first.
        val greenIndexes = emptyList<Int>().toMutableList()

        val greenIndices = guess.letters.filter { letter ->
            letter.color == Color.GREEN
        }.map { it.index }

        guess.letters.forEach { thisLetter ->
            if (thisLetter.color == Color.GREEN) {
                remainingWords = remainingWords.filter { word ->
                    word[thisLetter.index] == thisLetter.value
                }
            } // green

            if (thisLetter.color == Color.GREY) {
                remainingWords = remainingWords.filter { word ->
                    !word.contains(thisLetter.value)
                }
            }

            if (thisLetter.color == Color.YELLOW) {
                remainingWords = remainingWords.filter { word ->
                    // don't search green indices
                    word.contains(thisLetter.value) &&
                            word[thisLetter.index] != thisLetter.value &&
                            (greenIndices.fold(true) {acc, idx ->
                                acc && word[idx] != thisLetter.value
                            })
                }
            }

        }

        /*
        for each word in the list:
        check the indices of each green letter.
        check for the presence of a yellow letter in ANY INDEX THAT IS NOT GREEN
        check for the exclusion of each black letter

         */
//        guess.letters.forEach { letter ->
//            // if yellow, eliminate all words don't contain
//            remainingWords = when (letter.color) {
//                Color.YELLOW -> {
//                    remainingWords.filter { it.contains(letter.value) && !usedIndexes.contains(letter.index) }
//                        // if letter is yellow, can't be in this position in the answer
//                        .filter { letter.value != it[letter.index] }
//                }
//
//
//                Color.GREEN ->
//                    return@forEach
//                    // handled above
//
//                // if grey, eliminate all words that miss that letter
//                Color.GREY ->
//                    remainingWords.filter { !it.contains(letter.value) }
//
//            }
//        }
        return remainingWords
    }
}