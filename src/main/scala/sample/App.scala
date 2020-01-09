package sample

object App {

  sealed trait Services
  sealed trait ServiceA extends Services
  sealed trait ServiceB extends Services
  sealed trait ServiceC extends Services

  type All = ServiceA with ServiceB with ServiceC

  case class ServicesImpl(a: Option[ServiceA], b: Option[ServiceB], c: Option[ServiceC])

  case class Builder[State <: Services](
    a: Option[ServiceA] = None, b: Option[ServiceB] = None, c: Option[ServiceC] = None
  ) {

    def withA(a: ServiceA): Builder[State with ServiceA] = this.copy(a = Option(a))

    def withB(b: ServiceB): Builder[State with ServiceB] = this.copy(b = Option(b))

    def withC(c: ServiceC): Builder[State with ServiceC] = this.copy(c = Option(c))

    def build(implicit ev: State =:= All) = 
      ServicesImpl(a, b, c)

  }

  def main(args: Array[String]): Unit = {
    val ab = Builder()
      .withA(new ServiceA{})
      .withB(new ServiceB{})
      .build
    println(ab)
    val all = Builder()
      .withA(new ServiceA{})
      .withB(new ServiceB{})
      .withC(new ServiceC{})
      .build
    println(all)
  }

}
