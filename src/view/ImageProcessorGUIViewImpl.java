package view;

import controller.Features;
import model.IPixel;
import model.ImageProcessorModelState.GreyscaleType;
import model.ImageProcessorModelState.ColorTransType;
import model.ImageProcessorModelState.FilteringType;
import model.ImageProcessorModelState.FlipType;
import model.Pixel;
import util.Utils;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements the {@link ImageProcessorGUIView} interface, and will be in
 * charge of the GUI visualization of the image processor. It extends {@link JFrame}.
 */
public class ImageProcessorGUIViewImpl extends JFrame implements ImageProcessorGUIView {
  private final JLabel fileLoadDisplay;
  private final JLabel histogramDisplay;
  private final JLabel previewDisplay;
  private final JScrollPane previewScrollPane;
  private final JButton fileLoadButton;
  private final JButton fileSaveButton;
  private final JButton brightenPreviewButton;
  private final JButton redComponentPreviewButton;
  private final JButton greenComponentPreviewButton;
  private final JButton blueComponentPreviewButton;
  private final JButton valueComponentPreviewButton;
  private final JButton intensityComponentPreviewButton;
  private final JButton lumaComponentPreviewButton;
  private final JButton blurPreviewButton;
  private final JButton sharpenPreviewButton;
  private final JButton greyscalePreviewButton;
  private final JButton sepiaPreviewButton;
  private final JButton brightenButton;
  private final JButton redComponentButton;
  private final JButton greenComponentButton;
  private final JButton blueComponentButton;
  private final JButton valueComponentButton;
  private final JButton intensityComponentButton;
  private final JButton lumaComponentButton;
  private final JButton horizontalButton;
  private final JButton verticalButton;
  private final JButton blurButton;
  private final JButton sharpenButton;
  private final JButton greyscaleButton;
  private final JButton sepiaButton;
  private final JButton downscaleButton;
  private String currentImage;

  /**
   * Constructor for ImageProcessorGUIView Implementation.
   * This is where all the panels, labels, and buttons are
   * constructed in assigned locations.
   */
  public ImageProcessorGUIViewImpl() {
    super();
    setTitle("Image Processor");
    setSize(1200, 800);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // adapted from given SwingDemo code
    JPanel mainPanel = new JPanel();
    mainPanel.setSize(1200, 800);
    // scroll bars around this main panel
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    this.add(mainScrollPane);

    // contains two panels: imagePanel and histogramPanel
    JPanel leftPanel = new JPanel();
    leftPanel.setPreferredSize(new Dimension(800, 780));
    // aligns panels in leftPanel vertically
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(leftPanel, BorderLayout.WEST);

    // an image panel
    JPanel imagePanel = new JPanel();
    imagePanel.setPreferredSize(new Dimension(800, 500));
    // a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Current image"));
    // adds to the leftPanel
    leftPanel.add(imagePanel);
    // an image icon label
    this.fileLoadDisplay = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(this.fileLoadDisplay);
    imageScrollPane.setPreferredSize(new Dimension(780, 480));
    imagePanel.add(imageScrollPane);

    // a histogram panel
    JPanel histogramPanel = new JPanel();
    histogramPanel.setPreferredSize(new Dimension(800, 250));
    // a border around the panel with a caption
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    // adds to the leftPanel
    leftPanel.add(histogramPanel);
    // the inner (actual) histogram itself
    this.histogramDisplay = new JLabel();
    histogramPanel.add(histogramDisplay);

    // contains two panels: previewPanel and operationPanel
    JPanel rightPanel = new JPanel();
    rightPanel.setPreferredSize(new Dimension(360, 780));
    // aligns panels in rightPanel vertically
    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(rightPanel, BorderLayout.EAST);

    // a preview panel
    JPanel previewPanel = new JPanel();
    previewPanel.setPreferredSize(new Dimension(360, 450));
    // a border around the panel with a caption
    previewPanel.setBorder(BorderFactory.createTitledBorder("Preview buttons"));
    // instructions
    JLabel previewMessage1 = new JLabel("These buttons support a preview mode. To apply");
    previewPanel.add(previewMessage1);
    JLabel previewMessage2 = new JLabel("to the entire image, use operation buttons below.");
    previewPanel.add(previewMessage2);
    // adds to the rightPanel
    rightPanel.add(previewPanel);

    // a preview image panel
    JPanel previewImagePanel = new JPanel();
    previewImagePanel.setPreferredSize(new Dimension(300, 210));
    // adds to the preview panel
    previewPanel.add(previewImagePanel);
    // a preview icon label
    this.previewDisplay = new JLabel();
    this.previewScrollPane = new JScrollPane(this.previewDisplay);
    previewScrollPane.setPreferredSize(new Dimension(200, 200));
    previewImagePanel.add(previewScrollPane);

    // a operation panel
    JPanel operationPanel = new JPanel();
    operationPanel.setPreferredSize(new Dimension(360, 300));
    // a border around the panel with a caption
    operationPanel.setBorder(BorderFactory.createTitledBorder("Operation buttons"));
    // instructions
    JLabel operationMessage =
            new JLabel("These buttons apply operations to the entire image.");
    operationPanel.add(operationMessage);
    // adds to the rightPanel
    rightPanel.add(operationPanel);

    // brighten (preview)
    this.brightenPreviewButton = new JButton("Brighten");
    this.brightenPreviewButton.setActionCommand("Brighten");
    previewPanel.add(this.brightenPreviewButton);

    // red-component (preview)
    this.redComponentPreviewButton = new JButton("Red-component");
    this.redComponentPreviewButton.setActionCommand("Red-component");
    previewPanel.add(this.redComponentPreviewButton);

    // green-component (preview)
    this.greenComponentPreviewButton = new JButton("Green-component");
    this.greenComponentPreviewButton.setActionCommand("Green-component");
    previewPanel.add(this.greenComponentPreviewButton);

    // blue-component (preview)
    this.blueComponentPreviewButton = new JButton("Blue-component");
    this.blueComponentPreviewButton.setActionCommand("Blue-component");
    previewPanel.add(this.blueComponentPreviewButton);

    // value-component (preview)
    this.valueComponentPreviewButton = new JButton("Value-component");
    this.valueComponentPreviewButton.setActionCommand("Value-component");
    previewPanel.add(this.valueComponentPreviewButton);

    // intensity-component (preview)
    this.intensityComponentPreviewButton = new JButton("Intensity-component");
    this.intensityComponentPreviewButton.setActionCommand("Intensity-component");
    previewPanel.add(this.intensityComponentPreviewButton);

    // luma-component (preview)
    this.lumaComponentPreviewButton = new JButton("Luma-component");
    this.lumaComponentPreviewButton.setActionCommand("Luma-component");
    previewPanel.add(this.lumaComponentPreviewButton);

    // blur (preview)
    this.blurPreviewButton = new JButton("Blur");
    this.blurPreviewButton.setActionCommand("Blur");
    previewPanel.add(this.blurPreviewButton);

    // sharpen (preview)
    this.sharpenPreviewButton = new JButton("Sharpen");
    this.sharpenPreviewButton.setActionCommand("Sharpen");
    previewPanel.add(this.sharpenPreviewButton);

    // greyscale (preview)
    this.greyscalePreviewButton = new JButton("Greyscale");
    this.greyscalePreviewButton.setActionCommand("Greyscale");
    previewPanel.add(this.greyscalePreviewButton);

    // sepia (preview)
    this.sepiaPreviewButton = new JButton("Sepia");
    this.sepiaPreviewButton.setActionCommand("Sepia");
    previewPanel.add(this.sepiaPreviewButton);

    // file load
    this.fileLoadButton = new JButton("Load");
    this.fileLoadButton.setActionCommand("Load file");
    operationPanel.add(this.fileLoadButton);

    // file save
    this.fileSaveButton = new JButton("Save");
    this.fileSaveButton.setActionCommand("Save file");
    operationPanel.add(this.fileSaveButton);

    // brighten (operation)
    this.brightenButton = new JButton("Brighten");
    this.brightenButton.setActionCommand("Brighten");
    operationPanel.add(this.brightenButton);

    // red-component (operation)
    this.redComponentButton = new JButton("Red-component");
    this.redComponentButton.setActionCommand("Red-component");
    operationPanel.add(this.redComponentButton);

    // green-component (operation)
    this.greenComponentButton = new JButton("Green-component");
    this.greenComponentButton.setActionCommand("Green-component");
    operationPanel.add(this.greenComponentButton);

    // blue-component (operation)
    this.blueComponentButton = new JButton("Blue-component");
    this.blueComponentButton.setActionCommand("Blue-component");
    operationPanel.add(this.blueComponentButton);

    // value-component (operation)
    this.valueComponentButton = new JButton("Value-component");
    this.valueComponentButton.setActionCommand("Value-component");
    operationPanel.add(this.valueComponentButton);

    // intensity-component (operation)
    this.intensityComponentButton = new JButton("Intensity-component");
    this.intensityComponentButton.setActionCommand("Intensity-component");
    operationPanel.add(this.intensityComponentButton);

    // luma-component (operation)
    this.lumaComponentButton = new JButton("Luma-component");
    this.lumaComponentButton.setActionCommand("Luma-component");
    operationPanel.add(this.lumaComponentButton);

    // horizontal-flip (operation)
    this.horizontalButton = new JButton("Horizontal-flip");
    this.horizontalButton.setActionCommand("Horizontal-flip");
    operationPanel.add(this.horizontalButton);

    // vertical-flip (operation)
    this.verticalButton = new JButton("Vertical-flip");
    this.verticalButton.setActionCommand("Vertical-flip");
    operationPanel.add(this.verticalButton);

    // blur (operation)
    this.blurButton = new JButton("Blur");
    this.blurButton.setActionCommand("Blur");
    operationPanel.add(this.blurButton);

    // sharpen (operation)
    this.sharpenButton = new JButton("Sharpen");
    this.sharpenButton.setActionCommand("Sharpen");
    operationPanel.add(this.sharpenButton);

    // greyscale (operation)
    this.greyscaleButton = new JButton("Greyscale");
    this.greyscaleButton.setActionCommand("Greyscale");
    operationPanel.add(this.greyscaleButton);

    // sepia (operation)
    this.sepiaButton = new JButton("Sepia");
    this.sepiaButton.setActionCommand("Sepia");
    operationPanel.add(this.sepiaButton);

    // downscale (operation)
    this.downscaleButton = new JButton("Downscale");
    this.downscaleButton.setActionCommand("Downscale");
    operationPanel.add(this.downscaleButton);

    setVisible(true);
  }

  @Override
  public void refresh(Image image, Boolean original) {
    if (original) {
    this.fileLoadDisplay.setIcon(new ImageIcon(image));
    this.histogramDisplay.setIcon(new ImageIcon(this.histogramImage("", image)));
    } else {
      this.previewDisplay.setIcon(new ImageIcon(image));
    }
    this.repaint();
  }

  @Override
  public void addFeatures(Features feature) {
    this.fileLoadButton.addActionListener(act -> {
      final JFileChooser fileChooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "JPG, PPM, BMP, PNG images", "jpg", "png", "bmp", "ppm");
      fileChooser.setFileFilter(filter);
      int retValue = fileChooser.showOpenDialog(ImageProcessorGUIViewImpl.this);
      if (retValue == JFileChooser.APPROVE_OPTION) {
        File f = fileChooser.getSelectedFile();
        this.currentImage = f.getAbsolutePath();
        if (this.currentImage.endsWith("ppm")) {
          model.Image ppmImage = Utils.ppmToImage(this.currentImage, "image");
          BufferedImage bImage = Utils.toBufferedImage(ppmImage, this.currentImage);
          fileLoadDisplay.setIcon(new ImageIcon(bImage));
          previewDisplay.setIcon(new ImageIcon(bImage));
        } else {
          fileLoadDisplay.setIcon(new ImageIcon(this.currentImage));
          previewDisplay.setIcon(new ImageIcon(this.currentImage));
        }
        feature.load(this.currentImage);
        histogramDisplay.setIcon(
                new ImageIcon(this.histogramImage(this.currentImage, null)));
      }
    });
    this.fileSaveButton.addActionListener(act -> {
      final JFileChooser fileChooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "JPG, PNG, BMP, PNG images", "jpg", "png", "bmp", "ppm");
      fileChooser.setFileFilter(filter);
      int retValue = fileChooser.showSaveDialog(ImageProcessorGUIViewImpl.this);
      if (retValue == JFileChooser.APPROVE_OPTION) {
        File f = fileChooser.getSelectedFile();
        feature.save(f.getAbsolutePath(), f.getAbsolutePath());
      }
    });
    this.brightenButton.addActionListener(act -> {
      String brightenDisplay = JOptionPane.showInputDialog("Please enter a strength (0~255) " +
              "that you want to brighten this image by");
      try {
        int strength = Integer.parseInt(brightenDisplay);
        feature.brighten(strength, null);
      } catch (NumberFormatException e) {
        this.renderMessage("Please enter a valid strength");
      }
    });
    this.brightenPreviewButton.addActionListener(act -> {
      String brightenPrevDisplay = JOptionPane.showInputDialog("Please enter a strength (0~255) " +
              "that you want to brighten this preview image by");
      try {
        int strength = Integer.parseInt(brightenPrevDisplay);
        feature.brighten(strength, this.makeMask());
      } catch (NumberFormatException e) {
        this.renderMessage("Please enter a valid strength");
      }
    });
    this.downscaleButton.addActionListener(act -> {
      String widthDisplay = JOptionPane.showInputDialog("Please enter a scale factor to " +
              "downsize the width of this image by");
      String heightDisplay = JOptionPane.showInputDialog("Please enter a scale factor to " +
              "downsize the height of this image by");
      try {
        double width = Double.parseDouble(widthDisplay);
        double height = Double.parseDouble(heightDisplay);
        feature.downscale(width, height);
      } catch (NumberFormatException e) {
        this.renderMessage("Please enter a valid width and height");
      }
    });
    this.redComponentButton.addActionListener(act ->
            feature.greyComponent(GreyscaleType.Red, null));
    this.greenComponentButton.addActionListener(act ->
            feature.greyComponent(GreyscaleType.Green, null));
    this.blueComponentButton.addActionListener(act ->
            feature.greyComponent(GreyscaleType.Blue, null));
    this.valueComponentButton.addActionListener(act ->
            feature.greyComponent(GreyscaleType.Value, null));
    this.intensityComponentButton.addActionListener(act ->
            feature.greyComponent(GreyscaleType.Intensity, null));
    this.lumaComponentButton.addActionListener(act ->
            feature.greyComponent(GreyscaleType.Luma, null));
    this.horizontalButton.addActionListener(act -> feature.flip(FlipType.Horizontal));
    this.verticalButton.addActionListener(act -> feature.flip(FlipType.Vertical));
    this.blurButton.addActionListener(act -> feature.filtering(FilteringType.Blur, null));
    this.sharpenButton.addActionListener(act ->
            feature.filtering(FilteringType.Sharpen, null));
    this.greyscaleButton.addActionListener(act ->
            feature.colorTransformation(ColorTransType.Greyscale, null));
    this.sepiaButton.addActionListener(act ->
            feature.colorTransformation(ColorTransType.Sepia, null));
    this.redComponentPreviewButton.addActionListener(act ->
            feature.greyComponent(GreyscaleType.Red, this.makeMask()));
    this.greenComponentPreviewButton.addActionListener(act ->
            feature.greyComponent(GreyscaleType.Green, this.makeMask()));
    this.blueComponentPreviewButton.addActionListener(act ->
            feature.greyComponent(GreyscaleType.Blue, this.makeMask()));
    this.valueComponentPreviewButton.addActionListener(act ->
            feature.greyComponent(GreyscaleType.Value, this.makeMask()));
    this.intensityComponentPreviewButton.addActionListener(act ->
            feature.greyComponent(GreyscaleType.Intensity, this.makeMask()));
    this.lumaComponentPreviewButton.addActionListener(act ->
            feature.greyComponent(GreyscaleType.Luma, this.makeMask()));
    this.blurPreviewButton.addActionListener(act ->
            feature.filtering(FilteringType.Blur, this.makeMask()));
    this.sharpenPreviewButton.addActionListener(act ->
            feature.filtering(FilteringType.Sharpen, this.makeMask()));
    this.greyscalePreviewButton.addActionListener(act ->
            feature.colorTransformation(ColorTransType.Greyscale, this.makeMask()));
    this.sepiaPreviewButton.addActionListener(act ->
            feature.colorTransformation(ColorTransType.Sepia, this.makeMask()));
  }

  /**
   * A private helper function to create a 2D Pixel array
   * that represents a "mask" for the preview panel.
   *
   * @return a mask in a 2D array of pixels
   */
  private IPixel[][] makeMask() {
    int imageHeight = this.fileLoadDisplay.getHeight();
    int imageWidth = this.fileLoadDisplay.getWidth();
    int xPos = this.previewScrollPane.getViewport().getViewPosition().x;
    int yPos = this.previewScrollPane.getViewport().getViewPosition().y;

    IPixel[][] mask = new Pixel[imageHeight][imageWidth];
    for (int i = 0; i < imageHeight; i++) {
      for (int j = 0; j < imageWidth; j++) {
        if ((yPos <= i && i <= yPos + 200) && (xPos <= j && j <= xPos + 200)) {
          mask[i][j] = new Pixel(0,0,0);
        } else {
          mask[i][j] = new Pixel(255,255,255);
        }
      }
    }
    return mask;
  }

  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(
            this, message);
  }

  /**
   * A private helper function to generate an image of a
   * histogram. Adapted the idea from Lab07 for using {@link Graphics2D}
   * to generate the graph image. Produces four different graphs
   * (R, G, B, Intensity) which are overlapped.
   *
   * @param imageToLoad the image to make a histogram (in String)
   * @param realImage   the image to make a histogram (in BufferedImage)
   * @return the completed histogram image
   */
  private Image histogramImage(String imageToLoad, Image realImage) {
    BufferedImage imageForHistogram = (BufferedImage) realImage;
    IPixel[][] pixels = new IPixel[0][];
    int pixTotal;

    if (imageForHistogram == null) {
      // if PPM
      if (imageToLoad.endsWith("ppm")) {
        model.Image ppmImage = Utils.ppmToImage(imageToLoad, "image");
        imageForHistogram = Utils.toBufferedImage(ppmImage, imageToLoad);
        pixels = ppmImage.getPixels();
      } else {
        try {
          model.Image otherImage = Utils.othersToImage(imageToLoad, "image");
          imageForHistogram = ImageIO.read(new FileInputStream(imageToLoad));
          pixels = otherImage.getPixels();
        } catch (IOException e) {
          this.renderMessage("There has been an error while creating a histogram image.");
        }
      }
    } else {
      pixels = new Pixel[imageForHistogram.getHeight()][imageForHistogram.getWidth()];
      for (int i = 0; i < imageForHistogram.getHeight(); i++) {
        for (int j = 0; j < imageForHistogram.getWidth(); j++) {
          int red = new Color(imageForHistogram.getRGB(j, i)).getRed();
          int green = new Color(imageForHistogram.getRGB(j, i)).getGreen();
          int blue = new Color(imageForHistogram.getRGB(j, i)).getBlue();
          pixels[i][j] = new Pixel(red, green, blue);
        }
      }
    }
    pixTotal = imageForHistogram.getHeight() * imageForHistogram.getWidth();
    Map<Integer, Integer> rChannel = new HashMap<>();
    Map<Integer, Integer> gChannel = new HashMap<>();
    Map<Integer, Integer> bChannel = new HashMap<>();
    Map<Integer, Integer> iChannel = new HashMap<>();

    for (IPixel[] pix : pixels) {
      for (IPixel p : pix) {
        rChannel.put(p.getRed(), rChannel.getOrDefault(p.getRed(), 0) + 1);
        gChannel.put(p.getGreen(), gChannel.getOrDefault(p.getGreen(), 0) + 1);
        bChannel.put(p.getBlue(), bChannel.getOrDefault(p.getBlue(), 0) + 1);
        int intensity = (p.getRed() + p.getGreen() + p.getBlue()) / 3;
        iChannel.put(intensity, iChannel.getOrDefault(intensity, 0) + 1);
      }
    }
    Image histogramImage = new BufferedImage(256, 100, BufferedImage.TYPE_INT_RGB);
    Graphics g2d = histogramImage.getGraphics();
    g2d.setColor(new Color(255, 255, 255));
    g2d.fillRect(0, 0, 256, 100);

    for (int i = 0; i < 256; i++) {
      // intensity
      g2d.setColor(new Color(100, 100, 100, 70));
      int inInt = iChannel.getOrDefault(i, 0);
      for (int in = inInt / (pixTotal / 5000); in > 0; in--) {
        g2d.fillRect(i, 100 - in, 1, 1);
      }
      // red
      g2d.setColor(new Color(255, 0, 0, 70));
      int redInt = rChannel.getOrDefault(i, 0);
      for (int r = redInt / (pixTotal / 5000); r > 0; r--) {
        g2d.fillRect(i, 100 - r, 1, 1);
      }
      // green
      g2d.setColor(new Color(0, 255, 0, 70));
      int greenInt = gChannel.getOrDefault(i, 0);
      for (int gr = greenInt / (pixTotal / 5000); gr > 0; gr--) {
        g2d.fillRect(i, 100 - gr, 1, 1);
      }
      // blue
      g2d.setColor(new Color(0, 0, 255, 70));
      int blueInt = bChannel.getOrDefault(i, 0);
      for (int b = blueInt / (pixTotal / 5000); b > 0; b--) {
        g2d.fillRect(i, 100 - b, 1, 1);
      }
    }
    return histogramImage;
  }
}