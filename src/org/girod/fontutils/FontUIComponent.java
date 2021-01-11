/*
Copyright (c) 2021 Herve Girod
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 *
 * @since 0.3
 */
public class FontUIComponent {
   private static final Font FONT_DIAGRAM = new Font("SansSerif", Font.PLAIN, 10);
   private static final BasicStroke SOLID = new BasicStroke(2f);
   private static final BasicStroke DOTTED = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 4 }, 0);
   private static final float UI_FONT_SIZE = 150;
   private Font diagramFont = null;
   private final FontRenderContext ctx;
   private Font font = null;
   private final FontParams fontParams;
   private final Component comp;
   
   public FontUIComponent(Component comp, FontRenderContext ctx, FontParams fontParams) {
      this.ctx = ctx;
      this.comp = comp;
      this.fontParams = fontParams;
   }
   
   private void setRenderingHints(Graphics2D g) {
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
      g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
   }

   private Shape getShapeOfText(Font font, String msg) {
      BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
      Graphics2D g = bi.createGraphics();
      setRenderingHints(g);

      FontRenderContext frc = g.getFontRenderContext();
      GlyphVector gv = font.createGlyphVector(frc, msg);

      return gv.getOutline();
   }

   private void drawShapeImpl(Graphics2D g, Shape shape, int xOff, int yOff) {
      AffineTransform move = AffineTransform.getTranslateInstance(xOff, yOff);
      shape = move.createTransformedShape(shape);
      g.draw(shape);
   }

   private Point2D getShapePosition(Shape shape, int w, int h) {
      Rectangle2D b = shape.getBounds2D();
      double xOff = -b.getX() + ((w - b.getWidth()) / 2d);
      double yOff = -b.getY() + ((h - b.getHeight()) / 2d);
      return new Point2D.Float((float) xOff, (float) yOff);
   }

   private void drawArrow(Graphics2D g, int x, int y, int length, String text, boolean isHorizontal) {
      int DELTA_X = 7;
      int DELTA_Y = 5;
      int DELTA_TEXT = 12;
      if (isHorizontal) {
         g.drawLine(x, y, x + length, y);
         int x0 = x;
         x = x + length;
         g.drawLine(x, y, x - DELTA_X, y - DELTA_Y);
         g.drawLine(x, y, x - DELTA_X, y + DELTA_Y);
         Rectangle2D rec = FONT_DIAGRAM.getStringBounds(text, ctx);
         g.setFont(FONT_DIAGRAM);
         float textLength = (float) rec.getWidth();
         float textXPos = x0;
         if (textLength < length) {
            textXPos = x0 + (length - textLength) / 2f;
         }
         float textYPos = y + DELTA_TEXT;
         g.drawString(text, textXPos, textYPos);
      } else {
         g.drawLine(x, y, x, y + length);
         int y0 = y;
         y = y + length;
         g.drawLine(x, y, x - DELTA_Y, y + DELTA_X);
         g.drawLine(x, y, x + DELTA_Y, y + DELTA_X);
         Rectangle2D rec = FONT_DIAGRAM.getStringBounds(text, ctx);
         g.setFont(FONT_DIAGRAM);
         float textLength = (float) rec.getWidth();
         float textXPos = x - DELTA_TEXT;
         float textYPos = y0 + (length - textLength) / 2f;
         AffineTransform tr = g.getTransform();
         g.rotate(Math.PI / 2f, textXPos, textYPos);
         g.drawString(text, textXPos, textYPos);
         g.setTransform(tr);
      }
   }

   private void drawShapeBorders(Graphics2D g, Shape shape, int w, int h) {
      g.setColor(Color.RED);
      g.setStroke(DOTTED);
      Rectangle2D rec = shape.getBounds2D();
      Point2D p = getShapePosition(shape, w, h);
      int x = (int) p.getX();
      int y = (int) p.getY();
      drawShapeImpl(g, rec, x, y);

      x = (int)rec.getMinX() + x;
      y = (int)rec.getMaxY() + y;
      g.setStroke(SOLID);
      
      // arrows
      // width and height
      String withMsg = getFloatValueMM("width: ", fontParams.widthMM); 
      drawArrow(g, x, y + 10, (int)rec.getWidth(), withMsg, true);
      String heightMsg = getFloatValueMM("height: ", fontParams.heightMM);       
      drawArrow(g, x - 10, y, -(int)rec.getHeight(), heightMsg, false);
      
      // leading
      String leadingMsg = getFloatValueMM("leading: ", fontParams.leadingMM); 
      float leading = diagramFont.getLineMetrics("A", ctx).getLeading();
      int leadingY = y -(int)rec.getHeight();      
      drawArrow(g, x - 30, leadingY, -(int)leading, leadingMsg, false);
      
      // advance
      FontMetrics fontMetrics = comp.getFontMetrics(diagramFont);
      String advanceMsg = getFloatValueMM("advance: ", fontParams.advanceMM); 
      float advance = fontMetrics.charWidth('A');    
      drawArrow(g, x, y + 30, (int)advance, advanceMsg, true);      
   }
   
   private String getFloatValueMM(String msg, float value) {
      return msg + String.format("%.3f", value);
   }

   private void drawShape(Graphics2D g, Shape shape, int w, int h) {
      g.setColor(Color.BLACK);
      g.setStroke(SOLID);
      Rectangle2D b = shape.getBounds2D();
      double xOff = -b.getX() + ((w - b.getWidth()) / 2d);
      double yOff = -b.getY() + ((h - b.getHeight()) / 2d);
      AffineTransform move = AffineTransform.getTranslateInstance(xOff, yOff);
      shape = move.createTransformedShape(shape);
      g.fill(shape);
   }

   public BufferedImage getImage(String text) {
      this.font = fontParams.font;
      diagramFont = font.deriveFont(UI_FONT_SIZE);
      Shape shape = getShapeOfText(diagramFont, text);
      Rectangle2D boundsRectangle = shape.getBounds2D();
      double w = boundsRectangle.getWidth();
      double h = boundsRectangle.getHeight();
      int wBig = (int) (w * 2.6);
      int hBig = (int) (h * 2.3);
      BufferedImage bi = new BufferedImage(wBig, hBig, BufferedImage.TYPE_INT_RGB);
      Graphics2D g = bi.createGraphics();
      setRenderingHints(g);
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, wBig, hBig);

      drawShape(g, shape, wBig, hBig);

      drawShapeBorders(g, shape, wBig, hBig);
      g.dispose();

      return bi;
   }   
}
