package me.uditverma.sample.composeperformance

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.uditverma.sample.composeperformance.ui.step0.ComposePerformanceScreen
import org.brotli.dec.BrotliInputStream
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.util.Base64

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePerformanceScreen()
        }

//        decompress()
    }
}

fun decompress() {

    // enter token here
    val encodedToken = "G-UCAJwHdizgeHoM5rqMSilit3e6hsq_vwR4mkCxWnptB1QsfaFjuTMNRNvv_70teQ24uKcjiSSwFjZPuS1z10ojf-WmHV6n5EW9ebUOmy174YSBgqBYBbmxofbQbllSWw_TSIYV-9ylcicmFOOyHVowBk-wxKm2LVaZxivHHjDY1_9HOf5aB1fJ92W5N3dwRLo6VtQBOXb4D3P7dnV6JFmK8EQTqTkPkK0KOtGh872BpdxqjliGaNXZ_cPVw2vjtAEWw5cfAIB4BlTm0em5AGCyR51sDOB0Akmtphir1pswOQAdG5Tmgp6xhsJAFxtR88mMXD6df_rdLXs6RM8ApQwYkwlETWhbrY4-iYlOAw=="

    val rd =
        BufferedReader(
            InputStreamReader(
                BrotliInputStream(
                    ByteArrayInputStream(
                        Base64.getDecoder().decode(encodedToken)
                    )
                )
            )
        )

    val result = StringBuilder()
    var line: String?
    while (rd.readLine().also { line = it } != null) {
        result.append(line)
    }
    Log.d("OUTPUT", result.toString())
}
