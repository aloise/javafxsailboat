package jphs.sailboatsimulation.shapes;

import javafx.collections.ObservableFloatArray;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Created by jps on 22/04/2016.
 */
public class MastGroup extends Group {

    private final TriangleMesh sailMesh = new TriangleMesh();
    private final Rotate rz = new Rotate(0., Rotate.Z_AXIS);
    private final Translate translate = new Translate();

    public MastGroup() {
        super();
        this.getTransforms().addAll(translate, rz);
        this.drawMast();
    }

    public void computePoints(float b) {
        sailMesh.getPoints().set(7, b * 1.5f);
        sailMesh.getPoints().set(10, b * 2f);
        sailMesh.getPoints().set(13, b * 2f);
        sailMesh.getPoints().set(16, b* 1.5f);
    }

    /**
     * Creates the 3D shape of the Sail
     * @return the <code>MeshView</code> of the Sail
     */
    private void drawSail() {
        PhongMaterial sailMaterial = new PhongMaterial();
        sailMaterial.setDiffuseColor(Color.color(1, 1, 0));
        //float b = (float)-Math.atan(this.sailboat.getTheSail().getfV() / 500.);

        sailMesh.getTexCoords().addAll(0,0);

        sailMesh.getPoints().addAll(
                0,0,2,//0
                0,0,12,//1
                -1,0,10,//2
                -2,0,8,//3
                -3,0,6,//4
                -4,0,4,//5
                -5,0,2//6
        );

        sailMesh.getFaces().addAll(
                0,0, 1,0, 2,0,
                2,0, 3,0, 0,0,
                3,0, 4,0, 0,0,
                4,0, 5,0, 0,0,
                5,0, 6,0, 0,0,

                6,0, 5,0, 0,0,
                5,0 ,4,0, 0,0,
                4,0, 3,0, 0,0,
                3,0, 2,0, 0,0,
                2,0, 1,0, 0,0
        );

        MeshView sail = new MeshView(sailMesh);
        sail.setDrawMode(DrawMode.FILL);
        sail.setMaterial(sailMaterial);
        this.getChildren().add( sail);
    }

    /**
     * Creates the 3D shapes of the mast
     */
    private void drawMast() {
        PhongMaterial mastMaterial = new PhongMaterial();
        mastMaterial.setDiffuseColor(Color.GREY);
        Cylinder mastCylinder = new Cylinder(0.08, 14, 10);
        mastCylinder.setRotationAxis(Rotate.X_AXIS);
        mastCylinder.setRotate(90);
        mastCylinder.setTranslateZ(7.1);
        mastCylinder.setMaterial(mastMaterial);
        this.getChildren().add(mastCylinder);
        //MeshView sail = this.drawSail();
        this.drawSail();

        Cylinder sailSupport = new Cylinder(0.1, 5.5, 10);
        sailSupport.setRotationAxis(Rotate.Z_AXIS);
        sailSupport.setRotate(-90);
        sailSupport.setTranslateZ(2);
        sailSupport.setTranslateX(-2.75);
        sailSupport.setMaterial(mastMaterial);
        this.getChildren().add(sailSupport);
        //mast.getChildren().addAll(mastCylinder, sail, sailSupport);
    }

    public void setX(double newX) {
        this.translate.setX(newX);
    }

    public void setY(double newY) {
        this.translate.setY(newY);
    }

    public void setRzAngle(double newAngle) {
        this.rz.setAngle(newAngle);
    }
}
