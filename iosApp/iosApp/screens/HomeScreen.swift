//
//  HomeScreen.swift
//  iosApp
//
//  Created by Urinov G'ayratjon on 24/01/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI


struct TopFrameView: Shape {
    func path(in rect: CGRect) -> Path {
        let bezierPath = UIBezierPath()
        bezierPath.move(to: CGPoint(x: 9.16, y: 0.04))
        let curvePoints = [(CGPoint(x: 22.13, y: 1.8), CGPoint(x: 13.75, y: 0.17), CGPoint(x: 18.98, y: 0.6)), (CGPoint(x: 36.89, y: 10.14), CGPoint(x: 28.69, y: 4.3),  CGPoint(x: 31.15, y: 4.93)), (CGPoint(x: 46.73, y: 22.65), CGPoint(x: 42.63, y: 15.35), CGPoint(x: 44.27, y: 18.48)), (CGPoint(x: 58.2, y: 34.32), CGPoint(x: 49.19, y: 26.81), CGPoint(x: 52.47, y: 30.78)), (CGPoint(x: 70.5, y: 37.65), CGPoint(x: 63.94, y: 37.86), CGPoint(x: 70.5, y: 37.65)), (CGPoint(x: 82.8, y: 34.32), CGPoint(x: 70.5, y: 37.65), CGPoint(x: 77.06, y: 37.86)), (CGPoint(x: 94.27, y: 22.65), CGPoint(x: 88.53, y: 30.78), CGPoint(x: 91.81, y: 26.81)), (CGPoint(x: 104.11, y: 10.14), CGPoint(x: 96.73, y: 18.48), CGPoint(x: 98.37, y: 15.35)), (CGPoint(x: 118.87, y: 1.8), CGPoint(x: 109.85, y: 4.93), CGPoint(x: 112.31, y: 4.3)), (CGPoint(x: 140.79, y: 0.12), CGPoint(x: 124.98, y: -0.53), CGPoint(x: 138.91, y: 0.03)), (CGPoint(x: 140.97, y: 0.13), CGPoint(x: 140.88, y: 0.13), CGPoint(x: 140.94, y: 0.13)), (CGPoint(x: 141, y: 49), CGPoint(x: 140.99, y: 0.13), CGPoint(x: 141, y: 31.27))]
        for (to, controlPoint1, controlPoint2) in curvePoints {
            bezierPath.addCurve(to: to, controlPoint1: controlPoint1, controlPoint2: controlPoint2)
        }
        bezierPath.addLine(to: CGPoint(x: 0, y: 49))
        bezierPath.addCurve(to: CGPoint(x: 0, y: 0.13), controlPoint1: CGPoint(x: 0, y: 31.27), controlPoint2: CGPoint(x: 0, y: 0.13))
        bezierPath.addCurve(to: CGPoint(x: 9.16, y: 0.04), controlPoint1: CGPoint(x: 0, y: 0.13), controlPoint2: CGPoint(x: 4.2, y: -0.09))
        bezierPath.close()
        return Path(bezierPath.cgPath)
    }
}
enum ItemInformation: String {
    case plus = "plus";
    case list = "list.bullet";
    case arrowLeftArrowRight = "arrowLeftArrowRight";
    case branch = "arrow.triangle.branch";
    case person = "person"
    func iconView() -> some View {
        self != .arrowLeftArrowRight ? ViewBuilder.buildEither(first: Image(systemName: self.rawValue).font(.system(size: 21)).foregroundColor(Items.captionColor))
                                        : ViewBuilder.buildEither(second:
                                            HStack(spacing: 0) {
                                                    Image(systemName: "plus") .font(.system(size: 14, weight: .semibold)).foregroundColor(Items.captionColor).offset(CGSize(width: 3, height: 4))
                                                })
    }
}
enum Items {
    static let items: [ItemInformation] = [.plus, .list, .arrowLeftArrowRight, .branch, .person]
    static let maskMargin = CGFloat(1); static let topFrameHeight = CGFloat(28); static let tabBarHeight = CGFloat(49); static let bottomSafeAreaHeight = CGFloat(40); static let tabAreaHeight = Self.topFrameHeight + Self.tabBarHeight; static let itemEdge = CGFloat(42)
    static let backgroundColor = Color(UIColor(red: 0.271, green: 0.534, blue: 0.991, alpha: 1.000));
    static let captionColor = Color.black
}

struct HomeScreen: View {
    @State var selectedIndex = 0
    var body: some View {
        ZStack {
            VStack(alignment: .leading) {
                Image("ic_splash_logo")
                HStack{
                    Text("Статистика по заказам")
                    Image(systemName: "person")
                }
                Spacer()
            }.padding(30).frame(maxWidth: .infinity)
            
            GeometryReader { proxy in
                VStack {
                    Spacer()
                    ZStack {
                        ForEach(0..<5) { index in
                            Rectangle()
                                .frame(width: Items.itemEdge, height: Items.itemEdge)
                                .foregroundColor(.clear)
                                .overlay(Items.items[index].iconView())
                                .background(
                                    Circle()
                                        .frame(width: Items.itemEdge, height: Items.itemEdge)
                                        .foregroundColor(.white)
                                )
                                .offset(CGSize(width: CGFloat(self.selectedIndex - 2) * (proxy.size.width * 0.2), height: self.selectedIndex == index ? -5 : 51))
                        }
                    }
                    .frame(width: proxy.size.width, height: Items.tabAreaHeight+20)
                    .overlay(
                        VStack(spacing: 0) {
                            Spacer()
                            HStack(alignment: .center, spacing: 0){
                                ForEach(0..<5) { index in
                                    Rectangle()
                                        .foregroundColor(.clear)
                                        .frame(width: proxy.size.width / 5, height: Items.tabBarHeight)
                                        .overlay( Items.items[index].iconView().opacity(selectedIndex == index ? 0 : 1.0))
                                        .offset(CGSize(width: 0.0, height: selectedIndex == index ? 30 : 0))
                                        .contentShape( Rectangle() )
                                        .onTapGesture { withAnimation(.interactiveSpring(response: 0.4, dampingFraction: 0.7) )  { selectedIndex = index}}
                                }
                            }
                        }.frame(height: Items.tabAreaHeight)
                        .background(
                            Rectangle()
                                .foregroundColor(.white)
                                .frame(height: Items.tabAreaHeight + Items.bottomSafeAreaHeight)
                                .mask(
                                    VStack(spacing: 0){
                                        Spacer()
                                        HStack(alignment: .bottom, spacing: 0){
                                            Rectangle().frame(width: (proxy.size.width / 5) * 4).offset(CGSize(width: Items.maskMargin, height: 0))
                                            TopFrameView()
                                                .frame(width: 141)
                                            Rectangle().frame(width: (proxy.size.width / 5) * 4).offset(CGSize(width: -Items.maskMargin, height: 0))
                                        }.frame(height: Items.tabBarHeight)
                                        .offset(CGSize(width: (proxy.size.width / 5) * CGFloat(selectedIndex - 2) , height: 0))
                                        Rectangle().frame(height: Items.bottomSafeAreaHeight).offset(CGSize(width: 0, height: -Items.maskMargin))
                                    }.frame(height: Items.tabAreaHeight + Items.bottomSafeAreaHeight)
                                )
                                .offset(CGSize(width: 0, height: Items.bottomSafeAreaHeight * 0.5))
                        )
                    )
                }
            }
        }.edgesIgnoringSafeArea([.leading, .trailing]).background(Color.gray)
    }
}

struct HomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen()
    }
}



