
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class GLBTypeFactory extends java.lang.Object {
    // Declared in GLBTypeFactory.jadd at line 44

        // TODO add support for array types.
        public static TypeDecl glb(final ArrayList typeList) {
            TypeDecl retType = ((TypeDecl) typeList.get(0)).unknownType();
            TypeDecl cls = mostSpecificSuperClass(typeList);
            if (cls != null) {
                ArrayList intersectInterfaceList = new ArrayList();
                ArrayList allInterfaceList = new ArrayList();
                for (Iterator itera = typeList.iterator(); itera.hasNext();) {
                    TypeDecl typeDecl = (TypeDecl) itera.next();
                    addInterfaces(intersectInterfaceList, allInterfaceList, typeDecl);
                }

                // remove all interfaces that are not most specific.
                greatestLowerBounds(intersectInterfaceList);
                // check for interface compatibility.
                if (checkInterfaceCompatibility(allInterfaceList)
                        && checkClassInterfaceCompatibility(cls, intersectInterfaceList)) {
                    greatestLowerBounds(typeList);
                    if(typeList.size() == 1) {
                      retType = (TypeDecl)typeList.iterator().next();
                    }
                    else {
                      retType = retType.lookupGLBType(typeList);
                    }
                }
            }
            return retType;
        }

    // Declared in GLBTypeFactory.jadd at line 77


        /**
         * @param intersectInterfaceList
         * @param allInterfaceList
         * @param typeDecl
         */
        private static void addInterfaces(ArrayList intersectInterfaceList, ArrayList allInterfaceList, TypeDecl typeDecl) {
            if(typeDecl.isInterfaceDecl()) {
                intersectInterfaceList.add((InterfaceDecl)typeDecl);
                allInterfaceList.add((InterfaceDecl)typeDecl);
            }
            else if (typeDecl instanceof TypeVariable) {
                TypeVariable varTD = (TypeVariable)typeDecl;
                // add the interfaces created for type variables to
                // interface list to be checked for compatibility
                intersectInterfaceList.add(varTD.toInterface());
                // add the bounds of the type variable that are interfaces.
                allInterfaceList.addAll(varTD.implementedInterfaces());
            }
            else if (typeDecl instanceof LUBType) {
                allInterfaceList.addAll(typeDecl.implementedInterfaces());
            }
            else if (typeDecl instanceof GLBType) {
                allInterfaceList.addAll(typeDecl.implementedInterfaces());
            }
        }

    // Declared in GLBTypeFactory.jadd at line 113


        /**
         * See JLS section 4.9 about Intersection Types
         * <p>
         * For each <i>T<sub>i</sub></i>, 1 &le; i &le; n, let <i>C<sub>i</sub></i>
         * be the most specific class or array type such that <i>T<sub>i</sub></i>
         * &lt;: <i>C<sub>i</sub></i> Then there must be some <i>T<sub>k</sub></i>
         * &lt;: <i>C<sub>k</sub></i> such that <i>C<sub>k</sub></i> &lt;:
         * <i>C<sub>i</sub></i> for any <i>i</i>, 1 &le; i &le; n, or a
         * compile-time error occurs.
         * 
         * @param T
         * @param types
         * @return the most specific class that all elements in <i>types</i> are a
         *         subtype of. Or null if no such class exists.
         */
        public final static TypeDecl mostSpecificSuperClass(final ArrayList types) {
            ArrayList csList = new ArrayList();
            for(Iterator iter = types.iterator(); iter.hasNext(); ) {
              csList.add(mostSpecificSuperClass((TypeDecl)iter.next()));
            }

            // find Tk with Ck
            greatestLowerBounds(csList);
            if(csList.size() == 1) {
                // OK
                return (TypeDecl) csList.get(0);
            }
            else {
                // Ck does not exist.
                return null;
            }
        }

    // Declared in GLBTypeFactory.jadd at line 137


        /**
         * Return most specific superclass of t.
         * 
         * @param t
         * @return
         */
        private final static TypeDecl mostSpecificSuperClass(final TypeDecl t) {
            HashSet superTypes = new HashSet();
            addSuperClasses(t, superTypes);

            if (superTypes.isEmpty())
                return t.typeObject();

            ArrayList result = new ArrayList(superTypes.size());
            result.addAll(superTypes);
            greatestLowerBounds(result);

            if (result.size() == 1)
                return (TypeDecl) result.get(0);
            else
                return (TypeDecl) t.typeObject();
        }

    // Declared in GLBTypeFactory.jadd at line 168


        /**
         * Add the superclasses (<i>C<sub>i</sub></i>) of <i>t</i> to the set
         * <i>result</i>.
         * <ul>
         * <li>If <i>t</i> is a class, then <i>C<sub>i</sub></i> is t itself.</li>
         * <li>If <i>t</i> is a type variable, then <i>C<sub>i</sub></i> is the
         * first class encountered in it bounds</li>
         * <li>It <i>t</i> is an intersection type, then <i>C<sub>i</sub></i>
         * is class that is a member of the intersection, otherwise it's Object</li>
         * </ul>
         * 
         * @param t
         * @param result
         */
        private static final void addSuperClasses(final TypeDecl t, final HashSet result) {
            if (t == null)
                return;

            // class
            if (t.isClassDecl() && !result.contains(t)) {
                result.add((ClassDecl) t);
            }
            // type variable, probably called from from 1st if case.
            else if (t.isTypeVariable()) {
                TypeVariable var = (TypeVariable) t;
                for (int i = 0; i < var.getNumTypeBound(); i++)
                    addSuperClasses(var.getTypeBound(i).type(), result);
            }
            // intersection type
            else if (t instanceof LUBType || t instanceof GLBType) {
                result.add(t);
            }
            // interface
            else if (t.isInterfaceDecl())
                result.add((ClassDecl) t.typeObject());

        }

    // Declared in GLBTypeFactory.jadd at line 197


        /**
         * @param T
         * @param ifaceList
         * @return
         */
        private static boolean checkInterfaceCompatibility(ArrayList ifaceList) {
            for (int i = 0; i < ifaceList.size(); i++) {
                HashSet superISet_i = Constraints
                        .parameterizedSupertypes((TypeDecl) ifaceList.get(i));
                for (Iterator iter1 = superISet_i.iterator(); iter1.hasNext();) {
                    InterfaceDecl superIface_i = (InterfaceDecl) iter1.next();

                    if (superIface_i instanceof ParInterfaceDecl) {
                        ParInterfaceDecl pi = (ParInterfaceDecl) superIface_i;
                        for (int j = i + 1; j < ifaceList.size(); j++) {
                            HashSet superISet_j = Constraints
                                    .parameterizedSupertypes((TypeDecl) ifaceList
                                            .get(j));
                            for (Iterator iter2 = superISet_j.iterator(); iter2
                                    .hasNext();) {
                                InterfaceDecl superIface_j = (InterfaceDecl) iter2
                                        .next();
                                if (superIface_j instanceof ParInterfaceDecl) {
                                    ParInterfaceDecl pj = (ParInterfaceDecl) superIface_j;
                                    if (pi != pj
                                            && pi.genericDecl() == pj.genericDecl()
                                            && !pi.sameArgument(pj)) {
                                        return false;

                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }

    // Declared in GLBTypeFactory.jadd at line 237


        /**
         * @param t
         * @param cls
         * @param ifaceList
         * @return
         */
        private static boolean checkClassInterfaceCompatibility(TypeDecl cls,
                ArrayList ifaceList) {
            HashSet implementedInterfaces = cls.implementedInterfaces();
            for (Iterator iter1 = implementedInterfaces.iterator(); iter1.hasNext();) {
                InterfaceDecl impInterface = (InterfaceDecl) iter1.next();

                if (impInterface instanceof ParInterfaceDecl) {
                    ParInterfaceDecl impParIface = (ParInterfaceDecl) impInterface;
                    for (Iterator iter2 = ifaceList.iterator(); iter2.hasNext();) {
                        InterfaceDecl iface = (InterfaceDecl) iter2.next();

                        if (iface instanceof ParInterfaceDecl) {
                            ParInterfaceDecl parIface = (ParInterfaceDecl) iface;
                            if (parIface != impParIface
                                    && parIface.genericDecl() == impParIface
                                            .genericDecl()
                                    && !parIface.sameArgument(impParIface)) {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }

    // Declared in GLBTypeFactory.jadd at line 268


        /**
         * Find the greatest lower bound(s).
         * 
         * @param types
         */
        public static final void greatestLowerBounds(ArrayList types) {
            for (int i = 0; i < types.size(); i++) {
                TypeDecl U = (TypeDecl) types.get(i);
                for (int j = i + 1; j < types.size(); j++) {
                    TypeDecl V = (TypeDecl) types.get(j);
                    if (U == null || V == null)
                        continue;
                    if (U.instanceOf(V))
                        types.set(j, null);
                    else if (V.instanceOf(U))
                        types.set(i, null);
                }
            }
            // filter null's
            removeNullValues(types);
        }

    // Declared in GLBTypeFactory.jadd at line 290


        /**
         * Remove null values from the given list
         * 
         * @param types
         */
        public static final void removeNullValues(ArrayList types) {
            ArrayList filter = new ArrayList(1);
            filter.add(null);
            types.removeAll(filter);
        }


}
