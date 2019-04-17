package tutorial.webapp

import com.thoughtworks.binding.Binding.{BindingSeq, Constants, Var, Vars}
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.{Event, document}
import org.scalajs.dom.html.{Button, Table, TableRow, TableSection}
import org.scalajs.dom.raw.{HTMLDivElement, HTMLElement}

import scala.scalajs.js.annotation.JSExportTopLevel
import scala.xml.Elem

object TutorialApp {


  @JSExportTopLevel("runJSClient")
  def main(): Unit = {
    dom.render(document.getElementById("clientDiv"), contents)
  }

  case class Contact(name: Var[String], email: Var[String])

  val data = Vars.empty[Contact]

  @dom
  private lazy val header: Binding[TableSection] = {
    <thead>
      <tr>
        <th>Name</th>
        <th>E-mail</th>
        <th>Operation</th>
      </tr>
    </thead>
  }

  @dom
  private lazy val contents = {
    Constants(addButton, table).map(_.bind)

  }

  @dom
  private lazy val addButton: Binding[HTMLDivElement] =
    <div>
      <button
      onclick={event: Event =>
        data.value += Contact(Var("Yang Bo"), Var("yang.bo@rea-group.com"))
        println("added Data: " + data.value)}>
        Add a contact
      </button>
    </div>

  @dom
  private lazy val table: Binding[Table] = {
    <table>
      {header.bind}<tbody>
      {for (contact <- data) yield {
        bindingTr(contact).bind
      }}
    </tbody>
    </table>
  }

  @dom
  private def bindingTr(contact: Contact): Binding[TableRow] = {
    <tr>
      <td>
        {contact.name.bind}
      </td>
      <td>
        {contact.email.bind}
      </td>
      <td>
        {bindingButton(contact).bind}
      </td>
    </tr>
  }

  @dom
  private def bindingButton(contact: Contact): Binding[Button] = {
    <button
    onclick={event: Event =>
      contact.name.value = "Pascal"}>
      Modify the name
    </button>
  }
}