import SwiftUI
import shared


struct SplashScreen: View {
    let greet = Greeting().greeting()
    
    var body: some View {
        VStack{
            Image("ic_splash_logo")
        }
    }
}

struct SplashScreen_Previews: PreviewProvider {
    static var previews: some View {
        SplashScreen()
    }
}
