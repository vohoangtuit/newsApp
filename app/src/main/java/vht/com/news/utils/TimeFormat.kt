package vht.com.news.utils

import android.annotation.SuppressLint
import org.ocpsoft.prettytime.PrettyTime
import vht.com.news.constant.Constant
import java.text.DateFormat
import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

object TimeFormat {
    private val currentCalendar = Calendar.getInstance()
    enum class DateFormatDefinition(private val format: String) {
        YYYY("yyyy"),
        MM("MM"),
        DD("dd"),
        YY_MM("yy/MM"),
        YYYY_MM("yyyy/MM"),
        YYYY_MM_NO_SLASH("yyyyMM"),
        MM_DD("MM/dd"),
        MM_DD_YYYY("MM/dd/yyyy"),
        DD_MMM("dd MMM"),
        DD_MMM_YYYY("dd MMM yyyy"),
        MM_DD_NO_SLASH("MMdd"),
        YY_MM_DD("yy/MM/dd"),
        YYYY_MM_DD("yyyy/MM/dd"),
        HH_MM_EEE_YYYY_MM_DD("HH:mm EEE, yyyy/MM/dd"),
        YYYY_MM_DD_("yyyy-MM-dd"),
        DD_MM_YYYY("dd/MM/yyyy"),
        YYYY_MM_DD_HYPHEN("yyyy-MM-dd"),
        DD_MM_YYYY_HYPHEN("yyyy-MM-dd"),
        YYYY_MM_DD_NO_SLASH("yyyyMMdd"),
        HH_MM("HH:mm"),
        HH_MM_SS("HH:mm:ss"),
        YYYY_MM_DD_HH_MM("yyyy/MM/dd HH:mm"),
        YYYY_MM_DD_HYPHEN_HH_MM("yyyy/MM/dd - HH:mm"),
        DD_MM_YYYY_HYPHEN_HH_MM("dd/MM/yyyy - HH:mm"),
        YYYY_MM_DD_HYPHEN_HH_MM_("yyyy-MM-dd HH:mm"),
        MM_DD_YYYY_HH_MM("MM/dd/yyyy HH:mm"),
        YYYY_MM_DD_HH_MM_SS("yyyy/MM/dd HH:mm:ss"),
        DD_MM_YYYY_HH_MM_SS("dd/MM/yyyy HH:mm:ss"),
        DD_MM_YYYY_HH_MM("dd/MM/yyyy   HH:mm"),
        YYYY_MM_DD_HH_MM_SS_AM_PM("yyyy/MM/dd  hh:mm:ss a"),
        YYYY_MM_DD_HH_MM_NO_SLASH("yyyyMMddHHmm"),
        YYYY_MM_DD_HH_MM_SS_NO_SLASH("yyyyMMddHHmmss"),
        YYYY_MM_DD_HH_MM_SS_SSS("yyyy/MM/dd HH:mm:ss SSS"),
        YYYY_MM_DD_HH_MM_SS_SSS_NO_SLASH("yyyyMMddHHmmssSSS"),
        YYYY_MM_DD_T_HH_MM_SS_SSSSSS("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"),
        DD_MM_YYYY_T_HH_MM_SS_SSSSSS("dd-MM-yyyy'T'HH:mm:ss.SSSSSS"),
        YYYY_MM_DD_T_HH_MM_SS_Z("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
        YYYY_MM_DD_T_HH_MM_SS_POWER("yyyy-MM-dd'T'HH:mm:ssZ"),
        YYYY_MM_DD_T_HH_MM_SSS_Z("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
        DD_MMMM_YYYY("dd MMMM yyyy"),
        YYYY_MM_DD_T_HH_MM_SS("yyyy-MM-dd'T'HH:mm:ss"),
        DD_MMM_at_H_MM_A("dd MMM 'at' h:mm a"),
        HH_MM_A_EEE_DD_MMM_YYYY("hh.mm a, EEEE dd MMM yyyy"),
        HH_MM_EEE_DD_MM_YYYY("hh:mm      EEEE, yyyy/MM/dd"),
        EEE_MMM_DD_YYYY_HH_MM_A("EEE MMM, dd, yyyy h:mm a"),
        EEE_DD_MMM_YYYY_HH_MM_A("EEE dd, MMM, yyyy h:mm a"),
        YYYY_MM_DD_EEE_HH_MM_SS("yyyy/MM/dd EEE HH:mm:ss"),
        DD_MM_YYYY_EEE_HH_MM_SS("dd/MM/yyyy EEE HH:mm:ss"),
        E_d_MMM_yyyy("E, d MMM yyyy"),
        yyyy_MM_dd_T_HH_mm_ss_Z("yyyy-MM-dd'T'HH:mm:ss'Z'"),
        EEE_MMM_DD_YYYY("EEE, yyyy/MM/dd"), HH_MM_A("h:mm a");
        override fun toString(): String {
            return format
        }
    }
    fun getCurrentMillisecond(): Long {
        return System.currentTimeMillis()
    }

    fun getCurrentDayOfMonth(): Int {
        return currentCalendar.get(Calendar.DAY_OF_MONTH)
    }

    fun getCurrentMonth(): Int {
        return currentCalendar.get(Calendar.MONTH)
    }

    fun getCurrentHourOfDay(): Int {
        return currentCalendar.get(Calendar.HOUR_OF_DAY)
    }

    fun getCurrentMinute(): Int {
        return currentCalendar.get(Calendar.MINUTE)
    }
    fun getCurrentYear(): Int {
        return currentCalendar.get(Calendar.YEAR)
    }

    fun getDayOfWeek(): Int {
        return currentCalendar.get(Calendar.DAY_OF_WEEK)
    }

    fun getYear(date: Date?): Int {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal[Calendar.YEAR]
    }
    fun getMonth(date: Date?): Int {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal[Calendar.MONTH]
    }

    fun getDayOfMonth(date: Date?): Int {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal[Calendar.DAY_OF_MONTH]
    }
    fun getCurrentDate(): Date? {
        return currentCalendar.time
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateBefore(before: Int): String? {
        val cal = Calendar.getInstance()
        val s = SimpleDateFormat(DateFormatDefinition.YYYY_MM_DD_HYPHEN.toString())
        cal.add(Calendar.DAY_OF_YEAR, before)
        return s.format(Date(cal.timeInMillis))
    }
    private fun parse(src: String?, format: String, timeZone: String): Date? {
        if (src == null) {
            return null
        }
        @SuppressLint("SimpleDateFormat") val sdf =
            SimpleDateFormat(format)
        sdf.isLenient = false
        if (timeZone.isNotEmpty()) {
            sdf.timeZone = TimeZone.getTimeZone(timeZone)
        }
        return sdf.parse(src, ParsePosition(0))
    }
    fun toDate(str: String?, format: DateFormatDefinition): Date? {
        return parse(str, format.toString(), Constant.BLANK)
    }

    fun toDate(str: String?, timeZone: TCTimeZone, format: DateFormatDefinition): Date? {
        return parse(str, format.toString(), timeZone.value)
    }

    fun formatDate(year: Int, month: Int, day: Int, format: DateFormatDefinition?): String? {
        val calendar = Calendar.getInstance()
        calendar[year, month] = day
        return formatDate(calendar.time, format.toString())
    }
    fun formatDate(date: Date?, format: String?): String? {
        if (date == null) {
            return ""
        }
        val df: DateFormat = SimpleDateFormat(format)
        return df.format(date)
    }
    fun formatDate(date: String?): String? {
        return convertToCurrentTimeZoneDate(date, DateFormatDefinition.YYYY_MM_DD_T_HH_MM_SS_Z, DateFormatDefinition.HH_MM_EEE_YYYY_MM_DD
        )
    }
    fun formatDateE(date: String?): String? {
        return convertToCurrentTimeZoneDate(date, DateFormatDefinition.yyyy_MM_dd_T_HH_mm_ss_Z, DateFormatDefinition.E_d_MMM_yyyy
        )
    }
    fun formatDate(time:Long,outputFormat: DateFormatDefinition):String{
        @SuppressLint("SimpleDateFormat")
        val outFormat: DateFormat = SimpleDateFormat(outputFormat.toString())
        var date =Date(time)
        return outFormat.format(date)
    }
    fun convertToCurrentTimeZoneDate(dateString: String?, inputFormat: DateFormatDefinition, outputFormat: DateFormatDefinition): String? {
        @SuppressLint("SimpleDateFormat") val utcFormat: DateFormat =
            SimpleDateFormat(inputFormat.toString())
        utcFormat.timeZone = TimeZone.getTimeZone(TCTimeZone.UTC.value)
        @SuppressLint("SimpleDateFormat") val pstFormat: DateFormat =
            SimpleDateFormat(outputFormat.toString())
        pstFormat.timeZone = TimeZone.getTimeZone(getCurrentTimeZone())
        var date: Date? = null
        try {
            date = utcFormat.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return pstFormat.format(date)
    }
    enum class TCTimeZone(var value: String) {
        UTC("UTC"),
        GMT("GMT");
    }

    fun formatHoursMinueFromString(
        time: String?,
        fromFormat: DateFormatDefinition,
        toFormat: DateFormatDefinition
    ): String? {
        val date12Format =
            SimpleDateFormat(fromFormat.toString(), Locale.ENGLISH)
        @SuppressLint("SimpleDateFormat") val date24Format =
            SimpleDateFormat(toFormat.toString())
        var hours: String? = ""
        try {
            hours = date24Format.format(date12Format.parse(time))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return hours
    }
    private fun getCurrentTimeZone(): String? {
        val tz = Calendar.getInstance().timeZone
        return tz.id
    }
    fun getTimeZone(): String? {
        return TimeZone.getDefault().id
    }

    fun DateToTimeFormat(oldstringDate: String?): String? {
        val p = PrettyTime(Locale(getCountry()))
        var isTime: String? = null
        try {
            val sdf = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                Locale.ENGLISH
            )
            val date = sdf.parse(oldstringDate)
            isTime = p.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return isTime
    }

    fun DateFormat(oldstringDate: String?): String? {
        val newDate: String
        val dateFormat =
            SimpleDateFormat(DateFormatDefinition.E_d_MMM_yyyy.toString(), Locale(getCountry()))
        newDate = try {
            val date =
                SimpleDateFormat(DateFormatDefinition.yyyy_MM_dd_T_HH_mm_ss_Z.toString()).parse(oldstringDate)
            dateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace().toString()
        }
        return newDate
    }

    fun getCountry(): String? {
        val locale = Locale.getDefault()
        val country = locale.country.toString()
        return country.toLowerCase()
    }

    fun getLanguage(): String? {
        val locale = Locale.getDefault()
        val country = locale.language.toString()
        return country.toLowerCase()
    }
}