package com.example.footballpj.views

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.footballpj.viewsModels.OnboardingViewModel

@Composable
fun AppNavHost(navController: NavHostController,
               onboardingViewModel: OnboardingViewModel = hiltViewModel()) {

    val startDestination = if (onboardingViewModel.isOnboardingCompleted()) "home" else "onboarding"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("onboarding") {
            OnboardingScreen(
                onFinish = {
                    onboardingViewModel.setOnboardingCompleted()
                    navController.navigate("home") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(navController)
        }

        composable("leagueInfo/{leagueId}") { backStackEntry ->
            val leagueId = backStackEntry.arguments?.getString("leagueId")?.toIntOrNull()
            leagueId?.let {
                LeagueInfoScreen(navController,it)
            }
        }

    }
}
