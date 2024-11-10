package com.inner.second.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.inner.second.core.model.ContractType
import com.inner.second.core.navigation.SecondScreen
import com.inner.second.feature.contract.SecondContractGetInfoRoute
import com.inner.second.feature.contract.SecondContractMainRoute
import com.inner.second.feature.home.SecondHome
import kotlin.reflect.typeOf

fun NavGraphBuilder.secondNavigation(
    navController: NavController,
) {
    composable<SecondScreen.Home> {
        SecondHome()
    }

    navigation<SecondScreen.Contract>(startDestination = SecondScreen.Main) {
        composable<SecondScreen.Main> {
            SecondContractMainRoute(
                onButtonClick = { contractType ->
                    navController.navigate(
                        route = SecondScreen.GetInfo(contractType),
                    )
                }
            )
        }
        composable<SecondScreen.GetInfo>(
            typeMap = mapOf(
                typeOf<ContractType>() to ContractType.navType,
            )
        ) {
            SecondContractGetInfoRoute(
                onBackButtonClick = { navController.popBackStack() },
                onNextActionButtonClick = {
                    navController.navigate(SecondScreen.Signature)
                }
            )
        }
        composable<SecondScreen.Signature> {
            /* TODO */
        }
        composable<SecondScreen.Send> {
            /* TODO */
        }
        composable<SecondScreen.Finish> {
            /* TODO */
        }
    }
}