package pages.demo

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.document
import org.scalajs.dom.raw.{Event, HTMLElement}
import typings.openlayersLib
import typings.openlayersLib.openlayersMod.layerNs.{Base, Tile}
import typings.openlayersLib.openlayersMod.olxNs.{MapOptions, ViewOptions}
import typings.openlayersLib.openlayersMod.olxNs.layerNs.TileOptions
import typings.openlayersLib.openlayersMod.olxNs.sourceNs.OSMOptions
import typings.openlayersLib.openlayersMod.sourceNs.OSM
import typings.openlayersLib.openlayersMod.{View, projNs, Map => OLMap}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel


object MapExample {

  val radiusVar: Var[Double] = Var(3.0)

  @JSExportTopLevel("mapExample")
  def exampleGraph2(plotDiv: String, formDiv: String): Unit = {
    dom.render(document.getElementById(formDiv), printForm(plotDiv))
    plotGraph(plotDiv).watch
  }

  @dom
  private def printForm(plotDiv: String): Binding[HTMLElement] = {
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
              {radiusInput.bind}
            </td>
          </tr>
          <tr>
            <td>Area</td>
            <td>A</td>
            <td>
              {s"\\(\\pi*r^2\\)"}
            </td>
            <td>
              {areaInput.bind}
            </td>
            <td>
              {s"\\(r = \\sqrt{A/\\pi}\\)"}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  }

  @dom
  private lazy val radiusInput = {
    val r = radiusVar.bind
      <input class="right" type="text" id="radiusIn2" placeholder="Radius" value={s"$r"} onblur={_: Event => // adjust the radius on blur
      radiusVar.value = radiusIn2.value.toDouble}/>
  }

  @dom
  private lazy val areaInput = {
    val r = radiusVar.bind
    val area = r * r * Math.PI
    <input class="right" type="text" id="areaIn2" placeholder="Area" value={
      s"$area"
    } onblur={
      _: Event => // adjust the radius with the area
        radiusVar.value = Math.sqrt(areaIn2.value.toDouble / Math.PI)
    }/>
  }

  private def plotGraph(plotDiv: String) = Binding {
    val radius = radiusVar.bind
    val n = 8
    ("X "*n)sliding n take n map println

    val myTileServer = new Tile(TileOptions(
      source = new OSM(OSMOptions(
        crossOrigin = null,
        url = "http://toolserver.org/tiles/hikebike/{z}/{x}/{y}.png"
      ))
    ))
    new OLMap(MapOptions(
      target = plotDiv,
      layers = js.Array[Base](myTileServer),
      view = new View(
        ViewOptions(
          center = projNs.fromLonLat(js.Tuple2(37.41, 8.82)),
          zoom = 4
        )
      )
    ))

  }
}
