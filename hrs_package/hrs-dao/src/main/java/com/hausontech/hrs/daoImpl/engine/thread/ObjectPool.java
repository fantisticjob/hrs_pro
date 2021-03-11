package com.hausontech.hrs.daoImpl.engine.thread;

import java.util.Vector;

public class ObjectPool {

  public static final int UNLIMITED = Integer.MAX_VALUE;

  public static class CreateException extends Exception {
    public CreateException() {
    }

    public CreateException(String msg) {
      super(msg);
    }
  }

  public static class RemoveException extends Exception {
    public RemoveException() {
    }

    public RemoveException(String msg) {
      super(msg);
    }
  }

  public static interface ObjectFactory {
    public Object create() throws CreateException;

    public void remove(Object obj) throws RemoveException;
  }

  private Vector pool = new Vector();
  private ObjectFactory objFactory;
  private Class objClass;
  private int maxSize, curSize = 0;

  public ObjectPool(ObjectFactory objFactory) throws IllegalArgumentException {
    this(objFactory, null, UNLIMITED);
  }

  public ObjectPool(ObjectFactory objFactory, Class objClass, int maxSize)
      throws IllegalArgumentException {
    if (objFactory == null)
      throw new IllegalArgumentException("Object factory must not be null.");

    this.objFactory = objFactory;
    this.objClass = objClass;

    if (maxSize > 0)
      this.maxSize = maxSize;
    else
      throw new IllegalArgumentException("Object pool size must be greater than zero.");
  }

  public ObjectPool(ObjectFactory objFactory, Class objClass, int maxSize, boolean createObjects)
      throws IllegalArgumentException, CreateException {
    if (objFactory == null)
      throw new IllegalArgumentException("Object factory must not be null.");

    this.objFactory = objFactory;
    this.objClass = objClass;

    if (maxSize > 0)
      this.maxSize = maxSize;
    else
      throw new IllegalArgumentException("Object pool size must be greater than zero.");

    if (createObjects == true) {
      for (int i = 0; i < maxSize; i++) {
        Object obj = objFactory.create();
        pool.addElement(obj);
      }
      curSize = maxSize;
    }
  }

  public synchronized Object checkout() throws CreateException {

    if (pool.size() > 0) {

      return pool.remove(0);
    } else if (curSize < maxSize) {
      Object obj = objFactory.create();

      if (objClass != null && !objClass.isInstance(obj))
        throw new CreateException("Object factory created " + obj.getClass()
            + ", differs from desired " + objClass);

      ++curSize;
      return obj;
    } else {
      while (pool.size() == 0) {
        try {
          wait();
        } catch (Exception e) {
        }
      }

      return pool.remove(0);
    }
  }

  public synchronized void checkin(Object obj) throws IllegalArgumentException {
    if (obj == null)
      throw new IllegalArgumentException("Object factory must not be null.");

    if (objClass != null && !objClass.isInstance(obj))
      throw new IllegalArgumentException("Input object " + obj.getClass()
          + ", differs from desired " + objClass);
    pool.addElement(obj);
    notify();
  }

  public synchronized void clear() throws RemoveException {
    Object obj;
    RemoveException re = null;

    while (pool.size() > 0) {
      obj = pool.remove(0);

      try {
        objFactory.remove(obj);
      } catch (RemoveException e) {
        re = e;
      } catch (Exception e) {
        re = new RemoveException(e.toString());
      }
    }

    if (re != null)
      throw re;
  }

  //
  public static void main(String[] args) {
    final ObjectPool op = new ObjectPool(new ObjectPool.ObjectFactory() {
      public Object create() throws ObjectPool.CreateException {
        System.out.println("Creating an object...");
        return new Object();
      }

      public void remove(Object obj) throws ObjectPool.RemoveException {
        System.out.println("Removing an object: " + obj);
      }
    }, Object.class, 10);

    try {
      for (int i = 0; i < 15; ++i) {
        Thread t = new Thread(new Runnable() {
          public void run() {
            try {
              Object obj = op.checkout();

              System.out.println(Thread.currentThread() + " got " + obj);

              try {
                Thread.sleep((long) (Math.random() * 5000));
              } catch (Exception e) {
              }

              op.checkin(obj);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
        t.start();

        System.out.println(t + " started...");

        Thread.yield();
      }

      try {
        Thread.sleep(10000);
      } catch (Exception e) {
      }

      op.clear();
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      System.in.read();
    } catch (Exception e) {
    }
  }

}
