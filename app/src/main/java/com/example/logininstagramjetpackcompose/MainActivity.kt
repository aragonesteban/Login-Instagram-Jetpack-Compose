package com.example.logininstagramjetpackcompose

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logininstagramjetpackcompose.ui.theme.LoginInstagramJetpackComposeTheme
import java.util.regex.Pattern

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginInstagramJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginLayout()
                }
            }
        }
    }
}

@Composable
fun LoginLayout() {
    var email by rememberSaveable { mutableStateOf(String()) }
    var password by rememberSaveable { mutableStateOf(String()) }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var buttonEnabled by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            LogoInstagramName()
            InputText(
                value = email,
                placeHolder = "Email",
                onValueChange = {
                    email = it
                    buttonEnabled = toggleLoginButton(email, password)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            InputText(
                value = password,
                placeHolder = "Password",
                isPassword = true,
                showPassword = showPassword,
                onValueChange = {
                    password = it
                    buttonEnabled = toggleLoginButton(email, password)
                },
                toggleVisibilityPassword = { showPassword = it }
            )
            ForgotPassword()
            ButtonLogin(buttonEnabled)
            DividerOfFacebook()
            ContinueWithFacebook()
        }
        SignUpText(Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun LogoInstagramName() {
    Image(
        painter = painterResource(id = R.drawable.instagram_logo_name),
        contentDescription = "Instagram",
        modifier = Modifier.size(width = 200.dp, height = 120.dp)
    )
}

@Composable
fun InputText(
    value: String,
    placeHolder: String,
    isPassword: Boolean = false,
    showPassword: Boolean = false,
    onValueChange: (String) -> Unit = {},
    toggleVisibilityPassword: (Boolean) -> Unit = {}
) {
    TextField(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .border(1.dp, Color.LightGray.copy(alpha = 0.4F), RoundedCornerShape(8.dp)),
        placeholder = { Text(text = placeHolder, fontSize = 12.sp) },
        singleLine = true,
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.LightGray.copy(alpha = 0.1F),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
        textStyle = TextStyle(fontSize = 12.sp),
        visualTransformation = if (isPassword) if (showPassword) VisualTransformation.None else PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Email),
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { toggleVisibilityPassword(!showPassword) }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "",
                        tint = if (showPassword) MaterialTheme.colors.primaryVariant else Color.LightGray,
                        modifier = Modifier.alpha(0.5F)
                    )
                }
            }
        }
    )
}

@Composable
fun ForgotPassword() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Text(
            text = "Forgot password?",
            color = MaterialTheme.colors.primary,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable { })
    }
}

@Composable
fun ButtonLogin(buttonEnabled: Boolean) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(8.dp),
        enabled = buttonEnabled,
        elevation = ButtonDefaults.elevation(0.dp),
        colors = ButtonDefaults.buttonColors(
            disabledBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.4F),
            disabledContentColor = Color.White
        )
    ) {
        Text(text = "Log in", style = MaterialTheme.typography.body1, fontSize = 14.sp)
    }
}

@Composable
fun DividerOfFacebook() {
    Row(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(Modifier.weight(1F))
        Text(
            text = "OR",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Divider(Modifier.weight(1F))
    }
}

@Composable
fun ContinueWithFacebook() {
    Row(
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Facebook,
            contentDescription = "Facebook",
            tint = MaterialTheme.colors.primary
        )
        Text(
            text = "Continue with facebook",
            color = MaterialTheme.colors.primary,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun SignUpText(modifier: Modifier) {
    Column(modifier = modifier) {
        Divider()
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't have an account?",
                color = Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = "Sign Up.",
                color = MaterialTheme.colors.primary,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }

}

private fun toggleLoginButton(email: String, password: String): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.isNotEmpty()

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LoginInstagramJetpackComposeTheme {
        LoginLayout()
    }
}