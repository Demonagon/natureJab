package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;

import java.awt.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override public void start(Stage stage) {
        stage.setTitle( "Timeline Example" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        stage.setScene( theScene );

        Canvas canvas = new Canvas( 512, 512 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);

                // background image clears canvas
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.fillRect(x - 50, y - 50, 100, 100);
            }
        }.start();

        stage.show();
    }
}