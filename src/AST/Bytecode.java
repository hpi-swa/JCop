
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class Bytecode extends java.lang.Object {
    // Declared in JVMBytecodes.jrag at line 13

    public final static byte NOP = 0;

    // Declared in JVMBytecodes.jrag at line 14

    public final static byte ACONST_NULL = 1;

    // Declared in JVMBytecodes.jrag at line 15

    public final static byte ICONST_M1 = 2;

    // Declared in JVMBytecodes.jrag at line 16

    public final static byte ICONST_0 = 3;

    // Declared in JVMBytecodes.jrag at line 17

    public final static byte ICONST_1 = 4;

    // Declared in JVMBytecodes.jrag at line 18

    public final static byte ICONST_2 = 5;

    // Declared in JVMBytecodes.jrag at line 19

    public final static byte ICONST_3 = 6;

    // Declared in JVMBytecodes.jrag at line 20

    public final static byte ICONST_4 = 7;

    // Declared in JVMBytecodes.jrag at line 21

    public final static byte ICONST_5 = 8;

    // Declared in JVMBytecodes.jrag at line 22

    public final static byte LCONST_0 = 9;

    // Declared in JVMBytecodes.jrag at line 23

    public final static byte LCONST_1 = 10;

    // Declared in JVMBytecodes.jrag at line 24

    public final static byte FCONST_0 = 11;

    // Declared in JVMBytecodes.jrag at line 25

    public final static byte FCONST_1 = 12;

    // Declared in JVMBytecodes.jrag at line 26

    public final static byte FCONST_2 = 13;

    // Declared in JVMBytecodes.jrag at line 27

    public final static byte DCONST_0 = 14;

    // Declared in JVMBytecodes.jrag at line 28

    public final static byte DCONST_1 = 15;

    // Declared in JVMBytecodes.jrag at line 29

    public final static byte BIPUSH = 16;

    // Declared in JVMBytecodes.jrag at line 30

    public final static byte SIPUSH = 17;

    // Declared in JVMBytecodes.jrag at line 31

    public final static byte LDC = 18;

    // Declared in JVMBytecodes.jrag at line 32

    public final static byte LDC_W = 19;

    // Declared in JVMBytecodes.jrag at line 33

    public final static byte LDC2_W = 20;

    // Declared in JVMBytecodes.jrag at line 34

    public final static byte ILOAD = 21;

    // Declared in JVMBytecodes.jrag at line 35

    public final static byte LLOAD = 22;

    // Declared in JVMBytecodes.jrag at line 36

    public final static byte FLOAD = 23;

    // Declared in JVMBytecodes.jrag at line 37

    public final static byte DLOAD = 24;

    // Declared in JVMBytecodes.jrag at line 38

    public final static byte ALOAD = 25;

    // Declared in JVMBytecodes.jrag at line 39

    public final static byte ILOAD_0 = 26;

    // Declared in JVMBytecodes.jrag at line 40

    public final static byte ILOAD_1 = 27;

    // Declared in JVMBytecodes.jrag at line 41

    public final static byte ILOAD_2 = 28;

    // Declared in JVMBytecodes.jrag at line 42

    public final static byte ILOAD_3 = 29;

    // Declared in JVMBytecodes.jrag at line 43

    public final static byte LLOAD_0 = 30;

    // Declared in JVMBytecodes.jrag at line 44

    public final static byte LLOAD_1 = 31;

    // Declared in JVMBytecodes.jrag at line 45

    public final static byte LLOAD_2 = 32;

    // Declared in JVMBytecodes.jrag at line 46

    public final static byte LLOAD_3 = 33;

    // Declared in JVMBytecodes.jrag at line 47

    public final static byte FLOAD_0 = 34;

    // Declared in JVMBytecodes.jrag at line 48

    public final static byte FLOAD_1 = 35;

    // Declared in JVMBytecodes.jrag at line 49

    public final static byte FLOAD_2 = 36;

    // Declared in JVMBytecodes.jrag at line 50

    public final static byte FLOAD_3 = 37;

    // Declared in JVMBytecodes.jrag at line 51

    public final static byte DLOAD_0 = 38;

    // Declared in JVMBytecodes.jrag at line 52

    public final static byte DLOAD_1 = 39;

    // Declared in JVMBytecodes.jrag at line 53

    public final static byte DLOAD_2 = 40;

    // Declared in JVMBytecodes.jrag at line 54

    public final static byte DLOAD_3 = 41;

    // Declared in JVMBytecodes.jrag at line 55

    public final static byte ALOAD_0 = 42;

    // Declared in JVMBytecodes.jrag at line 56

    public final static byte ALOAD_1 = 43;

    // Declared in JVMBytecodes.jrag at line 57

    public final static byte ALOAD_2 = 44;

    // Declared in JVMBytecodes.jrag at line 58

    public final static byte ALOAD_3 = 45;

    // Declared in JVMBytecodes.jrag at line 59

    public final static byte IALOAD = 46;

    // Declared in JVMBytecodes.jrag at line 60

    public final static byte LALOAD = 47;

    // Declared in JVMBytecodes.jrag at line 61

    public final static byte FALOAD = 48;

    // Declared in JVMBytecodes.jrag at line 62

    public final static byte DALOAD = 49;

    // Declared in JVMBytecodes.jrag at line 63

    public final static byte AALOAD = 50;

    // Declared in JVMBytecodes.jrag at line 64

    public final static byte BALOAD = 51;

    // Declared in JVMBytecodes.jrag at line 65

    public final static byte CALOAD = 52;

    // Declared in JVMBytecodes.jrag at line 66

    public final static byte SALOAD = 53;

    // Declared in JVMBytecodes.jrag at line 67

    public final static byte ISTORE = 54;

    // Declared in JVMBytecodes.jrag at line 68

    public final static byte LSTORE = 55;

    // Declared in JVMBytecodes.jrag at line 69

    public final static byte FSTORE = 56;

    // Declared in JVMBytecodes.jrag at line 70

    public final static byte DSTORE = 57;

    // Declared in JVMBytecodes.jrag at line 71

    public final static byte ASTORE = 58;

    // Declared in JVMBytecodes.jrag at line 72

    public final static byte ISTORE_0 = 59;

    // Declared in JVMBytecodes.jrag at line 73

    public final static byte ISTORE_1 = 60;

    // Declared in JVMBytecodes.jrag at line 74

    public final static byte ISTORE_2 = 61;

    // Declared in JVMBytecodes.jrag at line 75

    public final static byte ISTORE_3 = 62;

    // Declared in JVMBytecodes.jrag at line 76

    public final static byte LSTORE_0 = 63;

    // Declared in JVMBytecodes.jrag at line 77

    public final static byte LSTORE_1 = 64;

    // Declared in JVMBytecodes.jrag at line 78

    public final static byte LSTORE_2 = 65;

    // Declared in JVMBytecodes.jrag at line 79

    public final static byte LSTORE_3 = 66;

    // Declared in JVMBytecodes.jrag at line 80

    public final static byte FSTORE_0 = 67;

    // Declared in JVMBytecodes.jrag at line 81

    public final static byte FSTORE_1 = 68;

    // Declared in JVMBytecodes.jrag at line 82

    public final static byte FSTORE_2 = 69;

    // Declared in JVMBytecodes.jrag at line 83

    public final static byte FSTORE_3 = 70;

    // Declared in JVMBytecodes.jrag at line 84

    public final static byte DSTORE_0 = 71;

    // Declared in JVMBytecodes.jrag at line 85

    public final static byte DSTORE_1 = 72;

    // Declared in JVMBytecodes.jrag at line 86

    public final static byte DSTORE_2 = 73;

    // Declared in JVMBytecodes.jrag at line 87

    public final static byte DSTORE_3 = 74;

    // Declared in JVMBytecodes.jrag at line 88

    public final static byte ASTORE_0 = 75;

    // Declared in JVMBytecodes.jrag at line 89

    public final static byte ASTORE_1 = 76;

    // Declared in JVMBytecodes.jrag at line 90

    public final static byte ASTORE_2 = 77;

    // Declared in JVMBytecodes.jrag at line 91

    public final static byte ASTORE_3 = 78;

    // Declared in JVMBytecodes.jrag at line 92

    public final static byte IASTORE = 79;

    // Declared in JVMBytecodes.jrag at line 93

    public final static byte LASTORE = 80;

    // Declared in JVMBytecodes.jrag at line 94

    public final static byte FASTORE = 81;

    // Declared in JVMBytecodes.jrag at line 95

    public final static byte DASTORE = 82;

    // Declared in JVMBytecodes.jrag at line 96

    public final static byte AASTORE = 83;

    // Declared in JVMBytecodes.jrag at line 97

    public final static byte BASTORE = 84;

    // Declared in JVMBytecodes.jrag at line 98

    public final static byte CASTORE = 85;

    // Declared in JVMBytecodes.jrag at line 99

    public final static byte SASTORE = 86;

    // Declared in JVMBytecodes.jrag at line 100

    public final static byte POP = 87;

    // Declared in JVMBytecodes.jrag at line 101

    public final static byte POP2 = 88;

    // Declared in JVMBytecodes.jrag at line 102

    public final static byte DUP = 89;

    // Declared in JVMBytecodes.jrag at line 103

    public final static byte DUP_X1 = 90;

    // Declared in JVMBytecodes.jrag at line 104

    public final static byte DUP_X2 = 91;

    // Declared in JVMBytecodes.jrag at line 105

    public final static byte DUP2 = 92;

    // Declared in JVMBytecodes.jrag at line 106

    public final static byte DUP2_X1 = 93;

    // Declared in JVMBytecodes.jrag at line 107

    public final static byte DUP2_X2 = 94 ;

    // Declared in JVMBytecodes.jrag at line 108

    public final static byte SWAP = 95;

    // Declared in JVMBytecodes.jrag at line 109

    public final static byte IADD = 96;

    // Declared in JVMBytecodes.jrag at line 110

    public final static byte LADD = 97;

    // Declared in JVMBytecodes.jrag at line 111

    public final static byte FADD = 98;

    // Declared in JVMBytecodes.jrag at line 112

    public final static byte DADD = 99;

    // Declared in JVMBytecodes.jrag at line 113

    public final static byte ISUB = 100;

    // Declared in JVMBytecodes.jrag at line 114

    public final static byte LSUB = 101;

    // Declared in JVMBytecodes.jrag at line 115

    public final static byte FSUB = 102;

    // Declared in JVMBytecodes.jrag at line 116

    public final static byte DSUB = 103;

    // Declared in JVMBytecodes.jrag at line 117

    public final static byte IMUL = 104;

    // Declared in JVMBytecodes.jrag at line 118

    public final static byte LMUL = 105;

    // Declared in JVMBytecodes.jrag at line 119

    public final static byte FMUL = 106;

    // Declared in JVMBytecodes.jrag at line 120

    public final static byte DMUL = 107;

    // Declared in JVMBytecodes.jrag at line 121

    public final static byte IDIV = 108;

    // Declared in JVMBytecodes.jrag at line 122

    public final static byte LDIV = 109;

    // Declared in JVMBytecodes.jrag at line 123

    public final static byte FDIV = 110;

    // Declared in JVMBytecodes.jrag at line 124

    public final static byte DDIV = 111;

    // Declared in JVMBytecodes.jrag at line 125

    public final static byte IREM = 112;

    // Declared in JVMBytecodes.jrag at line 126

    public final static byte LREM = 113;

    // Declared in JVMBytecodes.jrag at line 127

    public final static byte FREM = 114;

    // Declared in JVMBytecodes.jrag at line 128

    public final static byte DREM = 115;

    // Declared in JVMBytecodes.jrag at line 129

    public final static byte INEG = 116;

    // Declared in JVMBytecodes.jrag at line 130

    public final static byte LNEG = 117;

    // Declared in JVMBytecodes.jrag at line 131

    public final static byte FNEG = 118;

    // Declared in JVMBytecodes.jrag at line 132

    public final static byte DNEG = 119;

    // Declared in JVMBytecodes.jrag at line 133

    public final static byte ISHL = 120;

    // Declared in JVMBytecodes.jrag at line 134

    public final static byte LSHL = 121;

    // Declared in JVMBytecodes.jrag at line 135

    public final static byte ISHR = 122;

    // Declared in JVMBytecodes.jrag at line 136

    public final static byte LSHR = 123;

    // Declared in JVMBytecodes.jrag at line 137

    public final static byte IUSHR = 124;

    // Declared in JVMBytecodes.jrag at line 138

    public final static byte LUSHR = 125;

    // Declared in JVMBytecodes.jrag at line 139

    public final static byte IAND = 126;

    // Declared in JVMBytecodes.jrag at line 140

    public final static byte LAND = 127;

    // Declared in JVMBytecodes.jrag at line 141

    public final static byte IOR = -256+128;

    // Declared in JVMBytecodes.jrag at line 142

    public final static byte LOR = -256+129;

    // Declared in JVMBytecodes.jrag at line 143

    public final static byte IXOR = -256+130;

    // Declared in JVMBytecodes.jrag at line 144

    public final static byte LXOR = -256+131;

    // Declared in JVMBytecodes.jrag at line 145

    public final static byte IINC = -256+132;

    // Declared in JVMBytecodes.jrag at line 146

    public final static byte I2L = -256+133;

    // Declared in JVMBytecodes.jrag at line 147

    public final static byte I2F = -256+134;

    // Declared in JVMBytecodes.jrag at line 148

    public final static byte I2D = -256+135;

    // Declared in JVMBytecodes.jrag at line 149

    public final static byte L2I = -256+136;

    // Declared in JVMBytecodes.jrag at line 150

    public final static byte L2F = -256+137;

    // Declared in JVMBytecodes.jrag at line 151

    public final static byte L2D = -256+138;

    // Declared in JVMBytecodes.jrag at line 152

    public final static byte F2I = -256+139;

    // Declared in JVMBytecodes.jrag at line 153

    public final static byte F2L = -256+140;

    // Declared in JVMBytecodes.jrag at line 154

    public final static byte F2D = -256+141;

    // Declared in JVMBytecodes.jrag at line 155

    public final static byte D2I = -256+142;

    // Declared in JVMBytecodes.jrag at line 156

    public final static byte D2L = -256+143;

    // Declared in JVMBytecodes.jrag at line 157

    public final static byte D2F = -256+144;

    // Declared in JVMBytecodes.jrag at line 158

    public final static byte I2B = -256+145;

    // Declared in JVMBytecodes.jrag at line 159

    public final static byte I2C = -256+146;

    // Declared in JVMBytecodes.jrag at line 160

    public final static byte I2S = -256+147;

    // Declared in JVMBytecodes.jrag at line 161

    public final static byte LCMP = -256+148;

    // Declared in JVMBytecodes.jrag at line 162

    public final static byte FCMPL = -256+149;

    // Declared in JVMBytecodes.jrag at line 163

    public final static byte FCMPG = -256+150;

    // Declared in JVMBytecodes.jrag at line 164

    public final static byte DCMPL = -256+151;

    // Declared in JVMBytecodes.jrag at line 165

    public final static byte DCMPG = -256+152;

    // Declared in JVMBytecodes.jrag at line 166

    public final static byte IFEQ = -256+153;

    // Declared in JVMBytecodes.jrag at line 167

    public final static byte IFNE = -256+154;

    // Declared in JVMBytecodes.jrag at line 168

    public final static byte IFLT = -256+155;

    // Declared in JVMBytecodes.jrag at line 169

    public final static byte IFGE = -256+156;

    // Declared in JVMBytecodes.jrag at line 170

    public final static byte IFGT = -256+157;

    // Declared in JVMBytecodes.jrag at line 171

    public final static byte IFLE = -256+158;

    // Declared in JVMBytecodes.jrag at line 172

    public final static byte IF_ICMPEQ = -256+159;

    // Declared in JVMBytecodes.jrag at line 173

    public final static byte IF_ICMPNE = -256+160;

    // Declared in JVMBytecodes.jrag at line 174

    public final static byte IF_ICMPLT = -256+161;

    // Declared in JVMBytecodes.jrag at line 175

    public final static byte IF_ICMPGE = -256+162;

    // Declared in JVMBytecodes.jrag at line 176

    public final static byte IF_ICMPGT = -256+163;

    // Declared in JVMBytecodes.jrag at line 177

    public final static byte IF_ICMPLE = -256+164;

    // Declared in JVMBytecodes.jrag at line 178

    public final static byte IF_ACMPEQ = -256+165;

    // Declared in JVMBytecodes.jrag at line 179

    public final static byte IF_ACMPNE = -256+166;

    // Declared in JVMBytecodes.jrag at line 180

    public final static byte GOTO = -256+167;

    // Declared in JVMBytecodes.jrag at line 181

    public final static byte JSR = -256+168;

    // Declared in JVMBytecodes.jrag at line 182

    public final static byte RET = -256+169;

    // Declared in JVMBytecodes.jrag at line 183

    public final static byte TABLESWITCH = -256+170;

    // Declared in JVMBytecodes.jrag at line 184

    public final static byte LOOKUPSWITCH = -256+171;

    // Declared in JVMBytecodes.jrag at line 185

    public final static byte IRETURN = -256+172;

    // Declared in JVMBytecodes.jrag at line 186

    public final static byte LRETURN = -256+173;

    // Declared in JVMBytecodes.jrag at line 187

    public final static byte FRETURN = -256+174 ;

    // Declared in JVMBytecodes.jrag at line 188

    public final static byte DRETURN = -256+175;

    // Declared in JVMBytecodes.jrag at line 189

    public final static byte ARETURN = -256+176;

    // Declared in JVMBytecodes.jrag at line 190

    public final static byte RETURN = -256+177;

    // Declared in JVMBytecodes.jrag at line 191

    public final static byte GETSTATIC = -256+178 ;

    // Declared in JVMBytecodes.jrag at line 192

    public final static byte PUTSTATIC = -256+179;

    // Declared in JVMBytecodes.jrag at line 193

    public final static byte GETFIELD = -256+180;

    // Declared in JVMBytecodes.jrag at line 194

    public final static byte PUTFIELD = -256+181;

    // Declared in JVMBytecodes.jrag at line 195

    public final static byte INVOKEVIRTUAL = -256+182;

    // Declared in JVMBytecodes.jrag at line 196

    public final static byte INVOKESPECIAL = -256+183;

    // Declared in JVMBytecodes.jrag at line 197

    public final static byte INVOKESTATIC = -256+184;

    // Declared in JVMBytecodes.jrag at line 198

    public final static byte INVOKEINTERFACE = -256+185;

    // Declared in JVMBytecodes.jrag at line 200

    //Opcode 186 is unused
    public final static byte NEW = -256+187;

    // Declared in JVMBytecodes.jrag at line 201

    public final static byte NEWARRAY = -256+188;

    // Declared in JVMBytecodes.jrag at line 202

    public final static byte ANEWARRAY = -256+189;

    // Declared in JVMBytecodes.jrag at line 203

    public final static byte ARRAYLENGTH = -256+190;

    // Declared in JVMBytecodes.jrag at line 204

    public final static byte ATHROW = -256+191;

    // Declared in JVMBytecodes.jrag at line 205

    public final static byte CHECKCAST = -256+192;

    // Declared in JVMBytecodes.jrag at line 206

    public final static byte INSTANCEOF = -256+193;

    // Declared in JVMBytecodes.jrag at line 207

    public final static byte MONITORENTER = -256+194;

    // Declared in JVMBytecodes.jrag at line 208

    public final static byte MONITOREXIT = -256+195;

    // Declared in JVMBytecodes.jrag at line 209

    public final static byte WIDE = -256+196;

    // Declared in JVMBytecodes.jrag at line 210

    public final static byte MULTIANEWARRAY = -256+197;

    // Declared in JVMBytecodes.jrag at line 211

    public final static byte IFNULL = -256+198;

    // Declared in JVMBytecodes.jrag at line 212

    public final static byte IFNONNULL = -256+199;

    // Declared in JVMBytecodes.jrag at line 213

    public final static byte GOTO_W = -256+200;

    // Declared in JVMBytecodes.jrag at line 214

    public final static byte JSR_W = -256+201;

    // Declared in JVMBytecodes.jrag at line 215

    public final static byte BREAKPOINT = -256+202;

    // Declared in JVMBytecodes.jrag at line 216

    public final static byte IMPDEP1 = -256+254;

    // Declared in JVMBytecodes.jrag at line 217

    public final static byte IMPDEP2 = -256+255;


}
