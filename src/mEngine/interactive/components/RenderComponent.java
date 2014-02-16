package mEngine.interactive.components;

import mEngine.graphics.renderable.Model;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.util.ModelHelper;

public class RenderComponent extends Component {

    String modelFileName;
    public Model model;

    public RenderComponent(String modelFileName) {

        this.modelFileName = modelFileName;

    }

    public void initialize(GameObject obj) {

        model = new Model(modelFileName, obj.position, obj.rotation);

    }

    public void update(GameObject obj) {

        model.update(obj.position, obj.rotation);

    }

    public void updateByComponent(GameObject obj) {}

}
