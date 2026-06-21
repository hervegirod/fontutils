/*
Copyright (c) 2026 Herve Girod
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies,
either expressed or implied, of the FreeBSD Project.

Alternatively if you have any questions about this project, you can visit
the project website at the project page on https://github.com/hervegirod/FontUtils
 */
package org.girod.fontutils.ui;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.girod.fontutils.Configuration;
import org.girod.fontutils.model.FontParams;
import org.girod.fontutils.model.Utilities;
import org.mdi.bootstrap.MDIApplication;
import org.mdi.bootstrap.swing.AbstractMDIAction;
import org.mdiutil.io.FileUtilities;

/**
 * The Action that saves the characteristics of a Font in an html file.
 *
 * @since 0.4
 */
public class SaveHTMLAction extends AbstractMDIAction {
   private final File file;
   private final File resourceDir;
   private final File imgFile;
   private final FontUIComponent uiComp;
   private final FontParams fontParams;

   /**
    * Constructor.
    *
    * @param app the Application
    * @param file the file to open
    * @param fontParams the font parameters
    * @param uiComp the UI component
    */
   public SaveHTMLAction(MDIApplication app, File file, FontParams fontParams, FontUIComponent uiComp) {
      super(app, "Save");
      this.setDescription("Save", "Save");
      this.file = file;
      String body = FileUtilities.getFileNameBody(file);
      this.resourceDir = new File(file.getParentFile(), body + " - files");
      this.imgFile = new File(resourceDir, body + ".png");
      this.fontParams = fontParams;
      this.uiComp = uiComp;
   }

   private void writeTH(BufferedWriter writer, String name) throws IOException {
      writer.append("<th style=\"border: 2px solid black\">" + name + "</th>");
      writer.newLine();
   }
   
   private void writeTD(BufferedWriter writer, String name) throws IOException {
      writer.append("<td style=\"border: 1px solid black\">" + name + "</td>");
      writer.newLine();
   }   
   
   private void writeTD(BufferedWriter writer, Number value) throws IOException {
      writer.append("<td style=\"border: 1px solid black\">" + value + "</td>");
      writer.newLine();
   }     

   @Override
   public void run() throws Exception {
      if (! resourceDir.exists()) {
         resourceDir.mkdir();
      }      
      writeImage();
      try ( BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
         writer.append("<html>");
         writer.newLine();
         writer.append("<body>");
         writer.newLine();
         writeTable(writer);
         writeHTMLImage(writer);
         writer.append("</body>");
         writer.newLine();
         writer.append("</html>");
         writer.newLine();
         writer.flush();
      }
   }
   
   private void writeImage() throws IOException {
      BufferedImage img = uiComp.getImage(fontParams.text);
      ImageIO.write(img, "png", imgFile);
   }
   
   private void writeHTMLImage(BufferedWriter writer) throws IOException {
      String relativePath = FileUtilities.getRelativePath(file.getParentFile(), imgFile);
      writer.append("<img src=\"" + relativePath + "\">");
      writer.newLine();
   }
   
   private void writeTable(BufferedWriter writer) throws IOException {
         writer.append("<table style=\"border: 2px solid black\">");
         writer.newLine();
         float resol = Configuration.getInstance().dotsperInch;
         writer.append("<caption>" + fontParams.font.getFontName() + " Resolution: " + resol + "</caption>");
         writer.newLine();
         writer.append("<thead style=\"background-color:gray\">");
         writer.newLine();
         writer.append("<tr>");
         writer.newLine();
         writeTH(writer, "Name");
         writeTH(writer, "Value (pts)");
         writeTH(writer, "Value (mm)");
         writer.append("</tr>");
         writer.newLine();
         writer.append("</thead>");
         writer.newLine();
         writer.append("<tbody>");
         writer.newLine();

         // Font size
         writer.append("<tr>");
         writer.newLine();
         writeTD(writer, "Font size");
         writeTD(writer, fontParams.font.getSize());
         writeTD(writer, "-");
         writer.append("</tr>");
         writer.newLine();

         // Text
         writer.append("<tr>");
         writer.newLine();
         writeTD(writer, "Text");
         writeTD(writer, fontParams.text);
         writeTD(writer, "-");
         writer.append("</tr>");
         writer.newLine();

         // Size
         writer.append("<tr>");
         writer.newLine();
         writeTD(writer, "Size");
         writeTD(writer, fontParams.size);
         writeTD(writer, fontParams.sizeMM);
         writer.append("</tr>");
         writer.newLine();

         // Ascent
         writer.append("<tr>");
         writer.newLine();
         writeTD(writer, "Ascent");
         writeTD(writer, fontParams.ascent);
         writeTD(writer, fontParams.ascentMM);         
         writer.append("</tr>");
         writer.newLine();

         // Descent
         writer.append("<tr>");
         writer.newLine();
         writeTD(writer, "Descent");
         writeTD(writer, fontParams.descent);
         writeTD(writer, fontParams.descentMM);           
         writer.append("</tr>");
         writer.newLine();

         // Height
         writer.append("<tr>");
         writer.newLine();
         writeTD(writer, "Height");
         writeTD(writer, fontParams.height);
         writeTD(writer, fontParams.heightMM);           
         writer.append("</tr>");
         writer.newLine();

         // Leading
         writer.append("<tr>");
         writer.newLine();
         writeTD(writer, "Leading");
         writeTD(writer, fontParams.leading);
         writeTD(writer, fontParams.leadingMM);            
         writer.append("</tr>");
         writer.newLine();

         // Advance
         writer.append("<tr>");
         writer.newLine();
         writeTD(writer, "Advance");
         writeTD(writer, fontParams.advance);
         writeTD(writer, fontParams.advanceMM);             
         writer.append("</tr>");
         writer.newLine();

         // Text Box
         writer.append("<tr>");
         writer.newLine();
         writeTD(writer, "Text Box");
         writeTD(writer, "[" + Utilities.toString(fontParams.textBox) + "]");
         writeTD(writer, "[" + Utilities.toString(fontParams.textBoxMM) + "]");           
         writer.append("</tr>");
         writer.newLine();

         writer.append("</tbody>");
         writer.newLine();
         writer.append("</table>");
         writer.newLine();      
   }

}
