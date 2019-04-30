package pages.demo

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.document
import org.scalajs.dom.raw.{Event, HTMLElement}
import typings.plotlyDotJsLib.plotlyDotJsMod.{Data, Layout}
import typings.stdLib.Partial

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => dynLit}
import scala.scalajs.js.annotation.JSExportTopLevel

object GraphExample {

  val radiusVar: Var[Double] = Var(3.0)

  @JSExportTopLevel("exampleGraphOnly")
  def exampleGraph(plotDiv: String, radius: Double): Unit = {
    plotGraph(plotDiv, radius)
  }

  @JSExportTopLevel("exampleGraph")
  def exampleGraph(plotDiv: String, formDiv: String): Unit = {
    dom.render(document.getElementById(formDiv), printForm(plotDiv))
  }

  @dom
  private def printForm(plotDiv: String): Binding[HTMLElement] = {
    val r = radiusVar.bind // bind radius
    // this code below here is executed whenever the radius changes
    plotGraph(plotDiv, r)

    val area = r * r * Math.PI
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
              <input class="right" type="text" id="radiusIn" placeholder="Radius" value={
      s"$r"
    } onblur={
      _: Event => // adjust the radius on blur
        radiusVar.value = radiusIn.value.toDouble
    }/>
            </td>
          </tr>
          <tr>
            <td>Area</td>
            <td>A</td>
            <td>
              {s"\\(\\pi*r^2\\)"}
            </td>
            <td>
              <input class="right" type="text" id="areaIn" placeholder="Area" value={
      s"$area"
    } onblur={
      _: Event => // adjust the radius with the area
        radiusVar.value = Math.sqrt(areaIn.value.toDouble / Math.PI)
    }/>
            </td>
          <td>
          {s"\\(r = \\sqrt{A/\\pi}\\)"}
          </td>
          </tr>
        </tbody>
      </table>
    </div>
  }

  private def plotGraph(plotDiv: String, radius: Double) = {
      println("plotGraph")
    val data: js.Array[Data] = js.Array() // we don't have data
    val layout: Partial[Layout] =
      dynLit(
        xaxis = dynLit(
          zeroline = true,
          range = js.Array(-radius - 0.5, radius + 0.5)
        ),
        yaxis = dynLit(
          zeroline = true,
          range = js.Array(-radius - 0.5, radius + 0.5)
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
