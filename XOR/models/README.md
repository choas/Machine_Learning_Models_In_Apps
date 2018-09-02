# XOR models

In diesem Beispiel ist das [Keras](https://keras.io/) Modell definiert, um das XOR Modell für CoreML und TensonFlow Lite zu trainieren. Die bereits trainierten Modelle finden sich im [models](./models)-Verzeichnis und Aufgrund ihrer geringen Größe in den jeweiigen Projekten. Eine Ausführung ist optional.

## Python Umgebung aufsetzen

Für die Installation sind folgende Schritte notwendig:

```sh
virtualenv .
source bin/activate
pip install -r requirements.txt
```

Mit [vitualenv](https://virtualenv.pypa.io/) wird eine eigene Python Umgebung aufsetzen. Dies ist von Vorteil, da in der requirements.txt Datei möglicherweise Abhängigkeiten definiert sind, die auf dem System installierten Bibliotheken verändert.

Nach der Installation besteht die Möglichkeit die Modellerstellung Schritt für Schritt in einem [Jupyter Notebook](https://jupyter.org/) auszuführen, oder als eigenständiges Python Programm.


## Jupyter Notebook

Mit dem folgenden Befehl wird das Jupyter Notebook gestartet und es öffnet sich ein Browserfenster. Wählen Sie im Browser die XOR.ipynb Datei aus.

```sh
jupyter notebook
```

Die einzelnen Bereiche innerhalb des Notesbooks werden durch _Run_ ausgeführt. Am Ende befinden sich die Modelle im [models](./models)-Verzeichnis.

## Standalone Python

Durch folgenden Aufruf werden die Modelle trainiert und in das [models](./models)-Verzeichnis abgelegt.

```sh
python XOR.py
```
