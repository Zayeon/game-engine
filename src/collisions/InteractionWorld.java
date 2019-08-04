package collisions;

import entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class InteractionWorld {
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> entitiesToRemove = new ArrayList<>();

    public void addEntity(Entity entity){
        entities.add(entity);
    }

    public void removeEntity(Entity entity){
        entitiesToRemove.add(entity);
    }

    public void processInteractions(float timeSinceLastProcess){

    }


}
