package main;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import world.World;

public class UpdateTimer extends AnimationTimer {

    World world;
    int ups;
    long updateTime;
    long lastUpdateTime;

    public UpdateTimer(World world, int ups) {
        this.world = world;
        this.ups = ups;
        this.updateTime = 1000000000 / ups;
        this.lastUpdateTime = System.nanoTime();
    }

    @Override
    public void handle(long currentTime) {
        if(currentTime < lastUpdateTime + updateTime) return;

        lastUpdateTime = currentTime;

        world.update();
        world.getDecorator().paint(world);
    }
}
