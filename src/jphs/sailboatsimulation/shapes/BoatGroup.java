package jphs.sailboatsimulation.shapes;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import sun.jvm.hotspot.ui.tree.RootTreeNodeAdapter;

/**
 * Created by jps on 22/04/2016.
 */
public class BoatGroup extends Group {
    private final Rotate rx = new Rotate(0., Rotate.X_AXIS);
    private final Rotate rz = new Rotate(0., Rotate.Z_AXIS);
    private final Translate translate = new Translate();

    /**
     * Group for the mast
     */
    private final MastGroup mast = new MastGroup();
    /**
     * Group for the rudder
     */
    private final RudderGroup rudder = new RudderGroup();

    public BoatGroup() {
        super();
        this.getTransforms().addAll(translate, rz, rx);
        this.drawBoat();
    }

    /**
     * Creates the graphical representation of the floor
     * @return the <code>MeshView</code> for the floor
     */
    private MeshView drawFloor() {
        TriangleMesh floorMesh = new TriangleMesh();

        floorMesh.getTexCoords().addAll(0,0);

        floorMesh.getPoints().addAll(
                -1.f, -1.5f, 0.1f,//0
                3.f, -1.5f, 0.1f,//1
                3.f, 1.5f, 0.1f,//2
                -1.f, 1.5f,0.1f,//3
                4.5f, -0.75f, 0.1f,//4
                5.06f, -0.375f, 0.1f,//5
                5.06f, 0.375f, 0.1f,//6
                4.5f, 0.75f, 0.1f,//7
                5.25f, 0f, 0.1f//8
        );

        floorMesh.getFaces().addAll(
                0,0, 2,0, 1,0,
                0,0, 3,0, 2,0,
                4,0, 1,0, 7,0,
                7,0, 2,0, 1,0,
                4,0, 6,0, 5,0,
                4,0, 7,0, 6,0,
                5,0, 6,0, 8,0

        );

        MeshView floor = new MeshView(floorMesh);
        floor.setCullFace(CullFace.NONE);
        return floor;
    }

    /**
     * Creates the graphical representation of the roof
     * @return the <code>MeshView</code> of the boat roof
     */
    private MeshView drawUpFront() {
        PhongMaterial upFrontMaterial = new PhongMaterial();
        upFrontMaterial.setDiffuseColor(Color.color(0.8, 0.2, 0.2));
        TriangleMesh upFrontMesh = new TriangleMesh();

        upFrontMesh.getTexCoords().addAll(0,0);

        upFrontMesh.getPoints().addAll(
                3.0f, -2f, 1.1f,//0
                3.1f, -1.9f, 1.1f,//1
                3.75f,-1.6f, 1.1f,//2
                5.0f,-1,1.1f,//3
                5.75f,-0.5f,1.1f,//4
                6.0f, 0, 1.1f,//5
                5.75f,0.5f,1.1f,//6
                5.0f,1,1.1f,//7
                3.75f,1.6f,1.1f,//8
                3.1f,1.9f,1.1f,//9
                3.0f,2,1.1f//10
        );

        upFrontMesh.getFaces().addAll(
                0,0, 1,0, 10,0,
                1,0, 9,0, 10,0,
                1,0, 2,0, 9,0,
                2,0, 8,0, 9,0,
                2,0, 3,0, 8,0,
                3,0, 7,0, 8,0,
                3,0, 4,0, 7,0,
                4,0, 6,0, 7,0,
                4,0, 5,0, 6,0
        );

        MeshView upFront = new MeshView(upFrontMesh);
        upFront.setDrawMode(DrawMode.FILL);
        upFront.setMaterial(upFrontMaterial);
        return upFront;
    }

    /**
     * Creates the 3D shape of the right face of the boat
     * @return the <code>MeshView</code> of the right face of the boat
     */
    private MeshView drawRightFace() {
        PhongMaterial rightFaceMaterial = new PhongMaterial();
        rightFaceMaterial.setDiffuseColor(Color.GREEN);

        TriangleMesh rightFaceMesh = new TriangleMesh();

        rightFaceMesh.getTexCoords().addAll(0,0);

        rightFaceMesh.getPoints().addAll(
                -1,-1.5f,0.1f,//0
                -1, -1.9f, 0.6f,//1
                -1,-2,1.1f,//2
                3,-2,1.1f,//3
                3, -1.9f, 0.6f,//4
                3,-1.5f,0.1f//5
        );


        rightFaceMesh.getFaces().addAll(
                0,0, 5,0, 1,0,
                5,0, 4,0, 1,0,
                1,0, 4,0, 2,0,
                4,0, 3,0, 2,0,

                5,0, 0,0, 4,0,
                0,0, 1,0, 4,0,
                4,0, 1,0, 3,0,
                1,0, 2,0, 3,0
        );

        MeshView rightFace = new MeshView(rightFaceMesh);
        rightFace.setDrawMode(DrawMode.FILL);
        rightFace.setMaterial(rightFaceMaterial);
        return rightFace;
    }

    /**
     * Creates the 3D shape of the boat left face
     * @return the <code>MeshView</code> of the boat left face
     */
    private MeshView drawLeftFace() {
        PhongMaterial leftFaceMaterial = new PhongMaterial();
        leftFaceMaterial.setDiffuseColor(Color.GREEN);

        TriangleMesh leftFaceMesh = new TriangleMesh();

        leftFaceMesh.getTexCoords().addAll(0,0);

        leftFaceMesh.getPoints().addAll(
                -1,1.5f,0.1f,//0
                -1, 1.9f, 0.6f,//1
                -1,2,1.1f,//2
                3,2,1.1f,//3
                3, 1.9f, 0.6f,//4
                3,1.5f,0.1f//5
        );

        leftFaceMesh.getFaces().addAll(
                0,0, 1,0, 5,0,
                1,0, 4,0, 5,0,
                1,0, 2,0, 4,0,
                2,0, 3,0, 4,0,

                0,0, 5,0, 1,0,
                5,0, 4,0, 1,0,
                1,0, 4,0, 2,0,
                4,0, 3,0, 2,0
        );

        MeshView leftFace = new MeshView(leftFaceMesh);
        leftFace.setDrawMode(DrawMode.FILL);
        leftFace.setMaterial(leftFaceMaterial);
        return leftFace;

    }

    /**
     * Creates the 3D shape of the first bench
     * @return the <code>MeshView</code> of the boat first bench
     */
    private MeshView drawBench1() {
        PhongMaterial bench1Material = new PhongMaterial();
        bench1Material.setDiffuseColor(Color.color(1, 0, 0));

        TriangleMesh bench1Mesh = new TriangleMesh();

        bench1Mesh.getTexCoords().addAll(0, 0);

        bench1Mesh.getPoints().addAll(
                -1, 2, 1.1f,//0
                -1, 1.3f, 1.1f,//1
                3, 1.3f, 1.1f,//2
                3, 2, 1.1f,//3

                -1, 1.3f, 1.1f,//4
                -1, 1.1f, 0.9f,//5
                3, 1.1f, 0.9f,//6
                3, 1.3f, 1.1f,//7

                -1, 1.1f, 0.9f,//8
                -1, 0.9f, 0.7f,//9
                3, 0.9f, 0.7f,//10
                3, 1.1f, 0.9f,//11

                -1, 0.9f, 0.7f,//12
                -1, 0.8f, 0.1f,//13
                3, 0.8f, 0.1f,//14
                3, 0.9f, 0.7f//15
        );

        bench1Mesh.getFaces().addAll(
                2,0, 0,0, 1,0,
                2,0, 3,0, 0,0,
                5,0, 6,0, 4,0,
                6,0, 7,0, 4,0,
                9,0, 10,0, 8,0,
                10,0, 11,0, 8,0,
                13,0, 14,0, 12,0,
                14,0, 15,0, 12,0
        );

        MeshView bench1 = new MeshView(bench1Mesh);
        bench1.setMaterial(bench1Material);
        return bench1;
    }

    /**
     * Creates the 3D shape of the boat second bench
     * @return the <code>MeshView</code> of the boat second bench
     */
    private MeshView drawBench2() {
        PhongMaterial bench2Material = new PhongMaterial();
        bench2Material.setDiffuseColor(Color.color(1, 0, 0));

        TriangleMesh bench2Mesh = new TriangleMesh();

        bench2Mesh.getTexCoords().addAll(0,0);

        bench2Mesh.getPoints().addAll(
                -1,-2,1.1f,//0
                -1, -1.3f, 1.1f,//1
                3, -1.3f, 1.1f,//2
                3,-2,1.1f,//3
                -1, -1.3f, 1.1f,//4
                -1, -1.1f, 0.9f,//5
                3, -1.1f, 0.9f,//6
                3, -1.3f, 1.1f,//7
                -1, -1.1f, 0.9f,//7
                -1, -0.9f, 0.7f,//8
                3, -0.9f, 0.7f,//9
                3, -1.1f, 0.9f,//10
                -1, -0.9f, 0.7f,//11
                -1,-0.8f,0.1f,//12
                3,-0.8f,0.1f,//13
                3, -0.9f, 0.7f//14
        );

        bench2Mesh.getFaces().addAll(
                2,0, 1,0, 0,0,
                2,0, 0,0, 3,0,
                6,0, 5,0, 4,0,
                6,0, 4,0, 7,0,
                10,0, 9,0, 8,0,
                10,0, 8,0, 11,0,
                14,0, 13,0, 12,0,
                14,0, 12,0, 15,0
        );

        MeshView bench2 = new MeshView(bench2Mesh);
        bench2.setDrawMode(DrawMode.FILL);
        bench2.setMaterial(bench2Material);
        return bench2;
    }

    /**
     * Creates the 3D shape of the face in front of the mast
     * @return the <code>MeshView</code> of the face in front of the mast
     */
    private MeshView drawMastFrontFace() {
        PhongMaterial mastFrontFaceMaterial = new PhongMaterial();
        mastFrontFaceMaterial.setDiffuseColor(Color.BLUE);

        TriangleMesh mastFrontFaceMesh = new TriangleMesh();

        mastFrontFaceMesh.getTexCoords().addAll(0,0);

        mastFrontFaceMesh.getPoints().addAll(
                3+0,-1.5f+0.4f,0.1f,//0
                3+0, -1.9f+0.4f, 0.6f,//1
                3+0,-2+0.4f,1.1f,//2
                3+0,2-0.4f,1.1f,//3
                3+0, 1.9f-0.4f, 0.6f,//4
                3+0,1.5f-0.4f,0.1f//5
        );


        mastFrontFaceMesh.getFaces().addAll(
                5,0, 0,0, 1,0,
                5,0, 1,0, 4,0,
                4,0, 1,0, 2,0,
                4,0, 2,0, 3,0
        );

        MeshView mastFrontFace = new MeshView(mastFrontFaceMesh);
        mastFrontFace.setDrawMode(DrawMode.FILL);
        mastFrontFace.setCullFace(CullFace.NONE);
        mastFrontFace.setMaterial(mastFrontFaceMaterial);
        return mastFrontFace;
    }

    /**
     * Creates the 3D shape of the rear face of the boat
     * @return the <code>MeshView</code> of the rear face of the boat
     */
    private MeshView drawRearFace() {
        PhongMaterial rearFaceMaterial = new PhongMaterial();
        rearFaceMaterial.setDiffuseColor(Color.color(0.5, 1, 0));

        TriangleMesh rearFaceMesh = new TriangleMesh();

        rearFaceMesh.getTexCoords().addAll(0,0);

        rearFaceMesh.getPoints().addAll(
                -1,-1.5f,0.1f,//0
                -1, -1.9f, 0.6f,//1
                -1,-2,1.1f,//2
                -1,2,1.1f,//3
                -1, 1.9f, 0.6f,//4
                -1,1.5f,0.1f//5
        );

        rearFaceMesh.getFaces().addAll(
                0,0, 5,0, 1,0,
                1,0, 5,0, 4,0,
                1,0, 4,0, 2,0,
                2,0, 4,0, 3,0,

                0,0, 1,0, 5,0,
                1,0, 4,0, 5,0,
                1,0, 2,0, 4,0,
                2,0, 3,0, 4,0
        );

        MeshView rearFace = new MeshView(rearFaceMesh);
        rearFace.setDrawMode(DrawMode.FILL);
        rearFace.setMaterial(rearFaceMaterial);
        return rearFace;
    }

    /**
     * Creates the 3D shape of the front of the boat hull
     * @return the <code>MeshView</code> of the front of the boat hull
     */
    private MeshView drawFaceAvant() {
        PhongMaterial frontFaceMaterial = new PhongMaterial();
        frontFaceMaterial.setDiffuseColor(Color.GREEN);

        TriangleMesh frontFaceMesh = new TriangleMesh();

        frontFaceMesh.getTexCoords().addAll(0,0);

        frontFaceMesh.getPoints().addAll(
                3,-2,1.1f,//0
                3.1f,-1.7f,1.1f,//1
                3.75f,-1.5f, 1.1f,//2
                5.0f,-1,1.1f,//3
                5.75f,-0.5f,1.1f,//4
                6.0f, 0, 1.1f,//5
                5.25f,0, 0.1f,//6
                5.06f,-0.375f, 0.1f,//7
                4.5f,-0.75f, 0.1f,//8
                3,-1.5f,0.1f,//9
                3, -1.9f, 0.6f,//10

                3,2,1.1f,//11
                3.1f,1.7f,1.1f,//12
                3.75f,1.5f, 1.1f,//13
                5.0f,1,1.1f,//14
                5.75f,0.5f,1.1f,//15
                6.0f, 0, 1.1f,//16
                5.25f,0, 0.1f,//17
                5.06f,0.375f, 0.1f,//18
                4.5f,0.75f, 0.1f,//19
                3,1.5f,0.1f,//20
                3, 1.9f, 0.6f//21
        );

        frontFaceMesh.getFaces().addAll(
                10,0, 1,0, 0,0,
                10,0, 9,0, 1,0,
                9,0, 2,0, 1,0,
                9,0, 8,0, 2,0,
                8,0, 3,0, 2,0,
                8,0, 7,0, 3,0,
                7,0, 4,0, 3,0,
                7,0, 6,0, 4,0,
                6,0, 5,0, 4,0,

                21,0, 11,0, 12,0,
                21,0, 12,0, 20,0,
                20,0, 12,0, 13,0,
                20,0, 13,0, 19,0,
                19,0, 13,0, 14,0,
                19,0, 14,0, 18,0,
                18,0, 14,0, 15,0,
                18,0, 15,0, 17,0,
                17,0, 15,0, 16,0
        );

        MeshView frontFace = new MeshView(frontFaceMesh);
        frontFace.setDrawMode(DrawMode.FILL);
        frontFace.setMaterial(frontFaceMaterial);
        return frontFace;
    }






    /**
     * Creates the 3D shape of the boat
     */
    private void drawBoat() {
        PhongMaterial boatMaterial = new PhongMaterial();
        boatMaterial.setDiffuseColor(Color.BLUE);
        MeshView plancher = this.drawFloor();
        plancher.setMaterial(boatMaterial);
        MeshView upFront = this.drawUpFront();
        MeshView rightFace = this.drawRightFace();
        MeshView leftFace = this.drawLeftFace();
        MeshView banc1 = this.drawBench1();
        MeshView banc2 = this.drawBench2();
        MeshView faceAvantMat = this.drawMastFrontFace();
        MeshView faceArriereMat = this.drawRearFace();
        MeshView faceAvant = this.drawFaceAvant();
        this.getChildren().addAll(
                plancher,
                upFront,
                rightFace,
                leftFace,
                banc1,
                banc2,
                faceAvantMat,
                faceArriereMat,
                faceAvant
        );
        //boat.getChildren().addAll(faceAvant);
        rudder.setX(-1.);
        this.getChildren().add(rudder);
        //this.drawMast();
        mast.setX(5.0);
        this.getChildren().add(mast);

    }

    public void updateBoat(double deltavmax, double deltag, double phi, double theta, double x, double y, float b) {
        mast.setRzAngle(Math.toDegrees(deltavmax));
        mast.computePoints(b);
        rudder.setRzAngle(Math.toDegrees(deltag));
        rz.setAngle(Math.toDegrees(phi));
        rx.setAngle(Math.toDegrees(theta));
        translate.setX(x);
        translate.setY(y);
    }

    public void setX(double newX) {
        this.translate.setX(newX);
    }

    public void setY(double newY) {
        this.translate.setY(newY);
    }
}
