package miguel.insua.loveArt.modules.loginTest

import miguel.insua.loveArt.modules.login.LoginViewModel
import org.junit.Test
import org.mockito.Mockito


class LoginViewModelTest {
    val vievModel = Mockito.mock(LoginViewModel::class.java)



    @Test
    fun valid() {
       vievModel.validatePassword("")
    }
}