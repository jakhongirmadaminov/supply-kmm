//
//  PinCodeScreen.swift
//  iosApp
//
//  Created by Uzkassa on 07/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct PinCodeScreen : View {
    
    
    var maxDigits: Int = 4
    var label = "Создайте пин код для\nзащита аккаунта"
    
    @State var pin: String = ""
    @State var isDisabled = false
    @State var successPin = false
    public var body : some View {
        NavigationView {
            
            VStack(alignment: .leading){
                Text("Создать пин код")
                Spacer().frame(height: 50)
                VStack(spacing: 5) {
                    Text(label).font(.title2).multilineTextAlignment(.center)
                    Spacer().frame(height: 20)
                    ZStack {
                        pinDots
                        backgroundField
                    }
                }
                Spacer()
                NavigationLink(destination: HomeScreen(), isActive: $successPin){
                        EmptyView()
                }
            }.padding(20)
        }.navigationBarBackButtonHidden()
    }
    
    private var pinDots: some View {
        HStack {
            Spacer()
            ForEach(0..<maxDigits) { index in
                Image(systemName: self.getImageName(at: index))
                    .font(.system(size: 5))
                    .padding(20)
                    .cornerRadius(20)
                    .overlay(RoundedRectangle(cornerRadius: 10)
                                .stroke(Color.black, lineWidth: 1))
                Spacer()
            }
        }
    }
    
    private var backgroundField: some View {
        let boundPin = Binding<String>(get: { self.pin }, set: { newValue in
            self.pin = newValue
            self.submitPin()
        })
        
        return TextField("", text: boundPin, onCommit: submitPin)
            .accentColor(.clear)
            .foregroundColor(.clear)
            .keyboardType(.numberPad)
            .disabled(isDisabled)
    }
    
    private func submitPin() {
        guard !pin.isEmpty else {
            return
        }
        
        if pin.count == maxDigits {
            
            
            if(pin == "1234") {
                successPin = true
            }
        }
        if pin.count > maxDigits {
            pin = String(pin.prefix(maxDigits))
            submitPin()
        }
    }
    
    
    private func getImageName(at index: Int) -> String {
        if index >= self.pin.count {
            return ""
        }

        
        return "circle.fill"
    }
}

extension String {
    
    var digits: [Int] {
        var result = [Int]()
        
        for char in self {
            if let number = Int(String(char)) {
                result.append(number)
            }
        }
        
        return result
    }
    
}

extension Int {
    
    var numberString: String {
        
        guard self < 10 else { return "0" }
        
        return String(self)
    }
}

