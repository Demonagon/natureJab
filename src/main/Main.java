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

import java.util.Random;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override public void start(Stage stage) {
        stage.setTitle( "Nature" );

        Group root = new Group();
        Scene scene = new Scene( root );
        stage.setScene( scene );

        Canvas canvas = new Canvas( 1000, 1000 );
        root.getChildren().add( canvas );

        World world = new World();
        world.addObject(new Grass());
        Random random = new Random();
        for(int k = 0; k < 100; k++) {
            world.addObject(new BasicTree(random.nextInt() % 1001, random.nextInt() % 1001));
        }

        UpdateTimer timer = new UpdateTimer(world, canvas, 30);

        world.paintWorld(canvas);
        timer.start();
        stage.show();
    }
}