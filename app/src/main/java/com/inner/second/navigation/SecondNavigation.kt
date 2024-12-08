package com.inner.second.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.inner.feature.contract_detail.SecondContractDetailRoute
import com.inner.second.core.model.ContractType
import com.inner.second.core.navigation.SecondScreen
import com.inner.second.feature.contract.SecondContractFinishRoute
import com.inner.second.feature.contract.SecondContractGetInfoRoute
import com.inner.second.feature.contract.SecondContractMainRoute
import com.inner.second.feature.contract.SecondContractSendRoute
import com.inner.second.feature.contract.SecondContractSignatureRoute
import com.inner.second.feature.contract_receiver.SecondContractReceiverSignatureRoute
import com.inner.second.feature.home.SecondHomeRoute
import kotlin.reflect.typeOf

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.secondNavigation(
    navController: NavController,
) {
    composable<SecondScreen.Home> {
        SecondHomeRoute(
            navigateToContractMain = {
                navController.navigate(SecondScreen.Contract)
            },
            navigateToContractDetail = { contractId ->
                navController.navigate(SecondScreen.ContractDetail(contractId))
            }
        )
    }

    composable<SecondScreen.ContractDetail> {
        SecondContractDetailRoute(
            onBackButtonClick = { navController.popBackStack() },
            navigateToContractReceiverSignature = { contractId ->
                navController.navigate(SecondScreen.ContractReceiver)
            }
        )
    }

    navigation<SecondScreen.Contract>(
        startDestination = SecondScreen.Main,
    ) {
        composable<SecondScreen.Main> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(SecondScreen.Contract)
            }

            SecondContractMainRoute(
                contractViewModel = hiltViewModel(parentEntry),
                navigateToGetInfo = { contractType ->
                    navController.navigate(SecondScreen.GetInfo(contractType))
                }
            )
        }
        composable<SecondScreen.GetInfo>(
            typeMap = mapOf(
                typeOf<ContractType>() to ContractType.navType,
            ),
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(SecondScreen.Contract)
            }

            SecondContractGetInfoRoute(
                newContractViewModel = hiltViewModel(parentEntry),
                onBackButtonClick = { navController.popBackStack() },
                onNextActionButtonClick = {
                    navController.navigate(SecondScreen.Signature)
                }
            )
        }
        composable<SecondScreen.Signature> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(SecondScreen.Contract)
            }

            SecondContractSignatureRoute(
                newContractViewModel = hiltViewModel(parentEntry),
                onBackButtonClick = { navController.popBackStack() },
                navigateToContractSend = { navController.navigate(SecondScreen.Send) }
            )
        }
        composable<SecondScreen.Send> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(SecondScreen.Contract)
            }

            SecondContractSendRoute(
                newContractViewModel = hiltViewModel(parentEntry),
                onBackButtonClick = { navController.popBackStack() },
                navigateToFinish = {
                    navController.navigate(SecondScreen.Finish) {
                        popUpTo(SecondScreen.Contract) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<SecondScreen.Finish> {
            SecondContractFinishRoute(
                popBackStack = { navController.popBackStack() },
                navigateToDetail = {
                    navController.popBackStack()
                    /* TODO : 상세 화면으로 이동 */
                }
            )
        }
    }

    navigation<SecondScreen.ContractReceiver>(
        startDestination = SecondScreen.ReceiverSignature
    ) {
        composable<SecondScreen.ReceiverSignature> {
            SecondContractReceiverSignatureRoute(
                onBackButtonClick = { navController.popBackStack() },
                navigateToFinish = {
                    navController.navigate(SecondScreen.ReceiverFinish) {
                        popUpTo(SecondScreen.ContractReceiver) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<SecondScreen.ReceiverFinish> {

        }
    }
}