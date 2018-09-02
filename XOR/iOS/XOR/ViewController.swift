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
    
    func predict(_ a: Int, _ b: Int) -> NSNumber {
        let input_data = try! MLMultiArray(shape:[2],
                                           dataType: MLMultiArrayDataType.float32)
        input_data[0] = NSNumber(value: a)
        input_data[1] = NSNumber(value: b)
        
        let xor_input = xorInput(input: input_data)
        let prediction = try! model.prediction(input: xor_input)
        print(prediction.result.count)
        return prediction.result[0]
    }
}

