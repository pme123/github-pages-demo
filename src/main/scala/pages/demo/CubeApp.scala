package pages.demo

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.document
import org.scalajs.dom.raw.{Event, HTMLElement}
import org.scalajs.jquery.jQuery
import typings.stdLib.Partial
import typings.plotlyDotJsLib.plotlyDotJsMod.{Data, Layout, ^ => Plotly}

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => dynLit}
import scala.scalajs.js.annotation.JSExportTopLevel

object CubeApp extends IntellijImplicits {

  val radiusVar: Var[Double] = Var(3.0)
  val offsetMVar: Var[Double] = Var(0)
  val diagramWidth = 500
  val unit = s"\\(m\\)"
  val unit2 = s"\\(m^2\\)"

  @JSExportTopLevel("runJSCube")
  def runJSCube(): Unit = {
    dom.render(document.getElementById("cubeDiv"), printForm)
    plotGraph.watch
  }

  @dom
  private lazy val printForm: Binding[HTMLElement] = {

    <div class="ui form">
      <table>
        <tbody>
          {
      Binding
        .Constants(
          printRow(
            "Radius",
            "r",
            None,
            unit,
            printInput(
              "radiusIn",
              radiusVar,
              r => r,
              r => radiusVar.value = r
            ),
            None
          ),
          printRow(
            "Durchmesser",
            "d",
            Some("\\(2*r\\)"),
            unit,
            printInput(
              "diameterIn",
              radiusVar,
              r => 2 * r,
              d => radiusVar.value = d / 2
            ),
            Some("\\(r = d/2\\)")
          ),
          printRow(
            "Umfang",
            "U",
            Some("\\(2*\\pi *r\\)"),
            unit,
            printInput(
              "circumferenceIn",
              radiusVar,
              r => 2 * r * Math.PI,
              c => radiusVar.value = c / 2 / Math.PI
            ),
            Some("\\(r = \\frac{U}{2 * \\pi} \\)")
          ),
          printRow(
            "Fläche",
            "A",
            Some("\\(\\pi*r^2\\)"),
            unit2,
            printInput(
              "areaIn",
              radiusVar,
              r => r * r * Math.PI,
              area => radiusVar.value = Math.sqrt(area / Math.PI)
            ),
            Some("\\(r = \\sqrt{A/\\pi}\\)")
          ),
          printRow(
            "Mittelpunkt",
            "M",
            Some("\\((offsetX + r; offsetY + r)\\)"),
            unit,
            printInput(
              "centerIn",
              offsetMVar,
              o => o,
              o => offsetMVar.value = o
            ),
            None
          )
        )
        .map(_.bind)
    }
        </tbody>
      </table>
    </div>
  }

  @dom
  private def printRow(
      label: String,
      abbreviation: String,
      formula: Option[String] = None,
      unit: String,
      inputElement: Binding[HTMLElement],
      radiusFormula: Option[String] = None
  ) = {
    <tr>
      <td>
        {label}
      </td> <td>
      {abbreviation}
    </td> <td>
      {formula.getOrElse("")}
    </td> <td>
      {inputElement.bind}
    </td> <td>
      {unit}
    </td> <td>
      {radiusFormula.getOrElse("")}
    </td>
    </tr>
  }

  @dom
  private def printInput(
      inId: String,
      bindValue: Var[Double],
      calcValue: Double => Double,
      onBlur: Double => Unit
  ) = {
    val v = bindValue.bind
    <input class="right" type="text" id={s"CubeApp_$inId"}
             value={s"${calcValue(v)}"}
             onblur={
      _: Event => onBlur(jQuery(s"#CubeApp_$inId").value().toString.toDouble)
    }/>
  }

  private lazy val plotGraph = Binding {
    val radius = radiusVar.bind
    val offsetM = offsetMVar.bind

    val upper = offsetM + radius
    val textOffset = 0.03 * radius
    val data: js.Array[Data] = js.Array(
      dynLit(
        x = js.Array(1,0,0,0,0,0,0,1,1,1,1,0),
        y = js.Array(0,0,1,1,0,0,0,0,0,0,1,1),
        z = js.Array(0,0,0,1,1,0,1,1,0,1,1,1),
        `type` = "scatter3d",
        mode = "lines",
        name = "Quader"
      ).asInstanceOf[Partial[Data]],
      dynLit(
        x = js.Array(1, 1),
        y = js.Array(1, 0),
        z = js.Array(0, 0),
        `type` = "scatter3d",
        mode = "lines",
        name = "Länge"
      ).asInstanceOf[Partial[Data]],
      dynLit(
        x = js.Array(1, 0),
        y = js.Array(1, 1),
        z = js.Array(0, 0),
        `type` = "scatter3d",
        mode = "lines",
        name = "Breite"
      ).asInstanceOf[Partial[Data]],
      dynLit(
        x = js.Array(1, 1),
        y = js.Array(1, 1),
        z = js.Array(0, 1),
        `type` = "scatter3d",
        mode = "lines",
        name = "Höhe"
      ).asInstanceOf[Partial[Data]]
    )
    val layout: Partial[Layout] =
      dynLit(
        width = diagramWidth,
        height = diagramWidth
      ).asInstanceOf[Partial[Layout]]

    Graph.plot(
      "plotCube",
      data,
      layout
    )
  }

}
