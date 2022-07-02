/*     */ package ic2.core.gui.dynamic;
/*     */ 
/*     */ import com.google.common.base.Supplier;
/*     */ import ic2.core.init.Localization;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ 
/*     */ 
/*     */ public class TextProvider
/*     */ {
/*     */   public static ITextProvider of(String text) {
/*  19 */     return text.isEmpty() ? new ConstantEmpty() : new Constant(text);
/*     */   }
/*     */   
/*     */   public static ITextProvider of(final Supplier<String> supplier) {
/*  23 */     return new AbstractTextProvider()
/*     */       {
/*     */         public String getRaw(Object base, Map<String, TextProvider.ITextProvider> tokens) {
/*  26 */           return (String)supplier.get();
/*     */         }
/*     */ 
/*     */         
/*     */         public String getConstant(Class<?> baseClass) {
/*  31 */           return (String)supplier.get();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static ITextProvider ofTranslated(String key) {
/*  37 */     return new Translate(new Constant(key));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ITextProvider parse(String text, Class<?> baseClass) {
/*  43 */     Queue<List<AbstractTextProvider>> continuations = Collections.asLifoQueue(new ArrayDeque<>());
/*  44 */     StringBuilder continuationTypes = new StringBuilder();
/*     */     
/*  46 */     char currentType = Character.MIN_VALUE;
/*  47 */     List<AbstractTextProvider> providers = new ArrayList<>();
/*     */     
/*  49 */     StringBuilder part = new StringBuilder(text.length());
/*  50 */     boolean escaped = false;
/*     */     
/*  52 */     for (int i = 0; i < text.length(); i++) {
/*  53 */       char c = text.charAt(i);
/*     */       
/*  55 */       if (escaped) {
/*     */         
/*  57 */         part.append(c);
/*  58 */         escaped = false;
/*  59 */       } else if (c == '\\') {
/*  60 */         escaped = true;
/*  61 */       } else if (c == '{') {
/*  62 */         finish(part, providers);
/*     */         
/*  64 */         continuations.add(providers);
/*  65 */         continuationTypes.append(currentType);
/*  66 */         currentType = c;
/*  67 */         providers = new ArrayList<>();
/*  68 */       } else if (currentType == '{' && c == ',') {
/*  69 */         finish(part, providers);
/*     */         
/*  71 */         providers.add(null);
/*  72 */       } else if (currentType == '{' && c == '}') {
/*  73 */         finish(part, providers);
/*     */ 
/*     */ 
/*     */         
/*  77 */         AbstractTextProvider format = null;
/*  78 */         List<AbstractTextProvider> args = new ArrayList<>();
/*  79 */         int start = 0;
/*     */         
/*  81 */         for (int j = start; j < providers.size(); j++) {
/*  82 */           if (providers.get(j) == null) {
/*  83 */             AbstractTextProvider abstractTextProvider = getProvider(providers, start, j);
/*     */             
/*  85 */             if (format == null) {
/*  86 */               format = abstractTextProvider;
/*     */             } else {
/*  88 */               args.add(abstractTextProvider);
/*     */             } 
/*     */             
/*  91 */             start = j + 1;
/*     */           } 
/*     */         } 
/*     */         
/*  95 */         AbstractTextProvider provider = getProvider(providers, start, providers.size());
/*     */         
/*  97 */         if (format == null) {
/*  98 */           format = provider;
/*     */         } else {
/* 100 */           args.add(provider);
/*     */         } 
/*     */         
/* 103 */         if (args.isEmpty()) {
/* 104 */           provider = new Translate(format);
/*     */         } else {
/* 106 */           provider = new TranslateFormat(format, args);
/*     */         } 
/*     */         
/* 109 */         providers = continuations.remove();
/* 110 */         currentType = continuationTypes.charAt(continuationTypes.length() - 1);
/* 111 */         continuationTypes.setLength(continuationTypes.length() - 1);
/*     */         
/* 113 */         providers.add(provider);
/* 114 */       } else if (c == '%') {
/* 115 */         if (currentType != '%') {
/* 116 */           if (i + 1 < text.length() && text.charAt(i + 1) == '%') {
/* 117 */             part.append('%');
/* 118 */             i++;
/*     */           } else {
/* 120 */             finish(part, providers);
/*     */             
/* 122 */             continuations.add(providers);
/* 123 */             continuationTypes.append(currentType);
/* 124 */             currentType = c;
/* 125 */             providers = new ArrayList<>();
/*     */           } 
/*     */         } else {
/* 128 */           finish(part, providers);
/*     */           
/* 130 */           AbstractTextProvider provider = getResolver(getProvider(providers, 0, providers.size()), baseClass);
/*     */           
/* 132 */           providers = continuations.remove();
/* 133 */           currentType = continuationTypes.charAt(continuationTypes.length() - 1);
/* 134 */           continuationTypes.setLength(continuationTypes.length() - 1);
/*     */           
/* 136 */           providers.add(provider);
/*     */         } 
/*     */       } else {
/* 139 */         part.append(c);
/*     */       } 
/*     */     } 
/*     */     
/* 143 */     finish(part, providers);
/*     */     
/* 145 */     if (currentType != '\000')
/*     */     {
/* 147 */       return new Constant("ERROR: unfinished token " + currentType + " in " + text); } 
/* 148 */     if (escaped)
/*     */     {
/* 150 */       return new Constant("ERROR: unfinished escape sequence in " + text);
/*     */     }
/* 152 */     return getProvider(providers, 0, providers.size());
/*     */   }
/*     */ 
/*     */   
/*     */   private static void finish(StringBuilder part, List<AbstractTextProvider> providers) {
/* 157 */     if (part.length() == 0)
/*     */       return; 
/* 159 */     providers.add(new Constant(part.toString()));
/* 160 */     part.setLength(0);
/*     */   }
/*     */   
/*     */   private static AbstractTextProvider getProvider(List<AbstractTextProvider> providers, int start, int end) {
/* 164 */     assert start <= end;
/*     */     
/* 166 */     if (start == end)
/* 167 */       return new ConstantEmpty(); 
/* 168 */     if (start + 1 == end) {
/* 169 */       return providers.get(start);
/*     */     }
/*     */     
/* 172 */     return new Merge(new ArrayList<>(providers.subList(start, end)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static AbstractTextProvider getResolver(AbstractTextProvider token, Class<?> baseClass) {
/* 177 */     String staticToken = token.getConstant(baseClass);
/* 178 */     if (staticToken == null) return new TokenResolverDynamic(token);
/*     */     
/* 180 */     String staticResult = resolveToken(staticToken, baseClass, null, emptyTokens());
/* 181 */     if (staticResult != null) return new Constant(staticResult);
/*     */ 
/*     */ 
/*     */     
/* 185 */     return new TokenResolverStatic(staticToken);
/*     */   }
/*     */   
/*     */   private static String resolveToken(String token, Class<?> baseClass, Object base, Map<String, ITextProvider> tokens) {
/* 189 */     ITextProvider ret = tokens.get(token);
/*     */     
/* 191 */     if (ret != null) {
/* 192 */       if (ret instanceof AbstractTextProvider) {
/* 193 */         return ((AbstractTextProvider)ret).getRaw(base, tokens);
/*     */       }
/* 195 */       return ret.get(base, tokens);
/*     */     } 
/*     */ 
/*     */     
/* 199 */     if (baseClass == null) return null;
/*     */     
/* 201 */     if (token.startsWith("base.")) {
/* 202 */       Object value = retrieve(token, "base.".length(), baseClass, base);
/*     */       
/* 204 */       return toString(value);
/*     */     } 
/*     */     
/* 207 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object retrieve(String path, int start, Class<?> subjectClass, Object subject) {
/*     */     int end;
/*     */     do {
/* 214 */       end = path.indexOf('.', start);
/* 215 */       if (end == -1) end = path.length();
/*     */       
/* 217 */       String part = path.substring(start, end);
/*     */       
/* 219 */       if (part.endsWith("()")) {
/* 220 */         part = part.substring(0, part.length() - "()".length());
/* 221 */         Method method = getMethodOptional(subjectClass, part);
/*     */         
/* 223 */         if (method == null)
/*     */         {
/* 225 */           return null;
/*     */         }
/*     */         
/* 228 */         subject = invokeMethodOptional(method, subject);
/* 229 */         if (subject == null) return null;
/*     */         
/* 231 */         subjectClass = subject.getClass();
/*     */       } else {
/* 233 */         Field field = getFieldOptional(subjectClass, part);
/*     */         
/* 235 */         if (field == null)
/*     */         {
/* 237 */           return null;
/*     */         }
/*     */         
/* 240 */         subject = getFieldValueOptional(field, subject);
/* 241 */         if (subject == null) return null;
/*     */         
/* 243 */         subjectClass = subject.getClass();
/*     */       } 
/*     */       
/* 246 */       start = end + 1;
/* 247 */     } while (end != path.length());
/*     */     
/* 249 */     return subject;
/*     */   }
/*     */   
/*     */   private static Method getMethodOptional(Class<?> cls, String name) {
/*     */     try {
/* 254 */       return cls.getMethod(name, new Class[0]);
/* 255 */     } catch (NoSuchMethodException e) {
/* 256 */       return null;
/* 257 */     } catch (SecurityException e) {
/* 258 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   private static Object invokeMethodOptional(Method method, Object obj) {
/*     */     Object ret;
/* 263 */     if (obj == null && !Modifier.isStatic(method.getModifiers()))
/*     */     {
/* 265 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 271 */       ret = method.invoke(obj, new Object[0]);
/* 272 */     } catch (Exception e) {
/* 273 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 276 */     if (ret == null);
/*     */ 
/*     */ 
/*     */     
/* 280 */     return ret;
/*     */   }
/*     */   
/*     */   private static Field getFieldOptional(Class<?> cls, String name) {
/*     */     try {
/* 285 */       return cls.getField(name);
/* 286 */     } catch (NoSuchFieldException e) {
/* 287 */       return null;
/* 288 */     } catch (SecurityException e) {
/* 289 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   private static Object getFieldValueOptional(Field field, Object obj) {
/*     */     Object ret;
/* 294 */     if (obj == null && !Modifier.isStatic(field.getModifiers()))
/*     */     {
/* 296 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 302 */       ret = field.get(obj);
/* 303 */     } catch (Exception e) {
/* 304 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 307 */     if (ret == null);
/*     */ 
/*     */ 
/*     */     
/* 311 */     return ret;
/*     */   }
/*     */   
/*     */   private static String toString(Object o) {
/* 315 */     if (o == null) return null;
/*     */     
/* 317 */     return o.toString();
/*     */   }
/*     */   
/*     */   public static Map<String, ITextProvider> emptyTokens() {
/* 321 */     return Collections.emptyMap();
/*     */   }
/*     */   
/*     */   public static interface ITextProvider {
/*     */     String get(Object param1Object, Map<String, ITextProvider> param1Map);
/*     */     
/*     */     String getOptional(Object param1Object, Map<String, ITextProvider> param1Map); }
/*     */   
/*     */   private static abstract class AbstractTextProvider implements ITextProvider { private AbstractTextProvider() {}
/*     */     
/*     */     public final String get(Object base, Map<String, TextProvider.ITextProvider> tokens) {
/* 332 */       String result = getRaw(base, tokens);
/* 333 */       if (result != null) return result;
/*     */       
/* 335 */       return "ERROR";
/*     */     }
/*     */ 
/*     */     
/*     */     public final String getOptional(Object base, Map<String, TextProvider.ITextProvider> tokens) {
/* 340 */       return getRaw(base, tokens);
/*     */     }
/*     */     
/*     */     protected abstract String getRaw(Object param1Object, Map<String, TextProvider.ITextProvider> param1Map);
/*     */     
/*     */     protected abstract String getConstant(Class<?> param1Class); }
/*     */ 
/*     */   
/*     */   private static class Constant extends AbstractTextProvider {
/*     */     public Constant(String text) {
/* 350 */       this.text = text;
/*     */     }
/*     */     private final String text;
/*     */     
/*     */     public String getRaw(Object base, Map<String, TextProvider.ITextProvider> tokens) {
/* 355 */       return this.text;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getConstant(Class<?> baseClass) {
/* 360 */       return this.text;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ConstantEmpty
/*     */     extends AbstractTextProvider {
/*     */     private ConstantEmpty() {}
/*     */     
/*     */     public String getRaw(Object base, Map<String, TextProvider.ITextProvider> tokens) {
/* 369 */       return "";
/*     */     }
/*     */ 
/*     */     
/*     */     public String getConstant(Class<?> baseClass) {
/* 374 */       return "";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Merge extends AbstractTextProvider {
/*     */     public Merge(List<TextProvider.AbstractTextProvider> providers) {
/* 380 */       this.providers = providers;
/*     */     }
/*     */     private final List<TextProvider.AbstractTextProvider> providers;
/*     */     
/*     */     public String getRaw(Object base, Map<String, TextProvider.ITextProvider> tokens) {
/* 385 */       StringBuilder ret = new StringBuilder();
/*     */       
/* 387 */       for (TextProvider.AbstractTextProvider provider : this.providers) {
/* 388 */         String part = provider.getRaw(base, tokens);
/* 389 */         if (part == null) return null;
/*     */         
/* 391 */         ret.append(part);
/*     */       } 
/*     */       
/* 394 */       return ret.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getConstant(Class<?> baseClass) {
/* 399 */       StringBuilder ret = new StringBuilder();
/*     */       
/* 401 */       for (TextProvider.AbstractTextProvider provider : this.providers) {
/* 402 */         String part = provider.getConstant(baseClass);
/* 403 */         if (part == null) return null;
/*     */         
/* 405 */         ret.append(part);
/*     */       } 
/*     */       
/* 408 */       return ret.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Translate extends AbstractTextProvider {
/*     */     private final TextProvider.AbstractTextProvider key;
/*     */     
/*     */     public Translate(TextProvider.AbstractTextProvider key) {
/* 416 */       this.key = key;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getRaw(Object base, Map<String, TextProvider.ITextProvider> tokens) {
/* 421 */       String key = this.key.getRaw(base, tokens);
/* 422 */       if (key == null) return null;
/*     */       
/* 424 */       return Localization.translate(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getConstant(Class<?> baseClass) {
/* 429 */       return null;
/*     */     } }
/*     */   
/*     */   private static class TranslateFormat extends AbstractTextProvider {
/*     */     private final TextProvider.AbstractTextProvider format;
/*     */     private final List<TextProvider.AbstractTextProvider> args;
/*     */     
/*     */     public TranslateFormat(TextProvider.AbstractTextProvider format, List<TextProvider.AbstractTextProvider> args) {
/* 437 */       this.format = format;
/* 438 */       this.args = args;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getRaw(Object base, Map<String, TextProvider.ITextProvider> tokens) {
/* 443 */       String format = this.format.getRaw(base, tokens);
/* 444 */       if (format == null) return null;
/*     */       
/* 446 */       Object[] cArgs = new Object[this.args.size()];
/*     */       
/* 448 */       for (int i = 0; i < this.args.size(); i++) {
/* 449 */         String arg = ((TextProvider.AbstractTextProvider)this.args.get(i)).getRaw(base, tokens);
/* 450 */         if (arg == null) return null;
/*     */         
/* 452 */         cArgs[i] = arg;
/*     */       } 
/*     */       
/* 455 */       return Localization.translate(format, cArgs);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getConstant(Class<?> baseClass) {
/* 460 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class TokenResolverDynamic
/*     */     extends AbstractTextProvider {
/*     */     private final TextProvider.AbstractTextProvider token;
/*     */     
/*     */     public TokenResolverDynamic(TextProvider.AbstractTextProvider token) {
/* 469 */       this.token = token;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getRaw(Object base, Map<String, TextProvider.ITextProvider> tokens) {
/* 474 */       String token = this.token.getRaw(base, tokens);
/* 475 */       if (token == null) return null;
/*     */       
/* 477 */       return TextProvider.resolveToken(token, (base != null) ? base.getClass() : null, base, tokens);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getConstant(Class<?> baseClass) {
/* 482 */       String token = this.token.getConstant(baseClass);
/* 483 */       if (token == null) return null;
/*     */       
/* 485 */       return TextProvider.resolveToken(token, baseClass, null, TextProvider.emptyTokens());
/*     */     }
/*     */   }
/*     */   
/*     */   private static class TokenResolverStatic extends AbstractTextProvider {
/*     */     private final String token;
/*     */     
/*     */     public TokenResolverStatic(String token) {
/* 493 */       this.token = token;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getRaw(Object base, Map<String, TextProvider.ITextProvider> tokens) {
/* 498 */       return TextProvider.resolveToken(this.token, (base != null) ? base.getClass() : null, base, tokens);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getConstant(Class<?> baseClass) {
/* 503 */       return TextProvider.resolveToken(this.token, baseClass, null, TextProvider.emptyTokens());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\dynamic\TextProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */