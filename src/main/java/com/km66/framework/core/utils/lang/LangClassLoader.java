package com.km66.framework.core.utils.lang;

import com.km66.framework.core.utils.lang.asm.TargetObjectAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class LangClassLoader extends ClassLoader implements Opcodes {

    public void run() throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException {
        ClassReader cr = new ClassReader("TargetObject");
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor classAdapter = new TargetObjectAdapter(Opcodes.ASM4,cw);
        cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
        byte[] data = cw.toByteArray();
        LangClassLoader loader = new LangClassLoader();
        Class<?> appClass=loader.defineClass(null, data, 0,data.length);
        appClass.getMethods()[0].invoke(appClass.newInstance(), new Object[]{});
    }
}
