package pages.demo

trait MathJaxUtils {

  def mjInline(expression: String) = s"\\($expression\\)"

  def mjBlock(expression: String) = s"\\[$expression\\]"

}
