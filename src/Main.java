import GameControl.AGameMain;

public class Main {
  
  public static void main(String[] args) {
    // TODO:  Add in here full CLI argument parsing.
    // TODO:  Shift the creation of the dependent things used by GameMain into here and follow
    //  the inversion of control -- dependency injection -- design pattern.
    AGameMain main = new AGameMain();

    if (args.length == 1 && args[0].equalsIgnoreCase("Map")) {
      main.setStartWithMap(true);
    }
    main.go();
  }
}
