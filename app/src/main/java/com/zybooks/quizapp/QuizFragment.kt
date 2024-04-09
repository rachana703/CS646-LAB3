
package com.zybooks.quizapp
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.zybooks.quizapp.R

data class Question(
    val questionTextResId: Int,
    val correctAnswer: String,
    val options: List<String>
)

class QuizFragment : Fragment() {

    private lateinit var questions: List<Question>
    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var optionsContainer: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        optionsContainer = view.findViewById(R.id.optionsContainer)
        // Initialize questions
        questions = initializeQuestions()
        showQuestion()
        // Register the container for the context menu
        registerForContextMenu(optionsContainer)
    }

    private fun initializeQuestions(): List<Question> {
        return listOf(
            Question(R.string.question_1, getString(R.string.answer_1), resources.getStringArray(R.array.options_1).toList()),
            Question(R.string.question_2, getString(R.string.answer_2), resources.getStringArray(R.array.options_2).toList()),
            Question(R.string.question_3, getString(R.string.answer_3), resources.getStringArray(R.array.options_3).toList()),
            Question(R.string.question_4, getString(R.string.answer_4), resources.getStringArray(R.array.options_4).toList()),
            Question(R.string.question_5, getString(R.string.answer_5), resources.getStringArray(R.array.options_5).toList()),
            Question(R.string.question_6, getString(R.string.answer_6), resources.getStringArray(R.array.options_6).toList()),
            Question(R.string.question_7, getString(R.string.answer_7), resources.getStringArray(R.array.options_7).toList()),
            Question(R.string.question_8, getString(R.string.answer_8), resources.getStringArray(R.array.options_8).toList())
        )
    }

    private fun showQuestion() {
        if (currentQuestionIndex < questions.size) {
            val currentQuestion = questions[currentQuestionIndex]
            view?.findViewById<TextView>(R.id.questionTextView)?.text = getString(currentQuestion.questionTextResId)
            optionsContainer.removeAllViews()
            currentQuestion.options.forEach { option ->
                val button = Button(context).apply {
                    text = option
                    setOnClickListener { checkAnswer(option) }
                }
                optionsContainer.addView(button)
            }
        } else {
            showGameOverDialog()
        }
    }

    private fun checkAnswer(selectedAnswer: String) {
        val correctAnswer = questions[currentQuestionIndex].correctAnswer
        if (selectedAnswer == correctAnswer) {
            score++
        }
        currentQuestionIndex++
        showQuestion()
    }

    private fun showGameOverDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Game Over")
            .setMessage("Your score is $score")
            .setPositiveButton("Restart") { _, _ ->
                score = 0
                currentQuestionIndex = 0
                showQuestion()
            }
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.quiz_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.restart_game -> {
                score = 0
                currentQuestionIndex = 0
                showQuestion()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.quiz_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.skip_question -> {
                skipQuestion()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun skipQuestion() {
        // Increment the question index to skip the current question
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            showQuestion()
        } else {
            showGameOverDialog()
        }
    }


    companion object {
        fun newInstance() = QuizFragment()
    }
}
