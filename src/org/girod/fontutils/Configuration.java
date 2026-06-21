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
package org.girod.fontutils;

import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.PropertyResourceBundle;
import org.mdiutil.swing.ExtensionFileFilter;

/**
 * The unique configuration.
 *
 * @since 0.4
 */
public class Configuration {
   private static Configuration conf = null;
   public float dotsperInch = dotsperInch = Toolkit.getDefaultToolkit().getScreenResolution();
   public FontRenderContext ctx = new FontRenderContext(new AffineTransform(), true, true);
   public final ExtensionFileFilter fontfilter;
   public final ExtensionFileFilter htmlfilter;
   private String version;
   private String date;

   private Configuration() {    
      URL url = this.getClass().getResource("fontutils.properties");
      try {
         InputStream stream = url.openStream();
         PropertyResourceBundle prb = new PropertyResourceBundle(stream);
         version = prb.getString("version");
         date = prb.getString("date");
      } catch (IOException ex) {
      }

      String[] ext = {"ttf", "otf"};
      fontfilter = new ExtensionFileFilter(ext, "Fonts");      
      
      String[] ext2 = {"html"};
      htmlfilter = new ExtensionFileFilter(ext2, "HTML Files");                   
   }

   public static Configuration getInstance() {
      if (conf == null) {
         conf = new Configuration();
      }
      return conf;
   }
   
   public String getVersion() {
      return version;
   }
   
   public String getDate() {
      return date;
   }   
}
