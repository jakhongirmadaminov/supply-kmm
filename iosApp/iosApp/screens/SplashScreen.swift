import SwiftUI
import shared


struct SplashScreen: View {
    let greet = Greeting().greeting()
    
    var body: some View {
        VStack{
            Spacer().frame(height: 150)
            
            Image("ic_splash_logo")
            
            Spacer().frame(height: 50)
            Text(MR.strings().choose_language_uz.desc().localized())
                .foregroundColor(Color("PrimaryTextColor"))
                .font(
                    Font.custom("Mulish", size:30)
                        .weight(.heavy))
            
            Spacer().frame(height: 20)
            Text(MR.strings().choose_language_ru.desc().localized())
                .foregroundColor(Color("PrimaryTextColor"))
                .font(Font.custom("Mulish", size:16))
            
            Spacer().frame(height: 20)
            
            GeometryReader { metrics in
                let cardWidth = metrics.size.width * 0.4
                HStack{
                    Spacer()
                    NavigationLink(destination: AuthScreen()){
                        ZStack{
                            RoundedRectangle(cornerRadius:  5, style: .continuous)
                                .fill(.white)
                                .shadow(radius: 2)
                            
                            VStack{
                                Image("ic_flag_russia")
                                Text(MR.strings().russian.desc().localized())
                            }
                        }.frame(width: cardWidth, height: 70)
                    }
                    ZStack{
                        RoundedRectangle(cornerRadius:  5, style: .continuous)
                            .fill(.white)
                            .shadow(radius: 2)
                        
                        VStack{
                            Image("ic_flag_uz")
                            Text(MR.strings().uzbek.desc().localized())
                        }
                    }.frame(width: cardWidth, height: 70)
                    
                    Spacer()
                }
                
            }
            Spacer().frame(height: 150)
            
        }
    }
}

struct CardView : View {
    let width :Float
    init(_ width:Float){
        self.width = width
    }
    
    var body: some View{
        ZStack{
            RoundedRectangle(cornerRadius:  5, style: .continuous)
                .fill(.white)
                .shadow(radius: 2)
            
            VStack{
                Image("ic_flag_uz")
                Text(MR.strings().uzbek.desc().localized())
            }
        }.frame(width: CGFloat(self.width), height: 70)
    }
}

struct SplashScreen_Previews: PreviewProvider {
    static var previews: some View {
        SplashScreen()
    }
}
