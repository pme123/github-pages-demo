package pages.demo

import com.thoughtworks.binding.Binding
import org.scalajs.dom.raw.HTMLElement

import scala.language.implicitConversions

trait IntellijImplicits {
  //noinspection NotImplementedCode
  implicit def makeIntellijHappy(x: scala.xml.Elem): Binding[HTMLElement] = ???
  implicit def makeIntellijHappy2(x: HTMLElement): Binding[HTMLElement] = ???
}
