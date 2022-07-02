/*     */ package ic2.core.util;
/*     */ import ic2.core.IC2;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.CharacterCodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CoderResult;
/*     */ import org.apache.logging.log4j.Level;
/*     */ 
/*     */ class LogOutputStream extends OutputStream {
/*     */   private final Log log;
/*     */   private final LogCategory category;
/*     */   private final Level level;
/*     */   private final CharsetDecoder decoder;
/*     */   
/*     */   LogOutputStream(Log log, LogCategory category, Level level) {
/*  18 */     this.log = log;
/*  19 */     this.category = category;
/*  20 */     this.level = level;
/*  21 */     this.decoder = Charset.defaultCharset().newDecoder();
/*  22 */     this.inputBuffer = ByteBuffer.allocate(128);
/*  23 */     this.outputBuffer = CharBuffer.allocate(128);
/*  24 */     this.output = new StringBuilder();
/*     */   }
/*     */   private final ByteBuffer inputBuffer; private final CharBuffer outputBuffer; private final StringBuilder output; private boolean ignoreNextNewLine;
/*     */   
/*     */   public void write(int b) throws IOException {
/*  29 */     this.inputBuffer.put((byte)b);
/*  30 */     runDecoder();
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/*  35 */     while (len > 0) {
/*  36 */       int amount = Math.min(len, this.inputBuffer.remaining());
/*     */       
/*  38 */       this.inputBuffer.put(b, off, amount);
/*  39 */       off += amount;
/*  40 */       len -= amount;
/*     */       
/*  42 */       runDecoder();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/*  48 */     runDecoder();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  53 */     flush();
/*     */     
/*  55 */     if (this.output.length() > 0) {
/*  56 */       this.log.log(this.category, this.level, this.output.toString());
/*  57 */       this.output.setLength(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  63 */     if (this.inputBuffer.position() > 0) {
/*  64 */       IC2.log.warn(LogCategory.General, "LogOutputStream unclosed.");
/*  65 */       close();
/*     */     } 
/*     */   }
/*     */   private void runDecoder() {
/*     */     CoderResult result;
/*  70 */     this.inputBuffer.flip();
/*     */ 
/*     */     
/*     */     do {
/*  74 */       result = this.decoder.decode(this.inputBuffer, this.outputBuffer, false);
/*     */       
/*  76 */       if (result.isError()) {
/*     */         try {
/*  78 */           result.throwException();
/*  79 */         } catch (CharacterCodingException e) {
/*  80 */           throw new RuntimeException(e);
/*     */         } 
/*     */       }
/*     */       
/*  84 */       if (this.outputBuffer.position() <= 0)
/*  85 */         continue;  for (int i = 0; i < this.outputBuffer.position(); i++) {
/*  86 */         char c = this.outputBuffer.get(i);
/*     */         
/*  88 */         if (c == '\r' || c == '\n') {
/*  89 */           if (!this.ignoreNextNewLine) {
/*  90 */             this.ignoreNextNewLine = true;
/*     */             
/*  92 */             this.log.log(this.category, this.level, this.output.toString());
/*  93 */             this.output.setLength(0);
/*     */           } 
/*     */         } else {
/*  96 */           this.ignoreNextNewLine = false;
/*  97 */           this.output.append(c);
/*     */         } 
/*     */       } 
/*     */       
/* 101 */       this.outputBuffer.rewind();
/*     */     }
/* 103 */     while (result.isOverflow());
/*     */     
/* 105 */     if (this.inputBuffer.hasRemaining()) {
/* 106 */       this.inputBuffer.compact();
/*     */     } else {
/* 108 */       this.inputBuffer.clear();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\LogOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */