import SwiftUI
import shared


struct ContentView: View {
    
    @State var isActive:Bool = false
    
    var body: some View {
        
        VStack {
            if self.isActive {
                HomeScreen()
            } else {
                SplashScreen()
            }
        }
        .onAppear {
            DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
                withAnimation {
                    self.isActive = true
                }
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity).background(Color("blue"))
        .edgesIgnoringSafeArea(.all)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
