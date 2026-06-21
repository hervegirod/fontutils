/*
Copyright (c) 2021, 2026 Herve Girod
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
package org.girod.fontutils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import org.girod.fontutils.ui.FontPanel;
import org.girod.fontutils.ui.FontUIComponent;
import org.girod.fontutils.ui.OpenFontAction;
import org.girod.fontutils.ui.SaveHTMLAction;
import org.girod.fontutils.model.FontParams;
import org.mdi.app.swing.AbstractMDIApplication;
import org.mdiutil.io.FileUtilities;

/**
 *
 * @version 0.4
 */
public class FontUtils extends AbstractMDIApplication {
   private final Configuration conf = Configuration.getInstance();

   public FontUtils() {
      super("FontUtils");
      this.hasClosableTab(true);
      String version = conf.getVersion();
      this.setTitle("Font Utilities " + version);
      this.setSize(600, 800);
      constructApp();
   }

   public static void main(String[] args) {
      FontUtils fontUtils = new FontUtils();
      fontUtils.setVisible(true);
   }

   private void constructApp() {
      mfactory = new MenuFactory(this);
      super.preparePanels(true, true, mfactory);
   }

   public void refresh() {
      FontPanel fontPanel = (FontPanel) getSelectedComponent();
      if (fontPanel != null) {
         fontPanel.updateParams();
      }
   }

   public void save(File file) {
      FontPanel fontPanel = (FontPanel) getSelectedComponent();
      if (fontPanel == null) {
         return;
      }
      FontParams fontParams = fontPanel.getFontParams();
      FontUIComponent uiComp = fontPanel.getFontUIComponent();
      SaveHTMLAction action = new SaveHTMLAction(this, file, fontParams, uiComp);      
      this.executeAction(action);
   }

   public void openFont(File file) {
      try {
         String ext = FileUtilities.getFileExtension(file);
         if (ext != null) {
            Font font = null;
            ext = ext.toLowerCase();
            if (ext.equals("ttf")) {
               font = Font.createFont(Font.TRUETYPE_FONT, file);
            } else if (ext.equals("otf")) {
               font = Font.createFont(Font.TYPE1_FONT, file);
            }
            this.executeAction(new OpenFontAction(this, file, font));
         }
      } catch (FontFormatException ex) {
         System.err.println("Font format not supported");
      } catch (IOException ex) {
         System.err.println("Could not open Font");
      }
   }
}
