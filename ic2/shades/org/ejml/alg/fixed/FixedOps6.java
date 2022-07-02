/*     */ package ic2.shades.org.ejml.alg.fixed;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.FixedMatrix6_64F;
/*     */ import ic2.shades.org.ejml.data.FixedMatrix6x6_64F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FixedOps6
/*     */ {
/*     */   public static void add(FixedMatrix6x6_64F a, FixedMatrix6x6_64F b, FixedMatrix6x6_64F c) {
/*  46 */     a.a11 += b.a11;
/*  47 */     a.a12 += b.a12;
/*  48 */     a.a13 += b.a13;
/*  49 */     a.a14 += b.a14;
/*  50 */     a.a15 += b.a15;
/*  51 */     a.a16 += b.a16;
/*  52 */     a.a21 += b.a21;
/*  53 */     a.a22 += b.a22;
/*  54 */     a.a23 += b.a23;
/*  55 */     a.a24 += b.a24;
/*  56 */     a.a25 += b.a25;
/*  57 */     a.a26 += b.a26;
/*  58 */     a.a31 += b.a31;
/*  59 */     a.a32 += b.a32;
/*  60 */     a.a33 += b.a33;
/*  61 */     a.a34 += b.a34;
/*  62 */     a.a35 += b.a35;
/*  63 */     a.a36 += b.a36;
/*  64 */     a.a41 += b.a41;
/*  65 */     a.a42 += b.a42;
/*  66 */     a.a43 += b.a43;
/*  67 */     a.a44 += b.a44;
/*  68 */     a.a45 += b.a45;
/*  69 */     a.a46 += b.a46;
/*  70 */     a.a51 += b.a51;
/*  71 */     a.a52 += b.a52;
/*  72 */     a.a53 += b.a53;
/*  73 */     a.a54 += b.a54;
/*  74 */     a.a55 += b.a55;
/*  75 */     a.a56 += b.a56;
/*  76 */     a.a61 += b.a61;
/*  77 */     a.a62 += b.a62;
/*  78 */     a.a63 += b.a63;
/*  79 */     a.a64 += b.a64;
/*  80 */     a.a65 += b.a65;
/*  81 */     a.a66 += b.a66;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addEquals(FixedMatrix6x6_64F a, FixedMatrix6x6_64F b) {
/*  95 */     a.a11 += b.a11;
/*  96 */     a.a12 += b.a12;
/*  97 */     a.a13 += b.a13;
/*  98 */     a.a14 += b.a14;
/*  99 */     a.a15 += b.a15;
/* 100 */     a.a16 += b.a16;
/* 101 */     a.a21 += b.a21;
/* 102 */     a.a22 += b.a22;
/* 103 */     a.a23 += b.a23;
/* 104 */     a.a24 += b.a24;
/* 105 */     a.a25 += b.a25;
/* 106 */     a.a26 += b.a26;
/* 107 */     a.a31 += b.a31;
/* 108 */     a.a32 += b.a32;
/* 109 */     a.a33 += b.a33;
/* 110 */     a.a34 += b.a34;
/* 111 */     a.a35 += b.a35;
/* 112 */     a.a36 += b.a36;
/* 113 */     a.a41 += b.a41;
/* 114 */     a.a42 += b.a42;
/* 115 */     a.a43 += b.a43;
/* 116 */     a.a44 += b.a44;
/* 117 */     a.a45 += b.a45;
/* 118 */     a.a46 += b.a46;
/* 119 */     a.a51 += b.a51;
/* 120 */     a.a52 += b.a52;
/* 121 */     a.a53 += b.a53;
/* 122 */     a.a54 += b.a54;
/* 123 */     a.a55 += b.a55;
/* 124 */     a.a56 += b.a56;
/* 125 */     a.a61 += b.a61;
/* 126 */     a.a62 += b.a62;
/* 127 */     a.a63 += b.a63;
/* 128 */     a.a64 += b.a64;
/* 129 */     a.a65 += b.a65;
/* 130 */     a.a66 += b.a66;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void transpose(FixedMatrix6x6_64F m) {
/* 141 */     double tmp = m.a12; m.a12 = m.a21; m.a21 = tmp;
/* 142 */     tmp = m.a13; m.a13 = m.a31; m.a31 = tmp;
/* 143 */     tmp = m.a14; m.a14 = m.a41; m.a41 = tmp;
/* 144 */     tmp = m.a15; m.a15 = m.a51; m.a51 = tmp;
/* 145 */     tmp = m.a16; m.a16 = m.a61; m.a61 = tmp;
/* 146 */     tmp = m.a23; m.a23 = m.a32; m.a32 = tmp;
/* 147 */     tmp = m.a24; m.a24 = m.a42; m.a42 = tmp;
/* 148 */     tmp = m.a25; m.a25 = m.a52; m.a52 = tmp;
/* 149 */     tmp = m.a26; m.a26 = m.a62; m.a62 = tmp;
/* 150 */     tmp = m.a34; m.a34 = m.a43; m.a43 = tmp;
/* 151 */     tmp = m.a35; m.a35 = m.a53; m.a53 = tmp;
/* 152 */     tmp = m.a36; m.a36 = m.a63; m.a63 = tmp;
/* 153 */     tmp = m.a45; m.a45 = m.a54; m.a54 = tmp;
/* 154 */     tmp = m.a46; m.a46 = m.a64; m.a64 = tmp;
/* 155 */     tmp = m.a56; m.a56 = m.a65; m.a65 = tmp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FixedMatrix6x6_64F transpose(FixedMatrix6x6_64F input, FixedMatrix6x6_64F output) {
/* 171 */     if (input == null) {
/* 172 */       input = new FixedMatrix6x6_64F();
/*     */     }
/* 174 */     output.a11 = input.a11;
/* 175 */     output.a12 = input.a21;
/* 176 */     output.a13 = input.a31;
/* 177 */     output.a14 = input.a41;
/* 178 */     output.a15 = input.a51;
/* 179 */     output.a16 = input.a61;
/* 180 */     output.a21 = input.a12;
/* 181 */     output.a22 = input.a22;
/* 182 */     output.a23 = input.a32;
/* 183 */     output.a24 = input.a42;
/* 184 */     output.a25 = input.a52;
/* 185 */     output.a26 = input.a62;
/* 186 */     output.a31 = input.a13;
/* 187 */     output.a32 = input.a23;
/* 188 */     output.a33 = input.a33;
/* 189 */     output.a34 = input.a43;
/* 190 */     output.a35 = input.a53;
/* 191 */     output.a36 = input.a63;
/* 192 */     output.a41 = input.a14;
/* 193 */     output.a42 = input.a24;
/* 194 */     output.a43 = input.a34;
/* 195 */     output.a44 = input.a44;
/* 196 */     output.a45 = input.a54;
/* 197 */     output.a46 = input.a64;
/* 198 */     output.a51 = input.a15;
/* 199 */     output.a52 = input.a25;
/* 200 */     output.a53 = input.a35;
/* 201 */     output.a54 = input.a45;
/* 202 */     output.a55 = input.a55;
/* 203 */     output.a56 = input.a65;
/* 204 */     output.a61 = input.a16;
/* 205 */     output.a62 = input.a26;
/* 206 */     output.a63 = input.a36;
/* 207 */     output.a64 = input.a46;
/* 208 */     output.a65 = input.a56;
/* 209 */     output.a66 = input.a66;
/*     */     
/* 211 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void mult(FixedMatrix6x6_64F a, FixedMatrix6x6_64F b, FixedMatrix6x6_64F c) {
/* 227 */     c.a11 = a.a11 * b.a11 + a.a12 * b.a21 + a.a13 * b.a31 + a.a14 * b.a41 + a.a15 * b.a51 + a.a16 * b.a61;
/* 228 */     c.a12 = a.a11 * b.a12 + a.a12 * b.a22 + a.a13 * b.a32 + a.a14 * b.a42 + a.a15 * b.a52 + a.a16 * b.a62;
/* 229 */     c.a13 = a.a11 * b.a13 + a.a12 * b.a23 + a.a13 * b.a33 + a.a14 * b.a43 + a.a15 * b.a53 + a.a16 * b.a63;
/* 230 */     c.a14 = a.a11 * b.a14 + a.a12 * b.a24 + a.a13 * b.a34 + a.a14 * b.a44 + a.a15 * b.a54 + a.a16 * b.a64;
/* 231 */     c.a15 = a.a11 * b.a15 + a.a12 * b.a25 + a.a13 * b.a35 + a.a14 * b.a45 + a.a15 * b.a55 + a.a16 * b.a65;
/* 232 */     c.a16 = a.a11 * b.a16 + a.a12 * b.a26 + a.a13 * b.a36 + a.a14 * b.a46 + a.a15 * b.a56 + a.a16 * b.a66;
/* 233 */     c.a21 = a.a21 * b.a11 + a.a22 * b.a21 + a.a23 * b.a31 + a.a24 * b.a41 + a.a25 * b.a51 + a.a26 * b.a61;
/* 234 */     c.a22 = a.a21 * b.a12 + a.a22 * b.a22 + a.a23 * b.a32 + a.a24 * b.a42 + a.a25 * b.a52 + a.a26 * b.a62;
/* 235 */     c.a23 = a.a21 * b.a13 + a.a22 * b.a23 + a.a23 * b.a33 + a.a24 * b.a43 + a.a25 * b.a53 + a.a26 * b.a63;
/* 236 */     c.a24 = a.a21 * b.a14 + a.a22 * b.a24 + a.a23 * b.a34 + a.a24 * b.a44 + a.a25 * b.a54 + a.a26 * b.a64;
/* 237 */     c.a25 = a.a21 * b.a15 + a.a22 * b.a25 + a.a23 * b.a35 + a.a24 * b.a45 + a.a25 * b.a55 + a.a26 * b.a65;
/* 238 */     c.a26 = a.a21 * b.a16 + a.a22 * b.a26 + a.a23 * b.a36 + a.a24 * b.a46 + a.a25 * b.a56 + a.a26 * b.a66;
/* 239 */     c.a31 = a.a31 * b.a11 + a.a32 * b.a21 + a.a33 * b.a31 + a.a34 * b.a41 + a.a35 * b.a51 + a.a36 * b.a61;
/* 240 */     c.a32 = a.a31 * b.a12 + a.a32 * b.a22 + a.a33 * b.a32 + a.a34 * b.a42 + a.a35 * b.a52 + a.a36 * b.a62;
/* 241 */     c.a33 = a.a31 * b.a13 + a.a32 * b.a23 + a.a33 * b.a33 + a.a34 * b.a43 + a.a35 * b.a53 + a.a36 * b.a63;
/* 242 */     c.a34 = a.a31 * b.a14 + a.a32 * b.a24 + a.a33 * b.a34 + a.a34 * b.a44 + a.a35 * b.a54 + a.a36 * b.a64;
/* 243 */     c.a35 = a.a31 * b.a15 + a.a32 * b.a25 + a.a33 * b.a35 + a.a34 * b.a45 + a.a35 * b.a55 + a.a36 * b.a65;
/* 244 */     c.a36 = a.a31 * b.a16 + a.a32 * b.a26 + a.a33 * b.a36 + a.a34 * b.a46 + a.a35 * b.a56 + a.a36 * b.a66;
/* 245 */     c.a41 = a.a41 * b.a11 + a.a42 * b.a21 + a.a43 * b.a31 + a.a44 * b.a41 + a.a45 * b.a51 + a.a46 * b.a61;
/* 246 */     c.a42 = a.a41 * b.a12 + a.a42 * b.a22 + a.a43 * b.a32 + a.a44 * b.a42 + a.a45 * b.a52 + a.a46 * b.a62;
/* 247 */     c.a43 = a.a41 * b.a13 + a.a42 * b.a23 + a.a43 * b.a33 + a.a44 * b.a43 + a.a45 * b.a53 + a.a46 * b.a63;
/* 248 */     c.a44 = a.a41 * b.a14 + a.a42 * b.a24 + a.a43 * b.a34 + a.a44 * b.a44 + a.a45 * b.a54 + a.a46 * b.a64;
/* 249 */     c.a45 = a.a41 * b.a15 + a.a42 * b.a25 + a.a43 * b.a35 + a.a44 * b.a45 + a.a45 * b.a55 + a.a46 * b.a65;
/* 250 */     c.a46 = a.a41 * b.a16 + a.a42 * b.a26 + a.a43 * b.a36 + a.a44 * b.a46 + a.a45 * b.a56 + a.a46 * b.a66;
/* 251 */     c.a51 = a.a51 * b.a11 + a.a52 * b.a21 + a.a53 * b.a31 + a.a54 * b.a41 + a.a55 * b.a51 + a.a56 * b.a61;
/* 252 */     c.a52 = a.a51 * b.a12 + a.a52 * b.a22 + a.a53 * b.a32 + a.a54 * b.a42 + a.a55 * b.a52 + a.a56 * b.a62;
/* 253 */     c.a53 = a.a51 * b.a13 + a.a52 * b.a23 + a.a53 * b.a33 + a.a54 * b.a43 + a.a55 * b.a53 + a.a56 * b.a63;
/* 254 */     c.a54 = a.a51 * b.a14 + a.a52 * b.a24 + a.a53 * b.a34 + a.a54 * b.a44 + a.a55 * b.a54 + a.a56 * b.a64;
/* 255 */     c.a55 = a.a51 * b.a15 + a.a52 * b.a25 + a.a53 * b.a35 + a.a54 * b.a45 + a.a55 * b.a55 + a.a56 * b.a65;
/* 256 */     c.a56 = a.a51 * b.a16 + a.a52 * b.a26 + a.a53 * b.a36 + a.a54 * b.a46 + a.a55 * b.a56 + a.a56 * b.a66;
/* 257 */     c.a61 = a.a61 * b.a11 + a.a62 * b.a21 + a.a63 * b.a31 + a.a64 * b.a41 + a.a65 * b.a51 + a.a66 * b.a61;
/* 258 */     c.a62 = a.a61 * b.a12 + a.a62 * b.a22 + a.a63 * b.a32 + a.a64 * b.a42 + a.a65 * b.a52 + a.a66 * b.a62;
/* 259 */     c.a63 = a.a61 * b.a13 + a.a62 * b.a23 + a.a63 * b.a33 + a.a64 * b.a43 + a.a65 * b.a53 + a.a66 * b.a63;
/* 260 */     c.a64 = a.a61 * b.a14 + a.a62 * b.a24 + a.a63 * b.a34 + a.a64 * b.a44 + a.a65 * b.a54 + a.a66 * b.a64;
/* 261 */     c.a65 = a.a61 * b.a15 + a.a62 * b.a25 + a.a63 * b.a35 + a.a64 * b.a45 + a.a65 * b.a55 + a.a66 * b.a65;
/* 262 */     c.a66 = a.a61 * b.a16 + a.a62 * b.a26 + a.a63 * b.a36 + a.a64 * b.a46 + a.a65 * b.a56 + a.a66 * b.a66;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void multTransA(FixedMatrix6x6_64F a, FixedMatrix6x6_64F b, FixedMatrix6x6_64F c) {
/* 278 */     c.a11 = a.a11 * b.a11 + a.a21 * b.a21 + a.a31 * b.a31 + a.a41 * b.a41 + a.a51 * b.a51 + a.a61 * b.a61;
/* 279 */     c.a12 = a.a11 * b.a12 + a.a21 * b.a22 + a.a31 * b.a32 + a.a41 * b.a42 + a.a51 * b.a52 + a.a61 * b.a62;
/* 280 */     c.a13 = a.a11 * b.a13 + a.a21 * b.a23 + a.a31 * b.a33 + a.a41 * b.a43 + a.a51 * b.a53 + a.a61 * b.a63;
/* 281 */     c.a14 = a.a11 * b.a14 + a.a21 * b.a24 + a.a31 * b.a34 + a.a41 * b.a44 + a.a51 * b.a54 + a.a61 * b.a64;
/* 282 */     c.a15 = a.a11 * b.a15 + a.a21 * b.a25 + a.a31 * b.a35 + a.a41 * b.a45 + a.a51 * b.a55 + a.a61 * b.a65;
/* 283 */     c.a16 = a.a11 * b.a16 + a.a21 * b.a26 + a.a31 * b.a36 + a.a41 * b.a46 + a.a51 * b.a56 + a.a61 * b.a66;
/* 284 */     c.a21 = a.a12 * b.a11 + a.a22 * b.a21 + a.a32 * b.a31 + a.a42 * b.a41 + a.a52 * b.a51 + a.a62 * b.a61;
/* 285 */     c.a22 = a.a12 * b.a12 + a.a22 * b.a22 + a.a32 * b.a32 + a.a42 * b.a42 + a.a52 * b.a52 + a.a62 * b.a62;
/* 286 */     c.a23 = a.a12 * b.a13 + a.a22 * b.a23 + a.a32 * b.a33 + a.a42 * b.a43 + a.a52 * b.a53 + a.a62 * b.a63;
/* 287 */     c.a24 = a.a12 * b.a14 + a.a22 * b.a24 + a.a32 * b.a34 + a.a42 * b.a44 + a.a52 * b.a54 + a.a62 * b.a64;
/* 288 */     c.a25 = a.a12 * b.a15 + a.a22 * b.a25 + a.a32 * b.a35 + a.a42 * b.a45 + a.a52 * b.a55 + a.a62 * b.a65;
/* 289 */     c.a26 = a.a12 * b.a16 + a.a22 * b.a26 + a.a32 * b.a36 + a.a42 * b.a46 + a.a52 * b.a56 + a.a62 * b.a66;
/* 290 */     c.a31 = a.a13 * b.a11 + a.a23 * b.a21 + a.a33 * b.a31 + a.a43 * b.a41 + a.a53 * b.a51 + a.a63 * b.a61;
/* 291 */     c.a32 = a.a13 * b.a12 + a.a23 * b.a22 + a.a33 * b.a32 + a.a43 * b.a42 + a.a53 * b.a52 + a.a63 * b.a62;
/* 292 */     c.a33 = a.a13 * b.a13 + a.a23 * b.a23 + a.a33 * b.a33 + a.a43 * b.a43 + a.a53 * b.a53 + a.a63 * b.a63;
/* 293 */     c.a34 = a.a13 * b.a14 + a.a23 * b.a24 + a.a33 * b.a34 + a.a43 * b.a44 + a.a53 * b.a54 + a.a63 * b.a64;
/* 294 */     c.a35 = a.a13 * b.a15 + a.a23 * b.a25 + a.a33 * b.a35 + a.a43 * b.a45 + a.a53 * b.a55 + a.a63 * b.a65;
/* 295 */     c.a36 = a.a13 * b.a16 + a.a23 * b.a26 + a.a33 * b.a36 + a.a43 * b.a46 + a.a53 * b.a56 + a.a63 * b.a66;
/* 296 */     c.a41 = a.a14 * b.a11 + a.a24 * b.a21 + a.a34 * b.a31 + a.a44 * b.a41 + a.a54 * b.a51 + a.a64 * b.a61;
/* 297 */     c.a42 = a.a14 * b.a12 + a.a24 * b.a22 + a.a34 * b.a32 + a.a44 * b.a42 + a.a54 * b.a52 + a.a64 * b.a62;
/* 298 */     c.a43 = a.a14 * b.a13 + a.a24 * b.a23 + a.a34 * b.a33 + a.a44 * b.a43 + a.a54 * b.a53 + a.a64 * b.a63;
/* 299 */     c.a44 = a.a14 * b.a14 + a.a24 * b.a24 + a.a34 * b.a34 + a.a44 * b.a44 + a.a54 * b.a54 + a.a64 * b.a64;
/* 300 */     c.a45 = a.a14 * b.a15 + a.a24 * b.a25 + a.a34 * b.a35 + a.a44 * b.a45 + a.a54 * b.a55 + a.a64 * b.a65;
/* 301 */     c.a46 = a.a14 * b.a16 + a.a24 * b.a26 + a.a34 * b.a36 + a.a44 * b.a46 + a.a54 * b.a56 + a.a64 * b.a66;
/* 302 */     c.a51 = a.a15 * b.a11 + a.a25 * b.a21 + a.a35 * b.a31 + a.a45 * b.a41 + a.a55 * b.a51 + a.a65 * b.a61;
/* 303 */     c.a52 = a.a15 * b.a12 + a.a25 * b.a22 + a.a35 * b.a32 + a.a45 * b.a42 + a.a55 * b.a52 + a.a65 * b.a62;
/* 304 */     c.a53 = a.a15 * b.a13 + a.a25 * b.a23 + a.a35 * b.a33 + a.a45 * b.a43 + a.a55 * b.a53 + a.a65 * b.a63;
/* 305 */     c.a54 = a.a15 * b.a14 + a.a25 * b.a24 + a.a35 * b.a34 + a.a45 * b.a44 + a.a55 * b.a54 + a.a65 * b.a64;
/* 306 */     c.a55 = a.a15 * b.a15 + a.a25 * b.a25 + a.a35 * b.a35 + a.a45 * b.a45 + a.a55 * b.a55 + a.a65 * b.a65;
/* 307 */     c.a56 = a.a15 * b.a16 + a.a25 * b.a26 + a.a35 * b.a36 + a.a45 * b.a46 + a.a55 * b.a56 + a.a65 * b.a66;
/* 308 */     c.a61 = a.a16 * b.a11 + a.a26 * b.a21 + a.a36 * b.a31 + a.a46 * b.a41 + a.a56 * b.a51 + a.a66 * b.a61;
/* 309 */     c.a62 = a.a16 * b.a12 + a.a26 * b.a22 + a.a36 * b.a32 + a.a46 * b.a42 + a.a56 * b.a52 + a.a66 * b.a62;
/* 310 */     c.a63 = a.a16 * b.a13 + a.a26 * b.a23 + a.a36 * b.a33 + a.a46 * b.a43 + a.a56 * b.a53 + a.a66 * b.a63;
/* 311 */     c.a64 = a.a16 * b.a14 + a.a26 * b.a24 + a.a36 * b.a34 + a.a46 * b.a44 + a.a56 * b.a54 + a.a66 * b.a64;
/* 312 */     c.a65 = a.a16 * b.a15 + a.a26 * b.a25 + a.a36 * b.a35 + a.a46 * b.a45 + a.a56 * b.a55 + a.a66 * b.a65;
/* 313 */     c.a66 = a.a16 * b.a16 + a.a26 * b.a26 + a.a36 * b.a36 + a.a46 * b.a46 + a.a56 * b.a56 + a.a66 * b.a66;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void multTransAB(FixedMatrix6x6_64F a, FixedMatrix6x6_64F b, FixedMatrix6x6_64F c) {
/* 329 */     c.a11 = a.a11 * b.a11 + a.a21 * b.a12 + a.a31 * b.a13 + a.a41 * b.a14 + a.a51 * b.a15 + a.a61 * b.a16;
/* 330 */     c.a12 = a.a11 * b.a21 + a.a21 * b.a22 + a.a31 * b.a23 + a.a41 * b.a24 + a.a51 * b.a25 + a.a61 * b.a26;
/* 331 */     c.a13 = a.a11 * b.a31 + a.a21 * b.a32 + a.a31 * b.a33 + a.a41 * b.a34 + a.a51 * b.a35 + a.a61 * b.a36;
/* 332 */     c.a14 = a.a11 * b.a41 + a.a21 * b.a42 + a.a31 * b.a43 + a.a41 * b.a44 + a.a51 * b.a45 + a.a61 * b.a46;
/* 333 */     c.a15 = a.a11 * b.a51 + a.a21 * b.a52 + a.a31 * b.a53 + a.a41 * b.a54 + a.a51 * b.a55 + a.a61 * b.a56;
/* 334 */     c.a16 = a.a11 * b.a61 + a.a21 * b.a62 + a.a31 * b.a63 + a.a41 * b.a64 + a.a51 * b.a65 + a.a61 * b.a66;
/* 335 */     c.a21 = a.a12 * b.a11 + a.a22 * b.a12 + a.a32 * b.a13 + a.a42 * b.a14 + a.a52 * b.a15 + a.a62 * b.a16;
/* 336 */     c.a22 = a.a12 * b.a21 + a.a22 * b.a22 + a.a32 * b.a23 + a.a42 * b.a24 + a.a52 * b.a25 + a.a62 * b.a26;
/* 337 */     c.a23 = a.a12 * b.a31 + a.a22 * b.a32 + a.a32 * b.a33 + a.a42 * b.a34 + a.a52 * b.a35 + a.a62 * b.a36;
/* 338 */     c.a24 = a.a12 * b.a41 + a.a22 * b.a42 + a.a32 * b.a43 + a.a42 * b.a44 + a.a52 * b.a45 + a.a62 * b.a46;
/* 339 */     c.a25 = a.a12 * b.a51 + a.a22 * b.a52 + a.a32 * b.a53 + a.a42 * b.a54 + a.a52 * b.a55 + a.a62 * b.a56;
/* 340 */     c.a26 = a.a12 * b.a61 + a.a22 * b.a62 + a.a32 * b.a63 + a.a42 * b.a64 + a.a52 * b.a65 + a.a62 * b.a66;
/* 341 */     c.a31 = a.a13 * b.a11 + a.a23 * b.a12 + a.a33 * b.a13 + a.a43 * b.a14 + a.a53 * b.a15 + a.a63 * b.a16;
/* 342 */     c.a32 = a.a13 * b.a21 + a.a23 * b.a22 + a.a33 * b.a23 + a.a43 * b.a24 + a.a53 * b.a25 + a.a63 * b.a26;
/* 343 */     c.a33 = a.a13 * b.a31 + a.a23 * b.a32 + a.a33 * b.a33 + a.a43 * b.a34 + a.a53 * b.a35 + a.a63 * b.a36;
/* 344 */     c.a34 = a.a13 * b.a41 + a.a23 * b.a42 + a.a33 * b.a43 + a.a43 * b.a44 + a.a53 * b.a45 + a.a63 * b.a46;
/* 345 */     c.a35 = a.a13 * b.a51 + a.a23 * b.a52 + a.a33 * b.a53 + a.a43 * b.a54 + a.a53 * b.a55 + a.a63 * b.a56;
/* 346 */     c.a36 = a.a13 * b.a61 + a.a23 * b.a62 + a.a33 * b.a63 + a.a43 * b.a64 + a.a53 * b.a65 + a.a63 * b.a66;
/* 347 */     c.a41 = a.a14 * b.a11 + a.a24 * b.a12 + a.a34 * b.a13 + a.a44 * b.a14 + a.a54 * b.a15 + a.a64 * b.a16;
/* 348 */     c.a42 = a.a14 * b.a21 + a.a24 * b.a22 + a.a34 * b.a23 + a.a44 * b.a24 + a.a54 * b.a25 + a.a64 * b.a26;
/* 349 */     c.a43 = a.a14 * b.a31 + a.a24 * b.a32 + a.a34 * b.a33 + a.a44 * b.a34 + a.a54 * b.a35 + a.a64 * b.a36;
/* 350 */     c.a44 = a.a14 * b.a41 + a.a24 * b.a42 + a.a34 * b.a43 + a.a44 * b.a44 + a.a54 * b.a45 + a.a64 * b.a46;
/* 351 */     c.a45 = a.a14 * b.a51 + a.a24 * b.a52 + a.a34 * b.a53 + a.a44 * b.a54 + a.a54 * b.a55 + a.a64 * b.a56;
/* 352 */     c.a46 = a.a14 * b.a61 + a.a24 * b.a62 + a.a34 * b.a63 + a.a44 * b.a64 + a.a54 * b.a65 + a.a64 * b.a66;
/* 353 */     c.a51 = a.a15 * b.a11 + a.a25 * b.a12 + a.a35 * b.a13 + a.a45 * b.a14 + a.a55 * b.a15 + a.a65 * b.a16;
/* 354 */     c.a52 = a.a15 * b.a21 + a.a25 * b.a22 + a.a35 * b.a23 + a.a45 * b.a24 + a.a55 * b.a25 + a.a65 * b.a26;
/* 355 */     c.a53 = a.a15 * b.a31 + a.a25 * b.a32 + a.a35 * b.a33 + a.a45 * b.a34 + a.a55 * b.a35 + a.a65 * b.a36;
/* 356 */     c.a54 = a.a15 * b.a41 + a.a25 * b.a42 + a.a35 * b.a43 + a.a45 * b.a44 + a.a55 * b.a45 + a.a65 * b.a46;
/* 357 */     c.a55 = a.a15 * b.a51 + a.a25 * b.a52 + a.a35 * b.a53 + a.a45 * b.a54 + a.a55 * b.a55 + a.a65 * b.a56;
/* 358 */     c.a56 = a.a15 * b.a61 + a.a25 * b.a62 + a.a35 * b.a63 + a.a45 * b.a64 + a.a55 * b.a65 + a.a65 * b.a66;
/* 359 */     c.a61 = a.a16 * b.a11 + a.a26 * b.a12 + a.a36 * b.a13 + a.a46 * b.a14 + a.a56 * b.a15 + a.a66 * b.a16;
/* 360 */     c.a62 = a.a16 * b.a21 + a.a26 * b.a22 + a.a36 * b.a23 + a.a46 * b.a24 + a.a56 * b.a25 + a.a66 * b.a26;
/* 361 */     c.a63 = a.a16 * b.a31 + a.a26 * b.a32 + a.a36 * b.a33 + a.a46 * b.a34 + a.a56 * b.a35 + a.a66 * b.a36;
/* 362 */     c.a64 = a.a16 * b.a41 + a.a26 * b.a42 + a.a36 * b.a43 + a.a46 * b.a44 + a.a56 * b.a45 + a.a66 * b.a46;
/* 363 */     c.a65 = a.a16 * b.a51 + a.a26 * b.a52 + a.a36 * b.a53 + a.a46 * b.a54 + a.a56 * b.a55 + a.a66 * b.a56;
/* 364 */     c.a66 = a.a16 * b.a61 + a.a26 * b.a62 + a.a36 * b.a63 + a.a46 * b.a64 + a.a56 * b.a65 + a.a66 * b.a66;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void multTransB(FixedMatrix6x6_64F a, FixedMatrix6x6_64F b, FixedMatrix6x6_64F c) {
/* 380 */     c.a11 = a.a11 * b.a11 + a.a12 * b.a12 + a.a13 * b.a13 + a.a14 * b.a14 + a.a15 * b.a15 + a.a16 * b.a16;
/* 381 */     c.a12 = a.a11 * b.a21 + a.a12 * b.a22 + a.a13 * b.a23 + a.a14 * b.a24 + a.a15 * b.a25 + a.a16 * b.a26;
/* 382 */     c.a13 = a.a11 * b.a31 + a.a12 * b.a32 + a.a13 * b.a33 + a.a14 * b.a34 + a.a15 * b.a35 + a.a16 * b.a36;
/* 383 */     c.a14 = a.a11 * b.a41 + a.a12 * b.a42 + a.a13 * b.a43 + a.a14 * b.a44 + a.a15 * b.a45 + a.a16 * b.a46;
/* 384 */     c.a15 = a.a11 * b.a51 + a.a12 * b.a52 + a.a13 * b.a53 + a.a14 * b.a54 + a.a15 * b.a55 + a.a16 * b.a56;
/* 385 */     c.a16 = a.a11 * b.a61 + a.a12 * b.a62 + a.a13 * b.a63 + a.a14 * b.a64 + a.a15 * b.a65 + a.a16 * b.a66;
/* 386 */     c.a21 = a.a21 * b.a11 + a.a22 * b.a12 + a.a23 * b.a13 + a.a24 * b.a14 + a.a25 * b.a15 + a.a26 * b.a16;
/* 387 */     c.a22 = a.a21 * b.a21 + a.a22 * b.a22 + a.a23 * b.a23 + a.a24 * b.a24 + a.a25 * b.a25 + a.a26 * b.a26;
/* 388 */     c.a23 = a.a21 * b.a31 + a.a22 * b.a32 + a.a23 * b.a33 + a.a24 * b.a34 + a.a25 * b.a35 + a.a26 * b.a36;
/* 389 */     c.a24 = a.a21 * b.a41 + a.a22 * b.a42 + a.a23 * b.a43 + a.a24 * b.a44 + a.a25 * b.a45 + a.a26 * b.a46;
/* 390 */     c.a25 = a.a21 * b.a51 + a.a22 * b.a52 + a.a23 * b.a53 + a.a24 * b.a54 + a.a25 * b.a55 + a.a26 * b.a56;
/* 391 */     c.a26 = a.a21 * b.a61 + a.a22 * b.a62 + a.a23 * b.a63 + a.a24 * b.a64 + a.a25 * b.a65 + a.a26 * b.a66;
/* 392 */     c.a31 = a.a31 * b.a11 + a.a32 * b.a12 + a.a33 * b.a13 + a.a34 * b.a14 + a.a35 * b.a15 + a.a36 * b.a16;
/* 393 */     c.a32 = a.a31 * b.a21 + a.a32 * b.a22 + a.a33 * b.a23 + a.a34 * b.a24 + a.a35 * b.a25 + a.a36 * b.a26;
/* 394 */     c.a33 = a.a31 * b.a31 + a.a32 * b.a32 + a.a33 * b.a33 + a.a34 * b.a34 + a.a35 * b.a35 + a.a36 * b.a36;
/* 395 */     c.a34 = a.a31 * b.a41 + a.a32 * b.a42 + a.a33 * b.a43 + a.a34 * b.a44 + a.a35 * b.a45 + a.a36 * b.a46;
/* 396 */     c.a35 = a.a31 * b.a51 + a.a32 * b.a52 + a.a33 * b.a53 + a.a34 * b.a54 + a.a35 * b.a55 + a.a36 * b.a56;
/* 397 */     c.a36 = a.a31 * b.a61 + a.a32 * b.a62 + a.a33 * b.a63 + a.a34 * b.a64 + a.a35 * b.a65 + a.a36 * b.a66;
/* 398 */     c.a41 = a.a41 * b.a11 + a.a42 * b.a12 + a.a43 * b.a13 + a.a44 * b.a14 + a.a45 * b.a15 + a.a46 * b.a16;
/* 399 */     c.a42 = a.a41 * b.a21 + a.a42 * b.a22 + a.a43 * b.a23 + a.a44 * b.a24 + a.a45 * b.a25 + a.a46 * b.a26;
/* 400 */     c.a43 = a.a41 * b.a31 + a.a42 * b.a32 + a.a43 * b.a33 + a.a44 * b.a34 + a.a45 * b.a35 + a.a46 * b.a36;
/* 401 */     c.a44 = a.a41 * b.a41 + a.a42 * b.a42 + a.a43 * b.a43 + a.a44 * b.a44 + a.a45 * b.a45 + a.a46 * b.a46;
/* 402 */     c.a45 = a.a41 * b.a51 + a.a42 * b.a52 + a.a43 * b.a53 + a.a44 * b.a54 + a.a45 * b.a55 + a.a46 * b.a56;
/* 403 */     c.a46 = a.a41 * b.a61 + a.a42 * b.a62 + a.a43 * b.a63 + a.a44 * b.a64 + a.a45 * b.a65 + a.a46 * b.a66;
/* 404 */     c.a51 = a.a51 * b.a11 + a.a52 * b.a12 + a.a53 * b.a13 + a.a54 * b.a14 + a.a55 * b.a15 + a.a56 * b.a16;
/* 405 */     c.a52 = a.a51 * b.a21 + a.a52 * b.a22 + a.a53 * b.a23 + a.a54 * b.a24 + a.a55 * b.a25 + a.a56 * b.a26;
/* 406 */     c.a53 = a.a51 * b.a31 + a.a52 * b.a32 + a.a53 * b.a33 + a.a54 * b.a34 + a.a55 * b.a35 + a.a56 * b.a36;
/* 407 */     c.a54 = a.a51 * b.a41 + a.a52 * b.a42 + a.a53 * b.a43 + a.a54 * b.a44 + a.a55 * b.a45 + a.a56 * b.a46;
/* 408 */     c.a55 = a.a51 * b.a51 + a.a52 * b.a52 + a.a53 * b.a53 + a.a54 * b.a54 + a.a55 * b.a55 + a.a56 * b.a56;
/* 409 */     c.a56 = a.a51 * b.a61 + a.a52 * b.a62 + a.a53 * b.a63 + a.a54 * b.a64 + a.a55 * b.a65 + a.a56 * b.a66;
/* 410 */     c.a61 = a.a61 * b.a11 + a.a62 * b.a12 + a.a63 * b.a13 + a.a64 * b.a14 + a.a65 * b.a15 + a.a66 * b.a16;
/* 411 */     c.a62 = a.a61 * b.a21 + a.a62 * b.a22 + a.a63 * b.a23 + a.a64 * b.a24 + a.a65 * b.a25 + a.a66 * b.a26;
/* 412 */     c.a63 = a.a61 * b.a31 + a.a62 * b.a32 + a.a63 * b.a33 + a.a64 * b.a34 + a.a65 * b.a35 + a.a66 * b.a36;
/* 413 */     c.a64 = a.a61 * b.a41 + a.a62 * b.a42 + a.a63 * b.a43 + a.a64 * b.a44 + a.a65 * b.a45 + a.a66 * b.a46;
/* 414 */     c.a65 = a.a61 * b.a51 + a.a62 * b.a52 + a.a63 * b.a53 + a.a64 * b.a54 + a.a65 * b.a55 + a.a66 * b.a56;
/* 415 */     c.a66 = a.a61 * b.a61 + a.a62 * b.a62 + a.a63 * b.a63 + a.a64 * b.a64 + a.a65 * b.a65 + a.a66 * b.a66;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void mult(FixedMatrix6x6_64F a, FixedMatrix6_64F b, FixedMatrix6_64F c) {
/* 431 */     c.a1 = a.a11 * b.a1 + a.a12 * b.a2 + a.a13 * b.a3 + a.a14 * b.a4 + a.a15 * b.a5 + a.a16 * b.a6;
/* 432 */     c.a2 = a.a21 * b.a1 + a.a22 * b.a2 + a.a23 * b.a3 + a.a24 * b.a4 + a.a25 * b.a5 + a.a26 * b.a6;
/* 433 */     c.a3 = a.a31 * b.a1 + a.a32 * b.a2 + a.a33 * b.a3 + a.a34 * b.a4 + a.a35 * b.a5 + a.a36 * b.a6;
/* 434 */     c.a4 = a.a41 * b.a1 + a.a42 * b.a2 + a.a43 * b.a3 + a.a44 * b.a4 + a.a45 * b.a5 + a.a46 * b.a6;
/* 435 */     c.a5 = a.a51 * b.a1 + a.a52 * b.a2 + a.a53 * b.a3 + a.a54 * b.a4 + a.a55 * b.a5 + a.a56 * b.a6;
/* 436 */     c.a6 = a.a61 * b.a1 + a.a62 * b.a2 + a.a63 * b.a3 + a.a64 * b.a4 + a.a65 * b.a5 + a.a66 * b.a6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void mult(FixedMatrix6_64F a, FixedMatrix6x6_64F b, FixedMatrix6_64F c) {
/* 452 */     c.a1 = a.a1 * b.a11 + a.a2 * b.a21 + a.a3 * b.a31 + a.a4 * b.a41 + a.a5 * b.a51 + a.a6 * b.a61;
/* 453 */     c.a2 = a.a1 * b.a12 + a.a2 * b.a22 + a.a3 * b.a32 + a.a4 * b.a42 + a.a5 * b.a52 + a.a6 * b.a62;
/* 454 */     c.a3 = a.a1 * b.a13 + a.a2 * b.a23 + a.a3 * b.a33 + a.a4 * b.a43 + a.a5 * b.a53 + a.a6 * b.a63;
/* 455 */     c.a4 = a.a1 * b.a14 + a.a2 * b.a24 + a.a3 * b.a34 + a.a4 * b.a44 + a.a5 * b.a54 + a.a6 * b.a64;
/* 456 */     c.a5 = a.a1 * b.a15 + a.a2 * b.a25 + a.a3 * b.a35 + a.a4 * b.a45 + a.a5 * b.a55 + a.a6 * b.a65;
/* 457 */     c.a6 = a.a1 * b.a16 + a.a2 * b.a26 + a.a3 * b.a36 + a.a4 * b.a46 + a.a5 * b.a56 + a.a6 * b.a66;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double dot(FixedMatrix6_64F a, FixedMatrix6_64F b) {
/* 473 */     return a.a1 * b.a1 + a.a2 * b.a2 + a.a3 * b.a3 + a.a4 * b.a4 + a.a5 * b.a5 + a.a6 * b.a6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setIdentity(FixedMatrix6x6_64F a) {
/* 483 */     a.a11 = 1.0D; a.a21 = 0.0D; a.a31 = 0.0D; a.a41 = 0.0D; a.a51 = 0.0D; a.a61 = 0.0D;
/* 484 */     a.a12 = 0.0D; a.a22 = 1.0D; a.a32 = 0.0D; a.a42 = 0.0D; a.a52 = 0.0D; a.a62 = 0.0D;
/* 485 */     a.a13 = 0.0D; a.a23 = 0.0D; a.a33 = 1.0D; a.a43 = 0.0D; a.a53 = 0.0D; a.a63 = 0.0D;
/* 486 */     a.a14 = 0.0D; a.a24 = 0.0D; a.a34 = 0.0D; a.a44 = 1.0D; a.a54 = 0.0D; a.a64 = 0.0D;
/* 487 */     a.a15 = 0.0D; a.a25 = 0.0D; a.a35 = 0.0D; a.a45 = 0.0D; a.a55 = 1.0D; a.a65 = 0.0D;
/* 488 */     a.a16 = 0.0D; a.a26 = 0.0D; a.a36 = 0.0D; a.a46 = 0.0D; a.a56 = 0.0D; a.a66 = 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double trace(FixedMatrix6x6_64F a) {
/* 504 */     return a.a11 + a.a21 + a.a31 + a.a41 + a.a51 + a.a61;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void diag(FixedMatrix6x6_64F input, FixedMatrix6_64F out) {
/* 518 */     out.a1 = input.a11;
/* 519 */     out.a2 = input.a22;
/* 520 */     out.a3 = input.a33;
/* 521 */     out.a4 = input.a44;
/* 522 */     out.a5 = input.a55;
/* 523 */     out.a6 = input.a66;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double elementMax(FixedMatrix6x6_64F a) {
/* 537 */     double max = a.a11;
/* 538 */     max = Math.max(max, a.a12);
/* 539 */     max = Math.max(max, a.a13);
/* 540 */     max = Math.max(max, a.a14);
/* 541 */     max = Math.max(max, a.a15);
/* 542 */     max = Math.max(max, a.a16);
/* 543 */     max = Math.max(max, a.a21);
/* 544 */     max = Math.max(max, a.a22);
/* 545 */     max = Math.max(max, a.a23);
/* 546 */     max = Math.max(max, a.a24);
/* 547 */     max = Math.max(max, a.a25);
/* 548 */     max = Math.max(max, a.a26);
/* 549 */     max = Math.max(max, a.a31);
/* 550 */     max = Math.max(max, a.a32);
/* 551 */     max = Math.max(max, a.a33);
/* 552 */     max = Math.max(max, a.a34);
/* 553 */     max = Math.max(max, a.a35);
/* 554 */     max = Math.max(max, a.a36);
/* 555 */     max = Math.max(max, a.a41);
/* 556 */     max = Math.max(max, a.a42);
/* 557 */     max = Math.max(max, a.a43);
/* 558 */     max = Math.max(max, a.a44);
/* 559 */     max = Math.max(max, a.a45);
/* 560 */     max = Math.max(max, a.a46);
/* 561 */     max = Math.max(max, a.a51);
/* 562 */     max = Math.max(max, a.a52);
/* 563 */     max = Math.max(max, a.a53);
/* 564 */     max = Math.max(max, a.a54);
/* 565 */     max = Math.max(max, a.a55);
/* 566 */     max = Math.max(max, a.a56);
/* 567 */     max = Math.max(max, a.a61);
/* 568 */     max = Math.max(max, a.a62);
/* 569 */     max = Math.max(max, a.a63);
/* 570 */     max = Math.max(max, a.a64);
/* 571 */     max = Math.max(max, a.a65);
/* 572 */     max = Math.max(max, a.a66);
/*     */     
/* 574 */     return max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double elementMaxAbs(FixedMatrix6x6_64F a) {
/* 588 */     double max = a.a11;
/* 589 */     max = Math.max(max, Math.abs(a.a12));
/* 590 */     max = Math.max(max, Math.abs(a.a13));
/* 591 */     max = Math.max(max, Math.abs(a.a14));
/* 592 */     max = Math.max(max, Math.abs(a.a15));
/* 593 */     max = Math.max(max, Math.abs(a.a16));
/* 594 */     max = Math.max(max, Math.abs(a.a21));
/* 595 */     max = Math.max(max, Math.abs(a.a22));
/* 596 */     max = Math.max(max, Math.abs(a.a23));
/* 597 */     max = Math.max(max, Math.abs(a.a24));
/* 598 */     max = Math.max(max, Math.abs(a.a25));
/* 599 */     max = Math.max(max, Math.abs(a.a26));
/* 600 */     max = Math.max(max, Math.abs(a.a31));
/* 601 */     max = Math.max(max, Math.abs(a.a32));
/* 602 */     max = Math.max(max, Math.abs(a.a33));
/* 603 */     max = Math.max(max, Math.abs(a.a34));
/* 604 */     max = Math.max(max, Math.abs(a.a35));
/* 605 */     max = Math.max(max, Math.abs(a.a36));
/* 606 */     max = Math.max(max, Math.abs(a.a41));
/* 607 */     max = Math.max(max, Math.abs(a.a42));
/* 608 */     max = Math.max(max, Math.abs(a.a43));
/* 609 */     max = Math.max(max, Math.abs(a.a44));
/* 610 */     max = Math.max(max, Math.abs(a.a45));
/* 611 */     max = Math.max(max, Math.abs(a.a46));
/* 612 */     max = Math.max(max, Math.abs(a.a51));
/* 613 */     max = Math.max(max, Math.abs(a.a52));
/* 614 */     max = Math.max(max, Math.abs(a.a53));
/* 615 */     max = Math.max(max, Math.abs(a.a54));
/* 616 */     max = Math.max(max, Math.abs(a.a55));
/* 617 */     max = Math.max(max, Math.abs(a.a56));
/* 618 */     max = Math.max(max, Math.abs(a.a61));
/* 619 */     max = Math.max(max, Math.abs(a.a62));
/* 620 */     max = Math.max(max, Math.abs(a.a63));
/* 621 */     max = Math.max(max, Math.abs(a.a64));
/* 622 */     max = Math.max(max, Math.abs(a.a65));
/* 623 */     max = Math.max(max, Math.abs(a.a66));
/*     */     
/* 625 */     return max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double elementMin(FixedMatrix6x6_64F a) {
/* 639 */     double min = a.a11;
/* 640 */     min = Math.min(min, a.a12);
/* 641 */     min = Math.min(min, a.a13);
/* 642 */     min = Math.min(min, a.a14);
/* 643 */     min = Math.min(min, a.a15);
/* 644 */     min = Math.min(min, a.a16);
/* 645 */     min = Math.min(min, a.a21);
/* 646 */     min = Math.min(min, a.a22);
/* 647 */     min = Math.min(min, a.a23);
/* 648 */     min = Math.min(min, a.a24);
/* 649 */     min = Math.min(min, a.a25);
/* 650 */     min = Math.min(min, a.a26);
/* 651 */     min = Math.min(min, a.a31);
/* 652 */     min = Math.min(min, a.a32);
/* 653 */     min = Math.min(min, a.a33);
/* 654 */     min = Math.min(min, a.a34);
/* 655 */     min = Math.min(min, a.a35);
/* 656 */     min = Math.min(min, a.a36);
/* 657 */     min = Math.min(min, a.a41);
/* 658 */     min = Math.min(min, a.a42);
/* 659 */     min = Math.min(min, a.a43);
/* 660 */     min = Math.min(min, a.a44);
/* 661 */     min = Math.min(min, a.a45);
/* 662 */     min = Math.min(min, a.a46);
/* 663 */     min = Math.min(min, a.a51);
/* 664 */     min = Math.min(min, a.a52);
/* 665 */     min = Math.min(min, a.a53);
/* 666 */     min = Math.min(min, a.a54);
/* 667 */     min = Math.min(min, a.a55);
/* 668 */     min = Math.min(min, a.a56);
/* 669 */     min = Math.min(min, a.a61);
/* 670 */     min = Math.min(min, a.a62);
/* 671 */     min = Math.min(min, a.a63);
/* 672 */     min = Math.min(min, a.a64);
/* 673 */     min = Math.min(min, a.a65);
/* 674 */     min = Math.min(min, a.a66);
/*     */     
/* 676 */     return min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double elementMinAbs(FixedMatrix6x6_64F a) {
/* 690 */     double min = a.a11;
/* 691 */     min = Math.min(min, Math.abs(a.a12));
/* 692 */     min = Math.min(min, Math.abs(a.a13));
/* 693 */     min = Math.min(min, Math.abs(a.a14));
/* 694 */     min = Math.min(min, Math.abs(a.a15));
/* 695 */     min = Math.min(min, Math.abs(a.a16));
/* 696 */     min = Math.min(min, Math.abs(a.a21));
/* 697 */     min = Math.min(min, Math.abs(a.a22));
/* 698 */     min = Math.min(min, Math.abs(a.a23));
/* 699 */     min = Math.min(min, Math.abs(a.a24));
/* 700 */     min = Math.min(min, Math.abs(a.a25));
/* 701 */     min = Math.min(min, Math.abs(a.a26));
/* 702 */     min = Math.min(min, Math.abs(a.a31));
/* 703 */     min = Math.min(min, Math.abs(a.a32));
/* 704 */     min = Math.min(min, Math.abs(a.a33));
/* 705 */     min = Math.min(min, Math.abs(a.a34));
/* 706 */     min = Math.min(min, Math.abs(a.a35));
/* 707 */     min = Math.min(min, Math.abs(a.a36));
/* 708 */     min = Math.min(min, Math.abs(a.a41));
/* 709 */     min = Math.min(min, Math.abs(a.a42));
/* 710 */     min = Math.min(min, Math.abs(a.a43));
/* 711 */     min = Math.min(min, Math.abs(a.a44));
/* 712 */     min = Math.min(min, Math.abs(a.a45));
/* 713 */     min = Math.min(min, Math.abs(a.a46));
/* 714 */     min = Math.min(min, Math.abs(a.a51));
/* 715 */     min = Math.min(min, Math.abs(a.a52));
/* 716 */     min = Math.min(min, Math.abs(a.a53));
/* 717 */     min = Math.min(min, Math.abs(a.a54));
/* 718 */     min = Math.min(min, Math.abs(a.a55));
/* 719 */     min = Math.min(min, Math.abs(a.a56));
/* 720 */     min = Math.min(min, Math.abs(a.a61));
/* 721 */     min = Math.min(min, Math.abs(a.a62));
/* 722 */     min = Math.min(min, Math.abs(a.a63));
/* 723 */     min = Math.min(min, Math.abs(a.a64));
/* 724 */     min = Math.min(min, Math.abs(a.a65));
/* 725 */     min = Math.min(min, Math.abs(a.a66));
/*     */     
/* 727 */     return min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void elementMult(FixedMatrix6x6_64F a, FixedMatrix6x6_64F b) {
/* 739 */     a.a11 *= b.a11; a.a12 *= b.a12; a.a13 *= b.a13; a.a14 *= b.a14; a.a15 *= b.a15; a.a16 *= b.a16;
/* 740 */     a.a21 *= b.a21; a.a22 *= b.a22; a.a23 *= b.a23; a.a24 *= b.a24; a.a25 *= b.a25; a.a26 *= b.a26;
/* 741 */     a.a31 *= b.a31; a.a32 *= b.a32; a.a33 *= b.a33; a.a34 *= b.a34; a.a35 *= b.a35; a.a36 *= b.a36;
/* 742 */     a.a41 *= b.a41; a.a42 *= b.a42; a.a43 *= b.a43; a.a44 *= b.a44; a.a45 *= b.a45; a.a46 *= b.a46;
/* 743 */     a.a51 *= b.a51; a.a52 *= b.a52; a.a53 *= b.a53; a.a54 *= b.a54; a.a55 *= b.a55; a.a56 *= b.a56;
/* 744 */     a.a61 *= b.a61; a.a62 *= b.a62; a.a63 *= b.a63; a.a64 *= b.a64; a.a65 *= b.a65; a.a66 *= b.a66;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void elementMult(FixedMatrix6x6_64F a, FixedMatrix6x6_64F b, FixedMatrix6x6_64F c) {
/* 757 */     a.a11 *= b.a11; a.a12 *= b.a12; a.a13 *= b.a13; a.a14 *= b.a14; a.a15 *= b.a15; a.a16 *= b.a16;
/* 758 */     a.a21 *= b.a21; a.a22 *= b.a22; a.a23 *= b.a23; a.a24 *= b.a24; a.a25 *= b.a25; a.a26 *= b.a26;
/* 759 */     a.a31 *= b.a31; a.a32 *= b.a32; a.a33 *= b.a33; a.a34 *= b.a34; a.a35 *= b.a35; a.a36 *= b.a36;
/* 760 */     a.a41 *= b.a41; a.a42 *= b.a42; a.a43 *= b.a43; a.a44 *= b.a44; a.a45 *= b.a45; a.a46 *= b.a46;
/* 761 */     a.a51 *= b.a51; a.a52 *= b.a52; a.a53 *= b.a53; a.a54 *= b.a54; a.a55 *= b.a55; a.a56 *= b.a56;
/* 762 */     a.a61 *= b.a61; a.a62 *= b.a62; a.a63 *= b.a63; a.a64 *= b.a64; a.a65 *= b.a65; a.a66 *= b.a66;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void elementDiv(FixedMatrix6x6_64F a, FixedMatrix6x6_64F b) {
/* 774 */     a.a11 /= b.a11; a.a12 /= b.a12; a.a13 /= b.a13; a.a14 /= b.a14; a.a15 /= b.a15; a.a16 /= b.a16;
/* 775 */     a.a21 /= b.a21; a.a22 /= b.a22; a.a23 /= b.a23; a.a24 /= b.a24; a.a25 /= b.a25; a.a26 /= b.a26;
/* 776 */     a.a31 /= b.a31; a.a32 /= b.a32; a.a33 /= b.a33; a.a34 /= b.a34; a.a35 /= b.a35; a.a36 /= b.a36;
/* 777 */     a.a41 /= b.a41; a.a42 /= b.a42; a.a43 /= b.a43; a.a44 /= b.a44; a.a45 /= b.a45; a.a46 /= b.a46;
/* 778 */     a.a51 /= b.a51; a.a52 /= b.a52; a.a53 /= b.a53; a.a54 /= b.a54; a.a55 /= b.a55; a.a56 /= b.a56;
/* 779 */     a.a61 /= b.a61; a.a62 /= b.a62; a.a63 /= b.a63; a.a64 /= b.a64; a.a65 /= b.a65; a.a66 /= b.a66;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void elementDiv(FixedMatrix6x6_64F a, FixedMatrix6x6_64F b, FixedMatrix6x6_64F c) {
/* 792 */     a.a11 /= b.a11; a.a12 /= b.a12; a.a13 /= b.a13; a.a14 /= b.a14; a.a15 /= b.a15; a.a16 /= b.a16;
/* 793 */     a.a21 /= b.a21; a.a22 /= b.a22; a.a23 /= b.a23; a.a24 /= b.a24; a.a25 /= b.a25; a.a26 /= b.a26;
/* 794 */     a.a31 /= b.a31; a.a32 /= b.a32; a.a33 /= b.a33; a.a34 /= b.a34; a.a35 /= b.a35; a.a36 /= b.a36;
/* 795 */     a.a41 /= b.a41; a.a42 /= b.a42; a.a43 /= b.a43; a.a44 /= b.a44; a.a45 /= b.a45; a.a46 /= b.a46;
/* 796 */     a.a51 /= b.a51; a.a52 /= b.a52; a.a53 /= b.a53; a.a54 /= b.a54; a.a55 /= b.a55; a.a56 /= b.a56;
/* 797 */     a.a61 /= b.a61; a.a62 /= b.a62; a.a63 /= b.a63; a.a64 /= b.a64; a.a65 /= b.a65; a.a66 /= b.a66;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void scale(double alpha, FixedMatrix6x6_64F a) {
/* 811 */     a.a11 *= alpha; a.a12 *= alpha; a.a13 *= alpha; a.a14 *= alpha; a.a15 *= alpha; a.a16 *= alpha;
/* 812 */     a.a21 *= alpha; a.a22 *= alpha; a.a23 *= alpha; a.a24 *= alpha; a.a25 *= alpha; a.a26 *= alpha;
/* 813 */     a.a31 *= alpha; a.a32 *= alpha; a.a33 *= alpha; a.a34 *= alpha; a.a35 *= alpha; a.a36 *= alpha;
/* 814 */     a.a41 *= alpha; a.a42 *= alpha; a.a43 *= alpha; a.a44 *= alpha; a.a45 *= alpha; a.a46 *= alpha;
/* 815 */     a.a51 *= alpha; a.a52 *= alpha; a.a53 *= alpha; a.a54 *= alpha; a.a55 *= alpha; a.a56 *= alpha;
/* 816 */     a.a61 *= alpha; a.a62 *= alpha; a.a63 *= alpha; a.a64 *= alpha; a.a65 *= alpha; a.a66 *= alpha;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void scale(double alpha, FixedMatrix6x6_64F a, FixedMatrix6x6_64F b) {
/* 831 */     a.a11 *= alpha; a.a12 *= alpha; a.a13 *= alpha; a.a14 *= alpha; a.a15 *= alpha; a.a16 *= alpha;
/* 832 */     a.a21 *= alpha; a.a22 *= alpha; a.a23 *= alpha; a.a24 *= alpha; a.a25 *= alpha; a.a26 *= alpha;
/* 833 */     a.a31 *= alpha; a.a32 *= alpha; a.a33 *= alpha; a.a34 *= alpha; a.a35 *= alpha; a.a36 *= alpha;
/* 834 */     a.a41 *= alpha; a.a42 *= alpha; a.a43 *= alpha; a.a44 *= alpha; a.a45 *= alpha; a.a46 *= alpha;
/* 835 */     a.a51 *= alpha; a.a52 *= alpha; a.a53 *= alpha; a.a54 *= alpha; a.a55 *= alpha; a.a56 *= alpha;
/* 836 */     a.a61 *= alpha; a.a62 *= alpha; a.a63 *= alpha; a.a64 *= alpha; a.a65 *= alpha; a.a66 *= alpha;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void divide(FixedMatrix6x6_64F a, double alpha) {
/* 850 */     a.a11 /= alpha; a.a12 /= alpha; a.a13 /= alpha; a.a14 /= alpha; a.a15 /= alpha; a.a16 /= alpha;
/* 851 */     a.a21 /= alpha; a.a22 /= alpha; a.a23 /= alpha; a.a24 /= alpha; a.a25 /= alpha; a.a26 /= alpha;
/* 852 */     a.a31 /= alpha; a.a32 /= alpha; a.a33 /= alpha; a.a34 /= alpha; a.a35 /= alpha; a.a36 /= alpha;
/* 853 */     a.a41 /= alpha; a.a42 /= alpha; a.a43 /= alpha; a.a44 /= alpha; a.a45 /= alpha; a.a46 /= alpha;
/* 854 */     a.a51 /= alpha; a.a52 /= alpha; a.a53 /= alpha; a.a54 /= alpha; a.a55 /= alpha; a.a56 /= alpha;
/* 855 */     a.a61 /= alpha; a.a62 /= alpha; a.a63 /= alpha; a.a64 /= alpha; a.a65 /= alpha; a.a66 /= alpha;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void divide(FixedMatrix6x6_64F a, double alpha, FixedMatrix6x6_64F b) {
/* 870 */     a.a11 /= alpha; a.a12 /= alpha; a.a13 /= alpha; a.a14 /= alpha; a.a15 /= alpha; a.a16 /= alpha;
/* 871 */     a.a21 /= alpha; a.a22 /= alpha; a.a23 /= alpha; a.a24 /= alpha; a.a25 /= alpha; a.a26 /= alpha;
/* 872 */     a.a31 /= alpha; a.a32 /= alpha; a.a33 /= alpha; a.a34 /= alpha; a.a35 /= alpha; a.a36 /= alpha;
/* 873 */     a.a41 /= alpha; a.a42 /= alpha; a.a43 /= alpha; a.a44 /= alpha; a.a45 /= alpha; a.a46 /= alpha;
/* 874 */     a.a51 /= alpha; a.a52 /= alpha; a.a53 /= alpha; a.a54 /= alpha; a.a55 /= alpha; a.a56 /= alpha;
/* 875 */     a.a61 /= alpha; a.a62 /= alpha; a.a63 /= alpha; a.a64 /= alpha; a.a65 /= alpha; a.a66 /= alpha;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void changeSign(FixedMatrix6x6_64F a) {
/* 889 */     a.a11 = -a.a11; a.a12 = -a.a12; a.a13 = -a.a13; a.a14 = -a.a14; a.a15 = -a.a15; a.a16 = -a.a16;
/* 890 */     a.a21 = -a.a21; a.a22 = -a.a22; a.a23 = -a.a23; a.a24 = -a.a24; a.a25 = -a.a25; a.a26 = -a.a26;
/* 891 */     a.a31 = -a.a31; a.a32 = -a.a32; a.a33 = -a.a33; a.a34 = -a.a34; a.a35 = -a.a35; a.a36 = -a.a36;
/* 892 */     a.a41 = -a.a41; a.a42 = -a.a42; a.a43 = -a.a43; a.a44 = -a.a44; a.a45 = -a.a45; a.a46 = -a.a46;
/* 893 */     a.a51 = -a.a51; a.a52 = -a.a52; a.a53 = -a.a53; a.a54 = -a.a54; a.a55 = -a.a55; a.a56 = -a.a56;
/* 894 */     a.a61 = -a.a61; a.a62 = -a.a62; a.a63 = -a.a63; a.a64 = -a.a64; a.a65 = -a.a65; a.a66 = -a.a66;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void fill(FixedMatrix6x6_64F a, double v) {
/* 908 */     a.a11 = v; a.a12 = v; a.a13 = v; a.a14 = v; a.a15 = v; a.a16 = v;
/* 909 */     a.a21 = v; a.a22 = v; a.a23 = v; a.a24 = v; a.a25 = v; a.a26 = v;
/* 910 */     a.a31 = v; a.a32 = v; a.a33 = v; a.a34 = v; a.a35 = v; a.a36 = v;
/* 911 */     a.a41 = v; a.a42 = v; a.a43 = v; a.a44 = v; a.a45 = v; a.a46 = v;
/* 912 */     a.a51 = v; a.a52 = v; a.a53 = v; a.a54 = v; a.a55 = v; a.a56 = v;
/* 913 */     a.a61 = v; a.a62 = v; a.a63 = v; a.a64 = v; a.a65 = v; a.a66 = v;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\fixed\FixedOps6.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */