/*     */ package ic2.core.util;
/*     */ 
/*     */ import com.google.common.collect.Iterators;
/*     */ import java.util.AbstractQueue;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.RunnableFuture;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ public class PriorityExecutor
/*     */   extends ThreadPoolExecutor {
/*     */   public PriorityExecutor(int threadCount) {
/*  26 */     super(threadCount, threadCount, 0L, TimeUnit.MILLISECONDS, new FixedPriorityQueue<>(), new ThreadFactoryImpl());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <E> List<? extends Future<E>> submitAll(List<Callable<E>> tasks) {
/*  35 */     List<RunnableFuture<E>> ret = new ArrayList<>(tasks.size());
/*     */     
/*  37 */     for (Callable<E> task : tasks) {
/*  38 */       if (task == null) throw new NullPointerException();
/*     */       
/*  40 */       ret.add(newTaskFor(task));
/*     */     } 
/*     */     
/*  43 */     executeAll((List)ret);
/*     */     
/*  45 */     return (List)ret;
/*     */   }
/*     */   
/*     */   public <E> RunnableFuture<E> makeTask(Callable<E> callable) {
/*  49 */     return newTaskFor(callable);
/*     */   }
/*     */   
/*     */   public void executeAll(List<? extends Runnable> tasks) {
/*  53 */     if (isShutdown()) throw new RejectedExecutionException("Tasks " + tasks + " rejected from " + this + "."); 
/*  54 */     while (prestartCoreThread());
/*     */     
/*  56 */     getQueue().addAll(tasks);
/*     */   }
/*     */   
/*     */   public static interface CustomPriority
/*     */   {
/*     */     PriorityExecutor.Priority getPriority();
/*     */   }
/*     */   
/*     */   public enum Priority {
/*  65 */     High,
/*  66 */     Default,
/*  67 */     Low;
/*     */   }
/*     */   
/*     */   private static class FixedPriorityQueue<E> extends AbstractQueue<E> implements BlockingQueue<E> {
/*     */     public FixedPriorityQueue() {
/*  72 */       for (PriorityExecutor.Priority priority : PriorityExecutor.Priority.values()) {
/*  73 */         this.queues.put(priority, new ArrayDeque<>());
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized E poll() {
/*  79 */       for (Queue<E> queue : this.queues.values()) {
/*  80 */         E ret = queue.poll();
/*  81 */         if (ret != null) return ret;
/*     */       
/*     */       } 
/*  84 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized E peek() {
/*  89 */       for (Queue<E> queue : this.queues.values()) {
/*  90 */         E ret = queue.peek();
/*  91 */         if (ret != null) return ret;
/*     */       
/*     */       } 
/*  94 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized int size() {
/*  99 */       int ret = 0;
/*     */       
/* 101 */       for (Queue<E> queue : this.queues.values()) {
/* 102 */         ret += queue.size();
/*     */       }
/*     */       
/* 105 */       return ret;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized Iterator<E> iterator() {
/* 110 */       List<Iterator<E>> iterators = new ArrayList<>(this.queues.size());
/*     */       
/* 112 */       for (Queue<E> queue : this.queues.values()) {
/* 113 */         iterators.add(queue.iterator());
/*     */       }
/*     */       
/* 116 */       return Iterators.concat(iterators.iterator());
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized boolean offer(E e) {
/* 121 */       Queue<E> queue = this.queues.get(getPriority(e));
/* 122 */       queue.offer(e);
/* 123 */       notify();
/*     */       
/* 125 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void put(E e) throws InterruptedException {
/* 130 */       offer(e);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
/* 135 */       return offer(e);
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized E take() throws InterruptedException {
/* 140 */       E ret = poll();
/*     */       
/* 142 */       while (ret == null) {
/* 143 */         wait();
/* 144 */         ret = poll();
/*     */       } 
/*     */       
/* 147 */       return ret;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized E poll(long timeout, TimeUnit unit) throws InterruptedException {
/* 152 */       E ret = poll();
/* 153 */       if (ret != null) return ret;
/*     */       
/* 155 */       long endTime = System.nanoTime() + unit.toNanos(timeout);
/*     */       
/*     */       do {
/* 158 */         long duration = endTime - System.nanoTime();
/* 159 */         if (duration <= 0L)
/*     */           break; 
/* 161 */         wait(duration / 1000000L, (int)(duration % 1000000L));
/* 162 */         ret = poll();
/* 163 */       } while (ret == null);
/*     */       
/* 165 */       return ret;
/*     */     }
/*     */ 
/*     */     
/*     */     public int remainingCapacity() {
/* 170 */       return Integer.MAX_VALUE;
/*     */     }
/*     */ 
/*     */     
/*     */     public int drainTo(Collection<? super E> c) {
/* 175 */       return drainTo(c, 2147483647);
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized int drainTo(Collection<? super E> c, int maxElements) {
/* 180 */       int ret = 0;
/*     */       
/* 182 */       for (Queue<E> queue : this.queues.values()) {
/* 183 */         while (ret < maxElements) {
/* 184 */           E x = queue.poll();
/* 185 */           if (x == null)
/*     */             break; 
/* 187 */           c.add(x);
/* 188 */           ret++;
/*     */         } 
/*     */       } 
/*     */       
/* 192 */       return ret;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized void clear() {
/* 197 */       for (Queue<E> queue : this.queues.values()) {
/* 198 */         queue.clear();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized boolean contains(Object o) {
/* 204 */       for (Queue<E> queue : this.queues.values()) {
/* 205 */         if (queue.contains(o)) return true;
/*     */       
/*     */       } 
/* 208 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized boolean removeAll(Collection<?> c) {
/* 213 */       boolean ret = false;
/*     */       
/* 215 */       for (Queue<E> queue : this.queues.values()) {
/* 216 */         if (queue.removeAll(c)) ret = true;
/*     */       
/*     */       } 
/* 219 */       return ret;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized boolean retainAll(Collection<?> c) {
/* 224 */       boolean ret = false;
/*     */       
/* 226 */       for (Queue<E> queue : this.queues.values()) {
/* 227 */         if (queue.retainAll(c)) ret = true;
/*     */       
/*     */       } 
/* 230 */       return ret;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized Object[] toArray() {
/* 235 */       return super.toArray();
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized <T> T[] toArray(T[] a) {
/* 240 */       return (T[])super.toArray((Object[])a);
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized String toString() {
/* 245 */       return super.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized boolean addAll(Collection<? extends E> c) {
/* 250 */       if (c == null) throw new NullPointerException(); 
/* 251 */       if (c == this) throw new IllegalArgumentException();
/*     */       
/* 253 */       for (E e : c) {
/* 254 */         Queue<E> queue = this.queues.get(getPriority(e));
/* 255 */         queue.offer(e);
/*     */       } 
/*     */       
/* 258 */       notifyAll();
/*     */       
/* 260 */       return !c.isEmpty();
/*     */     }
/*     */     
/*     */     private PriorityExecutor.Priority getPriority(E x) {
/* 264 */       if (x instanceof PriorityExecutor.CustomPriority) {
/* 265 */         return ((PriorityExecutor.CustomPriority)x).getPriority();
/*     */       }
/* 267 */       return PriorityExecutor.Priority.Default;
/*     */     }
/*     */ 
/*     */     
/* 271 */     private final Map<PriorityExecutor.Priority, Queue<E>> queues = new EnumMap<>(PriorityExecutor.Priority.class);
/*     */   }
/*     */   
/*     */   private static class ThreadFactoryImpl
/*     */     implements ThreadFactory {
/* 276 */     private final ThreadGroup group = Thread.currentThread().getThreadGroup();
/*     */ 
/*     */ 
/*     */     
/*     */     public Thread newThread(Runnable r) {
/* 281 */       Thread thread = new Thread(this.group, r, "ic2-poolthread-" + number.getAndIncrement(), 0L);
/* 282 */       thread.setDaemon(true);
/* 283 */       thread.setPriority(5);
/*     */       
/* 285 */       return thread;
/*     */     }
/*     */ 
/*     */     
/* 289 */     private static final AtomicInteger number = new AtomicInteger(1);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\PriorityExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */