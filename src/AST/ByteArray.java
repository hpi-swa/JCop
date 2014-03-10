
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;
/*************************************************************
   * Auxiliary class
   *************************************************************/
public class ByteArray extends java.lang.Object {
    // Declared in CodeGeneration.jrag at line 379

    private int stackDepth = 0;

    // Declared in CodeGeneration.jrag at line 380

    private int maxStackDepth = 0;

    // Declared in CodeGeneration.jrag at line 381

    private int size = 64;

    // Declared in CodeGeneration.jrag at line 382

    private byte[] bytes = new byte[size];

    // Declared in CodeGeneration.jrag at line 383

    private int pos = 0;

    // Declared in CodeGeneration.jrag at line 384

    private int lastGotoPos = 0;

    // Declared in CodeGeneration.jrag at line 385

    ByteArray add(int i) {return add((byte)i);}

    // Declared in CodeGeneration.jrag at line 386

    ByteArray add(byte b) {
      if(pos >= size) {
        byte[] ba = new byte[size * 2];
        System.arraycopy(bytes, 0, ba, 0, size);
        size *= 2;
        bytes = ba;
      }
      bytes[pos++] = b;
      return this;
    }

    // Declared in CodeGeneration.jrag at line 396

    ByteArray add4(int i) { 
      add(i >> 24 & 0xff);
      add(i >> 16 & 0xff);
      add(i >> 8 & 0xff);
      add(i & 0xff);
      return this;
    }

    // Declared in CodeGeneration.jrag at line 403

    ByteArray add2(int index) {
      add(index >> 8 & 0xff);
      add(index & 0xff);
      return this;
    }

    // Declared in CodeGeneration.jrag at line 408

    ByteArray emit(byte b) {
      changeStackDepth(BytecodeDebug.stackChange(b));
      add(b);
      return this;
    }

    // Declared in CodeGeneration.jrag at line 413

    ByteArray emitGoto(byte b) {
      changeStackDepth(BytecodeDebug.stackChange(b));
      lastGotoPos = pos;
      add(b);
      return this;
    }

    // Declared in CodeGeneration.jrag at line 419

    ByteArray emit(byte b, int stackChange) {
      changeStackDepth(stackChange);
      add(b);
      return this;
    }

    // Declared in CodeGeneration.jrag at line 425

    
    public int maxStackDepth() {
      return maxStackDepth;
    }

    // Declared in CodeGeneration.jrag at line 428

    public int stackDepth() {
      return stackDepth;
    }

    // Declared in CodeGeneration.jrag at line 431

    public void changeStackDepth(int i) {
      stackDepth += i;
      if(stackDepth > maxStackDepth)
        maxStackDepth = stackDepth;
    }

    // Declared in CodeGeneration.jrag at line 437

    
    public int pos() {return pos;}

    // Declared in CodeGeneration.jrag at line 438

    public int lastGotoPos() {return lastGotoPos;}

    // Declared in CodeGeneration.jrag at line 439

    public void setPos(int index) { pos = index; }

    // Declared in CodeGeneration.jrag at line 440

    public int size() {return pos;}

    // Declared in CodeGeneration.jrag at line 441

    public byte get(int index) {return bytes[index];}

    // Declared in CodeGeneration.jrag at line 442

    public void set(int index, byte value) {bytes[index] = value;}

    // Declared in CodeGeneration.jrag at line 443

    public String toString() {
      StringBuffer b = new StringBuffer();
      for(int i = 0; i < pos; i++) b.append(" " + bytes[i]);
      return b.toString();
    }

    // Declared in CodeGeneration.jrag at line 448

    public byte[] toArray() {
      byte[] b = new byte[pos];
      System.arraycopy(bytes, 0, b, 0, pos);
      return b;
    }


}
