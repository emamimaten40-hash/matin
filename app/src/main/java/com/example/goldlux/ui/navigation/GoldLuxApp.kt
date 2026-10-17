package com.example.goldlux.ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.goldlux.localization.Strings
import com.example.goldlux.model.AppLanguage
import com.example.goldlux.ui.components.LogoMotionSplash
import com.example.goldlux.ui.screens.AdminPanelScreen
import com.example.goldlux.ui.screens.AuthScreen
import com.example.goldlux.ui.screens.PriceHomeScreen
import com.example.goldlux.ui.screens.ProductDetailScreen
import com.example.goldlux.ui.screens.ProfileScreen
import com.example.goldlux.ui.screens.ShopScreen
import com.example.goldlux.ui.screens.WhatsAppHubScreen
import com.example.goldlux.ui.screens.WishlistScreen
import com.example.goldlux.ui.viewmodel.GoldLuxViewModel
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PriceUpGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSilver
import com.example.ui.theme.TextWhite

enum class BottomTab(
    val titleKey: (AppLanguage) -> String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    PRICES(
        titleKey = { Strings.tabPrices(it) },
        selectedIcon = Icons.Filled.ShowChart,
        unselectedIcon = Icons.Outlined.ShowChart
    ),
    SHOP(
        titleKey = { Strings.tabShop(it) },
        selectedIcon = Icons.Filled.Diamond,
        unselectedIcon = Icons.Outlined.Storefront
    ),
    WHATSAPP(
        titleKey = { Strings.tabWhatsApp(it) },
        selectedIcon = Icons.Filled.Chat,
        unselectedIcon = Icons.Outlined.Chat
    ),
    WISHLIST(
        titleKey = { Strings.tabWishlist(it) },
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder
    ),
    PROFILE(
        titleKey = { Strings.tabProfile(it) },
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )
}

@Composable
fun GoldLuxApp(
    viewModel: GoldLuxViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isArabic = uiState.language == AppLanguage.ARABIC
    val layoutDirection = if (uiState.language.isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr

    var showSplash by remember { mutableStateOf(true) }
    var currentTab by remember { mutableStateOf(BottomTab.PRICES) }

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Crossfade(targetState = showSplash, label = "splash_crossfade") { isSplashVisible ->
            if (isSplashVisible) {
                LogoMotionSplash(
                    onFinish = { showSplash = false }
                )
            } else if (uiState.isAdminMode) {
                AdminPanelScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onClose = { viewModel.toggleAdminPortal(false) }
                )
            } else if (uiState.selectedProduct != null) {
                ProductDetailScreen(
                    product = uiState.selectedProduct!!,
                    viewModel = viewModel,
                    uiState = uiState,
                    onBack = { viewModel.selectProduct(null) }
                )
            } else {
                Scaffold(
                    bottomBar = {
                      Column {
                        // thin gold hairline on top of the bar (instead of a box border on all sides)
                        HorizontalDivider(thickness = 0.6.dp, color = GoldBorder)
                        NavigationBar(
                            containerColor = DarkSurface,
                            contentColor = TextWhite,
                            tonalElevation = 0.dp
                        ) {
                            BottomTab.values().forEach { tab ->
                                val isSelected = currentTab == tab
                                val label = tab.titleKey(uiState.language)

                                NavigationBarItem(
                                    selected = isSelected,
                                    onClick = { currentTab = tab },
                                    alwaysShowLabel = true,
                                    icon = {
                                        Icon(
                                            imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                            contentDescription = label,
                                            modifier = Modifier.size(22.dp),
                                            tint = if (isSelected) {
                                                if (tab == BottomTab.WHATSAPP) PriceUpGreen else GoldLight
                                            } else {
                                                TextSilver
                                            }
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = label,
                                            fontSize = 11.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Center,
                                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                            color = if (isSelected) {
                                                if (tab == BottomTab.WHATSAPP) PriceUpGreen else GoldLight
                                            } else {
                                                TextMuted
                                            }
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = GoldLight,
                                        selectedTextColor = GoldLight,
                                        unselectedIconColor = TextSilver,
                                        unselectedTextColor = TextMuted,
                                        indicatorColor = Color(0x33D4AF37)
                                    )
                                )
                            }
                        }
                      }
                    },
                    containerColor = DarkBackground
                ) { innerPadding ->
                    AnimatedContent(
                        targetState = currentTab,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "tab_switch_anim",
                        modifier = Modifier.padding(innerPadding)
                    ) { targetTab ->
                        when (targetTab) {
                            BottomTab.PRICES -> PriceHomeScreen(
                                viewModel = viewModel,
                                uiState = uiState,
                                onNavigateToProduct = { viewModel.selectProduct(it) },
                                onCategoryClick = { category ->
                                    viewModel.setSelectedCategory(category)
                                    currentTab = BottomTab.SHOP
                                },
                                onReplayLogoMotion = { showSplash = true }
                            )

                            BottomTab.SHOP -> ShopScreen(
                                viewModel = viewModel,
                                uiState = uiState,
                                onNavigateToProduct = { viewModel.selectProduct(it) }
                            )

                            BottomTab.WHATSAPP -> WhatsAppHubScreen(
                                viewModel = viewModel,
                                uiState = uiState,
                                onReplayLogoMotion = { showSplash = true },
                                onNavigateToShop = { currentTab = BottomTab.SHOP }
                            )

                            BottomTab.WISHLIST -> WishlistScreen(
                                viewModel = viewModel,
                                uiState = uiState,
                                onNavigateToProduct = { viewModel.selectProduct(it) },
                                onExploreClick = { currentTab = BottomTab.SHOP }
                            )

                            BottomTab.PROFILE -> {
                                if (!uiState.isUserLoggedIn) {
                                    AuthScreen(
                                        viewModel = viewModel,
                                        uiState = uiState,
                                        onSuccess = { /* Automatically switches to admin or profile based on credentials */ },
                                        onSkipGuest = { currentTab = BottomTab.PRICES }
                                    )
                                } else if (uiState.isAuthenticatedAdmin) {
                                    AdminPanelScreen(
                                        viewModel = viewModel,
                                        uiState = uiState,
                                        onClose = { viewModel.logoutUser() }
                                    )
                                } else {
                                    ProfileScreen(
                                        viewModel = viewModel,
                                        uiState = uiState,
                                        onReplayLogoMotion = { showSplash = true },
                                        onNavigateToShop = { currentTab = BottomTab.SHOP }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
