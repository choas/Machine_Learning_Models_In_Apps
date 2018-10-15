//
//  ViewController.swift
//  InceptionV3Quantization
//
//  Created by Gregori, Lars on 15.10.18.
//  Copyright © 2018 Gregori, Lars. All rights reserved.
//

import UIKit
import Vision

class ViewController: UIViewController {

    var requests: [VNRequest] = []

    @IBOutlet weak var selectedImage: UIImageView!
    @IBOutlet weak var resultLabel: UILabel!
    @IBOutlet weak var resultLabel16: UILabel!
    @IBOutlet weak var resultLabel8: UILabel!
    @IBOutlet weak var resultLabel4: UILabel!

    override func viewDidLoad() {
        super.viewDidLoad()

        if let model = try? VNCoreMLModel(for: Inceptionv3().model) {
            self.requests.append(VNCoreMLRequest(model: model, completionHandler: resHandler))
        }
        if let model = try? VNCoreMLModel(for: Inceptionv3Q16linear().model) {
            self.requests.append(VNCoreMLRequest(model: model, completionHandler: resHandler16))
        }
        if let model = try? VNCoreMLModel(for: Inceptionv3Q8linear().model) {
            self.requests.append(VNCoreMLRequest(model: model, completionHandler: resHandler8))
        }
        if let model = try? VNCoreMLModel(for: Inceptionv3Q4linear().model) {
            self.requests.append(VNCoreMLRequest(model: model, completionHandler: resHandler4))
        }
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

    func resHandler16(request: VNRequest, _: Error?) {
        if let results = request.results as? [VNClassificationObservation] {
            let percent = Int(results[0].confidence * 100)
            let identifier = results[0].identifier
            resultLabel16.text = "\(percent)% \(identifier) [16]"
        }
    }

    func resHandler8(request: VNRequest, _: Error?) {
        if let results = request.results as? [VNClassificationObservation] {
            let percent = Int(results[0].confidence * 100)
            let identifier = results[0].identifier
            resultLabel8.text = "\(percent)% \(identifier) [8]"
        }
    }

    func resHandler4(request: VNRequest, _: Error?) {
        if let results = request.results as? [VNClassificationObservation] {
            let percent = Int(results[0].confidence * 100)
            let identifier = results[0].identifier
            resultLabel4.text = "\(percent)% \(identifier) [4]"
        }
    }
}
