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
import javafx.scene.input.MouseEvent;
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

    private double mousePosX, mousePosY;
    private double mouseOldX, mouseOldY;
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);


    public Camera getCamera() {
        return camera;
    }

    public void initialize() {
        setupEarthMaterial();
        setupSlider();
        world.getChildren().add(Earth);
        Earth.getTransforms().addAll(rotateX, rotateY); // Add rotations to the sphere
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
        scene.setOnMousePressed(this::handleMousePressed);
        scene.setOnMouseDragged(this::handleMouseDragged);

        // Add smooth inertia for a better user experience
        group.setOnScroll(scrollEvent -> {
            double delta = scrollEvent.getDeltaY();
            group.setTranslateZ(group.getTranslateZ() + delta);
        });

    }
    // Capture initial mouse position on press
    private void handleMousePressed(MouseEvent event) {
        mouseOldX = event.getSceneX();
        mouseOldY = event.getSceneY();
    }

    // Rotate the Earth based on mouse drag movement
    private void handleMouseDragged(MouseEvent event) {
        mousePosX = event.getSceneX();
        mousePosY = event.getSceneY();

        double deltaX = mousePosX - mouseOldX;
        double deltaY = mousePosY - mouseOldY;

        // Reverse the signs to change rotation direction
        rotateY.setAngle(rotateY.getAngle() - deltaX * 0.3); // Inverted X-axis rotation
        rotateX.setAngle(rotateX.getAngle() + deltaY * 0.3); // Inverted Y-axis rotation

        mouseOldX = mousePosX;
        mouseOldY = mousePosY;
    }
}





