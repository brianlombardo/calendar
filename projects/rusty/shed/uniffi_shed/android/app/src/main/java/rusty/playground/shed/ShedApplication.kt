package rusty.playground.shed

import android.app.Application
import android.util.Log
import java.lang.Exception

class ShedApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    System.loadLibrary("playground")
  }
}