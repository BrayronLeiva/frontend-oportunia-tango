package oportunia.maps.frontend.taskapp.presentation.ui.components

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

fun updateLocale(context: Context, locale: Locale) {
    val activity = context as? Activity ?: return

    val appLocale = LocaleListCompat.create(locale)
    AppCompatDelegate.setApplicationLocales(appLocale)

    val config = Configuration(context.resources.configuration)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val localeListCompat = LocaleListCompat.create(locale)
        val localesArray = localeListCompat.toLanguageTags().split(",").map { Locale.forLanguageTag(it) }.toTypedArray()
        val frameworkLocaleList = LocaleList(*localesArray)
        config.setLocales(frameworkLocaleList)
    } else {
        @Suppress("DEPRECATION")
        config.locale = locale
    }

    context.resources.updateConfiguration(config, context.resources.displayMetrics)
    activity.recreate()
}