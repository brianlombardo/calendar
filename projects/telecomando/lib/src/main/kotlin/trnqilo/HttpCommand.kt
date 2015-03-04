package trnqilo

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request.Builder
import okhttp3.RequestBody.Companion.toRequestBody

data class HttpCommand(
  val url: String,
  val body: String? = null
) : Command {
  override fun execute(): String = try {
    OkHttpClient().newCall(
      Builder().url(url).apply {
        body?.apply {
          post(toRequestBody("application/json; charset=utf-8".toMediaType()))
        }
      }.build()
    ).execute().body?.string() ?: "Http command failed."
  } catch (e: Exception) {
    e.errorMessage()
  }
}
