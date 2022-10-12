//
//  ContentView.swift
//  swiftenums-ios
//
//  Created by Jacob Chapman on 10/12/22.
//

import SwiftUI
import examples

struct ContentView: View {
    
    private let content: UiModel = UiModelKt.getUiModelSampleContent()
    
    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundColor(.accentColor)
            Text("Hello, world!")
            uiModelView()
        }
        .padding()
    }
    
    @ViewBuilder
    private func uiModelView() -> some View {
        do {switch try content.toEnum() {
        case .error:
            return Text("Error");
        case .content(let title, let subtitle):
            return Text("\(title) - \(subtitle)");
        case .loading:
            return Text("Loading");
        default:
            return Text("Default");
        }
        } catch {
            return Text("Error Caught")
        }
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
    case content(title: String, subtitle: String)
    case error(message: String)
    case loading
}
extension UiModel {
    func toEnum() throws -> UiModelEnum {
        switch self {
            case let content as UiModel.Content:
                return UiModelEnum.content(title: content.title, subtitle: content.subtitle)
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
