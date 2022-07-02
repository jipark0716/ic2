/*     */ package ic2.core.util;
/*     */ import com.google.common.base.Charsets;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.LineNumberReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.text.DateFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.Deque;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ public class Config {
/*     */   private final Config parent;
/*     */   public final String name;
/*     */   private String comment;
/*     */   
/*     */   public Config(String name) {
/*  34 */     this(null, name, "");
/*     */   }
/*     */   
/*     */   private Config(Config parent, String name, String comment) {
/*  38 */     assert parent != this;
/*     */     
/*  40 */     this.parent = parent;
/*  41 */     this.name = name;
/*  42 */     this.comment = comment;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Config getRoot() {
/*  48 */     Config ret = this;
/*     */     
/*  50 */     while (ret.parent != null) {
/*  51 */       ret = ret.parent;
/*     */     }
/*     */     
/*  54 */     return ret;
/*     */   }
/*     */   
/*     */   public Config getSub(String key) {
/*  58 */     List<String> parts = split(key, '/');
/*     */     
/*  60 */     return getSub(parts, parts.size(), false);
/*     */   }
/*     */   
/*     */   public Config addSub(String key, String aComment) {
/*  64 */     assert split(key, '/').size() == 1;
/*     */     
/*  66 */     Config config = this.sections.get(key);
/*     */     
/*  68 */     if (config == null) {
/*  69 */       config = new Config(this, key, aComment);
/*  70 */       this.sections.put(key, config);
/*     */     } else {
/*  72 */       config.comment = aComment;
/*     */     } 
/*     */     
/*  75 */     return config;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Value get(String key) {
/*  81 */     List<String> parts = split(key, '/');
/*     */     
/*  83 */     Config config = getSub(parts, parts.size() - 1, false);
/*  84 */     if (config == null) return null;
/*     */     
/*  86 */     return config.values.get(parts.get(parts.size() - 1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String key, Value value) {
/*  92 */     List<String> parts = split(key, '/');
/*  93 */     assert ((String)parts.get(parts.size() - 1)).equals(value.name);
/*     */     
/*  95 */     Config config = getSub(parts, parts.size() - 1, true);
/*     */     
/*  97 */     config.values.put(parts.get(parts.size() - 1), value);
/*     */   }
/*     */   
/*     */   public <T> void set(String key, T value) {
/* 101 */     List<String> parts = split(key, '/');
/*     */     
/* 103 */     Config config = getSub(parts, parts.size() - 1, true);
/* 104 */     String tName = parts.get(parts.size() - 1);
/* 105 */     Value existingValue = config.values.get(tName);
/*     */     
/* 107 */     if (existingValue == null) {
/* 108 */       existingValue = new Value(tName, "", null);
/* 109 */       config.values.put(tName, existingValue);
/*     */     } 
/*     */     
/* 112 */     existingValue.set(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Value remove(String key) {
/* 118 */     List<String> parts = split(key, '/');
/*     */     
/* 120 */     Config config = getSub(parts, parts.size() - 1, true);
/* 121 */     String tName = parts.get(parts.size() - 1);
/*     */     
/* 123 */     return config.values.remove(tName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 129 */     this.sections.clear();
/* 130 */     this.values.clear();
/*     */   }
/*     */   
/*     */   public void sort() {
/* 134 */     List<Map.Entry<String, Value>> valueList = new ArrayList<>(this.values.entrySet());
/*     */     
/* 136 */     Collections.sort(valueList, new Comparator<Map.Entry<String, Value>>()
/*     */         {
/*     */           public int compare(Map.Entry<String, Config.Value> a, Map.Entry<String, Config.Value> b) {
/* 139 */             return ((String)a.getKey()).compareTo(b.getKey());
/*     */           }
/*     */         });
/*     */     
/* 143 */     this.values.clear();
/*     */     
/* 145 */     for (Map.Entry<String, Value> entry : valueList) {
/* 146 */       this.values.put(entry.getKey(), entry.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public Iterator<Config> sectionIterator() {
/* 151 */     return this.sections.values().iterator();
/*     */   }
/*     */   
/*     */   public boolean hasChildSection() {
/* 155 */     return !this.sections.isEmpty();
/*     */   }
/*     */   
/*     */   public int getNumberOfSections() {
/* 159 */     return this.sections.size();
/*     */   }
/*     */   
/*     */   public Iterator<Value> valueIterator() {
/* 163 */     return this.values.values().iterator();
/*     */   }
/*     */   
/*     */   public boolean isEmptySection() {
/* 167 */     return this.values.isEmpty();
/*     */   }
/*     */   
/*     */   public int getNumberOfConfigs() {
/* 171 */     return this.values.size();
/*     */   }
/*     */   
/*     */   public void setSaveWithParent(boolean saveWithParent) {
/* 175 */     this.saveWithParent = saveWithParent;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(InputStream is) throws IOException, ParseException {
/* 181 */     InputStreamReader isReader = new InputStreamReader(is, Charsets.UTF_8);
/* 182 */     LineNumberReader reader = new LineNumberReader(isReader);
/*     */     
/* 184 */     Config root = this;
/* 185 */     Config config = root;
/* 186 */     StringBuilder tComment = new StringBuilder();
/* 187 */     String line = "";
/*     */     
/*     */     try {
/* 190 */       while ((line = reader.readLine()) != null) {
/* 191 */         line = trim(line);
/*     */         
/* 193 */         if (line.isEmpty())
/*     */           continue; 
/* 195 */         if (line.startsWith(";")) {
/* 196 */           if (line.equals(";---")) {
/* 197 */             tComment = new StringBuilder(); continue;
/*     */           } 
/* 199 */           line = line.substring(1).trim();
/*     */           
/* 201 */           if (tComment.length() != 0) tComment.append(lineSeparator);
/*     */           
/* 203 */           tComment.append(line); continue;
/*     */         } 
/* 205 */         if (line.startsWith("[")) {
/* 206 */           if (!line.endsWith("]")) {
/* 207 */             throw new ParseException("section without closing bracket", reader.getLineNumber(), line);
/*     */           }
/*     */           
/* 210 */           String section = line.substring(1, line.length() - 1);
/* 211 */           List<String> keys = split(section, '/');
/*     */           
/* 213 */           for (ListIterator<String> it = keys.listIterator(); it.hasNext();) {
/* 214 */             it.set(unescapeSection(it.next()));
/*     */           }
/*     */           
/* 217 */           if (tComment.length() > 0) {
/* 218 */             config = root.getSub(keys, keys.size() - 1, true);
/* 219 */             config = config.addSub(keys.get(keys.size() - 1), tComment.toString());
/*     */             
/* 221 */             tComment = new StringBuilder(); continue;
/*     */           } 
/* 223 */           config = root.getSub(keys, keys.size(), true);
/*     */           continue;
/*     */         } 
/* 226 */         List<String> parts = split(line, '=');
/*     */         
/* 228 */         if (parts.size() != 2) {
/* 229 */           throw new ParseException("invalid key-value pair", reader.getLineNumber(), line);
/*     */         }
/*     */         
/* 232 */         String key = unescapeValue(((String)parts.get(0)).trim());
/*     */         
/* 234 */         if (key.isEmpty()) {
/* 235 */           throw new ParseException("empty key", reader.getLineNumber(), line);
/*     */         }
/*     */         
/* 238 */         String valueStr = ((String)parts.get(1)).trim();
/*     */ 
/*     */         
/* 241 */         while (valueStr.replaceAll("\\\\.", "xx").endsWith("\\")) {
/* 242 */           valueStr = valueStr.substring(0, valueStr.length() - 1) + " ";
/* 243 */           valueStr = valueStr + reader.readLine().trim();
/*     */         } 
/*     */         
/* 246 */         valueStr = unescapeValue(valueStr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 256 */         config.set(key, new Value(key, tComment.toString(), valueStr, reader.getLineNumber()));
/*     */         
/* 258 */         if (tComment.length() > 0) tComment = new StringBuilder();
/*     */       
/*     */       } 
/* 261 */     } catch (IOException e) {
/* 262 */       throw e;
/* 263 */     } catch (Exception e) {
/* 264 */       throw new ParseException("general parse error", reader.getLineNumber(), line, e);
/*     */     } finally {
/* 266 */       IOUtils.closeQuietly(reader);
/* 267 */       IOUtils.closeQuietly(isReader);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void load(File file) throws ParseException, IOException {
/* 272 */     FileInputStream is = null;
/*     */     
/*     */     try {
/* 275 */       is = new FileInputStream(file);
/*     */       
/* 277 */       load(is);
/*     */     } finally {
/* 279 */       IOUtils.closeQuietly(is);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(OutputStream os) throws IOException {
/* 286 */     OutputStreamWriter osWriter = new OutputStreamWriter(os, Charsets.UTF_8);
/* 287 */     BufferedWriter writer = new BufferedWriter(osWriter);
/*     */     
/*     */     try {
/* 290 */       writer.write("; ");
/* 291 */       writer.write(this.name);
/* 292 */       writer.newLine();
/* 293 */       writer.write("; created ");
/* 294 */       writer.write(DateFormat.getDateTimeInstance().format(new Date()));
/* 295 */       writer.newLine();
/* 296 */       writer.write(";---");
/* 297 */       writer.newLine();
/*     */       
/* 299 */       Config root = this;
/* 300 */       Deque<Config> todo = new ArrayDeque<>();
/* 301 */       todo.add(this);
/*     */       
/*     */       Config config;
/* 304 */       while ((config = todo.poll()) != null) {
/* 305 */         if (!config.values.isEmpty() || !config.comment.isEmpty() || config.sections.isEmpty()) {
/* 306 */           writer.newLine();
/*     */           
/* 308 */           if (config != root) {
/* 309 */             if (!config.comment.isEmpty()) {
/* 310 */               String[] commentParts = config.comment.split("\\n");
/*     */               
/* 312 */               for (String comment : commentParts) {
/* 313 */                 writer.write("; ");
/* 314 */                 writer.write(comment);
/* 315 */                 writer.newLine();
/*     */               } 
/*     */             } 
/*     */             
/* 319 */             writer.write(91);
/*     */             
/* 321 */             List<String> keys = new ArrayList<>();
/* 322 */             Config cSection = config;
/*     */             
/*     */             do {
/* 325 */               keys.add(cSection.name);
/* 326 */               cSection = cSection.parent;
/* 327 */             } while (cSection != root);
/*     */             
/* 329 */             for (int i = keys.size() - 1; i >= 0; i--) {
/* 330 */               writer.write(escapeSection(keys.get(i)));
/*     */               
/* 332 */               if (i > 0) writer.write(" / ");
/*     */             
/*     */             } 
/* 335 */             writer.write(93);
/* 336 */             writer.newLine();
/*     */           } 
/*     */           
/* 339 */           for (Value value : config.values.values()) {
/* 340 */             if (!value.comment.isEmpty()) {
/* 341 */               for (String line : value.comment.split("\\n")) {
/* 342 */                 writer.write("; ");
/* 343 */                 writer.write(line);
/* 344 */                 writer.newLine();
/*     */               } 
/*     */             }
/*     */             
/* 348 */             writer.write(escapeValue(value.name));
/* 349 */             writer.write(" = ");
/* 350 */             writer.write(escapeValue(value.getString()));
/* 351 */             writer.newLine();
/*     */           } 
/*     */         } 
/*     */         
/* 355 */         ArrayList<Config> toAdd = new ArrayList<>(config.sections.size());
/*     */         
/* 357 */         for (Config section : config.sections.values()) {
/* 358 */           if (section.saveWithParent) {
/* 359 */             toAdd.add(section);
/*     */           }
/*     */         } 
/*     */         
/* 363 */         for (ListIterator<Config> it = toAdd.listIterator(toAdd.size()); it.hasPrevious();) {
/* 364 */           todo.addFirst(it.previous());
/*     */         }
/*     */       } 
/*     */       
/* 368 */       writer.newLine();
/*     */     } finally {
/* 370 */       IOUtils.closeQuietly(writer);
/* 371 */       IOUtils.closeQuietly(osWriter);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void save(File file) throws IOException {
/* 376 */     FileOutputStream os = null;
/*     */     
/*     */     try {
/* 379 */       os = new FileOutputStream(file);
/*     */       
/* 381 */       save(os);
/*     */     } finally {
/* 383 */       IOUtils.closeQuietly(os);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Config getSub(List<String> keys, int end, boolean create) {
/* 389 */     Config ret = this;
/*     */     
/* 391 */     for (int i = 0; i < end; i++) {
/* 392 */       String key = keys.get(i);
/* 393 */       assert key.length() > 0;
/*     */       
/* 395 */       Config config = ret.sections.get(key);
/*     */       
/* 397 */       if (config == null) {
/* 398 */         if (create) {
/* 399 */           config = new Config(ret, key, "");
/* 400 */           ret.sections.put(key, config);
/*     */         } else {
/* 402 */           return null;
/*     */         } 
/*     */       }
/*     */       
/* 406 */       ret = config;
/*     */     } 
/*     */     
/* 409 */     return ret;
/*     */   }
/*     */   
/*     */   private static List<String> split(String str, char splitChar) {
/* 413 */     List<String> ret = new ArrayList<>();
/* 414 */     StringBuilder current = new StringBuilder();
/* 415 */     boolean empty = true;
/* 416 */     boolean passNext = false;
/* 417 */     boolean quoted = false;
/*     */     
/* 419 */     for (int i = 0; i < str.length(); i++) {
/* 420 */       char c = str.charAt(i);
/*     */       
/* 422 */       if (passNext) {
/* 423 */         current.append(c);
/* 424 */         empty = false;
/* 425 */         passNext = false;
/* 426 */       } else if (c == '\\') {
/* 427 */         current.append(c);
/* 428 */         empty = false;
/* 429 */         passNext = true;
/* 430 */       } else if (c == '"') {
/* 431 */         current.append(c);
/* 432 */         empty = false;
/* 433 */         quoted = !quoted;
/* 434 */       } else if (!quoted && c == splitChar) {
/* 435 */         ret.add(current.toString().trim());
/* 436 */         current = new StringBuilder();
/* 437 */         empty = true;
/* 438 */       } else if (!Character.isWhitespace(c) || !empty) {
/* 439 */         current.append(c);
/* 440 */         empty = false;
/*     */       } 
/*     */     } 
/*     */     
/* 444 */     ret.add(current.toString().trim());
/*     */     
/* 446 */     return ret;
/*     */   }
/*     */   
/*     */   private static String escapeSection(String str) {
/* 450 */     return str.replaceAll("([\\[\\];/])", "\\\\$1").replace("\n", "\\n");
/*     */   }
/*     */   
/*     */   private static String unescapeSection(String str) {
/* 454 */     return str.replaceAll("\\\\([\\[\\];/])", "$1").replace("\\n", "\n");
/*     */   }
/*     */   
/*     */   private static String escapeValue(String str) {
/* 458 */     return str.replaceAll("([\\[\\];=\\\\])", "\\\\$1").replace("\n", "\\\n");
/*     */   }
/*     */   
/*     */   private static String unescapeValue(String str) {
/* 462 */     return str.replaceAll("\\\\([\\[\\];=])", "$1");
/*     */   }
/*     */ 
/*     */   
/*     */   private static String trim(String str) {
/* 467 */     int len = str.length();
/*     */     
/*     */     int start;
/* 470 */     for (start = 0; start < len; start++) {
/* 471 */       char c = str.charAt(start);
/*     */       
/* 473 */       if (c > ' ' && c != '﻿')
/*     */         break; 
/*     */     }  int end;
/* 476 */     for (end = len - 1; end >= start; end--) {
/* 477 */       char c = str.charAt(end);
/*     */       
/* 479 */       if (c > ' ' && c != '﻿')
/*     */         break; 
/*     */     } 
/* 482 */     if (start > 0 || end < len - 1) {
/* 483 */       return str.substring(start, end + 1);
/*     */     }
/* 485 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean saveWithParent = true;
/*     */ 
/*     */   
/* 494 */   private final Map<String, Config> sections = new LinkedHashMap<>();
/* 495 */   private final Map<String, Value> values = new LinkedHashMap<>();
/*     */   
/* 497 */   private static final String lineSeparator = System.getProperty("line.separator");
/*     */   public static class Value { public final String name;
/*     */     public String comment;
/*     */     
/*     */     public Value(String name, String comment, String value) {
/* 502 */       this(name, comment, value, -1);
/*     */     }
/*     */     public String value; private final int line; private Number numberCache;
/*     */     private Value(String name, String comment, String value, int line) {
/* 506 */       this.name = name;
/* 507 */       this.comment = comment;
/* 508 */       this.value = value;
/* 509 */       this.line = line;
/*     */     }
/*     */     
/*     */     public String getString() {
/* 513 */       return this.value;
/*     */     }
/*     */     
/*     */     public boolean getBool() {
/* 517 */       return Boolean.valueOf(this.value).booleanValue();
/*     */     }
/*     */     
/*     */     public int getInt() {
/*     */       try {
/* 522 */         return getNumber().intValue();
/* 523 */       } catch (java.text.ParseException e) {
/* 524 */         throw new Config.ParseException("invalid value", this, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public float getFloat() {
/*     */       try {
/* 530 */         return getNumber().floatValue();
/* 531 */       } catch (java.text.ParseException e) {
/* 532 */         throw new Config.ParseException("invalid value", this, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public double getDouble() {
/*     */       try {
/* 538 */         return getNumber().doubleValue();
/* 539 */       } catch (java.text.ParseException e) {
/* 540 */         throw new Config.ParseException("invalid value", this, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public <T> void set(T value) {
/* 545 */       this.value = String.valueOf(value);
/* 546 */       this.numberCache = null;
/*     */     }
/*     */     
/*     */     public int getLine() {
/* 550 */       return this.line;
/*     */     }
/*     */     
/*     */     private Number getNumber() throws java.text.ParseException {
/* 554 */       if (this.numberCache == null) this.numberCache = NumberFormat.getInstance(Locale.US).parse(this.value);
/*     */       
/* 556 */       return this.numberCache;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ParseException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = 8721912755972301225L;
/*     */ 
/*     */ 
/*     */     
/*     */     public ParseException(String msg, int line, String content) {
/* 570 */       super(formatMsg(msg, line, content));
/*     */     }
/*     */     
/*     */     public ParseException(String msg, int line, String content, Exception e) {
/* 574 */       super(formatMsg(msg, line, content), e);
/*     */     }
/*     */     
/*     */     public ParseException(String msg, Config.Value value) {
/* 578 */       super(formatMsg(msg, value));
/*     */     }
/*     */     
/*     */     public ParseException(String msg, Config.Value value, Exception e) {
/* 582 */       super(formatMsg(msg, value), e);
/*     */     }
/*     */     
/*     */     private static String formatMsg(String msg, int line, String content) {
/* 586 */       if (!isPrintable(content)) {
/* 587 */         content = content + "|" + toPrintable(content);
/*     */       }
/*     */       
/* 590 */       if (line >= 0) {
/* 591 */         return msg + " at line " + line + " (" + content + ").";
/*     */       }
/* 593 */       return msg + " at an unknown line (" + content + ").";
/*     */     }
/*     */ 
/*     */     
/*     */     private static String formatMsg(String msg, Config.Value value) {
/* 598 */       return formatMsg(msg, value.getLine(), value.name + " = " + value.getString());
/*     */     }
/*     */     
/*     */     private static boolean isPrintable(String str) {
/* 602 */       int len = str.length();
/*     */       
/* 604 */       for (int i = 0; i < len; i++) {
/* 605 */         char c = str.charAt(i);
/*     */         
/* 607 */         if (c < ' ' || c > '~') {
/* 608 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 612 */       return true;
/*     */     }
/*     */     
/*     */     private static String toPrintable(String str) {
/* 616 */       int len = str.length();
/* 617 */       String ret = "";
/*     */       
/* 619 */       for (int i = 0; i < len; i++) {
/* 620 */         char c = str.charAt(i);
/*     */         
/* 622 */         if (c < ' ' || c > '~') {
/* 623 */           if (i > 0) ret = ret + ','; 
/* 624 */           ret = ret + String.format("0x%x", new Object[] { Integer.valueOf(c) });
/* 625 */           if (i < len - 1) ret = ret + ','; 
/*     */         } else {
/* 627 */           ret = ret + c;
/*     */         } 
/*     */       } 
/*     */       
/* 631 */       return ret;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */