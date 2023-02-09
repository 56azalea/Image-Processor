package controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.StringReader;

import model.IPixel;
import model.ImageProcessorModelState.ColorTransType;
import model.ImageProcessorModelState.FilteringType;
import model.ImageProcessorModelState.FlipType;
import model.ImageProcessorModelState.GreyscaleType;
import model.MaskProcessorModel;
import view.ImageProcessorGUIView;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * The new controller specifically built for the GUI
 * visualization, which implements {@link Features} interface.
 */
public class GUIController implements Features {
  private MaskProcessorModel model;
  private Controller delegate;
  private ImageProcessorGUIView view;
  private String title;
  private IPixel[][] masks;

  /**
   * The constructor for {@link GUIController}, which runs addFeatures method
   * on the given view. The {@link ControllerImpl} works as a delegate here.
   *
   * @param model the image processor model
   * @param view  the image processor GUI view
   */
  public GUIController(MaskProcessorModel model, ImageProcessorGUIView view) {
    this.model = model;
    this.delegate = new ControllerImpl(model, new StringReader(""));
    this.view = view;
    view.addFeatures(this);
    this.title = "";
    this.masks = null;
  }

  @Override
  public void load(String imagePath) throws IllegalArgumentException {
    if (imagePath == null) {
      throw new IllegalArgumentException("The parameter cannot be null");
    } else if (!(imagePath.endsWith("jpg") || imagePath.endsWith("png")
            || imagePath.endsWith("bmp") || imagePath.endsWith("ppm"))) {
      this.view.renderMessage("The file is not in a format that can be loaded."
              + " Only can load: jpg, png, bmp, or ppm.");
    }
    this.delegate.load(imagePath, "image");
    // for preview
    this.delegate.load(imagePath, "image-pre");
  }

  @Override
  public void save(String imagePath, String imageName) throws IllegalArgumentException {
    if (imagePath == null || imageName == null) {
      throw new IllegalArgumentException("The imagePath or imageName cannot be null");
    }
    if (!(imagePath.endsWith("jpg") || imagePath.endsWith("bmp")
            || imagePath.endsWith("png") || imagePath.endsWith("ppm"))) {
      this.view.renderMessage(
              "Please indicate the file format when saving. (Add .jpg, .png, .bmp. or .ppm.)");
    } else {
      this.delegate.save(imagePath, "image");
      this.view.renderMessage("Image has been saved.");
    }
  }

  /**
   * A private helper function to return the new image to the screen
   * with a modification. Used by all image operation commands.
   *
   * @param imageTitle the name of the tile
   * @return the new image
   */
  protected Image imageToSave(String imageTitle) {
    BufferedImage toSave = new BufferedImage(
            model.getImage(imageTitle).getWidth(),
            model.getImage(imageTitle).getHeight(), TYPE_INT_RGB);
    for (int i = 0; i < toSave.getHeight(); i++) {
      for (int j = 0; j < toSave.getWidth(); j++) {
        Color col = new Color(
                model.getImage(imageTitle).getPixelAt(i, j).getRed(),
                model.getImage(imageTitle).getPixelAt(i, j).getGreen(),
                model.getImage(imageTitle).getPixelAt(i, j).getBlue());
        toSave.setRGB(j, i, col.getRGB());
      }
    }
    return toSave;
  }

  /**
   * A private helper function to determine if the mask is null or not.
   * If it is null, then it means the original image is getting edited.
   *
   * @param maskPixel the mask represented in pixels
   * @return whether it is editing the original image or the preview image
   */
  private boolean notPreview(IPixel[][] maskPixel) {
    if (maskPixel == null) {
      title = "image";
    } else {
      title = "image-pre-edit";
    }
    return (maskPixel == null);
  }

  @Override
  public void brighten(int strength, IPixel[][] mask) {
    try {
      if (this.notPreview(masks)) {
        this.model.brighten(title, strength, title);
      } else {
        this.model.brighten("image-pre", strength, masks, title);
      }
      this.view.refresh(this.imageToSave(title), this.notPreview(mask));
    } catch (Exception e) {
      this.view.renderMessage("The image to brighten has not been found.");
    }
  }

  @Override
  public void greyComponent(GreyscaleType greyType, IPixel[][] mask) {
    try {
      if (this.notPreview(masks)) {
        this.model.multipleGreyscale(title, greyType, title);
      } else {
        this.model.multipleGreyscale("image-pre", greyType, masks, title);
      }
      this.view.refresh(this.imageToSave(title), this.notPreview(mask));
    } catch (Exception e) {
      this.view.renderMessage("The image to apply " + greyType + " has not been found.");
    }
  }

  @Override
  public void flip(FlipType flipType) {
    try {
      this.model.flip("image", flipType, "image");
      this.view.refresh(this.imageToSave(title), true);
    } catch (Exception e) {
      this.view.renderMessage("The image to apply " + flipType + " has not been found.");
    }
  }

  @Override
  public void filtering(FilteringType filterType, IPixel[][] mask) {
    try {
      if (this.notPreview(masks)) {
        this.model.filtering(title, filterType, title);
      } else {
        this.model.filtering("image-pre", filterType, masks, title);
      }
      this.view.refresh(this.imageToSave(title), notPreview(mask));
    } catch (Exception e) {
      this.view.renderMessage("The image to " + filterType + " has not been found.");
    }
  }

  @Override
  public void colorTransformation(ColorTransType colorTransType, IPixel[][] mask) {
    try {
      if (this.notPreview(masks)) {
        this.model.colorTransformation(title, colorTransType, title);
      } else {
        this.model.colorTransformation("image-pre", colorTransType, masks, title);
      }
      this.view.refresh(this.imageToSave(title), notPreview(mask));
    } catch (Exception e) {
      this.view.renderMessage(e.getMessage());
      this.view.renderMessage("The image to apply " + colorTransType + " has not been found.");
    }
  }

  @Override
  public void downscale(double width, double height) {
    try {
      this.model.downscale("image", width, height, "image");
      this.view.refresh(this.imageToSave("image"), true);
    } catch (Exception e) {
      this.view.renderMessage("The image to apply downscale has not been found.");
    }
  }
}