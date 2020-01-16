package free.`fun`.guess.number.a1b2.addicted

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.one_line_guess.*
import kotlinx.android.synthetic.main.one_line_guess.view.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var view: View
    var guessString = ""
    var realNumber = 0
    var isPlaying = false
    var realAnswer = 0
    var mGuessTimes = 0
    val initAnswerSet = Array(5040){0}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hide action bar
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        initializeAnswerSet()
        uiTestButton.setOnClickListener {
            view = LayoutInflater.from(this).inflate(R.layout.one_line_guess, null, false)
            uiScrollLinear.addView(view)
        }
        uiButton0.setOnClickListener {
            addNumber(0)
        }
        uiButton1.setOnClickListener {
            addNumber(1)
        }
        uiButton2.setOnClickListener {
            addNumber(2)
        }
        uiButton3.setOnClickListener {
            addNumber(3)
        }
        uiButton4.setOnClickListener {
            addNumber(4)
        }
        uiButton5.setOnClickListener {
            addNumber(5)
        }
        uiButton6.setOnClickListener {
            addNumber(6)
        }
        uiButton7.setOnClickListener {
            addNumber(7)
        }
        uiButton8.setOnClickListener {
            addNumber(8)
        }
        uiButton9.setOnClickListener {
            addNumber(9)
        }
        uiButtonC.setOnClickListener {
            minusNumber()
        }
        uiButtonOK.setOnClickListener {
            guessNumber(realNumber)
        }
        uiRestart.setOnClickListener {
            startGame()
        }
        uiRecord.setOnClickListener {
            showRecord()
        }
        if (GuessNumberSharedPreference.getPlayTimes(this) >= 1) {
            uiRecord.visibility = View.VISIBLE
        }
        startGame()
    }

    private fun minusNumber() {
        if (isPlaying) {
            if (guessString.isEmpty()) {
                return
            } else if (guessString.length == 1) {
                guessString = ""
                realNumber /= 10
            } else {
                guessString = guessString.substring(0, guessString.length - 2)
                realNumber /= 10
            }
            view.uiGuessNumber.text = guessString
        }
    }

    private fun addNumber(number : Int) {
        if (isPlaying) {
            if (guessString.isEmpty()) {
                guessString += number
                realNumber *= 10
                realNumber += number
            } else if (guessString.length < 7) {
                guessString += " " + number
                realNumber *= 10
                realNumber += number
            }
            view.uiGuessNumber.text = guessString
        }
    }

    private fun guessNumber(number : Int) {
        if (!isPlaying) return
        if (guessString.length != 7) return
        val totalDigit = Array(10){0}
        var number = number
        for(tmp in 0 until 4) {
            totalDigit[number % 10] ++
            if (totalDigit[number % 10] > 1) {
                Toast.makeText(this, "Can not contain dulplicate number", Toast.LENGTH_SHORT).show()
                return
            }
            number /= 10
        }
        var resultString = ""
        var resultNumber = compareTwoNumbers(realNumber, realAnswer)
        resultString = "" + resultNumber/10 + " A " + resultNumber % 10 + " B"
        view.uiGuessResult.text = resultString
        mGuessTimes ++
        uiGuessTimes.text = "" + mGuessTimes
        if (resultNumber != 40) {
            guessString = ""
            realNumber = 0
            view = LayoutInflater.from(this).inflate(R.layout.one_line_guess, null, false)
            uiScrollLinear.addView(view)
            uiScrollContainer.post {
                uiScrollContainer.fullScroll(View.FOCUS_DOWN)
            }
        } else {
            Toast.makeText(this, "Congrats you win", Toast.LENGTH_SHORT).show()
            isPlaying = false
            GuessNumberSharedPreference.setGuessTimesTotal(this, GuessNumberSharedPreference.getGuessTimesTotal(this) + mGuessTimes)
            GuessNumberSharedPreference.setPlayTimes(this, GuessNumberSharedPreference.getPlayTimes(this) + 1)
            if (mGuessTimes <= 7) {
                GuessNumberSharedPreference.setUnder7Times(this, GuessNumberSharedPreference.getUnder7Times(this) + 1)
            }
            if (GuessNumberSharedPreference.getPlayTimes(this) >= 1) {
                uiRecord.visibility = View.VISIBLE
            }
        }
    }

    private fun showRecord() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Games Record")
        var resultString = ""
        var mAverageTimes = String.format("%.2f", GuessNumberSharedPreference.getGuessTimesTotal(this).toFloat() / GuessNumberSharedPreference.getPlayTimes(this).toFloat())
        var mUnder7TimesRate = String.format("%.0f", 100 * GuessNumberSharedPreference.getUnder7Times(this).toFloat() / GuessNumberSharedPreference.getPlayTimes(this).toFloat())
        resultString = "Complete Games : " + GuessNumberSharedPreference.getPlayTimes(this) + "\n" + "Average Times : " + mAverageTimes + "\n" +"Under 7 times rate : " + mUnder7TimesRate + "%"
        builder.setMessage(resultString)
        builder.setPositiveButton("Confirm",{ dialog, whichButton ->
            println("confirm")
        })
        val dialog = builder.create()
        dialog.show()
    }

    private fun initializeAnswerSet() {
        var numberOfAnswer = 0
        for(num in 0 until 10000) {
            val tmp = Array(10){0}
            var number = num
            var flag = true
            for(i in 0 until 4) {
                tmp[number % 10] ++
                if (tmp[number % 10] > 1) {
                    flag = false
                    break
                }
                number /= 10
            }
            if (flag) {
                initAnswerSet[numberOfAnswer++] = num
            }
        }
    }

    private fun generateNewAnswer() {
        realAnswer = initAnswerSet[Random.nextInt(5040)]
    }

    private fun startGame() {
        generateNewAnswer()
        mGuessTimes = 0
        uiGuessTimes.text = "" + mGuessTimes
        isPlaying = true
        guessString = ""
        realNumber = 0
        uiScrollLinear.removeAllViews()
        view = LayoutInflater.from(this).inflate(R.layout.one_line_guess, null, false)
        uiScrollLinear.addView(view)
    }

    private fun compareTwoNumbers(numberA : Int, numberB : Int) : Int {
        val totalDigit = Array(10){0}
        val digitA = Array(4){0}
        val digitB = Array(4){0}
        var returnA = 0
        var returnB = 0
        var numberA = numberA
        var numberB = numberB
        for(tmp in 0 until 4) {
            digitA[tmp] = numberA % 10
            totalDigit[numberA % 10] ++
            numberA /= 10
        }
        for(tmp in 0 until 4) {
            digitB[tmp] = numberB % 10
            if (totalDigit[numberB % 10] > 0) {
                returnB ++
            }
            numberB /= 10
        }
        for(tmp in 0 until 4) {
            if (digitA[tmp] == digitB[tmp]) {
                returnA ++
            }
        }
        //combine both result
        return returnA * 10 + (returnB - returnA)
    }
}
