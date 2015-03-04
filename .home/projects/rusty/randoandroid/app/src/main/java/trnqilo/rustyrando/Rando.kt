package trnqilo.rustyrando

import java.lang.System.loadLibrary

class Rando {
  companion object {
    init {
      loadLibrary("rustyrando")
    }

    @JvmStatic
    external fun getRando(label: String, max: Int): String
  }
}
