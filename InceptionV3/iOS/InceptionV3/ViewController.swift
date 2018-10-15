//
//  ViewController.swift
//  InceptionV3 iOS
//
//  Created by Gregori, Lars on 24.06.18.
//  Copyright © 2018 Gregori, Lars. All rights reserved.
//

import UIKit
import Vision

class ViewController: UIViewController {

    var requests: [VNRequest] = []

    @IBOutlet weak var selectedImage: UIImageView!
    @IBOutlet weak var resultLabel: UILabel!

    override func viewDidLoad() {
        super.viewDidLoad()
        guard let model = try? VNCoreMLModel(for: Inceptionv3().model) else {
            print("model not found")
            return
        }
        let request = VNCoreMLRequest(model: model,
                                  completionHandler: resHandler)
        self.requests.append(request)
    }

    @IBAction func predict(_ sender: UIButton) {
        selectedImage.image = sender.currentImage
        let image = sender.currentImage!.cgImage!
        let handler = VNImageRequestHandler(cgImage: image,
                                            options: [:])
        try? handler.perform(self.requests)
    }

    func resHandler(request: VNRequest, _: Error?) {
        guard let results = request.results as? [VNClassificationObservation] else {
            print("request is not a VNClassificationObservation")
            return
        }
        results[0..<5].forEach { (result) in
            print(String(format: "%.1f%% %@", result.confidence * 100, result.identifier))
        }
        print("-----------------")
        let percent = Int(results[0].confidence * 100)
        let identifier = results[0].identifier
        resultLabel.text = "\(percent)% \(identifier)"
    }
}
