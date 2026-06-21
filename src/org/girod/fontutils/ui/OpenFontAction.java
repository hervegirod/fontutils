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

import java.awt.Font;
import java.io.File;
import java.net.MalformedURLException;
import org.girod.fontutils.model.FontParams;
import org.mdi.app.swing.AbstractMDIApplication;
import org.mdi.bootstrap.MDIApplication;
import org.mdi.bootstrap.swing.AbstractMDIAction;
import org.mdi.bootstrap.swing.SwingFileProperties;
import org.mdiutil.io.FileUtilities;

/**
 * The Action that opens a font.
 *
 * @since 0.4
 */
public class OpenFontAction extends AbstractMDIAction {
   private String name;
   private final File file;
   private final Font font;
   private FontParams fontParams;

   /**
    * Constructor.
    *
    * @param app the Application
    * @param file the file to open
    */
   public OpenFontAction(MDIApplication app, File file, Font font) {
      super(app, "Open Font");
      this.setDescription("Open Font", "Open Font");
      this.file = file;
      this.font = font;
      this.name = FileUtilities.getFileNameBody(file);
   }

   @Override
   public void run() throws Exception {
      fontParams = new FontParams();
      fontParams.font = font;
   }

   @Override
   public void endAction() {
         AbstractMDIApplication mdi = (AbstractMDIApplication) app;
         if (mdi.hasTab(name)) {
            int i = 1;
            String postName = null;
            while (true) {
               postName = name + "_" + i;
               if (!mdi.hasTab(postName)) {
                  break;
               }
            }
            name = postName;
         }      
         FontUIComponent uiComponent = new FontUIComponent(mdi.getApplicationWindow(), fontParams);
         FontPanel fontPanel = new FontPanel(uiComponent);
         fontPanel.setup();
         SwingFileProperties prop = new SwingFileProperties(name, fontPanel, fontParams);
         try {
            prop.setURL(file.toURI().toURL());
         } catch (MalformedURLException ex) {
         }
         ((AbstractMDIApplication) app).addTab(fontPanel, prop);         
   }
}
