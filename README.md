# fontUtils
fontUtils is a small Java application utilities allowing to see the characteristics of TTF fonts.

# Where is it?
The home page for the fontUtils project can be found in the github project web site (https://github.com/hervegirod/ChangeLicenceTag).
There you also find information on how to download the latest release as well as all the other information you might need regarding
this project.

# Requirements
A Java 1.8 or later compatible virtual machine for your operating system.

# Licence
The fontUtils Library uses a BSD license for the source code.

# Interface
  The window presents :
  - The screen resolution in pixels per inch
  - The font size (in points) to specify (default is 1)
  - The text to use for the bounds evaluation (default is "A")
  - An overview of the resulting image with the shape bounding box

# Usage
  Open a TTF file. You will see the following font characteristics (all shown in points and mm):
  - Size : font size
  - Ascent : the Font ascent. The ascent is the distance from the baseline to the ascender line. 
    The ascent usually represents the height of the capital letters of the text. 
  - Descent : the Font descent. The descent is the distance from the baseline to the descender line. 
    The descent usually represents the distance to the bottom of lower case letters like 'p'.
  - Height : the Font height. The height is equal to the sum of the ascent, the descent and the leading.
  - Leading : the leading of the text. 
    The leading is the recommended distance from the bottom of the descender line to the top of the next line.
  - Advance : the advance of the text. 
    The advance is the distance from the leftmost point to the rightmost point on the character's baseline. 
  - Text Box : the computed text box width and height for the text (computed by default with the "A" text). Beware
    that this value take into account the advance
  - Text Box as Shape : compute the outline of the character (computed by default with the "A" text) 

  By default the values are shown with a font size of 1 point. Changing the Font size (in points) and then selecting refresh
  in the menu will update the computed values.

  Chanding the text value will update the computed Text Box width and height.

# Font characteristics
  An example with the "p" letter:

                            Advance
             <--------------------------------------> 
                                                     |
             PPPPPPPP      ^            ^            | next character
             P      P      |            |            |
             P      P      | Ascent     |            | 
             P      P      |            |            | 
             PPPPPPPP      V            |  Height    | 
             P             ^            |            | 
             P             | Descent    |            | 
             P             |            |            | 
             P             V            |            | 
                           ^            |            | 
                           | Leading    |            | 
                           V            V            | 
  --------------- top of next line -------------------

# History
- 0.1 (03/07/2020) : initial version
- 0.2 (08/01/2021)
  - Add a more precise computing of the width and height of each character
  - Add the outline presentation for the text

