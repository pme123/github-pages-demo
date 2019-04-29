package pages.demo

import scala.scalajs.js.annotation.JSExportTopLevel

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
import scala.scalajs.js.Dynamic.{literal => dynLit}

import scala.collection.immutable
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

object GraphExample {

  @JSExportTopLevel("exampleGraph")
  def main(plotDiv: String, radius: Double): Unit = {

    val data: js.Array[Data] = js.Array() // we don't have data
    val layout: Partial[Layout] = 
      dynLit(
        xaxis = dynLit(
          zeroline = true,
          range = js.Array(- radius - 0.5, radius + 0.5)
        ),
        yaxis = dynLit(
          zeroline = true,
          range = js.Array(- radius - 0.5, radius + 0.5)
        ),
        width = 500,
        height = 500,
        shapes = js.Array( // the only shape is our circle
          dynLit(
            `type` = "circle",
            x0 = -radius,
            y0 = -radius,
            x1 = radius,
            y1 = radius
          )
        )
      ).asInstanceOf[Partial[Layout]] // Partial is not very nice done in the Facade

    Graph.plot(plotDiv, data, layout) // use the little helper to draw the Graph
  }
}
