# Inception V3 Beispiel

Dieses Beispiel demonstriert die Möglichkeit ein Inception V3 Modell in eine mobile Anwendung zu integrieren. Hierdurch lassen sich Objekte innerhalb eines Bildes erkennen. Für das Beispiel sind dies ein Elefant, Gänseblümchen und Himbeeren.

## Aufbau

Das Beispiel besteht aus
- **Android** - Android Projekt mit TensorFlow Lite
- **iOS** - iOS Projekt mit CoreML


## Android Installation

Das Projekt im Android Studio öffnen. Zusätzlich (auf Grund der Dateigröße) ist es noch notwendig in das Verzeichnis _app/src/main/assets/_ die [Inception V3 für TensorFlow Lite](https://github.com/tensorflow/tensorflow/blob/master/tensorflow/contrib/lite/g3doc/models.md) zu kopieren.


## iOS Installation

Das Projekt in Xcode öffnen. Zusätzlich (auf Grund der Dateigröße) ist es noch notwendig das [Inception V3 Modell](https://developer.apple.com/machine-learning/build-run-models/) herunterzuladen und per Drag&Drop direkt in das Projektverzeichnis zu schieben.


## Bilder

Die im Beispiel verwendeten Bilder stammen von Pixabay sind hier zu finden:
- [Elefant](https://pixabay.com/en/elephant-african-bush-elephant-114543)
- [Gänseblümchen](https://pixabay.com/en/floral-daisy-blossom-plant-natural-50157)
- [Himbeere](https://pixabay.com/en/raspberries-close-up-background-1495713)
