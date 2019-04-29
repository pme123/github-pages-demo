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

object CircleApp extends IntellijImplicits with UIHelper {

  val radiusVar: Var[Double] = Var(3.0)
  val offsetMVar: Var[Double] = Var(0)
  val diagramWidth = 500

  @JSExportTopLevel("runJSCircle")
  def main(): Unit = {
    dom.render(document.getElementById("circleDiv"), plotly)
  }

  @dom
  private lazy val plotly: Binding[HTMLElement] = {
    val r = radiusVar.bind
    println(s"new radius: $r")
    val offsetM = offsetMVar.bind
    println(s"new offsetM: $offsetM")

    val upper = offsetM + r
    val textOffset = 0.03 * r
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
          zeroline = true
        ),
        yaxis = dynLit(
          zeroline = true
        ),
        width = diagramWidth,
        height = diagramWidth,
        shapes = js.Array(
          dynLit(
            `type` = "circle",
            xref = "x",
            yref = "y",
            x0 = offsetM - r,
            y0 = offsetM - r,
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
            x0 = offsetM - 0.01 * r,
            y0 = offsetM - 0.01 * r,
            x1 = offsetM + 0.01 * r,
            y1 = offsetM + 0.01 * r,
            line = dynLit(
              color = "rgba(171, 171, 96, 1)"
            )
          ),
          dynLit(
            `type` = "line",
            x0 = offsetM,
            y0 = offsetM,
            x1 = upper,
            y1 = offsetM,
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
    val units = s"\\(m\\)"
    val units2 = s"\\(m^2\\)"
    <div class="ui form">
      <table>
        <tbody>
          <tr>
            <td>Radius</td>
            <td>r</td>
            <td>
              {s"\\(r\\)"}
            </td>
            <td>
              <input class="right" type="text" name="radiusIn" id="radiusIn" placeholder="Radius" value={
      s"$r"
    } onblur={_: Event => radiusVar.value = radiusIn.value.toDouble}/>
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
              <input class="right" type="text" id="diameterIn" placeholder="Diameter" value={
      s"$d"
    } onblur={_: Event => radiusVar.value = diameterIn.value.toDouble / 2}/>
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
              <input class="right" type="text" name="circumferenceIn" id="circumferenceIn" placeholder="Circumference" value={
      s"$circumference"
    } onblur={
      _: Event => radiusVar.value = circumferenceIn.value.toDouble / 2 / Math.PI
    }/>
            </td> <td>
            {units}
          </td>
          <td>
          {s"\\(r = \\sqrt{A/\\pi}\\)"}
          </td>
          </tr>
          <tr>
            <td>Fläche</td>
            <td>A</td>
            <td>
              {s"\\(\\pi*r^2\\)"}
            </td>
            <td>
              <input class="right" type="text" name="areaIn" id="areaIn" placeholder="Area" value={
      s"$area"
    } onblur={
      _: Event => radiusVar.value = Math.sqrt(areaIn.value.toDouble / Math.PI)
    }/>
            </td> <td>
            {units2}
          </td>
          <td>
          {s"\\(r = \\sqrt{A/\\pi}\\)"}
          </td>
          </tr>
          <tr>
            <td>Mittelpunkt</td>
            <td>M</td>
            <td>
              {s"\\((offsetX + r; offsetY + r)\\)"}
            </td>
            <td>
              <input class="right" type="text" name="offsetIn" id="offsetIn" placeholder="Offset" value={
      s"$offsetM"
    } onblur={_: Event => offsetMVar.value = offsetIn.value.toDouble}/>
            </td> <td>
            {units}
          </td>
          </tr>
        </tbody>
      </table>
    </div>
  }

}
