package mx.edu.utez.dealc

import android.app.Application
import mx.edu.utez.dealc.utils.Shared

class MainCoreApplication: Application() {
    companion object {
        lateinit var shared: Shared
    }

    override fun onCreate() {
        super.onCreate()
        shared = Shared(applicationContext)
    }
}