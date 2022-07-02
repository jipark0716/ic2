/*     */ package ic2.core.gui;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import ic2.core.GuiIC2;
/*     */ import ic2.core.util.Util;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ 
/*     */ 
/*     */ public class TextBox
/*     */   extends GuiElement<TextBox>
/*     */ {
/*     */   protected String text;
/*     */   protected boolean focused;
/*     */   protected int cursor;
/*     */   protected int cursorTick;
/*     */   protected int scrollOffset;
/*     */   protected int selectionEnd;
/*     */   protected int maxTextLength;
/*     */   
/*     */   public TextBox(GuiIC2<?> gui, int x, int y, int width, int height) {
/*  27 */     this(gui, x, y, width, height, "");
/*     */   }
/*     */   protected IEnableHandler enableHandler; protected Predicate<String> validator; protected ITextBoxWatcher watcher; protected final boolean drawBackground; protected static final int enabledColour = 14737632; protected static final int disabledColour = 7368816; protected static final int invalidColour = -3092272;
/*     */   public TextBox(GuiIC2<?> gui, int x, int y, int width, int height, String text) {
/*  31 */     this(gui, x, y, width, height, text, true);
/*     */   }
/*     */   
/*     */   public TextBox(GuiIC2<?> gui, int x, int y, int width, int height, boolean drawBackground) {
/*  35 */     this(gui, x, y, width, height, "", drawBackground);
/*     */   }
/*     */   
/*     */   public TextBox(GuiIC2<?> gui, int x, int y, int width, int height, String text, boolean drawBackground) {
/*  39 */     super(gui, x, y, width, height);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 523 */     this.maxTextLength = 32;
/*     */     
/* 525 */     this.validator = Predicates.alwaysTrue();
/*     */     this.text = text;
/*     */     this.drawBackground = drawBackground;
/*     */     this.selectionEnd = this.cursor = text.length();
/*     */   }
/*     */   
/*     */   public TextBox withTextEnableHandler(IEnableHandler enableHandler) {
/*     */     this.enableHandler = enableHandler;
/*     */     return this;
/*     */   }
/*     */   
/*     */   public TextBox withTextValidator(Predicate<String> validator) {
/*     */     this.validator = validator;
/*     */     return this;
/*     */   }
/*     */   
/*     */   public TextBox withTextWatcher(ITextBoxWatcher watcher) {
/*     */     this.watcher = watcher;
/*     */     return this;
/*     */   }
/*     */   
/*     */   public boolean willDraw() {
/*     */     return (this.enableHandler == null || this.enableHandler.isEnabled());
/*     */   }
/*     */   
/*     */   public void setFocused(boolean focused) {
/*     */     if (focused && !this.focused)
/*     */       this.cursorTick = 0; 
/*     */     this.focused = focused;
/*     */   }
/*     */   
/*     */   public boolean isFocused() {
/*     */     return this.focused;
/*     */   }
/*     */   
/*     */   public void setMaxTextLength(int length) {
/*     */     if (length >= 0)
/*     */       this.maxTextLength = length; 
/*     */   }
/*     */   
/*     */   public String getText() {
/*     */     return this.text;
/*     */   }
/*     */   
/*     */   public boolean setText(String text) {
/*     */     if (setText(text, true)) {
/*     */       setCursorPositionEnd();
/*     */       return true;
/*     */     } 
/*     */     return false;
/*     */   }
/*     */   
/*     */   public boolean setText(String text, boolean forceLength) {
/*     */     assert text != null;
/*     */     if (this.validator.apply(text) && (text.length() <= this.maxTextLength || forceLength)) {
/*     */       String old = this.text;
/*     */       this.text = (text.length() <= this.maxTextLength) ? text : text.substring(0, this.maxTextLength);
/*     */       if (this.watcher != null)
/*     */         this.watcher.onChanged(old, text); 
/*     */       return true;
/*     */     } 
/*     */     return false;
/*     */   }
/*     */   
/*     */   public void tick() {
/*     */     super.tick();
/*     */     this.cursorTick++;
/*     */   }
/*     */   
/*     */   public void drawBackground(int mouseX, int mouseY) {
/*     */     super.drawBackground(mouseX, mouseY);
/*     */     if (this.drawBackground) {
/*     */       this.gui.drawColoredRect(this.x - 1, this.y - 1, this.width + 2, this.height + 2, -6250336);
/*     */       this.gui.drawColoredRect(this.x, this.y, this.width, this.height, -16777216);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawForeground(int mouseX, int mouseY) {
/*     */     super.drawForeground(mouseX, mouseY);
/*     */     int colour = willDraw() ? 14737632 : 7368816;
/*     */     int textOffset = this.cursor - this.scrollOffset;
/*     */     int selectionOffset = this.selectionEnd - this.scrollOffset;
/*     */     String text = this.gui.trimStringToWidth(this.text.substring(this.scrollOffset), this.drawBackground ? (this.width - 8) : this.width);
/*     */     boolean validOffset = (textOffset >= 0 && textOffset <= text.length());
/*     */     int xStartPos = (this.drawBackground ? (this.x + 4) : this.x) - this.gui.getGuiLeft();
/*     */     int yPos = (this.drawBackground ? (this.y + (this.height - 8) / 2) : this.y) - this.gui.getGuiTop();
/*     */     int xPos = xStartPos;
/*     */     if (selectionOffset > text.length())
/*     */       selectionOffset = text.length(); 
/*     */     if (!text.isEmpty())
/*     */       xPos = this.gui.drawString(xStartPos, yPos, validOffset ? text.substring(0, textOffset) : text, colour, true); 
/*     */     boolean inStringOrFull = (this.cursor < this.text.length() || this.text.length() >= this.maxTextLength);
/*     */     int xCursorPos = xPos;
/*     */     if (!validOffset) {
/*     */       xCursorPos = (textOffset > 0) ? (xStartPos + this.width) : xStartPos;
/*     */     } else if (inStringOrFull) {
/*     */       xCursorPos = xPos - 1;
/*     */       xPos--;
/*     */     } 
/*     */     if (!text.isEmpty() && validOffset && textOffset < text.length())
/*     */       xPos = this.gui.drawString(xPos, yPos, text.substring(textOffset), colour, true); 
/*     */     if (this.focused && this.cursorTick / 6 % 2 == 0 && validOffset)
/*     */       if (inStringOrFull) {
/*     */         this.gui.drawColoredRect(xCursorPos, yPos - 1, 1, 10, -3092272);
/*     */       } else {
/*     */         this.gui.drawString(xCursorPos, yPos, "_", colour, true);
/*     */       }  
/*     */     if (selectionOffset != textOffset) {
/*     */       int selectionEnd = xStartPos + this.gui.getStringWidth(text.substring(0, selectionOffset));
/*     */       drawHighlightedArea(xCursorPos, yPos - 1, selectionEnd - 1, yPos + 1 + 8);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawHighlightedArea(int startX, int startY, int endX, int endY) {
/*     */     if (startX < endX) {
/*     */       int temp = startX;
/*     */       startX = endX;
/*     */       endX = temp;
/*     */     } 
/*     */     if (startY < endY) {
/*     */       int temp = startY;
/*     */       startY = endY;
/*     */       endY = temp;
/*     */     } 
/*     */     startX += this.gui.getGuiLeft();
/*     */     endX += this.gui.getGuiLeft();
/*     */     startY += this.gui.getGuiTop();
/*     */     endY += this.gui.getGuiTop();
/*     */     if (endX > this.x + this.width)
/*     */       endX = this.x + this.width; 
/*     */     if (startX > this.x + this.width)
/*     */       startX = this.x + this.width; 
/*     */     Tessellator tessellator = Tessellator.func_178181_a();
/*     */     BufferBuilder vertexbuffer = tessellator.func_178180_c();
/*     */     GlStateManager.func_179131_c(0.0F, 0.0F, 255.0F, 255.0F);
/*     */     GlStateManager.func_179090_x();
/*     */     GlStateManager.func_179115_u();
/*     */     GlStateManager.func_187422_a(GlStateManager.LogicOp.OR_REVERSE);
/*     */     vertexbuffer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/*     */     vertexbuffer.func_181662_b(startX, endY, 0.0D).func_181675_d();
/*     */     vertexbuffer.func_181662_b(endX, endY, 0.0D).func_181675_d();
/*     */     vertexbuffer.func_181662_b(endX, startY, 0.0D).func_181675_d();
/*     */     vertexbuffer.func_181662_b(startX, startY, 0.0D).func_181675_d();
/*     */     tessellator.func_78381_a();
/*     */     GlStateManager.func_179134_v();
/*     */     GlStateManager.func_179098_w();
/*     */   }
/*     */   
/*     */   public boolean onMouseClick(int mouseX, int mouseY, MouseButton button, boolean onThis) {
/*     */     setFocused(onThis);
/*     */     if (this.focused && onThis && MouseButton.left == button) {
/*     */       int end = mouseX - this.x;
/*     */       if (this.drawBackground)
/*     */         end -= 4; 
/*     */       String text = this.gui.trimStringToWidth(this.text.substring(this.scrollOffset), this.drawBackground ? (this.width - 8) : this.width);
/*     */       setCursorPosition(this.gui.trimStringToWidth(text, end).length() + this.scrollOffset);
/*     */     } 
/*     */     return onThis;
/*     */   }
/*     */   
/*     */   public boolean onKeyTyped(char typedChar, int keyCode) {
/*     */     if (!this.focused)
/*     */       return super.onKeyTyped(typedChar, keyCode); 
/*     */     if (GuiScreen.func_175278_g(keyCode)) {
/*     */       setCursorPositionEnd();
/*     */       setSelectionPos(0);
/*     */     } else if (GuiScreen.func_175280_f(keyCode)) {
/*     */       GuiScreen.func_146275_d(getSelectedText());
/*     */     } else if (GuiScreen.func_175279_e(keyCode)) {
/*     */       if (willDraw())
/*     */         writeText(GuiScreen.func_146277_j()); 
/*     */     } else if (GuiScreen.func_175277_d(keyCode)) {
/*     */       GuiScreen.func_146275_d(getSelectedText());
/*     */       if (willDraw())
/*     */         writeText(""); 
/*     */     } else {
/*     */       switch (keyCode) {
/*     */         case 14:
/*     */           if (GuiScreen.func_146271_m()) {
/*     */             if (willDraw())
/*     */               deleteWords(-1); 
/*     */           } else if (willDraw()) {
/*     */             deleteFromCursor(-1);
/*     */           } 
/*     */           return true;
/*     */         case 199:
/*     */           if (GuiScreen.func_146272_n()) {
/*     */             setSelectionPos(0);
/*     */           } else {
/*     */             setCursorPositionStart();
/*     */           } 
/*     */           return true;
/*     */         case 203:
/*     */           if (GuiScreen.func_146272_n()) {
/*     */             if (GuiScreen.func_146271_m()) {
/*     */               setSelectionPos(getNthWordFromPos(-1, this.selectionEnd));
/*     */             } else {
/*     */               setSelectionPos(this.selectionEnd - 1);
/*     */             } 
/*     */           } else if (GuiScreen.func_146271_m()) {
/*     */             setCursorPosition(getNthWordFromCursor(-1));
/*     */           } else {
/*     */             moveCursorBy(-1);
/*     */           } 
/*     */           return true;
/*     */         case 205:
/*     */           if (GuiScreen.func_146272_n()) {
/*     */             if (GuiScreen.func_146271_m()) {
/*     */               setSelectionPos(getNthWordFromPos(1, this.selectionEnd));
/*     */             } else {
/*     */               setSelectionPos(this.selectionEnd + 1);
/*     */             } 
/*     */           } else if (GuiScreen.func_146271_m()) {
/*     */             setCursorPosition(getNthWordFromCursor(1));
/*     */           } else {
/*     */             moveCursorBy(1);
/*     */           } 
/*     */           return true;
/*     */         case 207:
/*     */           if (GuiScreen.func_146272_n()) {
/*     */             setSelectionPos(this.text.length());
/*     */           } else {
/*     */             setCursorPositionEnd();
/*     */           } 
/*     */           return true;
/*     */         case 211:
/*     */           if (GuiScreen.func_146271_m()) {
/*     */             if (willDraw())
/*     */               deleteWords(1); 
/*     */           } else if (willDraw()) {
/*     */             deleteFromCursor(1);
/*     */           } 
/*     */           return true;
/*     */       } 
/*     */       if (ChatAllowedCharacters.func_71566_a(typedChar) && willDraw()) {
/*     */         writeText(String.valueOf(typedChar));
/*     */       } else {
/*     */         return super.onKeyTyped(typedChar, keyCode);
/*     */       } 
/*     */     } 
/*     */     return true;
/*     */   }
/*     */   
/*     */   public void writeText(String textToWrite) {
/*     */     int extraLength;
/*     */     StringBuilder newText = new StringBuilder();
/*     */     String cleanString = ChatAllowedCharacters.func_71565_a(textToWrite);
/*     */     int start = Math.min(this.cursor, this.selectionEnd);
/*     */     int end = Math.max(this.cursor, this.selectionEnd);
/*     */     int insertionPoint = this.maxTextLength - this.text.length() - start - end;
/*     */     if (!this.text.isEmpty())
/*     */       newText.append(this.text.substring(0, start)); 
/*     */     if (insertionPoint < cleanString.length()) {
/*     */       newText.append(cleanString.substring(0, insertionPoint));
/*     */       extraLength = insertionPoint;
/*     */     } else {
/*     */       newText.append(cleanString);
/*     */       extraLength = cleanString.length();
/*     */     } 
/*     */     if (!this.text.isEmpty() && end < this.text.length())
/*     */       newText.append(this.text.substring(end)); 
/*     */     if (setText(newText.toString(), true))
/*     */       moveCursorBy(start - this.selectionEnd + extraLength); 
/*     */   }
/*     */   
/*     */   public void deleteWords(int num) {
/*     */     if (!this.text.isEmpty())
/*     */       if (this.selectionEnd != this.cursor) {
/*     */         writeText("");
/*     */       } else {
/*     */         deleteFromCursor(getNthWordFromCursor(num) - this.cursor);
/*     */       }  
/*     */   }
/*     */   
/*     */   public void deleteFromCursor(int num) {
/*     */     if (!this.text.isEmpty())
/*     */       if (this.selectionEnd != this.cursor) {
/*     */         writeText("");
/*     */       } else {
/*     */         int start, end;
/*     */         if (num < 0) {
/*     */           start = this.cursor;
/*     */           end = this.cursor + num;
/*     */         } else {
/*     */           start = this.cursor + num;
/*     */           end = this.cursor;
/*     */         } 
/*     */         StringBuilder newText = new StringBuilder();
/*     */         if (end >= 0)
/*     */           newText.append(this.text.substring(0, end)); 
/*     */         if (start < this.text.length())
/*     */           newText.append(this.text.substring(start)); 
/*     */         if (this.validator.apply(newText.toString())) {
/*     */           String old = this.text;
/*     */           this.text = newText.toString();
/*     */           if (this.watcher != null)
/*     */             this.watcher.onChanged(old, this.text); 
/*     */           if (num < 0)
/*     */             moveCursorBy(num); 
/*     */         } 
/*     */       }  
/*     */   }
/*     */   
/*     */   protected int getNthWordFromCursor(int numWords) {
/*     */     return getNthWordFromPos(numWords, this.cursor);
/*     */   }
/*     */   
/*     */   protected int getNthWordFromPos(int numWords, int position) {
/*     */     return getNthWordFromPosWS(numWords, position, true);
/*     */   }
/*     */   
/*     */   protected int getNthWordFromPosWS(int numWords, int position, boolean skipWs) {
/*     */     boolean positive = (numWords >= 0);
/*     */     for (int k = 0, absN = Math.abs(numWords); k < absN; k++) {
/*     */       if (positive) {
/*     */         int end = this.text.length();
/*     */         position = this.text.indexOf(' ', position);
/*     */         if (position == -1) {
/*     */           position = end;
/*     */         } else {
/*     */           while (skipWs && position < end && this.text.charAt(position) == ' ')
/*     */             position++; 
/*     */         } 
/*     */       } else {
/*     */         while (skipWs && position > 0 && this.text.charAt(position - 1) == ' ')
/*     */           position--; 
/*     */         while (position > 0 && this.text.charAt(position - 1) != ' ')
/*     */           position--; 
/*     */       } 
/*     */     } 
/*     */     return position;
/*     */   }
/*     */   
/*     */   public String getSelectedText() {
/*     */     return this.text.substring(Math.min(this.cursor, this.selectionEnd), Math.max(this.cursor, this.selectionEnd));
/*     */   }
/*     */   
/*     */   protected void setCursorPositionStart() {
/*     */     setCursorPosition(0);
/*     */   }
/*     */   
/*     */   protected void setCursorPositionEnd() {
/*     */     setCursorPosition(this.text.length());
/*     */   }
/*     */   
/*     */   protected void moveCursorBy(int num) {
/*     */     setCursorPosition(this.selectionEnd + num);
/*     */   }
/*     */   
/*     */   protected void setCursorPosition(int position) {
/*     */     this.cursor = Util.limit(position, 0, this.text.length());
/*     */     setSelectionPos(this.cursor);
/*     */   }
/*     */   
/*     */   protected void setSelectionPos(int position) {
/*     */     int textLength = this.text.length();
/*     */     position = Util.limit(position, 0, textLength);
/*     */     this.selectionEnd = position;
/*     */     if (this.scrollOffset > textLength)
/*     */       this.scrollOffset = textLength; 
/*     */     int width = this.drawBackground ? (this.width - 8) : this.width;
/*     */     int maxPosition = this.gui.trimStringToWidth(this.text.substring(this.scrollOffset), width).length() + this.scrollOffset;
/*     */     if (position == this.scrollOffset)
/*     */       this.scrollOffset -= this.gui.trimStringToWidthReverse(this.text, width).length(); 
/*     */     if (position > maxPosition) {
/*     */       this.scrollOffset += position - maxPosition;
/*     */     } else if (position <= this.scrollOffset) {
/*     */       this.scrollOffset -= this.scrollOffset - position;
/*     */     } 
/*     */     this.scrollOffset = Util.limit(this.scrollOffset, 0, textLength);
/*     */   }
/*     */   
/*     */   public static interface ITextBoxWatcher {
/*     */     void onChanged(String param1String1, String param1String2);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\TextBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */