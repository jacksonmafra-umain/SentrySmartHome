package com.umain.sentry

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.umain.sentry.feature.activities.activitiesScreen
import com.umain.sentry.feature.devicehub.deviceHubScreen
import com.umain.sentry.feature.home.homeScreen
import com.umain.sentry.feature.room.roomScreen
import com.umain.sentry.navigation.SentryRoute

@Composable
fun SentryNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SentryRoute.Home,
    ) {
        homeScreen(onNavigate = { navController.navigateTo(it) })
        activitiesScreen(onBack = { navController.popBackStack() })
        deviceHubScreen(onBack = { navController.popBackStack() })
        roomScreen(onBack = { navController.popBackStack() })
    }
}

private fun androidx.navigation.NavController.navigateTo(route: SentryRoute) {
    navigate(route)
}
