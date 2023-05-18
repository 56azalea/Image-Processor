# Image Processor
### by Serin (Serena) Jeon & Dahye Jin

This is an image processor application, which allows the user to load an image, alter the image
display through applying different methods, and saves the altered image. These altercations do not
actually modify for the original image, but rather save them as separate files. The supported file
formats are PPM, JPG, PNG, and BMP. This project is built based on the MVC framework and supports
both text-based and interactive GUI(Graphical User Interface)-based user interfaces. Model,
Controller, and View are the three packages comprising the src folder. The test folder contains the
test classes for all the src classes.

## Model

Model contains five interfaces: IPixel, Image, ImageProcessorModelState, ImageProcessorModel, and
MaskProcessorModel.

IPixel is an interface that represents a pixel. Pixel is a class implementing this interface
which is able to extract out individual RGB values, brighten, and greyscale. For HW05, a method for
color transformation has been added in the Pixel class. The RGB value depends on the bit
representation of the value. Based on which bit system it is using, the maximum can be 255 or 1. For
former, (255,255,255) would represent white, while for latter, (1,1,1) would represent white.

Image is an interface that represents an image. An image is composed of pixels. Therefore,
the ImageImpl class, which implements Image interface, has a field of IPixel in a 2D array format.
A class implementing Image is able to get its width, height, maximum RGB value, pixels 2D array,
and a specific pixel. All of these are not return the actual field, since the fields must be
immutable by the users in any unintentional manner. Therefore, they all return the copies.

ImageProcessorModelState is an interface that works as the biggest, most general interface.
This interface is built apart from ImageProcessorModel interface, in order to separate out the
methods that do not alter the image alone. This is a result of taking Professor Vido's advice
from class on November 1st. This ModelState interface is also inspired by the ModelState interface
from Marble Solitaire project. This interface used to contain two enumerations: GreyscaleType and
FlipType. For HW05, however, it now contains four enumerations: GreyscaleType, FlipType,
FilteringType, and ColorTransType. GreyscaleType consists of six elements: Red, Green, Blue, Value,
Intensity, and Luma. These are the six methods to produce a greyscale image. FlipType consists of
two elements: Horizontal and Vertical. The idea of turning this into an enumeration was inspired by
the idea of Player enumeration back from Marble Solitaire project. FilteringType consists of two
elements: Blur and Sharpen. Filtering applies the filter on the image using kernels. ColorTransType
also consists of two elements: Greyscale and Sepia. This converts a color image into a greyscale
image using the luma-component or into a sepia-toned image. Apart from enumerations, this interface
also contains a method to get an image out from the image storage. The format of the image storage
is in map. This is because using a map allows to easily search for the image just with its key,
which in this case is a string name.

ImageProcessorModel is an interface that extends the ImageProcessorModelState interface and executes
image-altering methods. A class implementing this interface, ImageProcessorModelImpl, is able to
operate methods inside the ImageProcessorModelState interface. It can also add an image to the map
storage. Furthermore, it is in charge of flipping, brightening, blurring, and sharpening the image,
rendering greyscale using different components, converting a color image into a greyscale image or a
sepia-toned image, and downsizing an image. None of these methods modifies the actual pixels of the
images, but rather produces a new image and saves it with a new name into the map storage.

For HW08, a new interface called MaskProcessorModel has been added to support the ability of
applying any of the existing image manipulations to only part of an image. In order to prevent
editing every operation method we have made so far, which could cause a whole chaos in every part,
we rather made a new interface and extended the ImageProcessorModel interface. This is similar to
how TracingTurtleModel extends TurtleModel in the Command Design Pattern lecture.
MaskProcessorModelImpl is also a new class that has been added to implement the MaskProcessorModel
interface. In this class, there are four methods that support image manipulations of brighten,
filtering, color transformations, and component visualizations.

## Controller

Controller contains three interfaces: Command, Controller, and Features.

Command is an interface that handles the execution of different methods on an image. This is a
macro command, meaning it has only one method: execute. BulkCommand is a class that directly
implements this interface. This is a parent command, so every command would extend this class.
Therefore, all of them would have a String[] command line and would throw an illegal argument
exception if the command line or the model is null. Each method -- flip, brighten, greyscale,
filtering, color transformation, and downscale -- is represented as a separate class that extends
the parent command (BulkCommand). Based on the content of the command line, each command operates
differently.

Controller is an interface that operates as an image processor controller. ControllerImpl is a
class implementing this interface. The method inside the controllerImpl, runProcessor, runs the
controller by accepting an input and passing it to the image processor model. This method even
allows the user to quit an image processor application by entering a 'q' or 'Q' at any point. The
inputs for flip, greyscale, filter, and color transformation are 3, whereas for brighten are 4.
Therefore, when writing codes for runProcessor, a specific case for brighten is made where there are
4 inputs. Also, there are load and save methods, allowing the user to load and save an image from an
ASCII PPM file. For HW05, private helper functions, loadOther and saveOther, have been added. These
functions load and save image types other than ppm, which are png, jpg, and bmp because the
BufferedImage doesn't support a ppm format. In order to load and save an image specifically in a ppm
format, we created another private helper functions called loadPPM and savePPM. The idea was
inspired from the provided ImageUtil file back from HW 04. For HW06, there were some design changes
made. Private helper methods except for saveOther and savePPM have been removed; they have been
transferred to the Utils class from a new package called util, instead. saveOther is a private
helper function built to save other formats of images onto the computer device. On the other hand,
savePPM is a private helper function to save an image, specifically in a ppm format. This function
saves the current state of the image to the file directory. In addition, a transmit method has been
added to transmit a message to the view. For HW08, downscale and the mask feature has been added. As
a result, the code for runProcessor method has been newly implemented. The inputs for downscale are
5 and therefore a specific case for downscale is made where there are 5 inputs. Moreover, for every
case excluding load, save, and downscale, the else if statement adding one more input to the
original number of the inputs has been made for the mask feature.

Features is an interface that has been added for HW06. This is a controller interface specifically
built to support the GUI visualization. It stores information necessary to build the GUI, and thus
looks slightly different from the original controller interface. GUIController is a class
implementing the Features interface. This class has a number of methods, including load, save,
brighten, redComponent, greenComponent, blueComponent, valueComponent, intensityComponent,
lumaComponent, horizontalFlip, verticalFlip, blur, sharpen, greyscale, and sepia. All of these
methods executes a corresponding action to their names on the image that had been loaded in the GUI.
For HW08, a new method for downscale has been added. The mask feature has also been added to every
method to support the ability of applying any of the existing image manipulations to only part of an
image.

For HW06, a new package called util has been added.

## Util

Util contains one class: Utils.

Utils is a utility class built to reduce overlaps of codes between
different parts of the project such as image processing of ppm and BufferedImage. Here, we have
three methods: ppmToImage, othersToImage, and toBufferedImage. ppmToImage is a utility
that generates an image of a ppm file using its path and name. This is adapted from the provided
ImageUtil and used in multiple locations, including ControllerImpl and GUIController. On the other
hand, othersToImage is a utility that generates an image of an image file other than ppm, which are
png, jpg, and bmp using its path and name. This is also used in ControllerImpl and GUIController.
toBufferedImage is a utility to create a bufferedImage from an image object. It uses its individual
pixels to produce an image.

For HW06, an implementation of the view has been added.

## View

View contains two interfaces: ImageProcessorView and ImageProcessorGUIView.

ImageProcessorView is an interface allowing the user to view outputs produced an image processor.
ImageProcessorTextView is a class implementing this interface. There is one method inside this
class, which is a renderMessage. This method displays a message when the user must be let know of
any circumstances.

ImageProcessorGUIView is an interface that extends the ImageProcessorView interface. This interface
visualizes an image processor in the GUI system. ImageProcessorGUIViewImpl is a class directly
implementing this interface. The method inside this class, addFeatures, accepts an object of the
features interface from the controller package. Another method is called refresh which refreshes the
screen anytime it needs to be doing so. This is called when the something on the screen is updated,
and therefore must be redrawn. histogramImage is a private helper function to generate an image of a
histogram. This idea was adapted from Lab07 for using Graphics2D to generate the graph image. It
produces four different overlapped graphs: R, G, B, Intensity. For HW08, the constructor of the
ImageProcessorGUIViewImpl has been slightly changed to support a preview mode and expose this
functionality in the GUI.

## Main

Main is a class that basically runs the image processor application. It takes in a command line
argument to allow the performance of the image processor. The method inside the main class is also
called main. The main method used to just construct a controller that takes in System.in as the
input. This accepts a command line argument in a list of strings format and reads the argument from
System.in to determine what to demonstrate in System.out. In order to run the application using
main, type the following script command (The part of the line after the '#' symbol are comments
shown only for illustration):

#load house.ppm and call it "house"

`load res/house.ppm house`

#brighten house by adding 10

`brighten house 10 house-brighten`

#flip house vertically

`vertical-flip house house-vertical`

#flip the vertically flipped house horizontally

`horizontal-flip house-vertical house-vertical-horizontal`

#create a greyscale using only the value component, as an image house-value

`value-component house house-value`

#save house-brighten

`save res/house-brighten.ppm house-brighten`

#save house-value

`save res/house-value.ppm house-value`

For HW05, a condition has been added. Now, there are two ways to use the main class. If its
configuration takes in an empty program, just like how it used to work in HW04, it will take in
System.in as an input. However, a new feature allows to take in a script.txt file. The second
condition is made so that the script file can run on jar file. If the configuration takes in
'-file res/script.txt' as a program argument, and the main class is run, the file reader would read
the script-txt file inside res folder and use that as the readable input. Because the script file
is specifically built for the res folder, in actuality, house.ppm wouldn't be loaded. However,
the purpose of the second condition is exclusively for jar file, so that's appropriate.

For HW08, there has been a small modification in the code to add the mask feature when running the
program. At line 33, we changed the ImageProcessorModel to MaskProcessorModel and made it implement
new MaskProcessorModelImpl() instead of ImageProcessorModelImpl().

## Image Citation
The house.ppm file was obtained from here:
https://www.cs.cornell.edu/courses/cs664/2003fa/images/.
The webpage source for this ppm file was found through searching "ppm image download" on Google
search engine. The contributors of this project had resized the file originally called "house_1.ppm"
into 2 x 2, using Preview app on Mac and changed its name to house.ppm.
For HW05, we obtained another image that is in jpg format.
The dog.jpg and the doggie.jpg files were obtained from here:
https://commons.wikimedia.org/wiki/File:Dog_Breeds.jpg.
The webpage source for this jpg file was found through searching "dog" on Google search engine.
We chose this image because it has a free license to share and remix. The contributors of this
project had resized this file into 100 x 75, using Preview app on Mac and changed its name to
doggy.jpg.
