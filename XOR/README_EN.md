# XOR Example

Eine deutsche Anleitung finden Sie unter  [README.md](./README.md).

This example shows an easy way to create, train and integrate your own model into a mobile application. As an example, an XOR model is used that has two input values and _calculates_ the output by Machine Learning.

A short recap of XOR:
```
0 XOR 0 = 0
0 XOR 1 = 1
1 XOR 0 = 1
1 XOR 1 = 0
```

## Setup

The examples consist of:
- **Android** - Android project with TensorFlow Lite
- **iOS** - iOS project with CoreML
- **models**, to train the XOR model

## Android Installation

Open the project in Android Studio. No further adjustments are necessary, as the TensorFlow Lite model is already in the project.

## iOS Installation

Open the project in Xcode. No further adjustments are necessary, as the CoreML model is already in the project.

## (optional) train the model

see [models/README_EN.md](./models/README_EN.md)