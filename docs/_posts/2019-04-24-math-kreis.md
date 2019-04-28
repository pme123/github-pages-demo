---
title: "Kreis"
excerpt: "Alles über den Kreis."
categories:
  - Math
tags:
  - math
  - kreis
---

# Definition
Aus [Wikipedia](https://de.wikipedia.org/wiki/Kreis):
> Ein Kreis ist eine ebene geometrische Figur. Er wird definiert als die Menge aller Punkte einer Ebene, die einen konstanten Abstand zu einem vorgegebenen Punkt dieser Ebene (dem **Mittelpunkt**) haben. Der Abstand der Kreispunkte zum Mittelpunkt ist der **Radius** oder _Halbmesser_ des Kreises.

# Formeln
<div id="plotCircle"></div>
<div id="circleDiv"></div>
<script>runJSCircle();</script>

## Einheiten

| _mm_ | Millimeter | \\(1mm = 0.001m\\); \\(1mm = 0.1cm\\) |
| _cm_ | Centimeter | \\(1cm = 0.01m\\); \\(1cm = 10mm\\) |
| _dm_ | Dezimeter | \\(1dm = 0.1m\\) |
| _m_ | Meter | \\(1m = 100cm\\) |

# Weitere Erklärungen

{% include responsive-embed url="https://www.youtube.com/embed/eASZX0ocAc8" ratio="16:9" %}

# Beispiele

### 1. Billiardkugel
> Eine Poolbillardkugel hat einen Durchmesser von 57,2 mm. [Wiki](https://de.wikipedia.org/wiki/Billardkugel)

![billiard-kugeln](https://upload.wikimedia.org/wikipedia/commons/f/f5/Hyattballs.jpg){: .align-center} _(Wiki)_

Wenn wir die Kugel in der Mitte teilen erhalten wir einen Kreis.

- Wie gross ist der Umfang U dieses Kreises?
- Wie gross ist die Fläche A dieses Kreises?

**_Lösungen siehe am Ende dieser Seite_**

# Diagramme
### Kreis-Umfang im Verhältnis zum Radius:

<div id="plotKreisUmfang"></div>
<script>plotGraph("2*pi*x", "plotKreisUmfang", 0, 10);</script>

### Kreis-Fläche im Verhältnis zum Radius:

<div id="plotKreisFlaeche"></div>
<script>plotGraph("pi*x^2", "plotKreisFlaeche", 0, 10);</script>

# Beispiel Lösungen

### 1. Billiardkugel

- Umfang: \\(2 * \\pi * r = 2 * \\pi * 57.2 mm \\approx 359.4 mm \\approx 36 cm\\)
- Fläche: \\(\\pi * r^2 = \\pi * (57.2 mm)^2  \\approx 10278.8 mm^2 \\approx 103 cm^2\\)

