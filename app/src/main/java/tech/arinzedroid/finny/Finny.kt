package tech.arinzedroid.finny

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump

var context: Context? = null

class Finny : Application() {



    override fun onCreate() {
        super.onCreate()

        context = this.applicationContext

        Stetho.initializeWithDefaults(this)

        ViewPump.init(ViewPump.builder()
                .addInterceptor(CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/sedan_regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build())
    }


    companion object {
        @JvmStatic
        fun getContext() : Context? = context
    }

}