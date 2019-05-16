package pages.demo

import typings.mathjsLib.mathjsMod.{^ => Mathjs}
import typings.plotlyDotJsLib.plotlyDotJsMod.{Data, Layout, ^ => Plotly}
import typings.stdLib.Partial
import typings.mathjaxLib.MathJaxNs.{^ => MathJax}

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => dynLit}
import scala.scalajs.js.annotation.JSExportTopLevel

object Graph extends IntellijImplicits {

  import scala.scalajs.js.timers.setTimeout

  @JSExportTopLevel("plotGraph")
  def plotGraph(
      exprStr: String, // the formula you want to draw a Graph for (in form of `x*2` - it mus contain a `x`)
      plotDiv: String, // the name of the `<div>` where you want to create the Graph.
      rangeFrom: Int = -20, // the range of the x-axe - minimum
      rangeTo: Int = 20 // the range of the x-axe - maximum
  ) = {
    val expr = Mathjs.compile(exprStr)
    val xValues = js.Array((rangeFrom to rangeTo): _*)
    val yValues = xValues.map { x =>
      expr.eval(
        dynLit(
          x = x
        )
      )
    }
    val data: js.Array[Data] = js.Array(
      dynLit(
        x = xValues,
        y = yValues
      ).asInstanceOf[Partial[Data]]
    )
    val layout: Partial[Layout] =
      dynLit(showlegend = false)
        .asInstanceOf[Partial[Layout]]
    plot(
      plotDiv,
      data,
      layout
    )
  }

  def plot(plotDiv: String, data: js.Array[Data], layout: Partial[Layout]) =
    setTimeout(200) {
      Plotly.newPlot(plotDiv, data, layout)
      if (!js.isUndefined(MathJax)) {
        MathJax.Hub.Queue(js.Array("Typeset", MathJax.Hub))
      }
    }
}
