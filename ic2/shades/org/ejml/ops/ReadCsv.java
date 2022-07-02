/*     */ package ic2.shades.org.ejml.ops;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReadCsv
/*     */ {
/*     */   private boolean hasComment = false;
/*     */   private char comment;
/*     */   private BufferedReader in;
/*  52 */   private int lineNumber = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReadCsv(InputStream in) {
/*  60 */     this.in = new BufferedReader(new InputStreamReader(in));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setComment(char comment) {
/*  69 */     this.hasComment = true;
/*  70 */     this.comment = comment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineNumber() {
/*  79 */     return this.lineNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedReader getReader() {
/*  87 */     return this.in;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<String> extractWords() throws IOException {
/*     */     String line;
/*     */     while (true) {
/*  99 */       this.lineNumber++;
/* 100 */       line = this.in.readLine();
/* 101 */       if (line == null) {
/* 102 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 106 */       if (this.hasComment && 
/* 107 */         line.charAt(0) == this.comment) {
/*     */         continue;
/*     */       }
/*     */       break;
/*     */     } 
/* 112 */     return parseWords(line);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<String> parseWords(String line) {
/* 123 */     List<String> words = new ArrayList<String>();
/* 124 */     boolean insideWord = !isSpace(line.charAt(0));
/* 125 */     int last = 0;
/* 126 */     for (int i = 0; i < line.length(); i++) {
/* 127 */       char c = line.charAt(i);
/*     */       
/* 129 */       if (insideWord) {
/*     */         
/* 131 */         if (isSpace(c)) {
/* 132 */           words.add(line.substring(last, i));
/* 133 */           insideWord = false;
/*     */         }
/*     */       
/* 136 */       } else if (!isSpace(c)) {
/* 137 */         last = i;
/* 138 */         insideWord = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 144 */     if (insideWord) {
/* 145 */       words.add(line.substring(last));
/*     */     }
/* 147 */     return words;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSpace(char c) {
/* 157 */     return (c == ' ' || c == '\t');
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\ops\ReadCsv.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */