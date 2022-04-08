import MyAppShared
import SwiftUI

struct ContentView: View {
    let greeting: String = Greeting().greeting()

    var body: some View {
        Text(greeting)
            .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
