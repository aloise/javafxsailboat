package jphs.sailboatsimulation.shapes;

import javafx.scene.Group;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

/**
 * Created by jps on 22/04/2016.
 */
public class DrawnGroup extends Group {
    private final Scale scale = new Scale();
    private final Translate translate = new Translate();

    public DrawnGroup(double scaleFactor) {
        super();
        this.setScale(scaleFactor);
        this.getTransforms().addAll(translate, this.scale);
    }

    public DrawnGroup() {
        this(1.);
    }

    public void setScale(double scaleFactor) {
        this.scale.setX(scaleFactor);
        this.scale.setY(scaleFactor);
        this.scale.setZ(scaleFactor);
    }

    public void setX(double newX) {
        this.translate.setX(newX);
    }

    public void setY(double newY) {
        this.translate.setY(newY);
    }
}
