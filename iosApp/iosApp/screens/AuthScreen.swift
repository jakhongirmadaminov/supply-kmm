//
//  AuthScreen.swift
//  iosApp
//
//  Created by Jakhongir Madaminov on 24/01/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct AuthScreen : View{
    
    @State var phoneNumber :String = "+998"
    @State var password :String = ""
    
    var body : some View{
        
        NavigationView {
            VStack{
                VStack(alignment: .leading) {
                    Spacer().frame(height: 25)
                    Text(MR.strings().authorization.desc().localized())
                        .foregroundColor(Color("PrimaryTextColor"))
                        .font(Font.custom("Mulish", size:30).weight(.heavy))
                        .frame( maxWidth: .infinity,alignment: .topLeading)
                        .padding(.horizontal, 25.0)
                    
                    Spacer().frame(height: 25)
                    
                    Text(MR.strings().authorization_info.desc().localized())
                        .foregroundColor(Color("PrimaryTextColor"))
                        .font(Font.custom("Mulish", size:14).weight(.medium))
                        .frame( maxWidth: .infinity, alignment: .topLeading)
                        .padding(.horizontal, 25.0)
                    Spacer().frame(height: 20)
                    Text(MR.strings().phone_number.desc().localized())
                        .foregroundColor(Color("PrimaryTextColor"))
                        .font(Font.custom("Mulish", size:14).weight(.medium))
                        .frame( maxWidth: .infinity, alignment: .topLeading)
                        .padding(.horizontal, 25.0)
                    ZStack(alignment: .leading) {
                        TextField("", text: self.$phoneNumber)
                            .textFieldStyle(BorderedTextFieldStyle())
                            .keyboardType(.numberPad)
                            .onChange(of: self.phoneNumber){newValue in
                                if(newValue.count<4){
                                    self.phoneNumber = "+998"
                                }else{
                                    self.phoneNumber = newValue
                                }
                            }
                    }.padding(.horizontal, 25)
                        .foregroundColor(.gray)
                    Spacer().frame(height: 20)
                    Text(MR.strings().password.desc().localized())
                        .foregroundColor(Color("PrimaryTextColor"))
                        .font(Font.custom("Mulish", size:14).weight(.medium))
                        .frame( maxWidth: .infinity, alignment: .topLeading)
                        .padding(.horizontal, 25.0)
                    
                    SecureField(MR.strings().input_password.desc().localized(), text: $password)
                        .textFieldStyle(BorderedTextFieldStyle())
                        .padding(.horizontal, 25)
                        .foregroundColor(.gray)
                }.padding(.top, 30).navigationBarBackButtonHidden(true)
                Spacer()
                
                NavigationLink(destination: PinCodeScreen()){
                    ZStack(alignment: Alignment.center){
                        Text("Войти").foregroundColor(Color.white)
                    }.frame(maxWidth: .infinity)
                        .padding(10).background(Color.blue).cornerRadius(10)
                }.padding(10)
                
                NavigationLink(destination: MainMenuScreen()){
                    Text("Забыли пароль")
                }
            }
        }
    }
    
    struct AuthScreen_Previews: PreviewProvider {
        static var previews: some View {
            AuthScreen()
        }
    }
}
