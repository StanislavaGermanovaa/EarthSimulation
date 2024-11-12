package com.example.simulation.simulationproject.controllers;

import com.example.simulation.simulationproject.models.SmartGroup;
import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Camera;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.Objects;

public class HelloController {
    @FXML
    private ImageView GalaxyImage;
    @FXML
    private Sphere Earth;
    @FXML
    private Slider mySlider;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Camera camera;

    private final SmartGroup world = new SmartGroup();
    private double anchorX, anchorY;
    private double anchorAngleX=0;
    private double anchorAngleY=0;
    private final DoubleProperty angleX=new SimpleDoubleProperty(0);
    private final DoubleProperty angleY=new SimpleDoubleProperty(0);
    public Camera getCamera() {
        return camera;
    }

    public void initialize() {
        setupEarthMaterial();
        setupSlider();
        world.getChildren().add(Earth);
        anchorPane.getChildren().add(world);
        initMouseControl(world, anchorPane);
        startRotationAnimation();
    }



    private void setupEarthMaterial() {
        // Create material for the Earth sphere
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/earth.jpeg"))));
        material.setSelfIlluminationMap(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/selfIllumination.jpeg"))));
        material.setSpecularMap(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/SpecularImg.jpeg"))));
        material.setBumpMap(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/bumpMap.jpeg"))));

        // Set material to Earth sphere
        Earth.setMaterial(material);
        Earth.setRotationAxis(Rotate.Y_AXIS);
    }

    public void setupSlider() {
        // Link the slider to the zoom (translateZ) of the SmartGroup
        mySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            world.setTranslateZ(newValue.doubleValue());
        });
    }


    private void startRotationAnimation() {
        // Rotate the Earth in an animation timer
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Earth.setRotate(Earth.getRotate() + 0.2); // Continuously rotate Earth
            }
        };
        timer.start();
    }
    private void initMouseControl(SmartGroup group, AnchorPane scene) {
        Rotate xRotate;
        Rotate yRotate;

        group.getTransforms().addAll(
                xRotate=new Rotate(0,Rotate.X_AXIS),
                yRotate=new Rotate(0,Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event->{
            anchorX=event.getSceneX();
            anchorY=event.getSceneY();
            anchorAngleX=angleX.get();
            anchorAngleY=angleY.get();
            System.out.println("work");
        });
        scene.setOnMouseDragged(mouseEvent -> {
            angleX.set(anchorAngleX-(anchorY-mouseEvent.getSceneY()));
            angleY.set(anchorAngleY + anchorX-mouseEvent.getSceneX());
            System.out.println("right");
        });

    }}





