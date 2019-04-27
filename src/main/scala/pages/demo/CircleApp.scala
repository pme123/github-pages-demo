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

  val radiusVar: Var[Double] = Var(3.0)

  @JSExportTopLevel("runJSCircle")
  def main(): Unit = {
    dom.render(document.getElementById("circleDiv"), plotly)
  }

  @dom
  private lazy val plotly: Binding[HTMLElement] = {
    val r = radiusVar.bind
    println(s"new radius: $r")
    val offsetV = r / 6
    val upper = offsetV + 2 * r.toInt
    val xValues = js.Array(
      offsetV + upper / 2,
      upper / 2,
      offsetV + upper / 2,
      upper - offsetV
    )
    val yValues = js.Array(
      offsetV / 2,
      upper / 2,
      offsetV + upper / 2,
      offsetV + upper / 2
    )
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
          range = js.Array(0, upper + offsetV),
          zeroline = false
        ),
        yaxis = dynLit(
          range = js.Array(0, upper + offsetV),
          zeroline = false
        ),
        width = 500,
        height = 500,
        shapes = js.Array(
          dynLit(
            `type` = "circle",
            xref = "x",
            yref = "y",
            x0 = offsetV,
            y0 = offsetV,
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
            x0 = offsetV + r.toInt - 0.05,
            y0 = offsetV + r.toInt - 0.05,
            x1 = offsetV + r.toInt + 0.05,
            y1 = offsetV + r.toInt + 0.05,
            line = dynLit(
              color = "rgba(171, 171, 96, 1)"
            )
          ),
          dynLit(
            `type` = "line",
            x0 = offsetV + r.toInt,
            y0 = offsetV + r.toInt,
            x1 = upper,
            y1 = offsetV + r.toInt,
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
    val d = (2 * r)
    val circumference = d * Math.PI
    val area = Math.sqrt(r) * Math.PI
    val units = s"\\(mm \\ cm \\ dm \\ m\\)"
    val units2 = s"\\(mm^2 \\ cm^2 \\ dm^2 \\ m^2\\)"
    val offset = offsetV + r
    <div class="ui form">
    <h1>Formulas</h1>
      <table>
        <tbody>
          <tr>
            <td>Radius</td>
            <td>r</td>
            <td>
              {s"\\(r\\)"}
            </td>
            <td>
              {units}
            </td>
            <td>
            <input class="right" type="text" name="radius" id="radius" placeholder="Radius" value={
      s"$r"
    }/>
            </td>
          </tr>
          <tr>
            <td>Durchmesser</td>
            <td>d</td>
            <td>
              {s"\\(2*r\\)"}
            </td>
            <td>
              {units}
            </td>
            <td>
            <input type="text" name="diameter" id="diameter" placeholder="Diameter" value={
              s"$d"
            }/>
            </td>
          </tr>
          <tr>
            <td>Umfang</td>
            <td>U</td>
            <td>
            {s"\\(2*\\pi *r\\)"}
          </td>
          <td>
            {units}
          </td>
          <td>
            {s"$circumference"}
          </td>
          </tr>
          <tr>
            <td>Fläche</td>
            <td>A</td>
            <td>
            {s"\\(\\pi*r^2\\)"}
          </td>
          <td>
            {units2}
          </td>
          <td>
            {s"$area"}
          </td>
          </tr>
          <tr>
            <td>Mittelpunkt</td>
            <td>M</td>
            <td>
            {s"\\((offsetX + r; offsetY + r)\\)"}
          </td>
          <td>
            {units}
          </td>
          <td>
            {s"($offset; $offset)"}
          </td>
          </tr>
        </tbody>
      </table>

      <div class="field">
        <label>Radius</label>

      </div>
      <button class="ui button"
              onclick={
      _: Event =>
        println("radius.value: " + radius.value)
        radiusVar.value = radius.value.toDouble
    }>Submit</button>
    </div>
  }

}
