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
import scala.scalajs.js.Dynamic.{literal => dynLit}

import scala.collection.immutable
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import scala.scalajs.js.timers.setTimeout

object CircleApp extends IntellijImplicits {

  val radiusVar = Var("3")

  @JSExportTopLevel("runJSCircle")
  def main(): Unit = {
    dom.render(document.getElementById("circleDiv"), plotly)
  }

  @dom
  private lazy val plotly: Binding[HTMLElement] = {
    val radiusVal = radiusVar.bind
    println(s"new radius: $radiusVal")
    val upper = 1 + 2 * radiusVal.toInt
    val xValues = js.Array(1 + upper / 2, upper / 2, 1 + upper / 2, upper - 1)
    val yValues = js.Array(0.5, upper / 2, 0.5 + upper / 2, 0.5 + upper / 2)
    val data: js.Array[Data] = js.Array(
      dynLit(
        x = xValues,
        y = yValues,
        text = js.Array("U", "A", "M", "r"),
        mode = "text"
      ).asInstanceOf[Partial[Data]]
    )
    val layout: Partial[Layout] =
      dynLit(
        xaxis = dynLit(
          range = js.Array(0, upper + 1),
          zeroline = false
        ),
        yaxis = dynLit(
          range = js.Array(0, upper + 1),
          zeroline = false
        ),
        width = 500,
        height = 500,
        shapes = js.Array(
          dynLit(
            `type` = "circle",
            xref = "x",
            yref = "y",
            x0 = 1,
            y0 = 1,
            x1 = upper,
            y1 = upper,
            line = dynLit(
              color = "rgba(50, 171, 96, 1)"
            )
          ),
          dynLit(
            `type` = "circle",
            xref = "x",
            yref = "y",
            x0 = 1.0 + radiusVal.toInt - 0.05,
            y0 = 1.0 + radiusVal.toInt - 0.05,
            x1 = 1.0 + radiusVal.toInt + 0.05,
            y1 = 1.0 + radiusVal.toInt + 0.05,
            line = dynLit(
              color = "rgba(171, 171, 96, 1)"
            )
          ),
          dynLit(
            `type` = "line",
            x0 = 1.0 + radiusVal.toInt,
            y0 = 1.0 + radiusVal.toInt,
            x1 = upper,
            y1 = 1.0 + radiusVal.toInt,
            line = dynLit(
              color = "rgb(128, 0, 128, 0.5)",
              width = 2
            )
          )
        )
      ).asInstanceOf[Partial[Layout]]

    Graph.plot(
      "plotCircle",
      data,
      layout
    )

    <div class="ui form">
      <div class="field">
        <label>Radius</label>
        <input type="number" name="radius" id="radius" placeholder="Radius" value={
      radiusVal
    }/>
      </div>
      <button class="ui button" 
      onclick={
      _: Event =>
        println("radius.value: " + radius.value)
        radiusVar.value = radius.value
    }>Submit</button>
</div>
  }

}
