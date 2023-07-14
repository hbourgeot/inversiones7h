package com.hbourgeot.inversiones7h.controllers;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
import io.github.palexdev.materialfx.utils.others.loader.MFXLoader;
import io.github.palexdev.materialfx.utils.others.loader.MFXLoaderBean;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.hbourgeot.inversiones7h.MaterialJavaResourceLoader.loadURL;
import com.hbourgeot.inversiones7h.MaterialJavaResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class LoginViewController implements Initializable {
	private Stage stage;
	private double xOffset;
	private double yOffset;
	private final ToggleGroup toggleGroup;

	@FXML
	private HBox windowHeader;

	@FXML
	private MFXFontIcon closeIcon;

	@FXML
	private MFXFontIcon minimizeIcon;

	@FXML
	private MFXFontIcon alwaysOnTopIcon;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private MFXScrollPane scrollPane;

	@FXML
	private VBox navBar;

	@FXML
	private StackPane contentPane;

	@FXML
	private StackPane logoContainer;

	public LoginViewController() {
		this.toggleGroup = new ToggleGroup();
		ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.closeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Platform.exit());
		minimizeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ((Stage) rootPane.getScene().getWindow()).setIconified(true));
		alwaysOnTopIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				boolean newVal = !stage.isAlwaysOnTop();
				alwaysOnTopIcon.pseudoClassStateChanged(PseudoClass.getPseudoClass("always-on-top"), newVal);
				stage.setAlwaysOnTop(newVal);
			});

			windowHeader.setOnMousePressed(event -> {
				xOffset = stage.getX() - event.getScreenX();
				yOffset = stage.getY() - event.getScreenY();
			});
			windowHeader.setOnMouseDragged(event -> {
				stage.setX(event.getScreenX() + xOffset);
				stage.setY(event.getScreenY() + yOffset);
			});

		initializeLoader();

		ScrollUtils.addSmoothScrolling(scrollPane);

		// The only way to get a fucking smooth image in this shitty framework
		Image image = new Image(MaterialJavaResourceLoader.load("logo_alt.png"), 64, 64, true, true);
		ImageView logo = new ImageView(image);
		Circle clip = new Circle(30);
		clip.centerXProperty().set(logo.layoutBoundsProperty().get().getCenterX());
		clip.centerYProperty().set(logo.layoutBoundsProperty().get().getCenterY());
		logo.setClip(clip);
		logoContainer.getChildren().add(logo);
	}

	private void initializeLoader() {
		MFXLoader loader = new MFXLoader();
		loader.addView(MFXLoaderBean.of("LOGIN", loadURL("fxml/Login.fxml")).setBeanToNodeMapper(() -> createToggle("fas-circle-dot", "Inicio de sesion")).setDefaultRoot(true).get());
		loader.addView(MFXLoaderBean.of("SOBRE", loadURL("fxml/Sobre.fxml")).setBeanToNodeMapper(() -> createToggle("fas-icons", "Sobre nosotros")).get());
		//loader.addView(MFXLoaderBean.of("BUTTONS", loadURL("fxml/Buttons.fxml")).setBeanToNodeMapper(() -> createToggle("fas-circle-dot", "Buttons")).setDefaultRoot(true).get());
		//loader.addView(MFXLoaderBean.of("CHECKS_RADIOS_TOGGLES", loadURL("fxml/ChecksRadiosToggles.fxml")).setBeanToNodeMapper(() -> createToggle("fas-toggle-on", "Checks, Radios, Toggles")).get());
		//loader.addView(MFXLoaderBean.of("FONT-RESOURCES", loadURL("fxml/FontResources.fxml")).setBeanToNodeMapper(() -> createToggle("fas-icons", "Font Resources")).get());
		loader.setOnLoadedAction(beans -> {
			List<ToggleButton> nodes = beans.stream()
						.map(bean -> {
							ToggleButton toggle = (ToggleButton) bean.getBeanToNodeMapper().get();
							toggle.setOnAction(event -> contentPane.getChildren().setAll(bean.getRoot()));
							if (bean.isDefaultView()) {
								contentPane.getChildren().setAll(bean.getRoot());
								toggle.setSelected(true);
							}
							return toggle;
						}).collect(Collectors.toList());
			navBar.getChildren().setAll(nodes);
		});
		loader.start();
	}

	private ToggleButton createToggle(String icon, String text) {
		return createToggle(icon, text, 0);
	}

	private ToggleButton createToggle(String icon, String text, double rotate) {
		MFXIconWrapper wrapper = new MFXIconWrapper(icon, 24, 32);
		MFXRectangleToggleNode toggleNode = new MFXRectangleToggleNode(text, wrapper);
		toggleNode.setAlignment(Pos.CENTER_LEFT);
		toggleNode.setMaxWidth(Double.MAX_VALUE);
		toggleNode.setToggleGroup(toggleGroup);
		if (rotate != 0) wrapper.getIcon().setRotate(rotate);
		return toggleNode;
	}

	public void setStage(Stage stage){
		this.stage = stage;
	}
}
