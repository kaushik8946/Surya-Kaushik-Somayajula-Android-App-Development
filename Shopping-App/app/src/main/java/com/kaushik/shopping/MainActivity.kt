package com.kaushik.shopping

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kaushik.shopping.innerApp.AppStart
import com.kaushik.shopping.outerApp.OuterScreen
import com.kaushik.shopping.outerApp.SignInScreen
import com.kaushik.shopping.signIn.GoogleAuthUIClient
import com.kaushik.shopping.signIn.SignInViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val googleAuthUIClient by lazy {
        GoogleAuthUIClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.database.setPersistenceEnabled(true)
        setContent {
            MyNavigation()
        }
    }

    @Composable
    fun MyNavigation() {
        val navController = rememberNavController()
        Box {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    OuterScreen(navController)
                }

                composable("appstart") {
                    AppStart(
                        userdata = googleAuthUIClient.getSignedInUser(),
                        onSignOut = {
                            lifecycleScope.launch {
                                googleAuthUIClient.signOut()
                            }
                            Toast.makeText(applicationContext, "Signed Out", Toast.LENGTH_LONG)
                                .show()
                            navController.navigate("home")
                        },
                        googleAuthUIClient
                    )
                }

                composable("signup") {
                    val viewModel = viewModel<SignInViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()

                    LaunchedEffect(key1 = Unit) {
                        if (googleAuthUIClient.getSignedInUser() != null) {
                            navController.navigate("appstart")
                        }
                    }

                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if (result.resultCode == RESULT_OK) {
                                lifecycleScope.launch {
                                    val signInResult = googleAuthUIClient.signInWithIntent(
                                        intent = result.data ?: return@launch
                                    )
                                    viewModel.onSignInResult(signInResult)
                                }
                            }
                        }
                    )

                    LaunchedEffect(key1 = state.isSignInSuccessful) {
                        if (state.isSignInSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Sign in Successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate("appstart")
                            viewModel.resetState()
                        }
                    }

                    SignInScreen(
                        state = state,
                        onSignInClick = {
                            lifecycleScope.launch {
                                val signInIntentSender = googleAuthUIClient.signIn()
                                launcher.launch(
                                    IntentSenderRequest.Builder(
                                        signInIntentSender ?: return@launch
                                    ).build()
                                )
                            }
                        },
                        navController = navController
                    )
                }
            }
        }
    }
}
