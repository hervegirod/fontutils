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
package org.girod.fontutils.model;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import org.girod.fontutils.Configuration;
/**
 * utilities to compute the parameters for a Font.
 *
 * @since 0.4
 */
public class Utilities {
   private static final FontRenderContext CTX = new FontRenderContext(new AffineTransform(), true, true);
   
   private Utilities() {
   }

   public static void compute(Component comp, String text, FontParams fontParams, float size, Font font) {
      font = font.deriveFont(size);
      fontParams.font = font;
      
      LineMetrics metrics = font.getLineMetrics("TOTO", CTX);
      FontMetrics fontMetrics = comp.getFontMetrics(font);
      float _size = font.getSize2D();
      float ascent = metrics.getAscent();
      float descent = metrics.getDescent();
      float height = metrics.getHeight();
      float leading = metrics.getLeading();
      int advance = fontMetrics.charWidth('A');

      // use the real outline of the caracter, see https://stackoverflow.com/questions/19582502/how-to-get-the-correct-string-width-from-fontmetrics-in-java
      GlyphVector gv = font.createGlyphVector(CTX, text);
      Rectangle2D rec2 = gv.getOutline().getBounds2D();
      fontParams.textBox = rec2;
      fontParams.textBoxWidth = (float)rec2.getWidth();
      fontParams.textBoxHeight = (float)rec2.getHeight();
      fontParams.size = _size;
      fontParams.leading = leading;
      fontParams.advance = advance;
      fontParams.ascent = ascent;           
      fontParams.descent = descent;    
      fontParams.height = height;    
      fontParams.sizeMM = getMM(_size);
      fontParams.ascentMM = getMM(ascent); 
      fontParams.descentMM = getMM(descent);       
      fontParams.heightMM = getMM(fontParams.height);    
      fontParams.textBoxWidthMM = getMM(fontParams.textBoxWidth);
      fontParams.textBoxHeightMM = (float)getMM(fontParams.textBoxHeight);         
      fontParams.leadingMM = getMM(leading);
      fontParams.advanceMM = getMM(advance);
      fontParams.heightMM = getMM(height);
      fontParams.textBoxMM = getMM(fontParams.textBox);      
   }
   
   public static Rectangle2D getMM(Rectangle2D rec) {
      Rectangle2D recMM = new Rectangle2D.Float(getMM((float)rec.getX()), getMM((float)rec.getY()), getMM((float)rec.getWidth()), getMM((float)rec.getHeight()));
      return recMM;
   }
      
   public static float getMM(float point) {
      return point * 25.4f / Configuration.getInstance().dotsperInch;
   }

   public static String toString(Rectangle2D rec) {
      return (float) rec.getWidth() + " " + (float) rec.getHeight();
   }

   public static String toStringMM(Rectangle2D rec) {
      return getMM((float) rec.getWidth()) + " " + getMM((float) rec.getHeight());
   }   
}
