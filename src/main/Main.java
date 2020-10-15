package main;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import world.objects.BasicTree;
import world.objects.Grass;
import world.World;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override public void start(Stage stage) {
        stage.setTitle( "Timeline Example" );

        Group root = new Group();
        Scene scene = new Scene( root );
        stage.setScene( scene );

        Canvas canvas = new Canvas( 1000, 1000 );
        root.getChildren().add( canvas );

        World world = new World();
        world.addObject(new Grass());
        world.addObject(new BasicTree(0, 0));
        world.addObject(new BasicTree(100, 100));

        final long startNanoTime = System.nanoTime();

        AnimationTimer timer = new AnimationTimer()
                {
                    public void handle(long currentNanoTime)
                    {
                        world.update();
                        world.paintWorld(canvas);
                    }
                };

        timer.start();
        stage.show();
    }
}