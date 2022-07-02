/*    */ package ic2.core.ref;
/*    */ 
/*    */ public enum ItemFolder {
/*  4 */   root(null),
/*  5 */   armor,
/*  6 */   battery,
/*  7 */   bcTrigger,
/*  8 */   boat,
/*  9 */   brewing,
/* 10 */   cable,
/* 11 */   cell,
/* 12 */   crafting,
/* 13 */   crop,
/* 14 */   pipe,
/* 15 */   reactor,
/* 16 */   reactorFuelRod("reactor/fuel_rod"),
/* 17 */   resource,
/* 18 */   resourceCasing("resource/casing"),
/* 19 */   resourceCrushed("resource/crushed"),
/* 20 */   resourceDust("resource/dust"),
/* 21 */   resourceIngot("resource/ingot"),
/* 22 */   resourceNuclear("resource/nuclear"),
/* 23 */   resourcePlate("resource/plate"),
/* 24 */   resourcePurified("resource/purified"),
/* 25 */   rotor,
/* 26 */   tfbp,
/* 27 */   tool,
/* 28 */   toolElectric("tool/electric"),
/* 29 */   toolPainter("tool/painter"),
/* 30 */   turnable,
/* 31 */   upgrade;
/*    */   
/*    */   ItemFolder() {
/* 34 */     this.path = name();
/*    */   }
/*    */   final String path;
/*    */   ItemFolder(String path) {
/* 38 */     this.path = path;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ref\ItemFolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */