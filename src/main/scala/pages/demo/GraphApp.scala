package pages.demo

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.document
import org.scalajs.dom.raw.{Event, HTMLElement}
import typings.mathjaxLib.MathJaxNs.{^ => MathJax}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import scala.scalajs.js.timers.setTimeout

object GraphApp extends IntellijImplicits {

  val expressionVar: Var[String] = Var("x^2")

  @JSExportTopLevel("runJSGraph")
  def runJSGraph(): Unit = {
    dom.render(document.getElementById("graphDiv"), plotly)
  }

  @dom
  private lazy val plotly: Binding[HTMLElement] = {
    val expression = expressionVar.bind
    println(s"expression: $expression")

    Graph.plotGraph(expression, "plotGraph")
    <div class="ui form">
   <div class="field">
     <label>Formula</label>
     <input type="text" name="formula" id="formula" placeholder="Formula" value={
      expression
    }/>
   </div>
   <button class="ui button"
           onclick={
      _: Event =>
        println("expressionVal: " + formula.value)
        expressionVar.value = formula.value

    }>Submit</button>
    <p id="MathExample">{s"Formula: \\($expression\\)"}</p>
 </div>
  }

}
