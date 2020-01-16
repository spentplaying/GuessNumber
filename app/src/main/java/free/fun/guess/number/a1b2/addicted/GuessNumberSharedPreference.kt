package free.`fun`.guess.number.a1b2.addicted

import android.content.Context
import java.security.AccessControlContext

class GuessNumberSharedPreference {
    companion object {
        val GUESS_NUMBER = "GuessNumber"
        val PLAYING_TIMES = "PlayTimes"
        val GUESS_TIMES_TOTAL = "GuessTimesTotal"
        val UNDER_7_TIMES = "Under7Times"
        fun getPlayTimes(context: Context) : Int {
            val profilePreferences = context.getSharedPreferences(GUESS_NUMBER, Context.MODE_PRIVATE)
            return profilePreferences.getInt(PLAYING_TIMES, 0)
        }

        fun setPlayTimes(context: Context, value : Int) {
            val profilePreferences = context.getSharedPreferences(GUESS_NUMBER, Context.MODE_PRIVATE)
            profilePreferences.edit().putInt(PLAYING_TIMES, value).apply()
        }

        fun getGuessTimesTotal(context: Context) : Int {
            val profilePreferences = context.getSharedPreferences(GUESS_NUMBER, Context.MODE_PRIVATE)
            return profilePreferences.getInt(GUESS_TIMES_TOTAL, 0)
        }

        fun setGuessTimesTotal(context: Context, value : Int) {
            val profilePreferences = context.getSharedPreferences(GUESS_NUMBER, Context.MODE_PRIVATE)
            profilePreferences.edit().putInt(GUESS_TIMES_TOTAL, value).apply()
        }

        fun getUnder7Times(context: Context) : Int {
            val profilePreferences = context.getSharedPreferences(GUESS_NUMBER, Context.MODE_PRIVATE)
            return profilePreferences.getInt(UNDER_7_TIMES, 0)
        }

        fun setUnder7Times(context: Context, value : Int) {
            val profilePreferences = context.getSharedPreferences(GUESS_NUMBER, Context.MODE_PRIVATE)
            profilePreferences.edit().putInt(UNDER_7_TIMES, value).apply()
        }
    }
}