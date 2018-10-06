//
//  ViewController.swift
//  XOR
//
//  Created by Gregori, Lars on 17.06.18.
//  Copyright © 2018 Gregori, Lars. All rights reserved.
//

import UIKit
import CoreML

class ViewController: UIViewController {

    @IBOutlet weak var out00: UILabel!
    @IBOutlet weak var out01: UILabel!
    @IBOutlet weak var out10: UILabel!
    @IBOutlet weak var out11: UILabel!

    let model = xor()

    override func viewDidLoad() {
        super.viewDidLoad()

        out00.text = "\(predict(0, 0))"
        out01.text = "\(predict(0, 1))"
        out10.text = "\(predict(1, 0))"
        out11.text = "\(predict(1, 1))"
    }

    func predict(_ value1: Int, _ value2: Int) -> NSNumber {
        guard let inputData = try? MLMultiArray(shape: [2], dataType: MLMultiArrayDataType.float32) else {
            return -1
        }
        inputData[0] = NSNumber(value: value1)
        inputData[1] = NSNumber(value: value2)

        let input = xorInput(input: inputData)
        if let prediction = try? model.prediction(input: input) {
            return prediction.result[0]
        }
        return -1
    }
}
