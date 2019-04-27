package pages.demo

import typings.mathjsLib.mathjsMod.{^ => mathjs}
import typings.plotlyDotJsLib.plotlyDotJsMod.{Data, Layout, ^ => plotlyjs}
import typings.stdLib.Partial
import scala.scalajs.js.Dynamic.{global => g}
import typings.mathjaxLib.MathJaxNs.{^ => MathJax}

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => dynLit}
import scala.scalajs.js.annotation.JSExportTopLevel

object Graph extends IntellijImplicits {

  import scala.scalajs.js.timers.setTimeout

  @JSExportTopLevel("plotGraph")
  def plotly(
      exprStr: String,
      plotDiv: String,
      rangeFrom: Int = -20,
      rangeTo: Int = 20
  ) = {
    val expr = mathjs.compile(exprStr)
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
      plotlyjs.newPlot(plotDiv, data, layout)
      if (!js.isUndefined(MathJax)) {
        MathJax.Hub.Queue(js.Array("Typeset", MathJax.Hub))
      }
    }
}
