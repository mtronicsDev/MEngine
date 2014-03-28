package mEngine.gameObjects.components;

import mEngine.gameObjects.GameObject;
import mEngine.graphics.GraphicsController;
import mEngine.graphics.renderable.Model;

public class RenderComponent extends Component {

    String modelFileName;
    public Model model;

    public RenderComponent(String modelFileName) {

        this.modelFileName = modelFileName;

    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);
        model = new Model(modelFileName, parent.position, parent.rotation);

    }

    public void onUpdate() {

        GraphicsController.switchTo3D();
        model.update(parent.position, parent.rotation);

    }

}
