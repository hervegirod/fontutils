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
package org.girod.fontutils.model;

import java.awt.Font;
import java.awt.geom.Rectangle2D;

/**
 * The parameters for one Font.
 *
 * @version 0.4
 */
public class FontParams {
   /**
    * The Font.
    */
   public Font font = new Font("Dialog", Font.PLAIN, 1);
   /**
    * The text used to compute the sizes.
    */ 
   public String text = "A";
   /**
    * The Font size in points.
    */         
   public float size = 0f;      
   /**
    * The Font height in points.
    */   
   public float height = 0f;
   /**
    * The Font leading in points.
    */      
   public float leading = 0f;
   /**
    * The Font ascent in points.
    */         
   public float ascent = 0f;   
   /**
    * The Font descent in points.
    */         
   public float descent = 0f;      
   /**
    * The Font advance in points.
    */         
   public float advance = 0f;
   /**
    * The Font text box width in points.
    */            
   public float textBoxWidth = 0f;      
   /**
    * The Font text box height in points.
    */            
   public float textBoxHeight = 0f;   
   /**
    * The Font text box in points.
    */            
   public Rectangle2D textBox = null;
   /**
    * The Font size in mm.
    */         
   public float sizeMM = 0f;     
   /**
    * The Font height in mm.
    */      
   public float heightMM = 0f;
   /**
    * The Font leading in mm.
    */         
   public float leadingMM = 0f;
   /**
    * The Font ascent in mm.
    */         
   public float ascentMM = 0f;   
   /**
    * The Font descent in mm.
    */         
   public float descentMM = 0f;      
   /**
    * The Font advance in mm.
    */        
   public float advanceMM = 0f;
   /**
    * The Font text box in mm.
    */            
   public Rectangle2D textBoxMM = null;   
   /**
    * The Font text box width in mm.
    */            
   public float textBoxWidthMM = 0f;      
   /**
    * The Font text box height in mm.
    */            
   public float textBoxHeightMM = 0f;      
}
