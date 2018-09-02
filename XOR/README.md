# XOR Beispiel

Dieses Beispiel zeigt eine einfache Möglichkeit, um ein eigenes Modell zu erstellen, zu trainieren und dieses in eine mobile Anwendung zu integrieren. Als Beispiel wird ein XOR Modell verwendet, das zwei Eingabewerte hat und anhand diesen die Ausgabe durch Machine Learning _berechnet_.

Hier nocheinmal eine kurze Wiederholung, wie XOR funktioniert:
```
0 XOR 0 = 0
0 XOR 1 = 1
1 XOR 0 = 1
1 XOR 1 = 0
```

## Aufbau

Das Beispiel besteht aus
- **Android** - Android Projekt mit TensorFlow Lite
- **iOS** - iOS Projekt mit CoreML
- **models**, um das XOR Modell selber zu trainieren


## Android Installation

Das Projekt im Android Studio öffnen. Es sind keinen weitere Anpassungen notwendig, da sich das TensorFlow Lite Modell bereits im Projekt befindet.

## iOS Installation

Das Projekt in Xcode öffnen. Es sind keinen weitere Anpassungen notwendig, da sich das CoreML Modell bereits im Projekt befindet.

## (optional) Modelle trainieren

siehe [models/README.md](./models/README.md)