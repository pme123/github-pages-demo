package tutorial.webapp

import com.thoughtworks.binding.Binding.{BindingSeq, Var, Vars}
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.{Event, document}
import org.scalajs.dom.html.{Button, Table, TableRow, TableSection}

import scala.scalajs.js.annotation.JSExportTopLevel

object TutorialApp {


  @JSExportTopLevel("runJSClient")
  def main(): Unit = {
    val data = Vars(Contact(Var("Yang Bo"), Var("yang.bo@rea-group.com")))

    dom.render(document.getElementById("clientDiv"), bindingTable(data))
  }

  case class Contact(name: Var[String], email: Var[String])

  val data = Vars.empty[Contact]

  @dom
  def bindingButton(contact: Contact): Binding[Button] = {
    <button
    onclick={ event: Event =>
      contact.name.value = "Pascal"
    }
    >
      Modify the name
    </button>
  }

  @dom
  def bindingTr(contact: Contact): Binding[TableRow] = {
    <tr>
      <td>{ contact.name.bind }</td>
      <td>{ contact.email.bind }</td>
      <td>{ bindingButton(contact).bind }</td>
    </tr>
  }

  @dom
  lazy val header: Binding[TableSection] = {
    <thead>
      <tr>
        <th>Name</th>
        <th>E-mail</th>
        <th>Operation</th>
      </tr>
    </thead>
  }

  @dom
  def bindingTable(contacts: BindingSeq[Contact]): Binding[Table] = {
    <table>
      {header.bind}
      <tbody>
        {
        for (contact <- contacts) yield {
          bindingTr(contact).bind
        }
        }
      </tbody>
    </table>
  }

}