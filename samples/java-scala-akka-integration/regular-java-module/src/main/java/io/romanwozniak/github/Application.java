package io.romanwozniak.github;


import io.romanwozniak.github.akkamodule.ConsoleLogger;
import io.romanwozniak.github.akkamodule.actors.MyActorSystem;

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/21/15, 11:01
 */
public class Application {

    public void run() {
        ConsoleLogger.debug("Starting application...");

        ConsoleLogger.info("Doing something very important...");

        ConsoleLogger.warn("Something is wrong. Trying to recover it...");

        ConsoleLogger.error("Ooops, I just saw a dead raccoon!");

        try {
            throw new Exception("Was eaten by crazy raccoon!");
        } catch (Exception e) {
            ConsoleLogger.error("Hell, raccoon is not dead and it's trying to eat me!", e);
        } finally {
            MyActorSystem.system().shutdown();
        }


    }


    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }
}
