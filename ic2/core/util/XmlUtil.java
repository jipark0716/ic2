/*    */ package ic2.core.util;
/*    */ 
/*    */ import org.xml.sax.Attributes;
/*    */ import org.xml.sax.SAXException;
/*    */ 
/*    */ public final class XmlUtil {
/*    */   public static String getAttr(Attributes attributes, String name) throws SAXException {
/*  8 */     String val = attributes.getValue(name);
/*  9 */     if (val == null) throw new SAXException("missing attribute: " + name);
/*    */     
/* 11 */     return val;
/*    */   }
/*    */   
/*    */   public static String getAttr(Attributes attributes, String name, String defValue) {
/* 15 */     String val = attributes.getValue(name);
/* 16 */     if (val == null) return defValue;
/*    */     
/* 18 */     return val;
/*    */   }
/*    */   
/*    */   public static boolean getBoolAttr(Attributes attributes, String name) throws SAXException {
/* 22 */     String val = attributes.getValue(name);
/* 23 */     if (val == null) throw new SAXException("missing attribute: " + name);
/*    */     
/* 25 */     return parseBool(val);
/*    */   }
/*    */   
/*    */   public static boolean getBoolAttr(Attributes attributes, String name, boolean defValue) throws SAXException {
/* 29 */     String val = attributes.getValue(name);
/* 30 */     if (val == null) return defValue;
/*    */     
/* 32 */     return parseBool(val);
/*    */   }
/*    */   
/*    */   public static boolean parseBool(String str) throws SAXException {
/* 36 */     if (str.equals("true"))
/* 37 */       return true; 
/* 38 */     if (str.equals("false")) {
/* 39 */       return false;
/*    */     }
/* 41 */     throw new SAXException("invalid bool value: " + str);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getIntAttr(Attributes attributes, String name) throws SAXException {
/* 46 */     String val = attributes.getValue(name);
/* 47 */     if (val == null) throw new SAXException("missing attribute: " + name);
/*    */     
/* 49 */     return parseInt(val);
/*    */   }
/*    */   
/*    */   public static int getIntAttr(Attributes attributes, String name, int defValue) {
/* 53 */     String val = attributes.getValue(name);
/* 54 */     if (val == null) return defValue;
/*    */     
/* 56 */     return parseInt(val);
/*    */   }
/*    */   
/*    */   public static int getIntAttr(Attributes attributes, String nameA, String nameB, int defValue) {
/* 60 */     String val = attributes.getValue(nameA);
/*    */     
/* 62 */     if (val == null) {
/* 63 */       val = attributes.getValue(nameB);
/* 64 */       if (val == null) return defValue;
/*    */     
/*    */     } 
/* 67 */     return parseInt(val);
/*    */   }
/*    */   
/*    */   public static int parseInt(String str) {
/* 71 */     if (str.startsWith("#"))
/* 72 */       return Integer.parseInt(str.substring(1), 16); 
/* 73 */     if (str.startsWith("0x")) {
/* 74 */       return Integer.parseInt(str.substring(2), 16);
/*    */     }
/* 76 */     return Integer.parseInt(str);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\XmlUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */