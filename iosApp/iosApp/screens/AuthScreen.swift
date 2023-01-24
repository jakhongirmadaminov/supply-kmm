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
    
    @State var textFieldText :String = ""
    
    var body : some View{
        VStack(
            alignment: .leading
        ) {
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
            
            TextField("+998", text: $textFieldText)
                .padding()
                .cornerRadius(10)
            
            
            
        }.frame(maxWidth: .infinity, minHeight: 0, maxHeight: .infinity)
            .background(Color.blue)
    }
}

struct AuthScreen_Previews: PreviewProvider {
    static var previews: some View {
        AuthScreen()
    }
}
