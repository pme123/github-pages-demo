package pages.demo

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.raw.{Event, HTMLElement}
import org.scalajs.jquery.jQuery

trait MathApp extends MathJaxUtils with IntellijImplicits {

  val diagramWidth = 500
  val unit: String = mjInline("m")
  val unit2: String = mjInline("m^2")
  val unit3: String = mjInline("m^3")

  @dom
  protected def printRow(
                          label: String,
                          abbreviation: String,
                          formula: Option[String] = None,
                          unit: String,
                          inputElement: Binding[HTMLElement],
                          radiusFormula: Option[String] = None
                        ): Binding[HTMLElement] = {
    <tr>
      <td>
        {label}
      </td> <td>
      {abbreviation}
    </td> <td>
      {mjInline(formula.getOrElse(""))}
    </td> <td>
      {inputElement.bind}
    </td> <td>
      {unit}
    </td> <td>
      {mjInline(radiusFormula.getOrElse(""))}
    </td>
    </tr>
  }

}
