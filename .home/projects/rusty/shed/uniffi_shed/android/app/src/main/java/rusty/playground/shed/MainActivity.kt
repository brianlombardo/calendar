package rusty.playground.shed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import rusty.playground.shed.ui.theme.ShedTheme
import uniffi.Playground.createPlayground


class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val greeting =
      createPlayground(
        """ {"equipment": [
                  "Swing",
                  { "Slide": "Chute" },
                  { "MerryGoRound": { "radius": 5 }},
                  { "Sandbox": { "width": 10, "height": 1, "depth": 10 }}
                ]}
            """.trimIndent()
      )

    setContent {
      ShedTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          Greeting(greeting)
        }
      }
    }
  }
}

@Composable
fun Greeting(greeting: String, modifier: Modifier = Modifier) {
  Text(
    text = "$greeting!",
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  ShedTheme {
    Greeting("Android")
  }
}