package splash

import com.utsman.api.authentication.model.entity.User
import com.utsman.libraries.core.state.Async

data class SplashScreenState(
    val asyncUser: Async<User> = Async.Default
)