import Foundation
import Combine
import shared
import KMPNativeCoroutinesAsync

@MainActor
class FoldersViewModel: ObservableObject {
    @Published var folders = [Folder]()
    
    private var pollFoldersTask: Task<(), Never>? = nil
    
    private let repository: FilesRepository
    
    init(repository: FilesRepository) {
        self.repository = repository
        
        pollFoldersTask = Task {
            do {
                let stream = asyncStream(for: repository.observeFoldersAtPathNative(path: "/Photos"))
                for try await data in stream {
                    self.folders = data
                }
            } catch {
                print("Failed with error: \(error)")
            }
        }
    }
    
    func addRandomFolder() {
        repository.addFolder(folder: Folder(name: "foo", path: "bar"))
    }
    
    func cancel() {
//        fetchPeopleTask?.cancel()
    }
    
}
