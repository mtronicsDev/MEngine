package mEngine.interactive.gameObjects;

import mEngine.interactive.components.*;
import mEngine.util.componentHelper.ComponentContainer;
import mEngine.util.componentHelper.ComponentHelper;
import org.lwjgl.util.vector.Vector3f;

public class GameObject {

    public Vector3f position;
    public Vector3f rotation;
    public Vector3f percentRotation;
    public Component[] components;

    public GameObject(Vector3f pos, Vector3f rot, Component[] components) {

        position = pos;
        rotation = rot;
        percentRotation = new Vector3f();

        this.components = components;

        Component renderComponent = null;
        Component collideComponent = null;
        Component controlComponent = null;
        Component movementComponent = null;
        Component interactionComponent = null;

        for(Component component : this.components) {

            if(component.getClass().getName().equals("RenderComponent")) renderComponent = component;

            else if(component.getClass().getName().equals("CollideComponent")) collideComponent = component;

            else if(component.getClass().getName().equals("ControlComponent")) controlComponent = component;

            else if(component.getClass().getName().equals("MovementComponent")) movementComponent = component;

            else if(component.getClass().getName().equals("InteractionComponent")) interactionComponent = component;

        }

        ComponentHelper.components.add(new ComponentContainer((RenderComponent)renderComponent,
                (CollideComponent)collideComponent,
                (ControlComponent)controlComponent,
                (MovementComponent)movementComponent,
                (InteractionComponent)interactionComponent));

    }

    public void update() {

        for(Component component : components) {

            component.update(this);

        }

    }

}
