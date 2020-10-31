package main;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import util.Task;
import world.objects.*;
import world.World;
import world.objects.tree.AppleTree;
import world.objects.tree.Bamboo;
import world.objects.tree.CanvasTreeDecorator;

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

        World world = new World(new CanvasTreeDecorator(canvas));
        world.addObject(new Grass());
        //world.addObject(new TiledFloor());
        /*Random random = new Random();
        for(int k = 0; k < 100; k++) {
            world.addObject(new BasicTree(random.nextInt() % 1001, random.nextInt() % 1001));
        }*/
        //world.addObject(new SpreadTree(500, 500, 50));
        //world.addObject(new GrowTree(500, 900));
        world.addObject(AppleTree.appleTreeBase(50));

        UpdateTimer timer = new UpdateTimer(world, 30);

        world.getDecorator().paint(world);
        timer.start();
        stage.show();
    }

    static class CounterTask implements Task {

        String name;
        double counter = 0;
        double max;

        public CounterTask(String name, double max) {
            this.name = name;
            this.max = max;
        }

        @Override
        public double allocate(double credits) {
            double transfer = Math.min(max - counter, credits);
            System.out.println(name + " : passage de " + counter + " à " + (counter + transfer));
            counter += transfer;
            return credits - transfer;
        }

        @Override
        public boolean isOver() {
            return counter >= max;
        }
    }
}