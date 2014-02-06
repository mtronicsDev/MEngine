package mEngine.interactive.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GUIScreen {

    public List<GUIElement> guiElements = new ArrayList<GUIElement>();
    public boolean isEnabled;

    public  GUIScreen(GUIElement[] elements, boolean isEnabled) {

        Collections.addAll(guiElements, elements);
        this.isEnabled = isEnabled;

    }

    public void update() {

        if(isEnabled) for(GUIElement element : guiElements) { element.update(); }

    }

    public void addGUIElement(GUIElement element) { guiElements.add(element); }
    public GUIElement getGUIElement(int index) { return guiElements.get(index); }
    public GUIText getGUIText(int index) { return (GUIText)guiElements.get(index); }

}