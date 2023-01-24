//
//  BorderedTextFieldStyle.swift
//  iosApp
//
//  Created by Jakhongir Madaminov on 24/01/23.
//  Copyright © 2023 orgName. All rights reserved.
//
import Foundation
import SwiftUI

struct BorderedTextFieldStyle: TextFieldStyle {
    func _body(configuration: TextField<Self._Label>) -> some View {
        configuration
            .padding(15)
            .background(
                RoundedRectangle(cornerRadius: 10, style: .continuous)
                    .stroke(Color.gray, lineWidth: 0.5)
            )
    }
}
