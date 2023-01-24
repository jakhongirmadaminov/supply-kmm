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
    
    var body : some View{
        VStack{
            Text(MR.strings().authorization.desc().localized())
                .foregroundColor(Color("PrimaryTextColor"))
                .font(Font.custom("Mulish", size:30).weight(.heavy))
        }
    }
}
