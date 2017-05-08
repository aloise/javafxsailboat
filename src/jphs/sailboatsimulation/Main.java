package jphs.sailboatsimulation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import jphs.sailboatsimulation.shapes.BoatGroup;
import jphs.sailboatsimulation.shapes.DrawnGroup;

public class Main extends Application {

    /**
     * main group of the display
     */
    final Group root = new Group();
    /**
     * Group containing the reversed 3D shapes
     */
    final Group world = new Group();
    /**
     * Group containing the 3D shapes of the boat, the sea and the buoys
     */
    final DrawnGroup drawnGroup = new DrawnGroup();
    /**
     * Camera to render the scene
     */
    final Camera camera = new PerspectiveCamera(true);

    /**
     * Group for the boat
     * |-> mast
     * |-> rudder
     */
    private final BoatGroup boat = new BoatGroup();
    /**
     * Group to perform transformation on the camera
     */
    final Group cameraGroup = new Group();

    private final Translate cameraTranslate = new Translate();

    private final Rotate cameraRx = new Rotate(0., Rotate.X_AXIS);

    private final Rotate cameraRy = new Rotate(0., Rotate.Y_AXIS);

    /**
     * Value of the near clip of the camera
     */
    private static final double CAMERA_NEAR_CLIP = 0.1;
    /**
     * Value of the far clip of the camera
     */
    private static final double CAMERA_FAR_CLIP = 10000.0;
    /**
     * Old position of the mouse along x-axis of the screen
     */
    private double mouseOldX;
    /**
     * Old position of the mouse along y-axis of the screen
     */
    private double mouseOldY;
    /**
     * Current position of the mouse along x-axis of the screen
     */
    private double mousePosX;
    /**
     * Current position of the mous along y-axis of the screen
     */
    private double mousePosY;
    /**
     * Delta of mouse position along x-axis
     */
    private double mouseDeltaX;
    /**
     * Delta of mouse position along y-axis
     */
    private double mouseDeltaY;
    /**
     * Zoom of the display
     */
    private double zoom = 1;
    /**
     * Angle of the camera along x-axis
     */
    private double angleX;
    /**
     * Position of the camera along x-axis
     */
    private double xCam;
    /**
     * Position of the camera along y-axis
     */
    private double yCam;
    /**
     * Position of the camera along z-axis
     */
    private double zCam;
    /**
     * Angle of the camera along y-axis
     */
    private double angleY;
    /**
     * Distance of the camera from the origin
     */
    private double rCam;
    /**
     * Angle of the sail
     */
    private double deltavmax;
    /**
     * Angle of the rudder
     */
    private double deltag;
    /**
     * Sailboat to visualize
     */
    private Sailboat sailboat;
    /**
     * Flag of the execution of the simulation
     */
    private boolean isStarted;
    /**
     * Simulated wind
     */
    private Wind theWind;


    /**
     * Initialize the camera and perform the axis rotation of the world group
     */
    private void buildCamera() {
        cameraGroup.getChildren().add(camera);
        cameraGroup.getTransforms().addAll(cameraTranslate, cameraRy, cameraRx);

        Rotate rx = new Rotate(-270.0, Rotate.X_AXIS);
        world.getTransforms().addAll(rx);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setNearClip(CAMERA_NEAR_CLIP);
        root.getChildren().add(cameraGroup);
        world.getChildren().add(drawnGroup);

        moveCamera();
    }

    /**
     * Initialize the light in the display
     */
    private void buildLight() {
        AmbientLight light = new AmbientLight();
        root.getChildren().add(light);
    }

    /**
     * Create the graphical representation of the Wind as a black-filled arrow
     */
    private void drawWind() {
        double psi = this.theWind.getWindDir();
        double a = 1;

        PhongMaterial windMaterial = new PhongMaterial();
        windMaterial.setDiffuseColor(Color.BLACK);

        TriangleMesh windMesh = new TriangleMesh();

        windMesh.getTexCoords().addAll(0,0);

        windMesh.getPoints().addAll(
                3, 14, 13,//0
                3,  (float)(14 - a), 13,//1
                3,(float)(14-a),14,//2
                3,(float)(12.5-a),12.5f,//3
                3,(float)(14-a),11,//4
                3,(float)(14-a),12,//5
                3,14,12//6
        );

        windMesh.getFaces().addAll(
                0,0, 1,0, 6,0,
                1,0, 5,0, 6,0,
                2,0, 3,0, 4,0,

                0,0, 6,0, 1,0,
                6,0, 5,0, 1,0,
                4,0, 3,0, 2,0
        );

        MeshView wind = new MeshView(windMesh);
        wind.setDrawMode(DrawMode.FILL);
        wind.setMaterial(windMaterial);
        wind.setRotationAxis(Rotate.Z_AXIS);
        wind.setRotate((psi + 0.5 * Math.PI) * 180.0 / Math.PI);
        drawnGroup.getChildren().add(wind);
    }

    /**
     * Creates the graphical representation of the sea as a rectangle
     */
    private void drawSea() {
        final PhongMaterial seaMaterial = new PhongMaterial();
        seaMaterial.setDiffuseColor(Color.color(0.5, 0.5, 1));

        TriangleMesh seaMesh = new TriangleMesh();
        seaMesh.getTexCoords().addAll(0, 0);

        seaMesh.getPoints().addAll(
                -100000, 100000, 0.1f,//0
                -100000, -100000, 0.1f,//1
                100000, -100000, 0.1f,//2
                100000, 100000, 0.1f//3
        );

        seaMesh.getFaces().addAll(
                0,0, 1,0, 2,0,
                2,0, 3,0, 0,0
        );

        MeshView sea = new MeshView(seaMesh);
        sea.setDrawMode(DrawMode.FILL);
        sea.setMaterial(seaMaterial);
        drawnGroup.getChildren().add(sea);
    }

    /**
     * Creates the graphical representation of 3 buoys as red-filled sphere
     */
    private void drawBuoys() {
        PhongMaterial buoyMaterial = new PhongMaterial();
        buoyMaterial.setDiffuseColor(Color.RED);
        buoyMaterial.setSpecularColor(Color.DARKRED);
        for (int i = 0; i < 3; i++) {
            Sphere buoy = new Sphere(1.0);
            buoy.setMaterial(buoyMaterial);
            buoy.setTranslateX((i + 1) * 10.0);
            drawnGroup.getChildren().add(buoy);
        }
    }



    /**
     * Handles the mouse event
     * @param scene scene associated to the mouse event
     * @param root root node in which handling mouse event
     */
    private void handleMouse(Scene scene, final Node root) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();

                boat.setX(0);
                boat.setY(0);
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseDeltaX = (mousePosX - mouseOldX);
                mouseDeltaY = (mousePosY - mouseOldY);


                if (me.isPrimaryButtonDown()) {
                    angleX += mouseDeltaY;
                    if (angleX <= 2) {
                        angleX = 2;
                    } else if (angleX >= 90) {
                        angleX = 90;
                    }
                    angleY += mouseDeltaX;
                    moveCamera();
                }
            }
        });
    }

    /**
     * Moves the camera according to the user inputs
     */
    private void moveCamera() {
        this.zCam = -this.rCam * Math.cos(Math.toRadians(this.angleY)) * Math.cos(Math.toRadians(this.angleX));
        this.xCam = -this.rCam * Math.sin(Math.toRadians(this.angleY)) * Math.cos(Math.toRadians(this.angleX));
        this.yCam = -this.rCam * Math.sin(Math.toRadians(this.angleX));

        this.cameraTranslate.setX(this.xCam);
        this.cameraTranslate.setY(this.yCam);
        this.cameraTranslate.setZ(this.zCam);
        this.cameraRy.setAngle(this.angleY);
        this.cameraRx.setAngle(-this.angleX);
    }


    /**
     * Handles the keyboard events
     * @param scene the scene in which the keyboard event occured
     * @param root the root node on which handling the keyboard event
     */
    private void handleKeyboard(Scene scene, final Node root) {

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case I:
                        zoom *= 1.1;
                        drawnGroup.setScale(zoom);
                        break;
                    case O:
                        zoom /= 1.1;
                        drawnGroup.setScale(zoom);
                        break;
                    case UP:
                        deltavmax = Math.max(-1.57, sailboat.getDeltavmax()-0.05);
                        break;
                    case DOWN:
                        deltavmax = Math.min(1.57, sailboat.getDeltavmax()+0.05);
                        break;
                    case RIGHT:
                        deltag = Math.min(sailboat.getDeltag()+0.05, 0.5);
                        break;
                    case LEFT:
                        deltag = Math.max(sailboat.getDeltag()-0.05, -0.5);
                        break;
                    case B:
                        rCam++;
                        moveCamera();
                        break;
                    case F:
                        rCam--;
                        moveCamera();
                        break;
                    case S:
                        isStarted = !isStarted;
                        break;
                } // switch
            } // handle()
        });  // setOnKeyPressed
    }  //  handleKeyboard()

    /**
     * Perform one simulation step of the sailboat
     * @param dt
     */
    private void update(double dt) {
        this.sailboat.update(dt, this.deltag, this.deltavmax, this.theWind);
    }

    /**
     * Creates the display and launch the animation
     * @param primaryStage window in which the display is performed
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.angleY = 0;
        this.xCam = 0;
        this.yCam = 0;
        this.zCam = -30;
        this.rCam = Math.sqrt(Math.pow(this.yCam, 2.) + Math.pow(this.xCam, 2.) + Math.pow(this.zCam, 2.));
        this.angleX = 5;
        this.deltag = 0;
        this.deltavmax = 0.3;
        this.sailboat = new Sailboat();
        this.isStarted = false;
        this.theWind = new Wind(4, 0*Math.PI);
        buildCamera();
        buildLight();
        drawSea();
        drawBuoys();
        drawWind();
        drawnGroup.getChildren().add(boat);
        Scale scale = new Scale(this.zoom, this.zoom, this.zoom);
        world.getTransforms().add(scale);

        boat.updateBoat(deltavmax, deltag, this.sailboat.getPhi(), this.sailboat.getTheta(), this.sailboat.getX(),
            this.sailboat.getY(), 0.f);


        root.getChildren().add(world);
        Scene scene = new Scene(root, 1024, 768, true);

        handleMouse(scene, world);
        handleKeyboard(scene, world);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sailboat simulation");
        primaryStage.show();
        scene.setCamera(camera);

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);


        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.020),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        double time = isStarted?0.02:0.0;
                        update(time);
                        float b = (float)-Math.atan(sailboat.getTheSail().getfV() / 500.);
                        boat.updateBoat(deltavmax, deltag, sailboat.getPhi(), sailboat.getTheta(), sailboat.getX(),
                                sailboat.getY(), b);
                    }
                }
        );
        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
