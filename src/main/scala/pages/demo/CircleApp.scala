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

object CircleApp extends IntellijImplicits with UIHelper {

  val radiusVar: Var[Double] = Var(3.0)
  val offsetVar: Var[Double] = Var(0.5)
  val diagramWidth = 800

  @JSExportTopLevel("runJSCircle")
  def main(): Unit = {
    dom.render(document.getElementById("circleDiv"), plotly)
  }

  @dom
  private lazy val plotly: Binding[HTMLElement] = {
    val r = radiusVar.bind
    println(s"new radius: $r")
    val offset = offsetVar.bind
    println(s"new offsetV: $offset")

    val offsetM = offset + r

    val upper = offset + 2 * r.toInt
    val textOffset =  0.03 * r
    val data: js.Array[Data] = js.Array(
      dynLit(
        x = js.Array(
          offsetM + r + textOffset,
          offsetM - r / 2,
          offsetM + textOffset,
          offsetM + r / 2
        ),
        y = js.Array(
          offsetM + textOffset,
          offsetM - r / 2,
          offsetM + textOffset,
          offsetM + textOffset
        ),
        text = js.Array("U", "A", "M", "r"),
        mode = "text"
      ).asInstanceOf[Partial[Data]]
    )
    val layout: Partial[Layout] =
      dynLit(
        xaxis = dynLit(
          range = js.Array(0 - Math.abs(offset), upper + Math.abs(offset)),
        ),
        yaxis = dynLit(
          range = js.Array(0 - Math.abs(offset), upper + Math.abs(offset)),
          zeroline = true
        ),
        width = diagramWidth,
        height = diagramWidth,
        shapes = js.Array(
          dynLit(
            `type` = "circle",
            xref = "x",
            yref = "y",
            x0 = offset,
            y0 = offset,
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
            x0 = offset + r - 0.01 * r,
            y0 = offset + r - 0.01 * r,
            x1 = offset + r + 0.01 * r,
            y1 = offset + r + 0.01 * r,
            line = dynLit(
              color = "rgba(171, 171, 96, 1)"
            )
          ),
          dynLit(
            `type` = "line",
            x0 = offset + r.toInt,
            y0 = offset + r.toInt,
            x1 = upper,
            y1 = offset + r.toInt,
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
    val area = r * r * Math.PI
    val units = s"\\(mm \\ cm \\ dm \\ m\\)"
    val units2 = s"\\(mm^2 \\ cm^2 \\ dm^2 \\ m^2\\)"
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
              <input class="right" type="text" name="radiusIn" id="radiusIn" placeholder="Radius" value={s"$r"} onblur={_: Event => radiusVar.value = radiusIn.value.toDouble}/>
            </td>
            <td>
              {units}
            </td>
          </tr>
          <tr>
            <td>Durchmesser</td>
            <td>d</td>
            <td>
              {s"\\(2*r\\)"}
            </td>
            <td>
              <input class="right" type="text" id="diameterIn" placeholder="Diameter" value={s"$d"} onblur={_: Event => radiusVar.value = diameterIn.value.toDouble / 2}/>
            </td> <td>
            {units}
          </td>
          </tr>
          <tr>
            <td>Umfang</td>
            <td>U</td>
            <td>
              {s"\\(2*\\pi *r\\)"}
            </td>
            <td>
              <input class="right" type="text" name="circumferenceIn" id="circumferenceIn" placeholder="Circumference" value={s"$circumference"} onblur={_: Event => radiusVar.value = circumferenceIn.value.toDouble / 2 / Math.PI}/>
            </td> <td>
            {units}
          </td>
          </tr>
          <tr>
            <td>Fläche</td>
            <td>A</td>
            <td>
              {s"\\(\\pi*r^2\\)"}
            </td>
            <td>
              <input class="right" type="text" name="areaIn" id="areaIn" placeholder="Area" value={s"$area"} onblur={_: Event => radiusVar.value = Math.sqrt(areaIn.value.toDouble / Math.PI)}/>
            </td> <td>
            {units2}
          </td>
          </tr>
          <tr>
            <td>Mittelpunkt</td>
            <td>M</td>
            <td>
              {s"\\((offsetX + r; offsetY + r)\\)"}
            </td>
            <td>
              <input class="right" type="text" name="offsetIn" id="offsetIn" placeholder="Offset" value={s"$offsetM"} onblur={_: Event => offsetVar.value = offsetIn.value.toDouble - r}/>
            </td> <td>
            {units}
          </td>
          </tr>
        </tbody>
      </table>
    </div>
  }

}
