package mx.com.emv.menotes

import android.app.Application
import mx.com.emv.menotes.di.Injector

class MeNotesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Injector.setup(this)
    }
}