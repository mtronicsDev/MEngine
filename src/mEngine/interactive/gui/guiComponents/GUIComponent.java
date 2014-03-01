package mEngine.interactive.gui.guiComponents;

import mEngine.interactive.gui.GUIElement;

public abstract class GUIComponent {

    public GUIElement parent;

    public  GUIComponent() {}

    public void onCreation(GUIElement element) { parent = element; }
    public void onUpdate() {}
    public void onDestroy() {}
    public void onRemoteUpdate() {}
    public void onExternalUpdate(Object[] args) {}

}
