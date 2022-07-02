/*    */ package ic2.core.profile;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.zip.ZipFile;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProfileRoot
/*    */   extends ProfileTarget
/*    */ {
/*    */   public ProfileRoot(File root) {
/* 17 */     super(root, "");
/*    */   }
/*    */ 
/*    */   
/*    */   public File asFile() {
/* 22 */     return this.root;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ZipFile makeZip() throws IOException {
/* 27 */     return new ZipFile(this.root);
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream asStream() throws IOException {
/* 32 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public ProfileTarget offset(String extra) {
/* 37 */     return new ProfileTarget(this.root, extra);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 42 */     return "ProfileRoot<" + this.root + '>';
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\profile\ProfileRoot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */