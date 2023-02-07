//
//  HomeScreen.swift
//  iosApp
//
//  Created by Urinov G'ayratjon on 24/01/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct MainMenuScreen: View {
    
    var body: some View {
        NavigationView {
            MainView()
        }.navigationBarBackButtonHidden()
    }
}

struct MainView: View {
    
    @State var selected = 0
    
    @State var homeBackPressed = false
    
    @EnvironmentObject var menu: Menu
    
    var body: some View {
        
        ZStack{
            Spacer()
            GeometryReader{
                geo in VStack(){
                    VStack(spacing:0){
                        
                        VStack(spacing: 0) {
                            Spacer()
                            
                            ZStack{
                                switch selected {
                                case 0:
                                    if homeBackPressed {
                                        HomeScreen()
                                    }
                                    else {
                                        HomeScreen()
                                    }
                                case 1:
                                    ClientScreen()
                                case 2:
                                    CatalogScreen()
                                case 3:
                                    OrdersScreen()
                                case 4:
                                    AddOrderScreen()
                                default:
                                    HomeScreen()
                                }
                            }//: ZStack - content container
                            .frame(maxWidth:.infinity, maxHeight: .infinity)
                            Spacer()
                            
                        } //: VStack
                        Spacer()
                        ZStack() {
                            BottomBar(selected: self.$selected)
                                .background(CurvedShape())
                            
                            Button(action: {
                                selected = 4
                                
                                if homeBackPressed {
                                    self.homeBackPressed = false
                                }
                                else {
                                    self.homeBackPressed = true
                                }
                            }) {
                                Image(systemName: "plus").renderingMode(.template)
                                    .resizable()
                                    .foregroundColor(selected == 4 ? Color.white : Color.white)
                                    .frame(width: 30, height: 30)
                                    .padding(15)
                                
                            }.background(Color.blue)
                                .clipShape(Circle())
                                .offset(y: -45)
                                .shadow(radius: 5)
                        }
                        
                    }
                    
                }
                
            }
        }
        .background(Color.gray)
        .edgesIgnoringSafeArea(.vertical)
        
    }
}


struct CurvedShape : View {
    
    var body : some View{
        
        Path{path in
            
            path.move(to: CGPoint(x: 0, y: 0))
            path.addLine(to: CGPoint(x: UIScreen.main.bounds.width, y: 0))
            path.addLine(to: CGPoint(x: UIScreen.main.bounds.width, y: 55))
            
            path.addArc(center: CGPoint(x: UIScreen.main.bounds.width / 2, y: 55), radius: 45, startAngle: .zero, endAngle: .init(degrees: 180), clockwise: true)
            
            path.addLine(to: CGPoint(x: 0, y: 55))
            
        }.fill(Color("blue"))
            .rotationEffect(.init(degrees: 180))
    }
}

struct BottomBar : View {
    
    @Binding var selected : Int
    var body : some View{
        VStack{
            HStack(alignment: .top){
                Button(action: {
                    self.selected = 0
                }) {
                    VStack{
                        Image(systemName: "house").renderingMode(.template)
                        Text("Главная").font(.system(size: 10))
                    }
                }.foregroundColor(self.selected == 0 ? Color.white : Color.white.opacity(0.4))
                Spacer().frame(width: 30)
                Button(action: {
                    self.selected = 1
                }) {
                    VStack{
                        Image(systemName: "person").renderingMode(.template)
                        Text("Клиенты").font(.system(size: 10))
                    }
                }.foregroundColor(self.selected == 1 ? Color.white : Color.white.opacity(0.4))
                Spacer().frame(width: 80)
                Button(action: {
                    self.selected = 2
                }) {
                    VStack{
                        Image(systemName: "list.bullet.rectangle.portrait").renderingMode(.template)
                        Text("Каталог").font(.system(size: 10))
                    }
                }.foregroundColor(self.selected == 2 ? Color.white : Color.white.opacity(0.4))
                Spacer().frame(width: 30)
                Button(action: {
                    self.selected = 3
                }) {
                    VStack{
                        Image(systemName: "cart").renderingMode(.template)
                        Text("Заказы").font(.system(size: 10))
                    }
                }
                .foregroundColor(self.selected == 3 ? Color.white : Color.white.opacity(0.4))
            }
            
            Spacer().frame(height: 20)
        }
    }
}

class Menu : ObservableObject{
    @Published var title = "Bosh sahifa"
}

struct MainMenuScreen_Previews: PreviewProvider {
    static var previews: some View {
        MainMenuScreen()
    }
}



