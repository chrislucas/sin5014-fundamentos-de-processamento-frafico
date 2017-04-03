import java.awt.Dimension
import scala.swing.MainFrame
//import scala.swing.Dimension;

/**
  * Created by C.Lucas on 02/04/2017.
  */

class Frame extends MainFrame {
  title = "MY Graphic User Interface"
  preferredSize = new Dimension(320, 320)
}

object ScalaApp {
  def main(args: Array[String]): Unit = {
    val frame = new Frame
    frame.visible = true
  }
}
