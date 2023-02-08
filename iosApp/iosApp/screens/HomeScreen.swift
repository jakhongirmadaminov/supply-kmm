//
//  HomeScreen.swift
//  iosApp
//
//  Created by Uzkassa on 07/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

import SwiftUI

struct HomeScreen: View {
    
    var body: some View {
        NavigationView{
            
            VStack{
                
                HStack {
                    Image("ic_splash_logo")
                    Spacer()
                    Image(systemName: "person")
                }.padding()
                
                HStack {
                    Text("Статистика по заказам")
                    Spacer()
                    Image(systemName: "arrow.2.squarepath")
                }.padding()
                
                
                HStack {
                    VStack(alignment: .center) {
                        Text("Кол-во заказов за сегодня")
                        Text("10 000").foregroundColor(Color(hex: "#9061F9"))
                    }.frame(maxWidth: .infinity).border(Color(hex: "#9061F9")).background(Color(hex: "#9061F9").opacity(0.7)).cornerRadius(20)
                    Spacer().frame(width: 20)
                    VStack(alignment: .center) {
                        Text("Кол-во заказов за сегодня")
                        Text("10 000").foregroundColor(Color(hex: "#9061F9"))
                    }.frame(maxWidth: .infinity).border(Color(hex: "#9061F9")).background(Color(hex: "#9061F9").opacity(0.7)).cornerRadius(20)
                    
                }.padding()
                Spacer()
            }.padding()
            
        }
    }
}
