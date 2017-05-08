package jphs.sailboatsimulation.shapes;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Created by jps on 22/04/2016.
 */
public class RudderGroup extends Group {

    private final Rotate rz = new Rotate(0., Rotate.Z_AXIS);
    private final Translate translate = new Translate();

    public RudderGroup() {
        super();
        this.getTransforms().addAll(translate, rz);
        this.drawRudder();
    }

    /**
     * Creates the 3D shape of the vertical part of the rudder
     * @return the <code>MeshView</code> of the vertical part of the rudder
     */
    private MeshView drawRudderVerticalPart() {

        TriangleMesh rudderVerticalPartMesh = new TriangleMesh();

        rudderVerticalPartMesh.getTexCoords().addAll(0,0);

        rudderVerticalPartMesh.getPoints().addAll(
                0,-0.05f,1.1f,
                0,-0.05f,0,
                -0.6f,-0.05f,0,
                -0.3f,-0.05f,1.1f,

                0,0.05f,1.1f,//4
                0,0.05f,0,//5
                -0.6f,0.05f,0,//6
                -0.3f,0.05f,1.1f,//7

                0,-0.05f,1.1f,//8
                0,0.05f,1.1f,//9
                0,-0.05f,0,//10
                0,0.05f,0,//11
                -0.6f,-0.05f,0,//12
                -0.6f,0.05f,0,//13
                -0.3f,-0.05f,1.1f,//14
                -0.3f,0.05f,1.1f,//15
                0,-0.05f,1.1f,//16
                0,0.05f,1.1f//17
        );

        rudderVerticalPartMesh.getFaces().addAll(
                0,0, 3,0, 1,0,
                3,0, 2,0, 1,0,
                4,0, 5,0, 6,0,
                6,0, 7,0, 4,0,
                8,0, 10,0, 9,0,
                10,0, 11,0, 9,0,
                13,0, 12,0, 15,0,
                12,0, 14,0, 15,0,
                15,0, 14,0, 17,0,
                14,0, 16,0, 17,0
        );

        MeshView rudderVerticalPart = new MeshView(rudderVerticalPartMesh);
        return rudderVerticalPart;
    }

    /**
     * Creates the 3D shape of the rudder
     */
    private void drawRudder() {
        PhongMaterial rudderMaterial = new PhongMaterial();
        rudderMaterial.setDiffuseColor(Color.BLUE);
        MeshView rudderVerticalPart = this.drawRudderVerticalPart();
        rudderVerticalPart.setMaterial(rudderMaterial);
        Cylinder rudderBarre = new Cylinder(0.05, 3, 10);
        rudderBarre.setTranslateZ(1.1);
        rudderBarre.setTranslateX(1.2);
        rudderBarre.setRotate(90);
        rudderBarre.setRotationAxis(Rotate.Z_AXIS);
        rudderBarre.setMaterial(rudderMaterial);
        this.getChildren().addAll(rudderVerticalPart, rudderBarre);
    }

    public void setX(double newX) {
        this.translate.setX(newX);
    }

    public void setRzAngle(double newAngle) {
        this.rz.setAngle(newAngle);
    }

}
