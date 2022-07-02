/*     */ package ic2.core.network;
/*     */ 
/*     */ import ic2.api.network.IGrowingBuffer;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UTFDataFormatException;
/*     */ import java.nio.BufferUnderflowException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class GrowingBuffer
/*     */   extends OutputStream
/*     */   implements IGrowingBuffer {
/*     */   public GrowingBuffer() {
/*  17 */     this(4096);
/*     */   }
/*     */   
/*     */   public GrowingBuffer(int initialSize) {
/*  21 */     if (initialSize < 0)
/*  22 */       throw new IllegalArgumentException("invalid initial size: " + initialSize); 
/*  23 */     if (initialSize == 0) {
/*  24 */       this.buffer = emptyBuffer;
/*     */     } else {
/*  26 */       this.buffer = new byte[initialSize];
/*     */     } 
/*     */   }
/*     */   
/*     */   public static GrowingBuffer wrap(byte[] data) {
/*  31 */     GrowingBuffer ret = new GrowingBuffer(0);
/*  32 */     ret.buffer = data;
/*  33 */     ret.altPos = data.length;
/*  34 */     return ret;
/*     */   }
/*     */   public static GrowingBuffer wrap(ByteBuf buf) {
/*     */     GrowingBuffer ret;
/*  38 */     int len = buf.readableBytes();
/*     */ 
/*     */     
/*  41 */     if (buf.hasArray()) {
/*  42 */       ret = new GrowingBuffer(0);
/*  43 */       ret.buffer = buf.array();
/*  44 */       ret.pos = buf.arrayOffset() + buf.readerIndex();
/*  45 */       ret.altPos = ret.pos + len;
/*     */     } else {
/*  47 */       ret = new GrowingBuffer(len);
/*  48 */       ret.altPos = len;
/*  49 */       buf.getBytes(buf.readerIndex(), ret.buffer);
/*     */     } 
/*     */     
/*  52 */     return ret;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  56 */     this.pos = 0;
/*  57 */     this.altPos = 0;
/*  58 */     this.mark = -1;
/*     */   }
/*     */   
/*     */   public void mark() {
/*  62 */     this.mark = this.pos;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  66 */     if (this.mark == -1) throw new IllegalStateException("not marked");
/*     */     
/*  68 */     this.pos = this.mark;
/*     */   }
/*     */   
/*     */   public void flip() {
/*  72 */     int cPos = this.pos;
/*  73 */     this.pos = this.altPos;
/*  74 */     this.altPos = cPos;
/*     */   }
/*     */   
/*     */   public void rewind() {
/*  78 */     assert this.pos <= this.altPos;
/*  79 */     this.pos = 0;
/*     */   }
/*     */   
/*     */   public boolean hasAvailable() {
/*  83 */     return (this.pos < this.altPos);
/*     */   }
/*     */   
/*     */   public int available() {
/*  87 */     return Math.max(0, this.altPos - this.pos);
/*     */   }
/*     */   
/*     */   public void writeTo(GrowingBuffer target) {
/*  91 */     int len = this.altPos - this.pos;
/*  92 */     if (len <= 0)
/*     */       return; 
/*  94 */     target.ensureCapacity(len);
/*  95 */     System.arraycopy(this.buffer, this.pos, target.buffer, target.pos, len);
/*  96 */     target.pos += len;
/*  97 */     this.pos += len;
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) throws IOException {
/* 101 */     int len = this.altPos - this.pos;
/* 102 */     if (len <= 0)
/*     */       return; 
/* 104 */     os.write(this.buffer, this.pos, len);
/* 105 */     this.pos += len;
/*     */   }
/*     */   
/*     */   public ByteBuf toByteBuf(boolean advancePos) {
/* 109 */     int len = this.altPos - this.pos;
/* 110 */     if (len <= 0) return Unpooled.EMPTY_BUFFER;
/*     */     
/* 112 */     ByteBuf ret = Unpooled.wrappedBuffer(this.buffer, this.pos, len);
/* 113 */     if (advancePos) this.pos += len;
/*     */     
/* 115 */     return ret;
/*     */   }
/*     */   
/*     */   public GrowingBuffer copy(int maxLen) {
/* 119 */     int len = Math.max(0, Math.min(maxLen, this.altPos - this.pos));
/* 120 */     GrowingBuffer ret = new GrowingBuffer(len);
/*     */     
/* 122 */     if (len > 0) {
/* 123 */       System.arraycopy(this.buffer, this.pos, ret.buffer, 0, len);
/* 124 */       ret.altPos = len;
/* 125 */       this.pos += len;
/*     */     } 
/*     */     
/* 128 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(int b) {
/* 133 */     ensureCapacity(1);
/* 134 */     this.buffer[this.pos] = (byte)b;
/* 135 */     this.pos++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] b) {
/* 140 */     ensureCapacity(b.length);
/* 141 */     System.arraycopy(b, 0, this.buffer, this.pos, b.length);
/* 142 */     this.pos += b.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] b, int off, int len) {
/* 147 */     ensureCapacity(len);
/* 148 */     System.arraycopy(b, off, this.buffer, this.pos, len);
/* 149 */     this.pos += len;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeBoolean(boolean v) {
/* 154 */     write(v ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeByte(int v) {
/* 159 */     write(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeShort(int v) {
/* 164 */     ensureCapacity(2);
/* 165 */     this.buffer[this.pos] = (byte)(v >> 8);
/* 166 */     this.buffer[this.pos + 1] = (byte)v;
/* 167 */     this.pos += 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeChar(int v) {
/* 172 */     writeShort(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeInt(int v) {
/* 177 */     ensureCapacity(4);
/* 178 */     this.buffer[this.pos] = (byte)(v >> 24);
/* 179 */     this.buffer[this.pos + 1] = (byte)(v >> 16);
/* 180 */     this.buffer[this.pos + 2] = (byte)(v >> 8);
/* 181 */     this.buffer[this.pos + 3] = (byte)v;
/* 182 */     this.pos += 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeLong(long v) {
/* 187 */     ensureCapacity(8);
/* 188 */     this.buffer[this.pos] = (byte)(int)(v >> 56L);
/* 189 */     this.buffer[this.pos + 1] = (byte)(int)(v >> 48L);
/* 190 */     this.buffer[this.pos + 2] = (byte)(int)(v >> 40L);
/* 191 */     this.buffer[this.pos + 3] = (byte)(int)(v >> 32L);
/* 192 */     this.buffer[this.pos + 4] = (byte)(int)(v >> 24L);
/* 193 */     this.buffer[this.pos + 5] = (byte)(int)(v >> 16L);
/* 194 */     this.buffer[this.pos + 6] = (byte)(int)(v >> 8L);
/* 195 */     this.buffer[this.pos + 7] = (byte)(int)v;
/* 196 */     this.pos += 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeFloat(float v) {
/* 201 */     writeInt(Float.floatToRawIntBits(v));
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeDouble(double v) {
/* 206 */     writeLong(Double.doubleToRawLongBits(v));
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeVarInt(int i) {
/* 211 */     if (i < 0) throw new IllegalArgumentException("only positive numbers are supported");
/*     */     
/*     */     do {
/* 214 */       int part = i & 0x7F;
/* 215 */       i >>>= 7;
/*     */       
/* 217 */       if (i != 0) part |= 0x80;
/*     */       
/* 219 */       writeByte(part);
/* 220 */     } while (i != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeString(String s) {
/* 225 */     byte[] bytes = s.getBytes(utf8);
/* 226 */     writeVarInt(bytes.length);
/* 227 */     write(bytes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeBytes(String s) {
/* 232 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeChars(String s) {
/* 237 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeUTF(String s) {
/* 242 */     int encodedLen = 0;
/*     */     int i;
/* 244 */     for (i = 0; i < s.length(); i++) {
/* 245 */       char c = s.charAt(i);
/*     */       
/* 247 */       if (c > '\000' && c < '') {
/* 248 */         encodedLen++;
/* 249 */       } else if (c >= 'ࠀ') {
/* 250 */         encodedLen += 3;
/*     */       } else {
/* 252 */         encodedLen += 2;
/*     */       } 
/*     */     } 
/*     */     
/* 256 */     if (encodedLen > 65535) throw new IllegalArgumentException("string length limit exceeded");
/*     */     
/* 258 */     writeShort(encodedLen);
/*     */     
/* 260 */     for (i = 0; i < s.length(); i++) {
/* 261 */       char c = s.charAt(i);
/*     */       
/* 263 */       if (c > '\000' && c < '') {
/* 264 */         write(c);
/* 265 */       } else if (c >= 'ࠀ') {
/* 266 */         write(0xE0 | c >> 12 & 0xF);
/* 267 */         write(0x80 | c >> 6 & 0x3F);
/* 268 */         write(0x80 | c & 0x3F);
/*     */       } else {
/* 270 */         write(0xC0 | c >> 6 & 0x1F);
/* 271 */         write(0x80 | c & 0x3F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void ensureCapacity(int amount) {
/* 277 */     if (this.pos + amount > this.buffer.length) {
/* 278 */       this.buffer = Arrays.copyOf(this.buffer, Math.max(this.buffer.length * 2, this.pos + amount));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFully(byte[] b) {
/* 284 */     if (this.pos + b.length > this.altPos) throw new BufferUnderflowException();
/*     */     
/* 286 */     System.arraycopy(this.buffer, this.pos, b, 0, b.length);
/* 287 */     this.pos += b.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFully(byte[] b, int off, int len) {
/* 292 */     if (this.pos + len > this.altPos) throw new BufferUnderflowException();
/*     */     
/* 294 */     System.arraycopy(this.buffer, this.pos, b, off, len);
/* 295 */     this.pos += len;
/*     */   }
/*     */ 
/*     */   
/*     */   public int skipBytes(int n) {
/* 300 */     int skipped = Math.max(-this.pos, Math.min(n, Math.max(0, this.altPos - this.pos)));
/*     */     
/* 302 */     this.pos += skipped;
/*     */     
/* 304 */     return skipped;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean readBoolean() {
/* 309 */     return (readByte() != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte readByte() {
/* 314 */     if (this.pos + 1 > this.altPos) throw new BufferUnderflowException();
/*     */     
/* 316 */     return this.buffer[this.pos++];
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedByte() {
/* 321 */     return readByte() & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public short readShort() {
/* 326 */     if (this.pos + 2 > this.altPos) throw new BufferUnderflowException();
/*     */     
/* 328 */     short ret = (short)(this.buffer[this.pos] << 8 | this.buffer[this.pos + 1] & 0xFF);
/* 329 */     this.pos += 2;
/*     */     
/* 331 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedShort() {
/* 336 */     if (this.pos + 2 > this.altPos) throw new BufferUnderflowException();
/*     */     
/* 338 */     int ret = (this.buffer[this.pos] & 0xFF) << 8 | this.buffer[this.pos + 1] & 0xFF;
/* 339 */     this.pos += 2;
/*     */     
/* 341 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public char readChar() {
/* 346 */     return (char)readShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readInt() {
/* 351 */     if (this.pos + 4 > this.altPos) throw new BufferUnderflowException();
/*     */     
/* 353 */     int ret = (this.buffer[this.pos] & 0xFF) << 24 | (this.buffer[this.pos + 1] & 0xFF) << 16 | (this.buffer[this.pos + 2] & 0xFF) << 8 | this.buffer[this.pos + 3] & 0xFF;
/*     */ 
/*     */ 
/*     */     
/* 357 */     this.pos += 4;
/*     */     
/* 359 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public long readLong() {
/* 364 */     if (this.pos + 8 > this.altPos) throw new BufferUnderflowException();
/*     */     
/* 366 */     long ret = (this.buffer[this.pos] & 0xFFL) << 56L | (this.buffer[this.pos + 1] & 0xFFL) << 48L | (this.buffer[this.pos + 2] & 0xFFL) << 40L | (this.buffer[this.pos + 3] & 0xFFL) << 32L | (this.buffer[this.pos + 4] & 0xFFL) << 24L | (this.buffer[this.pos + 5] & 0xFFL) << 16L | (this.buffer[this.pos + 6] & 0xFFL) << 8L | this.buffer[this.pos + 7] & 0xFFL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 374 */     this.pos += 8;
/*     */     
/* 376 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public float readFloat() {
/* 381 */     return Float.intBitsToFloat(readInt());
/*     */   }
/*     */ 
/*     */   
/*     */   public double readDouble() {
/* 386 */     return Double.longBitsToDouble(readLong());
/*     */   }
/*     */ 
/*     */   
/*     */   public int readVarInt() {
/* 391 */     int i = 0;
/*     */     
/* 393 */     for (int shift = 0;; shift += 7) {
/* 394 */       int part = readByte();
/* 395 */       i |= (part & 0x7F) << shift;
/*     */       
/* 397 */       if ((part & 0x80) == 0)
/*     */         break; 
/*     */     } 
/* 400 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public String readString() {
/* 405 */     int len = readVarInt();
/* 406 */     byte[] bytes = new byte[len];
/* 407 */     readFully(bytes);
/*     */     
/* 409 */     return new String(bytes, utf8);
/*     */   }
/*     */ 
/*     */   
/*     */   public String readLine() {
/* 414 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public String readUTF() throws IOException {
/* 419 */     int len = readUnsignedShort();
/* 420 */     if (len == 0) return "";
/*     */     
/* 422 */     StringBuilder ret = new StringBuilder(Math.min(len, 10 + (len + 2) / 3));
/*     */     
/* 424 */     for (int i = 0; i < len; ) {
/* 425 */       byte b = readByte();
/*     */       
/* 427 */       if ((b & 0x80) == 0) {
/* 428 */         ret.append((char)b);
/* 429 */         i++; continue;
/* 430 */       }  if ((b & 0xE0) == 192) {
/* 431 */         if (len - i < 2) throw new UTFDataFormatException(); 
/* 432 */         byte b2 = readByte();
/* 433 */         if ((b2 & 0xC0) != 128) throw new UTFDataFormatException();
/*     */         
/* 435 */         ret.append((char)((b & 0x1F) << 6 | b2 & 0xEF));
/* 436 */         i += 2; continue;
/* 437 */       }  if ((b & 0xF0) == 224) {
/* 438 */         if (len - i < 3) throw new UTFDataFormatException(); 
/* 439 */         byte b2 = readByte();
/* 440 */         if ((b2 & 0xC0) != 128) throw new UTFDataFormatException(); 
/* 441 */         byte b3 = readByte();
/* 442 */         if ((b3 & 0xC0) != 128) throw new UTFDataFormatException();
/*     */         
/* 444 */         ret.append((char)((b & 0xF) << 12 | (b2 & 0xEF) << 6 | b3 & 0xEF));
/* 445 */         i += 3; continue;
/*     */       } 
/* 447 */       throw new UTFDataFormatException();
/*     */     } 
/*     */ 
/*     */     
/* 451 */     return ret.toString();
/*     */   }
/*     */   
/* 454 */   private static byte[] emptyBuffer = new byte[0];
/* 455 */   private static final Charset utf8 = Charset.forName("UTF-8");
/*     */   
/*     */   private byte[] buffer;
/*     */   private int pos;
/*     */   private int altPos;
/* 460 */   private int mark = -1;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\network\GrowingBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */