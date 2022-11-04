package com.example.muslim_everyday.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim_everyday.R
import com.example.muslim_everyday.data_class.Question
import com.example.muslim_everyday.databinding.QuestionCardItemBinding

class FirstSettingRVAdapter(private val questionsList: List<Question>): RecyclerView.Adapter<QuestionCardViewHolder>() {
    private lateinit var binding: QuestionCardItemBinding
    private lateinit var scaleInAnim: Animation
    private lateinit var scaleOutAnim: Animation

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionCardViewHolder {
        binding = QuestionCardItemBinding.inflate(LayoutInflater.from(parent.context))

        scaleInAnim = AnimationUtils.loadAnimation(parent.context,R.anim.scale_in)
        scaleOutAnim = AnimationUtils.loadAnimation(parent.context, R.anim.scale_out)

        return QuestionCardViewHolder(binding, scaleInAnim, scaleOutAnim)
    }

    override fun onBindViewHolder(holder: QuestionCardViewHolder, position: Int) {
        val question = questionsList[position]
        holder.bind(question)
    }

    override fun getItemCount(): Int {
        return questionsList.size
    }
}

class QuestionCardViewHolder(private val binding: QuestionCardItemBinding,
                   private val scaleInAnim: Animation,
                   private val scaleOutAnim: Animation):
    RecyclerView.ViewHolder(binding.root), Animation.AnimationListener {
    private var isQuestionCardView = false

    fun bind(questions: Question) {
        scaleOutAnim.setAnimationListener(this)

        binding.apply {
            isQuestionCardView = false
            activateQuestion(questions.q)
            deactivateAnswer()

            btnYes.setOnClickListener {
                activateAnswer(questions.a2)
                deactivateQuestion()
                isQuestionCardView = true
            }
            btnNo.setOnClickListener {
                activateAnswer(questions.a1)
                deactivateQuestion()
                isQuestionCardView = true
            }

            btnYes2.setOnClickListener {
                // Включить уведомление на намаз
            }
            btnNo2.setOnClickListener {
                // Выключить уведомление на намаз
            }
        }
    }

    private fun activateAnswer(question: String) {
        binding.apply {
            cvAnswer.isVisible = true
            cvAnswer.isActivated = true
            cvAnswer.startAnimation(scaleInAnim)
            tvQuestion2.text = question
            btnYes2.isClickable = true
            btnNo2.isClickable = true
        }
    }

    private fun deactivateAnswer() {
        binding.apply {
            isQuestionCardView = false
            cvAnswer.startAnimation(scaleOutAnim)
            btnYes2.isClickable = false
            btnNo2.isClickable = false
        }
    }

    private fun activateQuestion(question: String) {
        binding.apply {
            cvQuestion.isVisible = true
            cvQuestion.isActivated = true
            cvQuestion.startAnimation(scaleInAnim)
            tvQuestion.text = question
            btnYes.isClickable = true
            btnNo.isClickable = true
        }
    }

    private fun deactivateQuestion() {
        binding.apply {
            isQuestionCardView = true
            cvQuestion.startAnimation(scaleOutAnim)
            btnYes.isClickable = false
            btnNo.isClickable = false
        }
    }

    override fun onAnimationStart(p0: Animation?) {
    }

    override fun onAnimationEnd(p0: Animation?) {
        if(isQuestionCardView) {
            binding.cvQuestion.isActivated = false
            binding.cvQuestion.isVisible = false
        }else {
            binding.cvAnswer.isActivated = false
            binding.cvAnswer.isVisible = false
        }
    }

    override fun onAnimationRepeat(p0: Animation?) {
    }
}
