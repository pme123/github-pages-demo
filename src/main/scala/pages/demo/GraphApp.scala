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

import scala.collection.immutable
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

object GraphApp extends IntellijImplicits {

  val expressionVar = Var("x^2")

  @JSExportTopLevel("runJSGraph")
  def main(): Unit = {
    dom.render(document.getElementById("graphDiv"), plotly)
  }

  import scala.scalajs.js.timers.setTimeout

  @dom
  private def plotly: Binding[HTMLElement] = {
    val expression = expressionVar.bind
    setTimeout(2000) {

      println("Timeout: " + document.getElementById("plotItNow"))

      val expr = mathjsMod.^.compile(expression)
      val xValues = js.Array(-20 to 20: _*)
      val yValues = xValues.map { x =>
        val y = expr.eval(js.Dynamic.literal(
          x = x
        ))
        y
      }
      println("xValues: " + xValues.toSeq)
      println("yValues: " + yValues.toSeq)
      val data: js.Array[Data] = js.Array(js.Dynamic.literal(
        x = xValues,
        y = yValues
      ).asInstanceOf[Partial[Data]])
      val margin = js.Dynamic.literal(b = 0).asInstanceOf[Partial[Margin]]
      val layout: Partial[Layout] = js.Dynamic.literal(margin = margin).asInstanceOf[Partial[Layout]]
      val elem = document.getElementById("plotDiv")
      println(s"elem: $elem")
      newPlot("plotDiv",
        data,
        layout
      )
    }
    <div>
      <input type="text">
        {expression}
      </input>
    </div>
  }

  case class Point(x: Int, y: Int) {
    def +(p: Point) = Point(x + p.x, y + p.y)

    def /(d: Int) = Point(x / d, y / d)
  }

  @dom
  private def contents: Binding[HTMLElement] = {

    val canvas: HTMLCanvasElement = <canvas id="myCanvas" width={600} height={600}></canvas>.asInstanceOf[HTMLCanvasElement]
    val ctx = canvas.getContext("2d")
      .asInstanceOf[jsdom.CanvasRenderingContext2D]

    ctx.fillStyle = "white"
    ctx.fillRect(0, 0, 600, 600)
    ctx.fillStyle = "black"
    ctx.fillRect(0, 300, 600, 1)
    ctx.fillRect(300, 0, 1, 600)
    val yFormula: Double => Double = x => Math.pow(x, 4)

    def run(formula: Double => Double) = for (x <- -300 until 300) yield {

      val y = formula(x)
      println(s"X: $x  Y: $y")
      (x.toDouble, y)
    }

    val pairs = run(yFormula).filter { case (x, y) => x < 650 && x > -50 && y < 650 && y > -50 }
    val maxX = pairs.map(_._1).max
    val minX = pairs.map(_._1).min
    val factor = 300 / Seq(Math.abs(minX), maxX).max

    ctx.fillStyle = "black"
    ctx.fillRect(0, 300, 600, 1)
    ctx.fillRect(300, 0, 1, 600)

    println(s"Seq(minX, maxX): ${Seq(minX, maxX)}")
    println(s"factor: $factor")
    ctx.strokeStyle = s"blue"

    pairs.sliding(2).foreach { lines: immutable.Seq[(Double, Double)] =>
      println(s"factor: ${factor * lines.head._1} - ${lines.head._2}")

      ctx.moveTo(300 + factor * lines.head._1, 300 - lines.head._2)
      ctx.lineTo(300 + factor * lines.last._1, 300 - lines.last._2)
      ctx.stroke()
    }



    //jsdom.window.setInterval(() => run(), 50)

    canvas
  }
}