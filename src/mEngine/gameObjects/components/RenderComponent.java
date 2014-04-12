package mEngine.gameObjects.components;

import mEngine.gameObjects.GameObject;
import mEngine.graphics.renderable.Model;

public class RenderComponent extends Component {

    public Model model;
    String modelFileName;

    public RenderComponent(String modelFileName) {

        this.modelFileName = modelFileName;

    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);
        model = new Model(modelFileName, parent.position, parent.rotation);

    }

}
