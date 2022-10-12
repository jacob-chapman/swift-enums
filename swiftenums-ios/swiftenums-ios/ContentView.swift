//
//  ContentView.swift
//  swiftenums-ios
//
//  Created by Jacob Chapman on 10/12/22.
//

import SwiftUI
import examples

struct ContentView: View {
    
    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundColor(.accentColor)
            Text("Hello, world!")
        }
        .padding()
    }
    
    private func testMethod(uiModel: UiModel) {
        switch uiModel {
        case  let content as UiModel.Content:
            break;
        case _ as UiModel.Loading:
            break;
        case _ as UiModel.Error:
            break;
        default:
            print("NO OP")
        }
    }
}
enum ToEnumError : Error {
    case UnknownType
}
enum UiModelEnum {
    case content(title: String, description: String)
    case error(message: String)
    case loading
}
extension UiModel {
    func toEnum() throws -> UiModelEnum {
        switch self {
            case let content as UiModel.Content:
                return UiModelEnum.content(title: content.title, description: content.description)
            case let error as UiModel.Error:
                return UiModelEnum.error(message: error.message)
            case _ as UiModel.Loading:
                return UiModelEnum.loading
            default:
                throw ToEnumError.UnknownType
        }
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
