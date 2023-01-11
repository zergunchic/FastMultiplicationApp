package evs.factory.fastmultiplicationapp.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import evs.factory.fastmultiplicationapp.R

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int){
    textView.text = String.format(
                textView.context.getString(R.string.fin_right),
                count
            )
}

@BindingAdapter("score")
fun bindScore(textView: TextView, score:Int){
    textView.text = String.format(
                textView.context.getString(R.string.fin_score),
                score
            )
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, percent:Int){
    textView.text = String.format(
        textView.context.getString(R.string.fin_req),
        percent
    )
}

@BindingAdapter("resultEmoji")
fun bindResEmjk(textView: TextView, percent:Int){
    textView.text = String.format(
        textView.context.getString(R.string.fin_req),
        percent
    )
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, enough: Boolean){
    textView.setTextColor(getColorByState(textView.context,enough))
}
@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar, enough: Boolean){
    val color= getColorByState(progressBar.context,enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}
private fun getColorByState(context: Context, goodState: Boolean):Int{
    val colorResId = if(goodState)android.R.color.holo_green_light
    else android.R.color.holo_red_light
    return ContextCompat.getColor(context,colorResId)
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, num: Int){
    textView.text = num.toString()
}

interface OnOptionClickListener{
    fun onOptionClick( option: Int)
}
//Установка слушателей клика
@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener:OnOptionClickListener){
    textView.setOnClickListener{
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}