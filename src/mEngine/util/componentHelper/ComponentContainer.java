package mEngine.util.componentHelper;

import mEngine.interactive.components.*;

public class ComponentContainer {

    public RenderComponent renderComponent;
    public CollideComponent collideComponent;
    public ControlComponent controlComponent;
    public MovementComponent movementComponent;
    public InteractionComponent interactionComponent;

    public ComponentContainer(RenderComponent renderComponent,
                              CollideComponent collideComponent,
                              ControlComponent controlComponent,
                              MovementComponent movementComponent,
                              InteractionComponent interactionComponent) {

        this.renderComponent = renderComponent;
        this.collideComponent = collideComponent;
        this.controlComponent = controlComponent;
        this.movementComponent = movementComponent;
        this.interactionComponent = interactionComponent;

    }

}
