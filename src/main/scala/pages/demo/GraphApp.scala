package pages.demo

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.document
import org.scalajs.dom.raw.{Event, HTMLElement}

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}
import scala.scalajs.js.annotation.JSExportTopLevel
import scala.scalajs.js.timers.{setTimeout, clearTimeout}

object GraphApp extends IntellijImplicits {

  val expressionVar: Var[String] = Var("x^2")

  @JSExportTopLevel("runJSGraph")
  def main(): Unit = {
    dom.render(document.getElementById("graphDiv"), plotly)
  }
  import scala.scalajs.js.timers.SetTimeoutHandle
  var handle: SetTimeoutHandle = setTimeout(0) {}

  @dom
  private lazy val plotly: Binding[HTMLElement] = {
    val expression = expressionVar.bind
    println(s"expression: $expression")

    println(s"after timeout")

    Graph.plotly(expression, "plotGraph")
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
    <p id="MathExample2">{"Formula: $$" + expression + "$$"}</p>
 </div>
  }

}
