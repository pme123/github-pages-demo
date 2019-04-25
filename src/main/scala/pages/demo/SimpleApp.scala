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

object SimpleApp extends IntellijImplicits {

  val radiusVar = Var(3.0)

  @JSExportTopLevel("runJSSimple")
  def main(): Unit = {
    dom.render(document.getElementById("simpleDiv"), plotly)
  }

  @dom
  private lazy val plotly: Binding[HTMLElement] = {
    val radiusVal = radiusVar.bind
    println(s"new radius: $radiusVal")
    val areaVal: Double = Math.PI * Math.sqrt(radiusVal)
    println(s"areaVal: $areaVal")
    <div class="ui form">
      <div class="field">
        <label>Radius</label>
        <input type="text" name="radius" id="radius" placeholder="Radius" value={
      radiusVal.toString
    }/>
      </div>
            <button class="ui button" 
      onclick={
      _: Event =>
        println("radius.value: " + radius.value)
        radiusVar.value = radius.value.toInt
    }>Submit</button>
</div>
  }

}
