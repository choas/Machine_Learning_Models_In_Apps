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

    override func viewDidLoad() {
        super.viewDidLoad()

        out00.text = "\(predict(0, 0))"
        out01.text = "\(predict(0, 1))"
        out10.text = "\(predict(1, 0))"
        out11.text = "\(predict(1, 1))"
    }

    func predict(_ value1: NSNumber, _ value2: NSNumber) -> NSNumber {
        guard let inputData = try? MLMultiArray(shape: [2], dataType: MLMultiArrayDataType.float32) else {
            return -1
        }
        inputData[0] = value1
        inputData[1] = value2

        let input = xorInput(input: inputData)
        if let prediction = try? xor().prediction(input: input) {
            return prediction.result[0]
        }
        return -1
    }
}
