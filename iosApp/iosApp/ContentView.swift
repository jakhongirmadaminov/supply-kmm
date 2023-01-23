import SwiftUI
import shared


struct ContentView: View {
	let greet = Greeting().greeting()

	var body: some View {
        

        VStack{
            Spacer().frame(height: 150)
            
            Image("ic_splash_logo")
            
            Spacer().frame(height: 50)
            Text(MR.strings().choose_language_uz.desc().localized())
                .foregroundColor(Color("SecondaryTextColor"))
                .font(
                    Font.custom("Mulish", size:30)
                        .weight(.heavy))
            
            Spacer().frame(height: 20)
            Text(MR.strings().choose_language_ru.desc().localized())
                .foregroundColor(Color("SecondaryTextColor"))
                .font(Font.custom("Mulish", size:16))
            
            Spacer().frame(height: 20)
            HStack{
                
                GeometryReader{ metrics in
                    let height = metrics.size.height * 0.1
                    let width = metrics.size.width * 0.4
                    ZStack{
                        RoundedRectangle(cornerRadius:  5, style: .continuous)
                            .fill(.white)
                            .shadow(radius: 2)
                        
                        
                        VStack{
                            Image("ic_flag_russia")
                            Text(MR.strings().russian.desc().localized())
                        }
                    }.frame(width: width, height: height)
                  
                    ZStack{
                        RoundedRectangle(cornerRadius:  5, style: .continuous)
                            .fill(.white)
                            .shadow(radius: 2)
                        
                    
                        VStack{
                            Image("ic_flag_uz")
                            Text(MR.strings().uzbek.desc().localized())
                        }
                    }.frame(width: width, height: height, alignment: .topLeading).background(/*@START_MENU_TOKEN@*//*@PLACEHOLDER=View@*/Color.blue/*@END_MENU_TOKEN@*/)
                        
                }
            }.frame(minWidth: 0, maxWidth: .infinity)
            Spacer().frame(height: 200)
            
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
