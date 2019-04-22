package pages.demo

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.document
import org.scalajs.dom.raw.{HTMLCanvasElement, HTMLElement}
import org.scalajs.{dom => jsdom}
import typings.mathjsLib.mathjsMod
import typings.plotlyDotJsLib.plotlyDotJsMod.^._
import typings.plotlyDotJsLib.plotlyDotJsMod.{Data, Layout, Margin}
import typings.stdLib.Partial
import org.scalajs.dom.raw.{Event, FileReader, HTMLElement, HTMLInputElement}

import scala.collection.immutable
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

object Graph extends IntellijImplicits {

  import scala.scalajs.js.timers.setTimeout

  @JSExportTopLevel("plotGraph")
  def plotly(exprStr: String, plotDiv: String, rangeFrom: Int = -20, rangeTo:Int = 20) = {
    println(s"PLOTLY started--: $exprStr")
    setTimeout(200) {
      val expr = mathjsMod.^.compile(exprStr)
      val xValues = js.Array((rangeFrom to rangeTo): _*)
      val yValues = xValues.map { x =>
        expr.eval(
          js.Dynamic.literal(
            x = x
          )
        )
      }
      val data: js.Array[Data] = js.Array(
        js.Dynamic
          .literal(
            x = xValues,
            y = yValues
          )
          .asInstanceOf[Partial[Data]]
      )
      val margin = js.Dynamic.literal(b = 0).asInstanceOf[Partial[Margin]]
      val layout: Partial[Layout] =
        js.Dynamic
          .literal(showlegend = false)
          .asInstanceOf[Partial[Layout]]
      newPlot(plotDiv, data, layout)
    }
  }

}
