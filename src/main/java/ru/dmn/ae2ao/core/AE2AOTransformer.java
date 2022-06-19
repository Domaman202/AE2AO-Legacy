package ru.dmn.ae2ao.core;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

public class AE2AOTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (name.equals("appeng.tile.networking.TileController")) {
            ClassReader reader = new ClassReader(basicClass);
            ClassWriter writer = new ClassWriter(reader, 0);
            reader.accept(new Replacer(writer), 0);
            return writer.toByteArray();
        }

        return basicClass;
    }

    public static class Replacer extends ClassVisitor {
        public Replacer(ClassWriter cw) {
            super(ASM5, cw);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("updateMeta")) {
                return new MethodReplacer(super.visitMethod(ACC_PRIVATE, "updateMeta", "()V", null, null));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    public static class MethodReplacer extends MethodVisitor {
        public final MethodVisitor target;

        public MethodReplacer(MethodVisitor target) {
            super(ASM5);
            this.target = target;
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
            target.visitMaxs(5, 3);
        }

        @Override
        public void visitCode() {
            MethodVisitor methodVisitor = target;
            methodVisitor.visitCode();
            Label label0 = new Label();
            Label label1 = new Label();
            Label label2 = new Label();
            methodVisitor.visitTryCatchBlock(label0, label1, label2, "appeng/me/GridAccessException");
            Label label3 = new Label();
            methodVisitor.visitLabel(label3);
            methodVisitor.visitLineNumber(105, label3);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "appeng/tile/networking/TileController", "getProxy", "()Lappeng/me/helpers/AENetworkProxy;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "appeng/me/helpers/AENetworkProxy", "isReady", "()Z", false);
            Label label4 = new Label();
            methodVisitor.visitJumpInsn(IFNE, label4);
            Label label5 = new Label();
            methodVisitor.visitLabel(label5);
            methodVisitor.visitLineNumber(107, label5);
            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitLabel(label4);
            methodVisitor.visitLineNumber(110, label4);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitFieldInsn(GETSTATIC, "appeng/block/networking/BlockController$ControllerBlockState", "offline", "Lappeng/block/networking/BlockController$ControllerBlockState;");
            methodVisitor.visitVarInsn(ASTORE, 1);
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(114, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "appeng/tile/networking/TileController", "getProxy", "()Lappeng/me/helpers/AENetworkProxy;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "appeng/me/helpers/AENetworkProxy", "getEnergy", "()Lappeng/api/networking/energy/IEnergyGrid;", false);
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "appeng/api/networking/energy/IEnergyGrid", "isNetworkPowered", "()Z", true);
            methodVisitor.visitJumpInsn(IFEQ, label1);
            Label label6 = new Label();
            methodVisitor.visitLabel(label6);
            methodVisitor.visitLineNumber(116, label6);
            methodVisitor.visitFieldInsn(GETSTATIC, "appeng/block/networking/BlockController$ControllerBlockState", "online", "Lappeng/block/networking/BlockController$ControllerBlockState;");
            methodVisitor.visitVarInsn(ASTORE, 1);
            Label label7 = new Label();
            methodVisitor.visitLabel(label7);
            methodVisitor.visitLineNumber(118, label7);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "appeng/tile/networking/TileController", "getProxy", "()Lappeng/me/helpers/AENetworkProxy;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "appeng/me/helpers/AENetworkProxy", "getPath", "()Lappeng/api/networking/pathing/IPathingGrid;", false);
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "appeng/api/networking/pathing/IPathingGrid", "getControllerState", "()Lappeng/api/networking/pathing/ControllerState;", true);
            methodVisitor.visitFieldInsn(GETSTATIC, "appeng/api/networking/pathing/ControllerState", "CONTROLLER_ONLINE", "Lappeng/api/networking/pathing/ControllerState;");
            methodVisitor.visitJumpInsn(IF_ACMPNE, label1);
            Label label8 = new Label();
            methodVisitor.visitLabel(label8);
            methodVisitor.visitLineNumber(120, label8);
            methodVisitor.visitFieldInsn(GETSTATIC, "appeng/block/networking/BlockController$ControllerBlockState", "online", "Lappeng/block/networking/BlockController$ControllerBlockState;");
            methodVisitor.visitVarInsn(ASTORE, 1);
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(127, label1);
            methodVisitor.visitFrame(Opcodes.F_APPEND, 1, new Object[]{"appeng/block/networking/BlockController$ControllerBlockState"}, 0, null);
            Label label9 = new Label();
            methodVisitor.visitJumpInsn(GOTO, label9);
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLineNumber(124, label2);
            methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"appeng/me/GridAccessException"});
            methodVisitor.visitVarInsn(ASTORE, 2);
            Label label10 = new Label();
            methodVisitor.visitLabel(label10);
            methodVisitor.visitLineNumber(126, label10);
            methodVisitor.visitFieldInsn(GETSTATIC, "appeng/block/networking/BlockController$ControllerBlockState", "offline", "Lappeng/block/networking/BlockController$ControllerBlockState;");
            methodVisitor.visitVarInsn(ASTORE, 1);
            methodVisitor.visitLabel(label9);
            methodVisitor.visitLineNumber(129, label9);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "appeng/tile/networking/TileController", "field_174879_c", "Lnet/minecraft/util/math/BlockPos;");
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "appeng/tile/networking/TileController", "checkController", "(Lnet/minecraft/util/math/BlockPos;)Z", false);
            Label label11 = new Label();
            methodVisitor.visitJumpInsn(IFEQ, label11);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "appeng/tile/networking/TileController", "field_145850_b", "Lnet/minecraft/world/World;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "appeng/tile/networking/TileController", "field_174879_c", "Lnet/minecraft/util/math/BlockPos;");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/World", "func_180495_p", "(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;", false);
            methodVisitor.visitFieldInsn(GETSTATIC, "appeng/block/networking/BlockController", "CONTROLLER_STATE", "Lnet/minecraft/block/properties/PropertyEnum;");
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", "func_177229_b", "(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;", true);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitJumpInsn(IF_ACMPEQ, label11);
            Label label12 = new Label();
            methodVisitor.visitLabel(label12);
            methodVisitor.visitLineNumber(131, label12);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "appeng/tile/networking/TileController", "field_145850_b", "Lnet/minecraft/world/World;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "appeng/tile/networking/TileController", "field_174879_c", "Lnet/minecraft/util/math/BlockPos;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "appeng/tile/networking/TileController", "field_145850_b", "Lnet/minecraft/world/World;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "appeng/tile/networking/TileController", "field_174879_c", "Lnet/minecraft/util/math/BlockPos;");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/World", "func_180495_p", "(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;", false);
            methodVisitor.visitFieldInsn(GETSTATIC, "appeng/block/networking/BlockController", "CONTROLLER_STATE", "Lnet/minecraft/block/properties/PropertyEnum;");
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", "func_177226_a", "(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;", true);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/World", "func_175656_a", "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z", false);
            methodVisitor.visitInsn(POP);
            methodVisitor.visitLabel(label11);
            methodVisitor.visitLineNumber(134, label11);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitInsn(RETURN);
            Label label13 = new Label();
            methodVisitor.visitLabel(label13);
            methodVisitor.visitLocalVariable("e", "Lappeng/me/GridAccessException;", null, label10, label9, 2);
            methodVisitor.visitLocalVariable("this", "Lappeng/tile/networking/TileController;", null, label3, label13, 0);
            methodVisitor.visitLocalVariable("metaState", "Lappeng/block/networking/BlockController$ControllerBlockState;", null, label0, label13, 1);
        }

        @Override
        public void visitEnd() {
            target.visitEnd();
        }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            return target.visitAnnotation(desc, visible);
        }

        @Override
        public void visitParameter(String name, int access) {
            target.visitParameter(name, access);
        }
    }
}
