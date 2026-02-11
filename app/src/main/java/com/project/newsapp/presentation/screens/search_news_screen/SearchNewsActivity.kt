package com.project.newsapp.presentation.screens.search_news_screen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.newsapp.presentation.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class SearchNewsActivity : ComponentActivity() {
    lateinit var speechRecognizer: SpeechRecognizer
    lateinit var recognitionIntent: Intent
    lateinit var searchViewModel: SearchNewsViewModel
    var isMicrophoneListening = mutableStateOf(
        false
    )
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            // Handle permission denial (e.g., show a toast)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
            NewsAppTheme {
                searchViewModel = hiltViewModel<SearchNewsViewModel>()
                setUpSpeechRecognizer()
                SearchNewsScreen(
                    searchViewModel.newsQueryState,
                    isMicrophoneListening = isMicrophoneListening.value,
                    onBackPress = {
                        onBackPressedDispatcher.onBackPressed()
                    },
                    onSubmitText = { newsSearchText ->
                        searchViewModel.updateQueryText(newsSearchText)
                    },
                    textFromSpeechRecognizer = {
                        isMicrophoneListening.value=true
                        speechRecognizer.startListening(recognitionIntent)
                    })
            }
        }
    }



    fun setUpSpeechRecognizer(){
        speechRecognizer= SpeechRecognizer.createSpeechRecognizer(this)

        recognitionIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,true)
            }
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onBeginningOfSpeech() {}

            override fun onBufferReceived(p0: ByteArray?) {}

            override fun onEndOfSpeech() {
                isMicrophoneListening.value=false
            }

            override fun onError(error: Int) {
                val message = when(error) {
                    SpeechRecognizer.ERROR_NO_MATCH -> "No speech recognized"
                    SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
                    else -> "Error: $error"
                }
                isMicrophoneListening.value = false
            }

            override fun onEvent(p0: Int, p1: Bundle?) {}

            override fun onPartialResults(p0: Bundle?) {}

            override fun onReadyForSpeech(p0: Bundle?) {}

            override fun onResults(results: Bundle?) {
                val data = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                 data?.get(0)?.let {searchText->
                     Log.d("mrjnwgngjnjgn", "onResults: $searchText ")
                     searchViewModel.fetchNews(searchText)
                 }
            }

            override fun onRmsChanged(p0: Float) {}

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
    }
}