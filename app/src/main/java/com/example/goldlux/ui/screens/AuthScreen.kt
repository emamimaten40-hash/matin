package com.example.goldlux.ui.screens

import com.example.ui.theme.LuxDisplay
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.goldlux.model.AppLanguage
import com.example.goldlux.ui.components.GoldCard
import com.example.goldlux.ui.components.GoldGradientButton
import com.example.goldlux.ui.viewmodel.GoldLuxUiState
import com.example.goldlux.ui.viewmodel.GoldLuxViewModel
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PriceDownRed
import com.example.ui.theme.PriceUpGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSilver
import com.example.ui.theme.TextWhite

/**
 * Authentication screen requested by the user:
 * Entry with Phone Number, Password, and Confirm Password (شماره و رمز و تایید رمز).
 * Distinct Admin account (+96599434920 / 8888) with dedicated Management Panel,
 * while regular client accounts enjoy personalized VIP catalog without admin controls.
 */
@Composable
fun AuthScreen(
    viewModel: GoldLuxViewModel,
    uiState: GoldLuxUiState,
    onSuccess: () -> Unit,
    onSkipGuest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isArabic = uiState.language == AppLanguage.ARABIC

    var isRegisterMode by remember { mutableStateOf(false) }
    var phoneInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var confirmPasswordInput by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var localError by remember { mutableStateOf<String?>(null) }

    val displayError = localError ?: uiState.authError

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF14121E),
                        DarkBackground,
                        Color(0xFF07060A)
                    )
                )
            )
            .windowInsetsPadding(WindowInsets.navigationBars)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Official Sanea Al-Ahlam Logo Emblem
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        listOf(Color(0x55D4AF37), Color(0x221D1A2A), Color.Transparent)
                    )
                )
                .border(1.2.dp, GoldBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_sane_al_ahlam_logo),
                contentDescription = "صانع الأحلام",
                modifier = Modifier.size(72.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Brand Name & Tagline
        Text(
            text = "صانع الأحلام",
            textAlign = TextAlign.Center,
            color = TextWhite,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = LuxDisplay
        )
        Text(
            text = "مجوهرات صانع الأحلام • JEWELRY DREAM MAKER",
            textAlign = TextAlign.Center,
            color = GoldLight,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Switcher: Sign In vs Register
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = DarkSurface,
            border = androidx.compose.foundation.BorderStroke(0.8.dp, GoldBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                // Sign In Tab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (!isRegisterMode) GoldPrimary else Color.Transparent)
                        .clickable {
                            isRegisterMode = false
                            localError = null
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isArabic) "ورود به حساب" else "Sign In",
                        color = if (!isRegisterMode) Color(0xFF0E0E13) else TextSilver,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Register Tab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isRegisterMode) GoldPrimary else Color.Transparent)
                        .clickable {
                            isRegisterMode = true
                            localError = null
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isArabic) "ثبت‌نام جدید" else "Register",
                        color = if (isRegisterMode) Color(0xFF0E0E13) else TextSilver,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Auth Form Card
        GoldCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = DarkSurface,
            borderColor = GoldBorder,
            borderWidth = 0.8.dp
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                // 1. Phone Number Field (شماره)
                Text(
                    text = if (isArabic) "شماره تلفن / موبایل" else "Phone Number",
                    color = GoldLight,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = phoneInput,
                    onValueChange = {
                        phoneInput = it
                        localError = null
                    },
                    placeholder = {
                        Text(
                            text = if (isArabic) "+965 9943 4920 یا شماره همراه" else "+965 9943 4920",
                            color = TextMuted,
                            fontSize = 13.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = GoldLight,
                            modifier = Modifier.size(19.dp)
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceVariant,
                        unfocusedContainerColor = DarkSurfaceVariant,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedIndicatorColor = GoldPrimary,
                        unfocusedIndicatorColor = GoldBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                // 2. Password Field (رمز)
                Text(
                    text = if (isArabic) "رمز عبور" else "Password",
                    color = GoldLight,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = passwordInput,
                    onValueChange = {
                        passwordInput = it
                        localError = null
                    },
                    placeholder = {
                        Text(
                            text = if (isArabic) "حداقل ۴ کاراکتر" else "Min 4 characters",
                            color = TextMuted,
                            fontSize = 13.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = GoldLight,
                            modifier = Modifier.size(19.dp)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle password",
                                tint = TextSilver,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    },
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceVariant,
                        unfocusedContainerColor = DarkSurfaceVariant,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedIndicatorColor = GoldPrimary,
                        unfocusedIndicatorColor = GoldBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                // 3. Confirm Password Field (تأیید رمز) - Required per user specification
                Text(
                    text = if (isArabic) "تأیید رمز عبور" else "Confirm Password",
                    color = GoldLight,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = confirmPasswordInput,
                    onValueChange = {
                        confirmPasswordInput = it
                        localError = null
                    },
                    placeholder = {
                        Text(
                            text = if (isArabic) "تکرار دقیق رمز عبور" else "Re-enter password",
                            color = TextMuted,
                            fontSize = 13.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = if (confirmPasswordInput.isNotEmpty() && confirmPasswordInput == passwordInput) PriceUpGreen else GoldLight,
                            modifier = Modifier.size(19.dp)
                        )
                    },
                    trailingIcon = {
                        if (confirmPasswordInput.isNotEmpty()) {
                            Icon(
                                imageVector = if (confirmPasswordInput == passwordInput) Icons.Default.Check else Icons.Default.Lock,
                                contentDescription = null,
                                tint = if (confirmPasswordInput == passwordInput) PriceUpGreen else PriceDownRed,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    },
                    singleLine = true,
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            performAuth(
                                viewModel = viewModel,
                                phone = phoneInput,
                                pass = passwordInput,
                                confirmPass = confirmPasswordInput,
                                isRegister = isRegisterMode,
                                isArabic = isArabic,
                                onError = { localError = it },
                                onSuccess = onSuccess
                            )
                        }
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceVariant,
                        unfocusedContainerColor = DarkSurfaceVariant,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedIndicatorColor = if (confirmPasswordInput.isNotEmpty() && confirmPasswordInput != passwordInput) PriceDownRed else GoldPrimary,
                        unfocusedIndicatorColor = GoldBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // Error Message Display
                if (!displayError.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = displayError,
                        color = PriceDownRed,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Submit Button
                GoldGradientButton(
                    text = if (isRegisterMode) {
                        if (isArabic) "ثبت‌نام و ورود به صانع الأحلام" else "Register & Enter Boutique"
                    } else {
                        if (isArabic) "ورود به صانع الأحلام" else "Sign In to Boutique"
                    },
                    onClick = {
                        performAuth(
                            viewModel = viewModel,
                            phone = phoneInput,
                            pass = passwordInput,
                            confirmPass = confirmPasswordInput,
                            isRegister = isRegisterMode,
                            isArabic = isArabic,
                            onError = { localError = it },
                            onSuccess = onSuccess
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    height = 48.dp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Admin & Demo VIP Shortcuts
        Text(
            text = if (isArabic) "حساب‌های پیش‌فرض جهت تست و بازبینی:" else "Fast Demo Accounts:",
            color = TextMuted,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Admin Quick Fill Button
            OutlinedButton(
                onClick = {
                    phoneInput = "+96599434920"
                    passwordInput = "8888"
                    confirmPasswordInput = "8888"
                    localError = null
                    val success = viewModel.login(phoneInput, passwordInput, confirmPasswordInput, false)
                    if (success) {
                        Toast.makeText(context, if (isArabic) "👑 ورود موفق با دسترسی مدیر ارشد صانع الأحلام" else "Admin account connected", Toast.LENGTH_SHORT).show()
                        onSuccess()
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(42.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0x22D4AF37))
            ) {
                Icon(
                    imageVector = Icons.Default.AdminPanelSettings,
                    contentDescription = null,
                    tint = GoldLight,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isArabic) "👑 ورود مدیر (Admin)" else "👑 Admin Demo",
                    color = GoldLight,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // VIP Customer Quick Fill Button
            OutlinedButton(
                onClick = {
                    phoneInput = "+96598765432"
                    passwordInput = "1234"
                    confirmPasswordInput = "1234"
                    localError = null
                    val success = viewModel.login(phoneInput, passwordInput, confirmPasswordInput, false)
                    if (success) {
                        Toast.makeText(context, if (isArabic) "💎 ورود به عنوان مشتری VIP" else "VIP Client connected", Toast.LENGTH_SHORT).show()
                        onSuccess()
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(42.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(0.8.dp, GoldBorder),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = DarkSurfaceVariant)
            ) {
                Icon(
                    imageVector = Icons.Default.Diamond,
                    contentDescription = null,
                    tint = TextSilver,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isArabic) "💎 ورود کاربر (Client)" else "💎 Client Demo",
                    color = TextSilver,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Guest Skip Button
        TextButton(onClick = onSkipGuest) {
            Text(
                text = if (isArabic) "متابعة كزائر دون تسجيل • Browse as Guest" else "Browse as Guest",
                color = TextSilver,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

private fun performAuth(
    viewModel: GoldLuxViewModel,
    phone: String,
    pass: String,
    confirmPass: String,
    isRegister: Boolean,
    isArabic: Boolean,
    onError: (String) -> Unit,
    onSuccess: () -> Unit
) {
    if (phone.isBlank()) {
        onError(if (isArabic) "لطفاً شماره تلفن یا موبایل خود را وارد کنید" else "Please enter phone number")
        return
    }
    if (pass.length < 4) {
        onError(if (isArabic) "رمز عبور باید حداقل ۴ رقم باشد" else "Password must be at least 4 digits")
        return
    }
    if (pass != confirmPass) {
        onError(if (isArabic) "تأیید رمز عبور با رمز وارد شده مطابقت ندارد!" else "Passwords do not match!")
        return
    }

    val success = viewModel.login(
        phone = phone,
        pass = pass,
        confirmPass = confirmPass,
        isRegister = isRegister
    )

    if (success) {
        onSuccess()
    }
}
