package ru.DmN.AE2AO.core;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

public class AE2AOTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (name.equals("appeng.tile.networking.TileController")) {
            ClassReader reader = new ClassReader(basicClass);
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES);
            reader.accept(new Replacer0(writer), 0);
            return writer.toByteArray();
        } else if (name.equals("appeng.me.GridNode")) {
            ClassReader reader = new ClassReader(basicClass);
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES);
            reader.accept(new Replacer1(writer), 0);
            return writer.toByteArray();
        }

        return basicClass;
    }

    public static class Replacer1 extends ClassVisitor {
        public Replacer1(ClassWriter cw) {
            super(ASM5, cw);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("getMaxChannels")) {
                System.out.println("[AE2AO] " + name);
                return new MethodReplacer1(super.visitMethod(ACC_PRIVATE, name, desc, null, null));
            } else if (name.equals("getLastUsedChannels") | name.equals("usedChannels")) {
                System.out.println("[AE2AO] " + name);
                return new MethodReplacer2(super.visitMethod(ACC_PUBLIC, name, desc, null, null));
            } else if (name.equals("getUsedChannels")) {
                System.out.println("[AE2AO] " + name);
                return new MethodReplacer3(super.visitMethod(ACC_PRIVATE, name, desc, null, null));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    public static class MethodReplacer3 extends MethodVisitor {
        public final MethodVisitor target;

        public MethodReplacer3(MethodVisitor target) {
            super(ASM5);
            this.target = target;
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
            target.visitMaxs(1, 1);
        }

        @Override
        public void visitCode() {
            Label label0 = new Label();
            target.visitLabel(label0);
            target.visitLineNumber(46, label0);
            target.visitFieldInsn(GETSTATIC, "ru/DmN/AE2AO/Main", "LC", "Lru/DmN/AE2AO/Config;");
            target.visitFieldInsn(GETFIELD, "ru/DmN/AE2AO/Config", "DisableChannels", "Z");
            Label label1 = new Label();
            target.visitJumpInsn(IFEQ, label1);
            target.visitInsn(ICONST_1);
            Label label2 = new Label();
            target.visitJumpInsn(GOTO, label2);
            target.visitLabel(label1);
            target.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            target.visitVarInsn(ALOAD, 0);
            target.visitFieldInsn(GETFIELD, "appeng/me/GridNode", "usedChannels", "I");
            target.visitLabel(label2);
            target.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{Opcodes.INTEGER});
            target.visitInsn(IRETURN);
            Label label3 = new Label();
            target.visitLabel(label3);
            target.visitLocalVariable("this", "Lappeng/me/GridNode;", null, label0, label3, 0);
        }
    }

    public static class MethodReplacer2 extends MethodVisitor {
        public final MethodVisitor target;

        public MethodReplacer2(MethodVisitor target) {
            super(ASM5);
            this.target = target;
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
            target.visitMaxs(1, 1);
        }

        @Override
        public void visitCode() {
            Label label0 = new Label();
            target.visitLabel(label0);
            target.visitLineNumber(46, label0);
            target.visitFieldInsn(GETSTATIC, "ru/DmN/AE2AO/Main", "LC", "Lru/DmN/AE2AO/Config;");
            target.visitFieldInsn(GETFIELD, "ru/DmN/AE2AO/Config", "DisableChannels", "Z");
            Label label1 = new Label();
            target.visitJumpInsn(IFEQ, label1);
            target.visitInsn(ICONST_1);
            Label label2 = new Label();
            target.visitJumpInsn(GOTO, label2);
            target.visitLabel(label1);
            target.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            target.visitVarInsn(ALOAD, 0);
            target.visitFieldInsn(GETFIELD, "appeng/me/GridNode", "lastUsedChannels", "I");
            target.visitLabel(label2);
            target.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{Opcodes.INTEGER});
            target.visitInsn(IRETURN);
            Label label3 = new Label();
            target.visitLabel(label3);
            target.visitLocalVariable("this", "Lappeng/me/GridNode;", null, label0, label3, 0);
        }
    }

    public static class MethodReplacer1 extends MethodVisitor {
        public final MethodVisitor target;

        public MethodReplacer1(MethodVisitor target) {
            super(ASM5);
            this.target = target;
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
            target.visitMaxs(3, 1);
        }

        @Override
        public void visitCode() {
            Label label0 = new Label();
            target.visitLabel(label0);
            target.visitLineNumber(47, label0);
            target.visitFieldInsn(GETSTATIC, "ru/DmN/AE2AO/Main", "LC", "Lru/DmN/AE2AO/Config;");
            target.visitFieldInsn(GETFIELD, "ru/DmN/AE2AO/Config", "DisableChannels", "Z");
            Label label1 = new Label();
            target.visitJumpInsn(IFEQ, label1);
            target.visitLdcInsn(2147483647);
            Label label2 = new Label();
            target.visitJumpInsn(GOTO, label2);
            target.visitLabel(label1);
            target.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            target.visitFieldInsn(GETSTATIC, "appeng/me/GridNode", "CHANNEL_COUNT", "[I");
            target.visitVarInsn(ALOAD, 0);
            target.visitFieldInsn(GETFIELD, "appeng/me/GridNode", "compressedData", "I");
            target.visitInsn(ICONST_3);
            target.visitInsn(IAND);
            target.visitInsn(IALOAD);
            target.visitLabel(label2);
            target.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{Opcodes.INTEGER});
            target.visitInsn(IRETURN);
            Label label3 = new Label();
            target.visitLabel(label3);
            target.visitLocalVariable("this", "Lappeng/me/GridNode;", null, label0, label3, 0);
        }
    }

    public static class Replacer0 extends ClassVisitor {
        public Replacer0(ClassWriter cw) {
            super(ASM5, cw);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("updateMeta")) {
                System.out.println("[AE2AO] " + name);
                return new MethodReplacer0(super.visitMethod(ACC_PRIVATE, "updateMeta", "()V", null, null));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    public static class MethodReplacer0 extends MethodVisitor {
        public final MethodVisitor target;

        public MethodReplacer0(MethodVisitor target) {
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
            methodVisitor.visitLineNumber(87, label3);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "appeng/tile/networking/TileController", "getProxy", "()Lappeng/me/helpers/AENetworkProxy;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "appeng/me/helpers/AENetworkProxy", "isReady", "()Z", false);
            Label label4 = new Label();
            methodVisitor.visitJumpInsn(IFNE, label4);
            Label label5 = new Label();
            methodVisitor.visitLabel(label5);
            methodVisitor.visitLineNumber(89, label5);
            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitLabel(label4);
            methodVisitor.visitLineNumber(92, label4);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitFieldInsn(GETSTATIC, "appeng/block/networking/BlockController$ControllerBlockState", "offline", "Lappeng/block/networking/BlockController$ControllerBlockState;");
            methodVisitor.visitVarInsn(ASTORE, 1);
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(96, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "appeng/tile/networking/TileController", "getProxy", "()Lappeng/me/helpers/AENetworkProxy;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "appeng/me/helpers/AENetworkProxy", "getEnergy", "()Lappeng/api/networking/energy/IEnergyGrid;", false);
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "appeng/api/networking/energy/IEnergyGrid", "isNetworkPowered", "()Z", true);
            methodVisitor.visitJumpInsn(IFEQ, label1);
            Label label6 = new Label();
            methodVisitor.visitLabel(label6);
            methodVisitor.visitLineNumber(98, label6);
            methodVisitor.visitFieldInsn(GETSTATIC, "appeng/block/networking/BlockController$ControllerBlockState", "online", "Lappeng/block/networking/BlockController$ControllerBlockState;");
            methodVisitor.visitVarInsn(ASTORE, 1);
            Label label7 = new Label();
            methodVisitor.visitLabel(label7);
            methodVisitor.visitLineNumber(100, label7);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "appeng/tile/networking/TileController", "getProxy", "()Lappeng/me/helpers/AENetworkProxy;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "appeng/me/helpers/AENetworkProxy", "getPath", "()Lappeng/api/networking/pathing/IPathingGrid;", false);
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "appeng/api/networking/pathing/IPathingGrid", "getControllerState", "()Lappeng/api/networking/pathing/ControllerState;", true);
            methodVisitor.visitFieldInsn(GETSTATIC, "appeng/api/networking/pathing/ControllerState", "CONTROLLER_CONFLICT", "Lappeng/api/networking/pathing/ControllerState;");
            methodVisitor.visitJumpInsn(IF_ACMPNE, label1);
            Label label8 = new Label();
            methodVisitor.visitLabel(label8);
            methodVisitor.visitLineNumber(102, label8);
            methodVisitor.visitFieldInsn(GETSTATIC, "ru/DmN/AE2AO/Main", "LC", "Lru/DmN/AE2AO/Config;");
            methodVisitor.visitFieldInsn(GETFIELD, "ru/DmN/AE2AO/Config", "ControllerLimits", "Z");
            Label label9 = new Label();
            methodVisitor.visitJumpInsn(IFEQ, label9);
            methodVisitor.visitFieldInsn(GETSTATIC, "appeng/block/networking/BlockController$ControllerBlockState", "conflicted", "Lappeng/block/networking/BlockController$ControllerBlockState;");
            Label label10 = new Label();
            methodVisitor.visitJumpInsn(GOTO, label10);
            methodVisitor.visitLabel(label9);
            methodVisitor.visitFrame(Opcodes.F_APPEND, 1, new Object[]{"appeng/block/networking/BlockController$ControllerBlockState"}, 0, null);
            methodVisitor.visitFieldInsn(GETSTATIC, "appeng/block/networking/BlockController$ControllerBlockState", "online", "Lappeng/block/networking/BlockController$ControllerBlockState;");
            methodVisitor.visitLabel(label10);
            methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"appeng/block/networking/BlockController$ControllerBlockState"});
            methodVisitor.visitVarInsn(ASTORE, 1);
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(109, label1);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            Label label11 = new Label();
            methodVisitor.visitJumpInsn(GOTO, label11);
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLineNumber(106, label2);
            methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"appeng/me/GridAccessException"});
            methodVisitor.visitVarInsn(ASTORE, 2);
            Label label12 = new Label();
            methodVisitor.visitLabel(label12);
            methodVisitor.visitLineNumber(108, label12);
            methodVisitor.visitFieldInsn(GETSTATIC, "appeng/block/networking/BlockController$ControllerBlockState", "offline", "Lappeng/block/networking/BlockController$ControllerBlockState;");
            methodVisitor.visitVarInsn(ASTORE, 1);
            methodVisitor.visitLabel(label11);
            methodVisitor.visitLineNumber(111, label11);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "appeng/tile/networking/TileController", "field_174879_c", "Lnet/minecraft/util/math/BlockPos;");
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "appeng/tile/networking/TileController", "checkController", "(Lnet/minecraft/util/math/BlockPos;)Z", false);
            Label label13 = new Label();
            methodVisitor.visitJumpInsn(IFEQ, label13);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "appeng/tile/networking/TileController", "field_145850_b", "Lnet/minecraft/world/World;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "appeng/tile/networking/TileController", "field_174879_c", "Lnet/minecraft/util/math/BlockPos;");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/World", "func_180495_p", "(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;", false);
            methodVisitor.visitFieldInsn(GETSTATIC, "appeng/block/networking/BlockController", "CONTROLLER_STATE", "Lnet/minecraft/block/properties/PropertyEnum;");
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", "func_177229_b", "(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;", true);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitJumpInsn(IF_ACMPEQ, label13);
            Label label14 = new Label();
            methodVisitor.visitLabel(label14);
            methodVisitor.visitLineNumber(113, label14);
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
            methodVisitor.visitLabel(label13);
            methodVisitor.visitLineNumber(116, label13);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitInsn(RETURN);
            Label label15 = new Label();
            methodVisitor.visitLabel(label15);
            methodVisitor.visitLocalVariable("e", "Lappeng/me/GridAccessException;", null, label12, label11, 2);
            methodVisitor.visitLocalVariable("this", "Lappeng/tile/networking/TileController;", null, label3, label15, 0);
            methodVisitor.visitLocalVariable("metaState", "Lappeng/block/networking/BlockController$ControllerBlockState;", null, label0, label15, 1);
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
