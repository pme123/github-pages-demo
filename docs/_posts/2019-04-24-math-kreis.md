---
title: "Kreis"
excerpt: "Alles über den Kreis."
categories:
  - Math
tags:
  - math
  - kreis
---
# Formeln

| Radius       | \\(r\\) |
| Durchmesser  | \\(2*r\\) |
| Umfang       | \\(2*\\pi *r\\) |
| Fläche       | \\(\\pi *r^2\\) |

<div id="circleDiv"></div>
<div id="plotCircle"></div>
<script>runJSCircle();</script>

### Kreis-Umfang im Verhältnis zum Radius:

<div id="plotKreisUmfang"></div>
<script>plotGraph("2*pi*x", "plotKreisUmfang", 0, 10);</script>

### Kreis-Fläche im Verhältnis zum Radius:

<div id="plotKreisFlaeche"></div>
<script>plotGraph("pi*x^2", "plotKreisFlaeche", 0, 10);</script>

