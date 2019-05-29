package pages.demo

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.document
import org.scalajs.dom.raw.{Event, HTMLElement}
import typings.openlayersLib
import typings.openlayersLib.openlayersMod.layerNs.{Base, Tile}
import typings.openlayersLib.openlayersMod.{
  EventsListenerFunctionType,
  View,
  eventsNs,
  formatNs,
  layerNs,
  projNs,
  sourceNs,
  styleNs,
  Map => OLMap
}
import scala.scalajs.js.timers.{SetTimeoutHandle, clearTimeout, setTimeout}

import typings.openlayersLib.openlayersMod.olxNs.{MapOptions, ViewOptions}
import typings.openlayersLib.openlayersMod.olxNs.layerNs.{
  TileOptions,
  VectorOptions
}
import typings.openlayersLib.openlayersMod.olxNs.sourceNs.{
  VectorOptions => SourceVectorOptions
}
import typings.openlayersLib.openlayersMod.olxNs.sourceNs.OSMOptions

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import typings.openlayersLib.openlayersMod.olxNs.styleNs.{
  StrokeOptions,
  StyleOptions
}
import typings.openlayersLib.openlayersMod.MapEvent

object MapExample {

  val centerLatVar: Var[Double] = Var(47.06472)
  val centerLonVar: Var[Double] = Var(8.524444)

  @JSExportTopLevel("mapExample")
  def mapExample(formDiv: String): Unit = {
    dom.render(document.getElementById(formDiv), printForm)
    plotGraph.watch
  }

  @dom
  private lazy val printForm: Binding[HTMLElement] = {
    <div class="ui form">
      <table>
        <tbody>
          <tr>
            <td>Latitute</td>
            <td>lat</td>
            <td>
              {latInput.bind}
            </td>
          </tr>
          <tr>
            <td>Longitute</td>
            <td>lon</td>
            <td>
              {lonInput.bind}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  }

  @dom
  private lazy val latInput = {
    val lat = centerLatVar.bind
    <input class="right" type="text" id="latIn" placeholder="Latitute" value={
      s"$lat"
    } onblur={_: Event => centerLatVar.value = latIn.value.toDouble}/>
  }

  @dom
  private lazy val lonInput = {
    val lon = centerLonVar.bind
    <input class="right" type="text" id="lonIn" placeholder="Longitute" value={
      s"$lon"
    } onblur={_: Event => centerLonVar.value = lonIn.value.toDouble}/>
  }

  private lazy val plotGraph = Binding {
    val lat = centerLatVar.bind
    val lon = centerLonVar.bind
    map
      .getView()
      .setCenter(
        projNs.transform(js.Tuple2(lon, lat), "EPSG:4326", "EPSG:3857")
      )
  }

  lazy val map = {
    // Layer with the Map
    val lmap = new Tile(
      TileOptions(
        source = new sourceNs.OSM()
      )
    )
    // Layer with the GPX Track
    val lgpx = new layerNs.Vector(
      VectorOptions(
        source = new sourceNs.Vector(
          SourceVectorOptions(
            url = "/assets/gps/rigi_kulm.gpx",
            format = new formatNs.GPX()
          )
        ),
        style = new styleNs.Style(
          StyleOptions(
            stroke = new styleNs.Stroke(
              StrokeOptions(
                color = "#f00",
                width = 3
              )
            )
          )
        )
      )
    )

    val olMap = new OLMap(
      MapOptions(
        target = "mapExample",
        // projection= new OpenLayers.Projection("EPSG:900913"),
        // displayProjection= new OpenLayers.Projection("EPSG:4326"),
        layers = js.Array[Base](
          lmap,
          lgpx
        ),
        view = new View(
          ViewOptions(
            center = projNs.fromLonLat(
              js.Tuple2(centerLonVar.value, centerLatVar.value)
            ),
            zoom = 10
          )
        )
      )
    )
    setTimeout(500) {
      val event: (js.Function1[eventsNs.Event, scala.Unit]) = {
        (e: eventsNs.Event) =>
          val view = olMap.getView()
          if (view != null){
            val center = view.getCenter()
            if (center != null && center._1 > Double.MinValue && center._1 < Double.MaxValue) {
              val center2 = projNs.transform(center, "EPSG:3857", "EPSG:4326")
              centerLatVar.value = center2._2
              centerLonVar.value = center2._1
            }
          }

      }
      olMap.on("moveend", event)
    }
    
    olMap
  }
}
