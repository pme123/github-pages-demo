package pages.demo

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.document
import org.scalajs.dom.raw.{HTMLCanvasElement, HTMLElement}
import org.scalajs.{dom => jsdom}
import scala.scalajs.js.Dynamic.{literal => dynLit}
import typings.mathjsLib.mathjsMod.{^ => mathjs}
import typings.plotlyDotJsLib.plotlyDotJsMod.{^ => plotlyjs}
import typings.plotlyDotJsLib.plotlyDotJsMod.{Data, Layout, Margin}
import typings.stdLib.Partial
import org.scalajs.dom.raw.{Event, FileReader, HTMLElement, HTMLInputElement}

import scala.collection.immutable
import scala.scalajs.js
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
      println("printDiv")
      plotlyjs.newPlot(plotDiv, data, layout)
    }
}
