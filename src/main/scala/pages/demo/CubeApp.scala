package pages.demo

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.document
import org.scalajs.dom.raw.HTMLElement
import typings.plotlyDotJsLib.plotlyDotJsMod._
import typings.stdLib.Partial
import org.scalajs.jquery.jQuery

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => dynLit}
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.raw.Event

object CubeApp extends MathApp {

  val lVar: Var[Double] = Var(1.0)
  val bVar: Var[Double] = Var(1.0)
  val hVar: Var[Double] = Var(1.0)

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
            "Länge",
            "l",
            None,
            unit,
            printInput(
              "lIn",
              (l, _, _) => l,
              l => lVar.value = l
            ),
            None
          ),
          printRow(
            "Breite",
            "b",
            None,
            unit,
            printInput(
              "bIn",
              (_, b, _) => b,
              b => bVar.value = b
            ),
            None
          ),
          printRow(
            "Höhe",
            "h",
            None,
            unit,
            printInput(
              "hIn",
              (_, _, h) => h,
              h => hVar.value = h
            ),
            None
          ),
          printRow(
            "Umfang",
            "U",
            Some("4*(l + b + h)"),
            unit,
            printInput(
              "circumferenceIn",
              (l, b, h) => 4 * (l + b + h),
              u => lVar.value = u / 4 - bVar.value - hVar.value
            ),
            Some("l = U/4 - b - h")
          ),
          printRow(
            "Fläche",
            "A",
            Some("2*(l*b+l*h+b*h)"),
            unit2,
            printInput(
              "areaIn",
              (l, b, h) => 2 * (l * b + l * h + b * h),
              area =>
                lVar.value = (area / 2 - bVar.value * hVar.value) / (bVar.value + hVar.value)
            ),
            Some("l = \\frac{A/2 - (b*h)}{b+h}")
          ),
          printRow(
            "Volumen",
            "V",
            Some("l*b*h"),
            unit3,
            printInput(
              "volIn",
              (l, b, h) => l*b*h,
              vol => 
              {println(s"Volume: $vol")
                lVar.value = vol / (bVar.value * hVar.value)}
            ),
            Some("l = \\frac{V}{b*h}")
          )
        )
        .map(_.bind)
    }
        </tbody>
      </table>
    </div>
  }

  @dom
  protected def printInput(
      inId: String,
      calcValue: (Double, Double, Double) => Double,
      onBlur: Double => Unit
  ): Binding[HTMLElement] = {
    val l = lVar.bind
    val b = bVar.bind
    val h = hVar.bind
    <input class="right" type="text" id={s"CubeApp_$inId"}
             value={s"${calcValue(l, b, h)}"}
             onblur={
      _: Event => onBlur(jQuery(s"#CubeApp_$inId").value().toString.toDouble)
    }/>
  }

  private lazy val plotGraph = Binding {
    val l = lVar.bind
    val b = bVar.bind
    val h = hVar.bind

    val data: js.Array[Data] = js.Array(
      dynLit(
        x = js.Array(b, 0, 0, 0, 0, 0, 0, b, b, b, b, 0),
        y = js.Array(0, 0, l, l, 0, 0, 0, 0, 0, 0, l, l),
        z = js.Array(0, 0, 0, h, h, 0, h, h, 0, h, h, h),
        `type` = "scatter3d",
        mode = "lines",
        name = "Quader"
      ).asInstanceOf[Partial[Data]],
      dynLit(
        x = js.Array(b, b),
        y = js.Array(l, 0),
        z = js.Array(0, 0),
        `type` = "scatter3d",
        mode = "lines",
        name = "Länge"
      ).asInstanceOf[Partial[Data]],
      dynLit(
        x = js.Array(b, 0),
        y = js.Array(l, l),
        z = js.Array(0, 0),
        `type` = "scatter3d",
        mode = "lines",
        name = "Breite"
      ).asInstanceOf[Partial[Data]],
      dynLit(
        x = js.Array(b, b),
        y = js.Array(l, l),
        z = js.Array(0, h),
        `type` = "scatter3d",
        mode = "lines",
        name = "Höhe"
      ).asInstanceOf[Partial[Data]],
      dynLit(
        x = js.Array(b, 0),
        y = js.Array(l, 0),
        z = js.Array(h, 0),
        `type` = "scatter3d",
        mode = "lines",
        name = "Diagonale"
      ).asInstanceOf[Partial[Data]]
    )
    val layout: Partial[Layout] =
      dynLit(
        width = diagramWidth * 1.5,
        height = diagramWidth,
        scene = dynLit(
          aspectmode = "data",
          aspectratio = dynLit(
            x = b,
            y = l,
            z = h
          ).asInstanceOf[Partial[Point]]
        ).asInstanceOf[Partial[Scene]]
      ).asInstanceOf[Partial[Layout]]

    Graph.plot(
      "plotCube",
      data,
      layout
    )
  }

}
