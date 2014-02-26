package mEngine.interactive.gui.guiComponents;

import mEngine.interactive.gui.GUIElement;

public abstract class GUIComponent {

    public GUIElement parent;

    public  GUIComponent() {}

    public void onCreation(GUIElement element) { parent = element; }
    public void onUpdate(GUIElement element) {}
    public void onDestroy(GUIElement element) {}
    public void onRemoteUpdate(GUIElement element) {}
    public void onExternalUpdate(Object[] args) {}

}
