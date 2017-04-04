import java.io.{BufferedReader, InputStreamReader, PrintStream, PrintWriter}


/**
  * Created by r028367 on 04/04/2017.
  * http://codeforces.com/blog/entry/21074
  * https://github.com/tomoki/Scala-competitive-library/blob/master/src/main/scala/Main.scala
  */
object IO {
  def ioString(is: InputStreamReader) = {
    val rd: BufferedReader = new BufferedReader(new InputStreamReader(System.in));

    try {
      var line = rd.readLine();
      println(line)
    } catch  {
      case e : Exception => println(e);
    }
  }

  def main(args: Array[String]): Unit = {
    val out = new PrintWriter(Console.out, false)
    out.printf("%d\n", new Integer(10))
  }
}
